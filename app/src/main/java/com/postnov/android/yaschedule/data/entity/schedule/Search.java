package com.postnov.android.yaschedule.data.entity.schedule;

import com.google.gson.annotations.SerializedName;

/**
 * Created by platon on 30.05.2016.
 */
public class Search {
    @SerializedName("date")
    private String date;

    @SerializedName("from")
    private From from;

    @SerializedName("to")
    private To to;

    public class To {
        @SerializedName("title")
        private String title;

        @SerializedName("code")
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class From {
        @SerializedName("title")
        private String title;

        @SerializedName("code")
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Search search = (Search) o;

        if (!date.equals(search.date)) return false;
        if (!from.equals(search.from)) return false;
        return to.equals(search.to);

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Search{" +
                "date='" + date + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
