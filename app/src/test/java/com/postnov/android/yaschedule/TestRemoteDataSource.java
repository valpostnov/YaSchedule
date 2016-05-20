package com.postnov.android.yaschedule;

import com.postnov.android.yaschedule.data.entity.Response;
import com.postnov.android.yaschedule.data.source.RemoteDataSource;
import com.postnov.android.yaschedule.utils.SearchQuery;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import rx.observers.TestSubscriber;

/**
 * ToStation work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TestRemoteDataSource
{
    private static final String FROM = "c23243"; //нино
    private static final String TO = "c213";     //москва
    private static final String DATE = "2016-05-26";
    private static final String API_KEY = "7242ce30-c8da-4a49-af04-f6853753989d";

    private RemoteDataSource mRemoteDataSource;
    private Response mResponse;
    private Map<String, String> mOptions;

    @Before
    public void setupDataSource()
    {
        mOptions = SearchQuery.builder()
                .setApiKey(API_KEY)
                .setFormat(SearchQuery.FORMAT_JSON)
                .setFrom(FROM)
                .setTo(TO)
                .setDate(DATE)
                .build();

        mRemoteDataSource = new RemoteDataSource();
    }

    @Test
    public void testSearch() throws Exception
    {
        TestSubscriber<Response> subscriber = new TestSubscriber<>();
        mRemoteDataSource.search(mOptions).subscribe(subscriber);
        subscriber.assertNoErrors();
        mResponse = subscriber.getOnNextEvents().get(0);
    }
}