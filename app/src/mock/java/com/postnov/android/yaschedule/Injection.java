package com.postnov.android.yaschedule;

import com.postnov.android.yaschedule.data.FakeRemoteDataSource;
import com.postnov.android.yaschedule.data.source.DataSource;

/**
 * Created by platon on 20.05.2016.
 */
public class Injection
{
    public static DataSource provideDataSource()
    {
        return new FakeRemoteDataSource();
    }
}
