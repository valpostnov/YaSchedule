package com.postnov.android.yaschedule.data.source.stations;

import com.postnov.android.yaschedule.data.entity.stations.Stops;

import java.util.Map;

import rx.Observable;

/**
 * Created by platon on 31.05.2016.
 */
public interface IStationsDataSource {
    Observable<Stops> getStops(Map<String, String> options);
}
