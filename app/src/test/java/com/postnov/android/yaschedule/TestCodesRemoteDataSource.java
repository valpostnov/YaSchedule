package com.postnov.android.yaschedule;

import com.postnov.android.yaschedule.data.entity.codes.CityCodes;
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
        mRemoteDataSource = Injection.provideCodesDataSource();
    }

    @Test
    public void testFetchCodes() throws Exception
    {
        TestSubscriber<CityCodes> subscriber = new TestSubscriber<>();
        mRemoteDataSource.getList(CITY, LIMIT).subscribe(subscriber);
        subscriber.assertNoErrors();
    }
}