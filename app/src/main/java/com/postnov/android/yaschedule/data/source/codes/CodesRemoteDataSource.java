package com.postnov.android.yaschedule.data.source.codes;

import com.postnov.android.yaschedule.api.CityCodesApi;
import com.postnov.android.yaschedule.data.entity.codes.CityCodes;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by platon on 24.05.2016.
 */
public class CodesRemoteDataSource implements ICodesDataSource
{
    private static final String ENDPOINT = "https://suggests.rasp.yandex.net/";
    private CityCodesApi mApi;

    public CodesRemoteDataSource()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApi = retrofit.create(CityCodesApi.class);
    }

    @Override
    public Observable<CityCodes> getList(String city, String limit)
    {
        return mApi.fetchCodes(city, limit);
    }
}
