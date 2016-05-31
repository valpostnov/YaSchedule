package com.postnov.android.yaschedule.stations.interfaces;

import com.postnov.android.yaschedule.base.BasePresenter;

/**
 * Created by platon on 29.05.2016.
 */
public interface StationsPresenter extends BasePresenter<StationsView>
{
    void fetchStations(String id);
}
