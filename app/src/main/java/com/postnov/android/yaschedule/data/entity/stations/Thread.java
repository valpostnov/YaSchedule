package com.postnov.android.yaschedule.data.entity.stations;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by platon on 31.05.2016.
 */
public class Thread
{
    @SerializedName("uid")
    private String mUid;

    @SerializedName("days")
    private String mDays;

    @SerializedName("stops")
    private List<Stop> mStops;

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        this.mUid = uid;
    }

    public String getDays() {
        return mDays;
    }

    public void setDays(String days) {
        this.mDays = days;
    }

    public List<Stop> getStops() {
        return mStops;
    }

    public void setStops(List<Stop> stops) {
        this.mStops = stops;
    }
}
