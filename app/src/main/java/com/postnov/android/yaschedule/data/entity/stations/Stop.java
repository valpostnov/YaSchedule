package com.postnov.android.yaschedule.data.entity.stations;

import com.google.gson.annotations.SerializedName;
import com.postnov.android.yaschedule.data.entity.schedule.Station;

/**
 * Created by platon on 31.05.2016.
 */
public class Stop
{
    @SerializedName("uid")
    private String arrival;

    @SerializedName("departure")
    private String departure;

    @SerializedName("terminal")
    private String terminal;

    @SerializedName("platform")
    private String platform;

    @SerializedName("station")
    private Station station;

    @SerializedName("stop_time")
    private String stopTime;

    @SerializedName("duration")
    private String duration;

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
