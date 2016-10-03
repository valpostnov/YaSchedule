package com.postnov.android.yaschedule.data.entity.codes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by platon on 24.05.2016.
 */
public class Cities {
    @SerializedName("suggests")
    private List<Suggest> suggests;

    public List<Suggest> getSuggests() {
        return suggests;
    }

    public void setSuggests(List<Suggest> suggests) {
        this.suggests = suggests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cities cities = (Cities) o;

        return suggests.equals(cities.suggests);

    }

    @Override
    public int hashCode() {
        return suggests.hashCode();
    }

    @Override
    public String toString() {
        return "Cities{" +
                "suggests=" + suggests +
                '}';
    }
}
