package com.postnov.android.yaschedule.fave.interfaces;

import com.postnov.android.yaschedule.base.BasePresenter;

/**
 * Created by platon on 29.05.2016.
 */
public interface FavePresenter extends BasePresenter<FaveView>
{
    void fetchFaves();
}
