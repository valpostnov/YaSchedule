package com.postnov.android.yaschedule.stations;

import com.postnov.android.yaschedule.data.entity.stations.Thread;
import com.postnov.android.yaschedule.data.source.stations.IStationsDataSource;
import com.postnov.android.yaschedule.stations.interfaces.StationsPresenter;
import com.postnov.android.yaschedule.stations.interfaces.StationsView;

import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by platon on 31.05.2016.
 */
public class StationsPresenterImpl implements StationsPresenter
{
    private IStationsDataSource mDataSource;
    private StationsView mView;
    private Subscription mSubscription;

    public StationsPresenterImpl(IStationsDataSource dataSource)
    {
        mDataSource = dataSource;
    }

    @Override
    public void fetchStations(final Map<String, String> query)
    {
        mView.showProgressDialog();
        mSubscription = mDataSource
                .stationList(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Thread>()
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
                        mView.loadStations(null);
                        mView.showError(e);
                    }

                    @Override
                    public void onNext(Thread thread)
                    {
                        mView.loadStations(thread.getStops());
                    }
                });
    }

    @Override
    public void bind(StationsView view)
    {
        mView = view;
    }

    @Override
    public void unbind()
    {
        mView = null;
    }

    @Override
    public void unsubscribe()
    {
        mSubscription.unsubscribe();
    }
}
