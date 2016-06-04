package com.postnov.android.yaschedule.recent.interfaces;

import com.postnov.android.yaschedule.base.BasePresenter;

/**
 * Created by platon on 29.05.2016.
 */
public interface RecentPresenter extends BasePresenter<RecentView>
{
    void fetchFaves();
}
