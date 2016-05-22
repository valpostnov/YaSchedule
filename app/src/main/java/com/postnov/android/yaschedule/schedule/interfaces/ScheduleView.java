package com.postnov.android.yaschedule.schedule.interfaces;

import com.postnov.android.yaschedule.data.entity.Response;

/**
 * Created by platon on 20.05.2016.
 */
public interface ScheduleView
{
    void showList(Response response);
    void showError(Throwable e);
    void showProgressDialog();
    void hideProgressDialog();
}
