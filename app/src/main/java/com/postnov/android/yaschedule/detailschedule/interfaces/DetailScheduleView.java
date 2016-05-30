package com.postnov.android.yaschedule.detailschedule.interfaces;

import com.postnov.android.yaschedule.base.BaseView;
import com.postnov.android.yaschedule.data.entity.schedule.Route;

/**
 * Created by platon on 29.05.2016.
 */
public interface DetailScheduleView extends BaseView
{
    void loadSchedule(Route route);
}
