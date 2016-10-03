package com.postnov.android.yaschedule.data.entity.schedule;

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

    @SerializedName("search")
    private Search mSearch;

    public List<Route> getRoutes() {
        return mRoutes;
    }

    public void setRoutes(List<Route> routes) {
        mRoutes = routes;
    }

    public Pagination getPagination() {
        return mPagination;
    }

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
    }

    public Search getSearch() {
        return mSearch;
    }

    public void setSearch(Search search) {
        this.mSearch = search;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (!mRoutes.equals(response.mRoutes)) return false;
        if (!mPagination.equals(response.mPagination)) return false;
        return mSearch.equals(response.mSearch);

    }

    @Override
    public int hashCode() {
        int result = mRoutes.hashCode();
        result = 31 * result + mPagination.hashCode();
        result = 31 * result + mSearch.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "mRoutes=" + mRoutes +
                ", mPagination=" + mPagination +
                ", mSearch=" + mSearch +
                '}';
    }
}
