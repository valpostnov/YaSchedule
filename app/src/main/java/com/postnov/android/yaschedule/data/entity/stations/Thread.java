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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thread thread = (Thread) o;

        if (!mUid.equals(thread.mUid)) return false;
        if (!mDays.equals(thread.mDays)) return false;
        return mStops.equals(thread.mStops);

    }

    @Override
    public int hashCode() {
        int result = mUid.hashCode();
        result = 31 * result + mDays.hashCode();
        result = 31 * result + mStops.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "mUid='" + mUid + '\'' +
                ", mDays='" + mDays + '\'' +
                ", mStops=" + mStops +
                '}';
    }
}
