package com.postnov.android.yaschedule.data.source.schedule;

import com.postnov.android.yaschedule.data.entity.schedule.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by platon on 20.05.2016.
 */
public interface IScheduleDataSource {
    Observable<Response> search(Map<String, String> options);
}
