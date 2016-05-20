package com.postnov.android.yaschedule.data.source;

import com.postnov.android.yaschedule.data.entity.Response;

import java.util.Map;

import rx.Observable;

/**
 * Created by platon on 20.05.2016.
 */
public interface DataSource
{
    Observable<Response> search(Map<String, String> options);
}
