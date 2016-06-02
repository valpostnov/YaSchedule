package com.postnov.android.yaschedule.data;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.data.entity.stations.Thread;
import com.postnov.android.yaschedule.data.source.stations.IStationsDataSource;
import com.postnov.android.yaschedule.utils.StationsQueryBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import rx.observers.TestSubscriber;

public class TestStationsRemoteDataSource
{
    private Map<String, String> mOptions;
    private IStationsDataSource mRemoteDataSource;

    @Before
    public void setupDataSource()
    {
        mOptions = StationsQueryBuilder
                .builder()
                .setDate("2016-6-4")
                .setUID("075YE_12_2")
                .setLang("ru")
                .build();

        mRemoteDataSource = Injection.provideStationsDataSource();
    }

    @Test
    public void testFetchStations() throws Exception
    {
        TestSubscriber<Thread> subscriber = new TestSubscriber<>();
        mRemoteDataSource.stationList(mOptions).subscribe(subscriber);
        subscriber.assertNoErrors();
    }
}