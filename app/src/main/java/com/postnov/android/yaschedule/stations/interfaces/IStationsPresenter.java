package com.postnov.android.yaschedule.stations.interfaces;

import com.postnov.android.yaschedule.base.BasePresenter;

import java.util.Map;

/**
 * Created by platon on 29.05.2016.
 */
public interface IStationsPresenter extends BasePresenter<IStationsView>
{
    void fetchStations(Map<String, String> query);
}
