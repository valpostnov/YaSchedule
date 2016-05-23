package com.postnov.android.yaschedule;

import com.postnov.android.yaschedule.data.entity.Response;
import com.postnov.android.yaschedule.data.source.DataSource;
import com.postnov.android.yaschedule.data.source.RemoteDataSource;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.SearchQuery;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import rx.observers.TestSubscriber;

public class TestRemoteDataSource
{
    private static final String FROM = "c23243"; //нино
    private static final String TO = "c213";     //москва
    private static final String DATE = "2016-05-26";

    private DataSource mRemoteDataSource;
    private Response mResponse;
    private Map<String, String> mOptions;

    @Before
    public void setupDataSource()
    {
        mOptions = SearchQuery.builder()
                .setApiKey(Const.API_KEY)
                .setFormat(SearchQuery.FORMAT_JSON)
                .setFrom(FROM)
                .setTo(TO)
                .setDate(DATE)
                .build();

        mRemoteDataSource = Injection.provideDataSource();
    }

    @Test
    public void testSearch() throws Exception
    {
        TestSubscriber<Response> subscriber = new TestSubscriber<>();
        mRemoteDataSource.search(mOptions).subscribe(subscriber);
        subscriber.assertNoErrors();
    }
}