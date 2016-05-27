package com.postnov.android.yaschedule.search.interfaces;

import com.postnov.android.yaschedule.base.BasePresenter;

/**
 * Created by platon on 24.05.2016.
 */
public interface ISearchPresenter extends BasePresenter<ISearchView>
{
    void search(String city, String limit);
}
