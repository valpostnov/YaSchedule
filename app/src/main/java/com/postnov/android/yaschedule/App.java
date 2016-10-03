package com.postnov.android.yaschedule;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.postnov.android.yaschedule.data.source.codes.CodesRemoteDataSource;
import com.postnov.android.yaschedule.data.source.codes.ICodesDataSource;
import com.postnov.android.yaschedule.data.source.recent.IRecentDataSource;
import com.postnov.android.yaschedule.data.source.recent.RecentLocalDataSource;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.data.source.schedule.ScheduleRemoteDataSource;
import com.postnov.android.yaschedule.data.source.stations.IStationsDataSource;
import com.postnov.android.yaschedule.data.source.stations.StationsDataSourceImpl;
import com.postnov.android.yaschedule.utils.INetworkManager;
import com.postnov.android.yaschedule.utils.NetworkManager;
import com.yandex.metrica.YandexMetrica;

/**
 * Created by platon on 22.09.2016.
 */

public class App extends Application {

    private IRecentDataSource recentDataSource;
    private IScheduleDataSource scheduleDataSource;
    private ICodesDataSource codesDataSource;
    private IStationsDataSource stationsDataSource;
    private INetworkManager networkManager;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static App get(Fragment fragment) {
        return (App) fragment.getContext().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            YandexMetrica.activate(getApplicationContext(), BuildConfig.METRICA_KEY);
            YandexMetrica.enableActivityAutoTracking(this);
        }

        networkManager = new NetworkManager(this);
        recentDataSource = new RecentLocalDataSource(this);
        scheduleDataSource = new ScheduleRemoteDataSource();
        codesDataSource = new CodesRemoteDataSource();
        stationsDataSource = new StationsDataSourceImpl();
    }

    public IRecentDataSource recentDataSource() {
        return recentDataSource;
    }

    public IScheduleDataSource scheduleDataSource() {
        return scheduleDataSource;
    }

    public ICodesDataSource codesDataSource() {
        return codesDataSource;
    }

    public IStationsDataSource stationsDataSource() {
        return stationsDataSource;
    }

    public INetworkManager networkManager() {
        return networkManager;
    }
}
