package com.postnov.android.yaschedule.base;

/**
 * Created by platon on 27.05.2016.
 */
public interface BasePresenter<V>
{
    void bind(V view);
    void unbind();
    void unsubscribe();
}
