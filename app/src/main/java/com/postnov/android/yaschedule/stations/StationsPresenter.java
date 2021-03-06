package com.postnov.android.yaschedule.stations;

import com.postnov.android.yaschedule.data.source.stations.IStationsDataSource;
import com.postnov.android.yaschedule.stations.interfaces.IStationsPresenter;
import com.postnov.android.yaschedule.stations.interfaces.IStationsView;

import java.util.Map;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by platon on 31.05.2016.
 */
public class StationsPresenter implements IStationsPresenter {
    private final IStationsDataSource stationsDataSource;
    private IStationsView stationsView;
    private Subscription subscription;

    public StationsPresenter(IStationsDataSource stationsDataSource) {
        this.stationsDataSource = stationsDataSource;
    }

    @Override
    public void fetchStations(final Map<String, String> query) {
        stationsView.showProgressDialog();
        subscription = stationsDataSource.getStops(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thread -> {
                    stationsView.hideProgressDialog();
                    stationsView.loadStations(thread.getStops());
                }, onError);
    }

    @Override
    public void bind(IStationsView view) {
        stationsView = view;
    }

    @Override
    public void unbind() {
        subscription.unsubscribe();
        stationsView = null;
    }

    private Action1<Throwable> onError = e -> {
        stationsView.hideProgressDialog();
        stationsView.loadStations(null);
        stationsView.showError(e);
    };
}
