package com.postnov.android.yaschedule.api;

import com.postnov.android.yaschedule.data.entity.codes.CityCodes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by platon on 24.05.2016.
 */
public interface CityCodesApi
{
    @GET("/all_suggests")
    Observable<CityCodes> fetchCodes(@Query("query") String city, @Query("limit") String limit);
}
