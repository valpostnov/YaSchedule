package com.postnov.android.yaschedule.data.source.schedule;

import com.postnov.android.yaschedule.api.ScheduleApi;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.utils.Const;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by platon on 20.05.2016.
 */
public class ScheduleRemoteDataSource implements IScheduleDataSource
{
    private static ScheduleRemoteDataSource sDataSource;

    private ScheduleApi mApi;
    private Response mResponse;
    private Map<String, String> mCachedOptions;

    public static ScheduleRemoteDataSource getInstance()
    {
        if (sDataSource == null)
        {
            sDataSource = new ScheduleRemoteDataSource();
            return sDataSource;
        }

        return sDataSource;
    }

    private ScheduleRemoteDataSource()
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
        if (mResponse != null && mCachedOptions.equals(options))
        {
            return Observable.just(mResponse);
        }

        return mApi.search(options).doOnNext(new Action1<Response>()
        {
            @Override
            public void call(Response response) {
                mResponse = response;
                mCachedOptions = options;
            }
        });
    }
}
