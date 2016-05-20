package com.postnov.android.yaschedule.twostation.interfaces;

import com.postnov.android.yaschedule.data.entity.Response;

/**
 * Created by platon on 20.05.2016.
 */
public interface TwoStationView
{
    void go();
    void showList(Response response);
    void showError();
}
