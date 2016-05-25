package com.postnov.android.yaschedule.api;

import com.postnov.android.yaschedule.data.entity.schedule.Response;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by platon on 19.05.2016.
 */
public interface ScheduleApi
{
    @GET("search/")
    Observable<Response> search(@QueryMap Map<String, String> options);
}
