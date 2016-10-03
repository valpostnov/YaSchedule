package com.postnov.android.yaschedule.recent.interfaces;

import com.postnov.android.yaschedule.base.BaseView;
import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;

import java.util.List;

/**
 * Created by platon on 29.05.2016.
 */
public interface IRecentView extends BaseView
{
    void loadRecentList(List<RecentRoute> routes);
}
