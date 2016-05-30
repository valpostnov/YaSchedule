package com.postnov.android.yaschedule.detailschedule;

import com.postnov.android.yaschedule.data.entity.schedule.Route;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.detailschedule.interfaces.DetailSchedulePresenter;
import com.postnov.android.yaschedule.detailschedule.interfaces.DetailScheduleView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by platon on 29.05.2016.
 */
public class DetailSchedulePresenterImpl implements DetailSchedulePresenter
{
    private DetailScheduleView mView;
    private IScheduleDataSource mLocalDataSource;
    private IScheduleDataSource mRemoteDataSource;
    private Subscription mSubscription;

    public DetailSchedulePresenterImpl(IScheduleDataSource localDataSource, IScheduleDataSource remoteDataSource)
    {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    @Override
    public void fetchSchedule(int position)
    {
        mSubscription = mRemoteDataSource
                .getRoute(position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Route>()
                {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e)
                    {
                        mView.showError(e);
                    }

                    @Override
                    public void onNext(Route route)
                    {
                        mView.loadSchedule(route);
                    }
                });
    }

    @Override
    public void save(Route route)
    {
        mLocalDataSource.save(route);
    }

    @Override
    public void bind(DetailScheduleView view) {
        mView = view;
    }

    @Override
    public void unbind() {
        mView = null;
    }

    @Override
    public void unsubscribe()
    {
        mSubscription.unsubscribe();
    }
}
