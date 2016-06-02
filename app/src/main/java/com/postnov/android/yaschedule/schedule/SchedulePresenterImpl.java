package com.postnov.android.yaschedule.schedule;

import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.schedule.interfaces.SchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 21.05.2016.
 */
public class SchedulePresenterImpl implements SchedulePresenter
{
    private IScheduleDataSource mDataSource;
    private ScheduleView mView;
    private CompositeSubscription mSubscriptions;

    public SchedulePresenterImpl(IScheduleDataSource dataSource)
    {
        mDataSource = dataSource;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void search(Map<String, String> options)
    {
        mView.showProgressDialog();

        Subscription subscription = mDataSource.search(options)
                .subscribeOn(Schedulers.io())
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

        mSubscriptions.add(subscription);
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
}
