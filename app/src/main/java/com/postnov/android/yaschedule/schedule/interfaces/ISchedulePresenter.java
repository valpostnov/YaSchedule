package com.postnov.android.yaschedule.schedule.interfaces;

import com.postnov.android.yaschedule.base.BasePresenter;

import java.util.Map;

/**
 * Created by platon on 20.05.2016.
 */
public interface ISchedulePresenter extends BasePresenter<ScheduleView>
{
    void search(Map<String, String> options);
}
