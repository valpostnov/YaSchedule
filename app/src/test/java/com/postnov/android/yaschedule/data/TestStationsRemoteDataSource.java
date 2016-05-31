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
                .setUID("empty_2_f9654609t9633000_413")
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