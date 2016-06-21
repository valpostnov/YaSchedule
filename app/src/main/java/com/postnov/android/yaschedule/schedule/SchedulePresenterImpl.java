package com.postnov.android.yaschedule.schedule;

import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.data.source.recent.IRecentDataSource;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.schedule.interfaces.SchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.NetworkManager;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionError;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 21.05.2016.
 */
public class SchedulePresenterImpl implements SchedulePresenter
{
    private IScheduleDataSource mDataSource;
    private IRecentDataSource mFaveDataSource;
    private ScheduleView mView;
    private CompositeSubscription mSubscriptions;
    private NetworkManager mNetworkManager;

    public SchedulePresenterImpl(IScheduleDataSource dataSource,
                                 IRecentDataSource faveDataSource,
                                 NetworkManager networkManager)
    {
        mDataSource = dataSource;
        mFaveDataSource = faveDataSource;
        mSubscriptions = new CompositeSubscription();
        mNetworkManager = networkManager;
    }

    @Override
    public void search(Map<String, String> options)
    {
        if (mNetworkManager.networkIsAvailable())
        {
            mView.showProgressDialog();
            mSubscriptions.add(createSearchSubscription(options));
            return;
        }

        mView.showError(new NetworkConnectionError(Const.ERROR_NO_CONNECTION));
    }

    @Override
    public void bind(ScheduleView view)
    {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    private void saveSearchRequest(Response response)
    {
        RecentRoute route = new RecentRoute();
        route.setFrom(response.getSearch().getFrom().getTitle());
        route.setTo(response.getSearch().getTo().getTitle());
        route.setFromCode(response.getSearch().getFrom().getCode());
        route.setToCode(response.getSearch().getTo().getCode());

        mFaveDataSource.save(route);
    }

    private Subscription createSearchSubscription(Map<String, String> options)
    {
        return mDataSource.search(options)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Response>()
                {
                    @Override
                    public void call(Response response)
                    {
                        saveSearchRequest(response);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>()
                {
                    @Override
                    public void onCompleted()
                    {
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mView.hideProgressDialog();
                        mView.showList(null);
                        mView.showError(e);
                    }

                    @Override
                    public void onNext(Response response)
                    {
                        mView.showList(response);
                    }
                });
    }
}
