package com.postnov.android.yaschedule.data;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.SearchQueryBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import rx.observers.TestSubscriber;

public class TestScheduleRemoteDataSource
{
    private static final String FROM = "c23243";    //нино
    private static final String TO = "l96";
    private static final String DATE = "2016-06-02";

    private IScheduleDataSource mRemoteDataSource;
    private Map<String, String> mOptions;

    @Before
    public void setupDataSource()
    {
        mOptions = SearchQueryBuilder.builder()
                .setFrom(FROM)
                .setTo(TO)
                .setLang(Const.LANG_RU)
                .setPage(1)
                .setDate(DATE)
                .build();

        mRemoteDataSource = Injection.provideScheduleDataSource();
    }

    @Test
    public void testSearch() throws Exception
    {
        TestSubscriber<Response> subscriber = new TestSubscriber<>();
        mRemoteDataSource.search(mOptions).subscribe(subscriber);
        subscriber.assertNoErrors();
    }
}
