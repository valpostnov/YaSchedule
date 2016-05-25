package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 19.05.2016.
 */
public class Codes
{
    @SerializedName("iata")
    private String iata;

    @SerializedName("icao")
    private String icao;

    @SerializedName("sirena")
    private String sirena;

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getSirena() {
        return sirena;
    }

    public void setSirena(String sirena) {
        this.sirena = sirena;
    }
}
