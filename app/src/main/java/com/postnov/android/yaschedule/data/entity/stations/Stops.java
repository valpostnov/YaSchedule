package com.postnov.android.yaschedule.data.entity.stations;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by platon on 31.05.2016.
 */
public class Stops {
    @SerializedName("uid")
    private String uid;

    @SerializedName("days")
    private String days;

    @SerializedName("stops")
    private List<Stop> stops;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stops stops = (Stops) o;

        if (!uid.equals(stops.uid)) return false;
        if (!days.equals(stops.days)) return false;
        return this.stops.equals(stops.stops);

    }

    @Override
    public int hashCode() {
        int result = uid.hashCode();
        result = 31 * result + days.hashCode();
        result = 31 * result + stops.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Stops{" +
                "uid='" + uid + '\'' +
                ", days='" + days + '\'' +
                ", stops=" + stops +
                '}';
    }
}
