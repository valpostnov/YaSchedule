package com.postnov.android.yaschedule.data;

import com.postnov.android.yaschedule.data.entity.codes.Cities;
import com.postnov.android.yaschedule.data.source.codes.CodesRemoteDataSource;
import com.postnov.android.yaschedule.data.source.codes.ICodesDataSource;

import org.junit.Before;
import org.junit.Test;

import rx.observers.TestSubscriber;

public class TestCodesRemoteDataSource
{
    private static final String CITY = "Москва";
    private static final String LIMIT = "5";

    private ICodesDataSource mRemoteDataSource;

    @Before
    public void setupDataSource()
    {
        mRemoteDataSource = new CodesRemoteDataSource();
    }

    @Test
    public void testFetchCodes() throws Exception
    {
        TestSubscriber<Cities> subscriber = new TestSubscriber<>();
        mRemoteDataSource.getCities(CITY, LIMIT).subscribe(subscriber);
        subscriber.assertNoErrors();
    }
}