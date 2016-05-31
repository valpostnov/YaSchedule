package com.postnov.android.yaschedule.stations.interfaces;

import com.postnov.android.yaschedule.base.BaseView;
import com.postnov.android.yaschedule.data.entity.stations.Stop;

import java.util.List;

/**
 * Created by platon on 29.05.2016.
 */
public interface StationsView extends BaseView
{
    void loadStations(List<Stop> stopList);
}
