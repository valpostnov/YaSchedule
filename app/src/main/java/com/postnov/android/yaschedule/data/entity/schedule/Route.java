package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 19.05.2016.
 */
public class Route
{
    @SerializedName("arrival")
    private String arrival;

    @SerializedName("duration")
    private String duration;

    @SerializedName("arrival_terminal")
    private String arrivalTerminal;

    @SerializedName("arrival_platform")
    private String arrivalPlatform;

    @SerializedName("from")
    private Station fromStation;

    @SerializedName("thread")
    private RouteOptions routeOptions;

    @SerializedName("departure_platform")
    private String departurePlatform;

    @SerializedName("departure")
    private String departure;

    @SerializedName("stops")
    private String stops;

    @SerializedName("to")
    private Station toStation;

    @SerializedName("departure_terminal")
    private String departureTerminal;

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getArrivalPlatform() {
        return arrivalPlatform;
    }

    public void setArrivalPlatform(String arrivalPlatform) {
        this.arrivalPlatform = arrivalPlatform;
    }

    public Station getFromStation() {
        return fromStation;
    }

    public void setFromStation(Station fromStation) {
        this.fromStation = fromStation;
    }

    public RouteOptions getRouteOptions() {
        return routeOptions;
    }

    public void setRouteOptions(RouteOptions routeOptions) {
        this.routeOptions = routeOptions;
    }

    public String getDeparturePlatform() {
        return departurePlatform;
    }

    public void setDeparturePlatform(String departurePlatform) {
        this.departurePlatform = departurePlatform;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public Station getToStation() {
        return toStation;
    }

    public void setToStation(Station toStation) {
        this.toStation = toStation;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public void setDepartureTerminal(String departureTerminal) {
        this.departureTerminal = departureTerminal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (!arrival.equals(route.arrival)) return false;
        if (!duration.equals(route.duration)) return false;
        if (!arrivalTerminal.equals(route.arrivalTerminal)) return false;
        if (!arrivalPlatform.equals(route.arrivalPlatform)) return false;
        if (!fromStation.equals(route.fromStation)) return false;
        if (!routeOptions.equals(route.routeOptions)) return false;
        if (!departurePlatform.equals(route.departurePlatform)) return false;
        if (!departure.equals(route.departure)) return false;
        if (!stops.equals(route.stops)) return false;
        if (!toStation.equals(route.toStation)) return false;
        return departureTerminal.equals(route.departureTerminal);

    }

    @Override
    public int hashCode() {
        int result = arrival.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + arrivalTerminal.hashCode();
        result = 31 * result + arrivalPlatform.hashCode();
        result = 31 * result + fromStation.hashCode();
        result = 31 * result + routeOptions.hashCode();
        result = 31 * result + departurePlatform.hashCode();
        result = 31 * result + departure.hashCode();
        result = 31 * result + stops.hashCode();
        result = 31 * result + toStation.hashCode();
        result = 31 * result + departureTerminal.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Route{" +
                "arrival='" + arrival + '\'' +
                ", duration='" + duration + '\'' +
                ", arrivalTerminal='" + arrivalTerminal + '\'' +
                ", arrivalPlatform='" + arrivalPlatform + '\'' +
                ", fromStation=" + fromStation +
                ", routeOptions=" + routeOptions +
                ", departurePlatform='" + departurePlatform + '\'' +
                ", departure='" + departure + '\'' +
                ", stops='" + stops + '\'' +
                ", toStation=" + toStation +
                ", departureTerminal='" + departureTerminal + '\'' +
                '}';
    }
}
