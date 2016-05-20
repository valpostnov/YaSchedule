package com.postnov.android.yaschedule.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by platon on 19.05.2016.
 */
public class Response {

    @SerializedName("threads")
    private List<Route> mRoutes;

    @SerializedName("pagination")
    private Pagination mPagination;

    public List<Route> getRoutes ()
    {
        return mRoutes;
    }

    public void setRoutes (List<Route> routes)
    {
        mRoutes = routes;
    }

    public Pagination getPagination ()
    {
        return mPagination;
    }

    public void setPagination (Pagination pagination)
    {
        mPagination = pagination;
    }
}
