package com.postnov.android.yaschedule.data.source.codes;

import com.postnov.android.yaschedule.data.entity.codes.Cities;

import rx.Observable;

/**
 * Created by platon on 24.05.2016.
 */
public interface ICodesDataSource
{
    Observable<Cities> getList(String city, String limit);
}
