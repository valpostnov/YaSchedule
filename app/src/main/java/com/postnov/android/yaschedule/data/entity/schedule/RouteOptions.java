package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 19.05.2016.
 */
public class RouteOptions {
    @SerializedName("carrier")
    private Carrier carrier;

    @SerializedName("transport_type")
    private String transportType;

    @SerializedName("uid")
    private String uid;

    @SerializedName("title")
    private String title;

    @SerializedName("vehicle")
    private String vehicle;

    @SerializedName("number")
    private String number;

    @SerializedName("short_title")
    private String shortTitle;

    @SerializedName("express_type")
    private String expressType;

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteOptions that = (RouteOptions) o;

        if (!carrier.equals(that.carrier)) return false;
        if (!transportType.equals(that.transportType)) return false;
        if (!uid.equals(that.uid)) return false;
        if (!title.equals(that.title)) return false;
        if (!vehicle.equals(that.vehicle)) return false;
        if (!number.equals(that.number)) return false;
        if (!shortTitle.equals(that.shortTitle)) return false;
        return expressType.equals(that.expressType);

    }

    @Override
    public int hashCode() {
        int result = carrier.hashCode();
        result = 31 * result + transportType.hashCode();
        result = 31 * result + uid.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + vehicle.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + shortTitle.hashCode();
        result = 31 * result + expressType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "RouteOptions{" +
                "carrier=" + carrier +
                ", transportType='" + transportType + '\'' +
                ", uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", number='" + number + '\'' +
                ", shortTitle='" + shortTitle + '\'' +
                ", expressType='" + expressType + '\'' +
                '}';
    }
}
