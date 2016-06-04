package com.postnov.android.yaschedule.data.entity.recent;

/**
 * Created by platon on 03.06.2016.
 */
public class RecentRoute
{
    private String mFrom;
    private String mTo;
    private String mFromCode;
    private String mToCode;

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
}
