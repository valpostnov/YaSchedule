package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 30.05.2016.
 */
public class Search
{
    @SerializedName("date")
    private String date;

    @SerializedName("from")
    private From from;

    @SerializedName("to")
    private To to;

    public class To
    {
        @SerializedName("title")
        private String title;

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }
    }

    public class From
    {
        @SerializedName("title")
        private String title;

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }
}
