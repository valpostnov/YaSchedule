package com.postnov.android.yaschedule.schedule.interfaces;

import java.util.Map;

/**
 * Created by platon on 20.05.2016.
 */
public interface SchedulePresenter
{
    void getSchedule(Map<String, String> options);
    void bind(ScheduleView view);
    void unbind();
    void unsubscribe();
}
