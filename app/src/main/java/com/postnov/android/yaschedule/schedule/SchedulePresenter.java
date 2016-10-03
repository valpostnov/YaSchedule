package com.postnov.android.yaschedule.schedule;

import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.data.source.recent.IRecentDataSource;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.schedule.interfaces.ISchedulePresenter;
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
public class SchedulePresenter implements ISchedulePresenter {
    private final IScheduleDataSource scheduleDataSource;
    private final IRecentDataSource recentDataSource;
    private ScheduleView scheduleView;
    private CompositeSubscription subscription;
    private NetworkManager networkManager;

    public SchedulePresenter(IScheduleDataSource sds, IRecentDataSource rds, NetworkManager nm) {
        scheduleDataSource = sds;
        recentDataSource = rds;
        networkManager = nm;
        subscription = new CompositeSubscription();
    }

    @Override
    public void search(Map<String, String> options) {
        if (networkManager.networkIsAvailable()) {
            scheduleView.showProgressDialog();
            subscription.add(createSearchSubscription(options));
            return;
        }

        scheduleView.showError(new NetworkConnectionException(Const.ERROR_NO_CONNECTION));
    }

    @Override
    public void bind(ScheduleView view) {
        scheduleView = view;
    }

    @Override
    public void unbind() {
        subscription.clear();
        scheduleView = null;
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
                .subscribe(response -> scheduleView.showList(response), onError);
    }

    private Action1<Throwable> onError = e -> {
        scheduleView.hideProgressDialog();
        scheduleView.showList(null);
        scheduleView.showError(e);
    };
}
