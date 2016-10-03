package com.postnov.android.yaschedule.data.entity.stations;

import com.google.gson.annotations.SerializedName;
import com.postnov.android.yaschedule.data.entity.schedule.Station;

/**
 * Created by platon on 31.05.2016.
 */
public class Stop {
    @SerializedName("arrival")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (!arrival.equals(stop.arrival)) return false;
        if (!departure.equals(stop.departure)) return false;
        if (!terminal.equals(stop.terminal)) return false;
        if (!platform.equals(stop.platform)) return false;
        if (!station.equals(stop.station)) return false;
        if (!stopTime.equals(stop.stopTime)) return false;
        return duration.equals(stop.duration);

    }

    @Override
    public int hashCode() {
        int result = arrival.hashCode();
        result = 31 * result + departure.hashCode();
        result = 31 * result + terminal.hashCode();
        result = 31 * result + platform.hashCode();
        result = 31 * result + station.hashCode();
        result = 31 * result + stopTime.hashCode();
        result = 31 * result + duration.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "arrival='" + arrival + '\'' +
                ", departure='" + departure + '\'' +
                ", terminal='" + terminal + '\'' +
                ", platform='" + platform + '\'' +
                ", station=" + station +
                ", stopTime='" + stopTime + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
