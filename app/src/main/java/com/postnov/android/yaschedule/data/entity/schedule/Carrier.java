package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 19.05.2016.
 */
public class Carrier
{
    @SerializedName("title")
    private String title;

    @SerializedName("code")
    private String code;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Carrier carrier = (Carrier) o;

        if (!title.equals(carrier.title)) return false;
        return code.equals(carrier.code);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Carrier{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
