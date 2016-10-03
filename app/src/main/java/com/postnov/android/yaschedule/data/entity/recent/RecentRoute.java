package com.postnov.android.yaschedule.data.entity.recent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by platon on 03.06.2016.
 */
public class RecentRoute implements Parcelable {
    private String from;
    private String to;
    private String fromCode;
    private String toCode;

    public RecentRoute() {
    }

    protected RecentRoute(Parcel in) {
        from = in.readString();
        to = in.readString();
        fromCode = in.readString();
        toCode = in.readString();
    }

    public static final Creator<RecentRoute> CREATOR = new Creator<RecentRoute>() {
        @Override
        public RecentRoute createFromParcel(Parcel in) {
            return new RecentRoute(in);
        }

        @Override
        public RecentRoute[] newArray(int size) {
            return new RecentRoute[size];
        }
    };

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecentRoute that = (RecentRoute) o;

        if (!fromCode.equals(that.fromCode)) return false;
        return toCode.equals(that.toCode);
    }

    @Override
    public int hashCode() {
        int result = fromCode.hashCode();
        result = 31 * result + toCode.hashCode();
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(fromCode);
        dest.writeString(toCode);
    }

    @Override
    public String toString() {
        return "RecentRoute{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", fromCode='" + fromCode + '\'' +
                ", toCode='" + toCode + '\'' +
                '}';
    }
}
