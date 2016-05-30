package com.postnov.android.yaschedule.detailschedule.interfaces;

import com.postnov.android.yaschedule.base.BasePresenter;
import com.postnov.android.yaschedule.data.entity.schedule.Route;

/**
 * Created by platon on 29.05.2016.
 */
public interface DetailSchedulePresenter extends BasePresenter<DetailScheduleView>
{
    void fetchSchedule(int position);
    void save(Route route);
}
