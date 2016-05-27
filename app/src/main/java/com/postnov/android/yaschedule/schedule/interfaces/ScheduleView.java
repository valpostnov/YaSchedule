package com.postnov.android.yaschedule.schedule.interfaces;

import com.postnov.android.yaschedule.base.BaseView;
import com.postnov.android.yaschedule.data.entity.schedule.Response;

/**
 * Created by platon on 20.05.2016.
 */
public interface ScheduleView extends BaseView
{
    void showList(Response response);
    void showProgressDialog();
    void hideProgressDialog();
}
