package com.postnov.android.yaschedule.data.source.schedule;

import com.postnov.android.yaschedule.api.ScheduleApi;
import com.postnov.android.yaschedule.data.entity.schedule.Response;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by platon on 20.05.2016.
 */
public class ScheduleRemoteDataSource implements IScheduleDataSource
{
    private static final String ENDPOINT = "https://api.rasp.yandex.net/v1.0/";
    private ScheduleApi mApi;

    public ScheduleRemoteDataSource()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApi = retrofit.create(ScheduleApi.class);
    }
    @Override
    public Observable<Response> search(Map<String, String> options) {
        return mApi.search(options);
    }
}
