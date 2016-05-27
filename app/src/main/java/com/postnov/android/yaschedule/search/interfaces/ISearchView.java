package com.postnov.android.yaschedule.search.interfaces;

import com.postnov.android.yaschedule.base.BaseView;
import com.postnov.android.yaschedule.data.entity.codes.Suggest;

import java.util.List;

/**
 * Created by platon on 24.05.2016.
 */
public interface ISearchView extends BaseView
{
    void showCities(List<Suggest> cities);
}
