package com.postnov.android.yaschedule.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by platon on 20.05.2016.
 */
public class StationsQueryBuilder
{
    private Map<String, String> mQuery = new HashMap<>();

    private StationsQueryBuilder()
    {
        mQuery.put("apikey", Const.API_KEY);
        mQuery.put("format", Const.FORMAT_JSON);
    }

    public static Builder builder()
    {
        return new StationsQueryBuilder().new Builder();
    }

    public class Builder
    {
        public Builder setUID(String uid)
        {
            mQuery.put("uid", uid);
            return this;
        }

        public Builder setLang(String lang)
        {
            mQuery.put("lang", lang);
            return this;
        }

        public Builder setDate(String date)
        {
            mQuery.put("date", date);
            return this;
        }

        public Map<String, String> build()
        {
            return mQuery;
        }
    }
}
