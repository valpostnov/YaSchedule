package com.postnov.android.yaschedule.search.interfaces;

/**
 * Created by platon on 24.05.2016.
 */
public interface ISearchPresenter
{
    void search(String city, String limit);
    void bind(ISearchView view);
    void unbind();
    void unsubscribe();
}
