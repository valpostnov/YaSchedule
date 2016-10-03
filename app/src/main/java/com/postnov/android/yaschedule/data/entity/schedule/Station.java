package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 19.05.2016.
 */
public class Station {
    @SerializedName("code")
    private String code;

    @SerializedName("station_type")
    private String stationType;

    @SerializedName("title")
    private String title;

    @SerializedName("popular_title")
    private String popularTitle;

    @SerializedName("short_title")
    private String shortTitle;

    @SerializedName("transport_type")
    private String transportType;

    @SerializedName("type")
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (!code.equals(station.code)) return false;
        if (!stationType.equals(station.stationType)) return false;
        if (!title.equals(station.title)) return false;
        if (!popularTitle.equals(station.popularTitle)) return false;
        if (!shortTitle.equals(station.shortTitle)) return false;
        if (!transportType.equals(station.transportType)) return false;
        return type.equals(station.type);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + stationType.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + popularTitle.hashCode();
        result = 31 * result + shortTitle.hashCode();
        result = 31 * result + transportType.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Station{" +
                "code='" + code + '\'' +
                ", stationType='" + stationType + '\'' +
                ", title='" + title + '\'' +
                ", popularTitle='" + popularTitle + '\'' +
                ", shortTitle='" + shortTitle + '\'' +
                ", transportType='" + transportType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
