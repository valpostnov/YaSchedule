package com.postnov.android.yaschedule.fave.interfaces;

import com.postnov.android.yaschedule.base.BaseView;
import com.postnov.android.yaschedule.data.entity.schedule.Route;

import java.util.List;

/**
 * Created by platon on 29.05.2016.
 */
public interface FaveView extends BaseView
{
    void loadFaves(List<Route> routes);
}
