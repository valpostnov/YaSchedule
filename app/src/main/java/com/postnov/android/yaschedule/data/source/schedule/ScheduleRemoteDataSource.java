package com.postnov.android.yaschedule.data.source.schedule;

import com.postnov.android.yaschedule.api.ScheduleApi;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.utils.Const;

import java.lang.ref.SoftReference;
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
    private ScheduleApi mApi;
    private SoftReference<Response> mCachedResponse;
    private Map<String, String> mCachedOptions;

    public ScheduleRemoteDataSource()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApi = retrofit.create(ScheduleApi.class);
    }
    @Override
    public Observable<Response> search(final Map<String, String> options)
    {
        if (mCachedResponse != null && mCachedOptions.equals(options))
        {
            return Observable.just(mCachedResponse.get());
        }

        return mApi.search(options).doOnNext(response -> {
            mCachedResponse = new SoftReference<>(response);
            mCachedOptions = options;
        });
    }
}
