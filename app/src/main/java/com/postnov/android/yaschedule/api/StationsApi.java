package com.postnov.android.yaschedule.api;

import com.postnov.android.yaschedule.data.entity.stations.Stops;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by platon on 31.05.2016.
 */
public interface StationsApi {
    @GET("thread/")
    Observable<Stops> stationList(@QueryMap Map<String, String> options);
}
