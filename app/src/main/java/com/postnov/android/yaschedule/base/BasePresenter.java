package com.postnov.android.yaschedule.base;

/**
 * Created by platon on 27.05.2016.
 */
public interface BasePresenter<V extends BaseView>
{
    void bind(V view);
    void unbind();
}
