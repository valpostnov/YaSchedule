package com.postnov.android.yaschedule.schedule;

import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.data.source.recent.IRecentDataSource;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.schedule.interfaces.SchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.NetworkManager;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionException;

import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 21.05.2016.
 */
public class SchedulePresenterImpl implements SchedulePresenter {
    private final IScheduleDataSource scheduleDataSource;
    private final IRecentDataSource recentDataSource;
    private ScheduleView mView;
    private CompositeSubscription mSubscriptions;
    private NetworkManager mNetworkManager; //Todo init!!!

    public SchedulePresenterImpl(IScheduleDataSource scheduleDataSource,
                                 IRecentDataSource recentDataSource) {
        this.scheduleDataSource = scheduleDataSource;
        this.recentDataSource = recentDataSource;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void search(Map<String, String> options) {
        if (mNetworkManager.networkIsAvailable()) {
            mView.showProgressDialog();
            mSubscriptions.add(createSearchSubscription(options));
            return;
        }

        mView.showError(new NetworkConnectionException(Const.ERROR_NO_CONNECTION));
    }

    @Override
    public void bind(ScheduleView view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mSubscriptions.clear();
        mView = null;
    }

    private void saveSearchRequest(Response response) {
        RecentRoute route = new RecentRoute();
        route.setFrom(response.getSearch().getFrom().getTitle());
        route.setTo(response.getSearch().getTo().getTitle());
        route.setFromCode(response.getSearch().getFrom().getCode());
        route.setToCode(response.getSearch().getTo().getCode());

        recentDataSource.save(route);
    }

    private Subscription createSearchSubscription(Map<String, String> options) {
        return scheduleDataSource.search(options)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::saveSearchRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> mView.showList(response), onError);
    }

    private Action1<Throwable> onError = e -> {
        mView.hideProgressDialog();
        mView.showList(null);
        mView.showError(e);
    };
}
