package com.postnov.android.yaschedule;

import com.postnov.android.yaschedule.api.ScheduleApi;
import com.postnov.android.yaschedule.data.source.DataSource;
import com.postnov.android.yaschedule.data.source.RemoteDataSource;

/**
 * Created by platon on 20.05.2016.
 */
public class Injection
{
    public static DataSource provideDataSource()
    {
        return new RemoteDataSource();
    }
}
