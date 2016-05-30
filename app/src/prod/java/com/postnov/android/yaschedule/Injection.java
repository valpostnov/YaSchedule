package com.postnov.android.yaschedule;

import com.postnov.android.yaschedule.data.source.codes.CodesRemoteDataSource;
import com.postnov.android.yaschedule.data.source.codes.ICodesDataSource;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.data.source.schedule.remote.ScheduleRemoteDataSource;

/**
 * Created by platon on 20.05.2016.
 */
public class Injection
{
    public static IScheduleDataSource provideScheduleDataSource()
    {
        return ScheduleRemoteDataSource.getInstance();
    }

    public static ICodesDataSource provideCodesDataSource()
    {
        return new CodesRemoteDataSource();
    }
}
