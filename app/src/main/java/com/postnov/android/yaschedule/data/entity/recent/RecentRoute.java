package com.postnov.android.yaschedule.data.entity.recent;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by platon on 03.06.2016.
 */
public class RecentRoute implements Parcelable
{
    private String mFrom;
    private String mTo;
    private String mFromCode;
    private String mToCode;

    public RecentRoute() {}

    protected RecentRoute(Parcel in)
    {
        mFrom = in.readString();
        mTo = in.readString();
        mFromCode = in.readString();
        mToCode = in.readString();
    }

    public static final Creator<RecentRoute> CREATOR = new Creator<RecentRoute>()
    {
        @Override
        public RecentRoute createFromParcel(Parcel in)
        {
            return new RecentRoute(in);
        }

        @Override
        public RecentRoute[] newArray(int size)
        {
            return new RecentRoute[size];
        }
    };

    public String getFrom() {
        return mFrom;
    }

    public void setFrom(String from) {
        this.mFrom = from;
    }

    public String getTo() {
        return mTo;
    }

    public void setTo(String to) {
        this.mTo = to;
    }

    public String getFromCode() {
        return mFromCode;
    }

    public void setFromCode(String fromCode) {
        this.mFromCode = fromCode;
    }

    public String getToCode() {
        return mToCode;
    }

    public void setToCode(String toCode) {
        this.mToCode = toCode;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecentRoute that = (RecentRoute) o;

        if (!mFromCode.equals(that.mFromCode)) return false;
        return mToCode.equals(that.mToCode);
    }

    @Override
    public int hashCode()
    {
        int result = mFromCode.hashCode();
        result = 31 * result + mToCode.hashCode();
        return result;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(mFrom);
        dest.writeString(mTo);
        dest.writeString(mFromCode);
        dest.writeString(mToCode);
    }

    @Override
    public String toString() {
        return "RecentRoute{" +
                "mFrom='" + mFrom + '\'' +
                ", mTo='" + mTo + '\'' +
                ", mFromCode='" + mFromCode + '\'' +
                ", mToCode='" + mToCode + '\'' +
                '}';
    }
}
