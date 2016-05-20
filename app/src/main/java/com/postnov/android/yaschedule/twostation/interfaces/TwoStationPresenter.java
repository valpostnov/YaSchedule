package com.postnov.android.yaschedule.twostation.interfaces;

/**
 * Created by platon on 20.05.2016.
 */
public interface TwoStationPresenter
{
    void getSchedule();
    void bind(TwoStationView view);
    void unbind();
    void unsubscribe();
}
