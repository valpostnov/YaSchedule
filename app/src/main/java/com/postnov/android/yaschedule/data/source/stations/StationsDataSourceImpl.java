package com.postnov.android.yaschedule.data.source.stations;

import com.postnov.android.yaschedule.api.StationsApi;
import com.postnov.android.yaschedule.data.entity.stations.Stops;
import com.postnov.android.yaschedule.utils.Const;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by platon on 31.05.2016.
 */
public class StationsDataSourceImpl implements IStationsDataSource {
    private StationsApi stationsApi;

    public StationsDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        stationsApi = retrofit.create(StationsApi.class);
    }

    @Override
    public Observable<Stops> getStops(Map<String, String> options) {
        return stationsApi.stationList(options);
    }
}
