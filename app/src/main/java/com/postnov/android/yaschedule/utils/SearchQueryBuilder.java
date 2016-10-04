package com.postnov.android.yaschedule.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by platon on 20.05.2016.
 */
public class SearchQueryBuilder {
    private Map<String, String> mQuery = new HashMap<>();

    private SearchQueryBuilder() {
        mQuery.put("apikey", Const.API_KEY);
        mQuery.put("format", Const.FORMAT_JSON);
    }

    public static Builder builder() {
        return new SearchQueryBuilder().new Builder();
    }

    public class Builder {
        public Builder setFrom(String from) {
            mQuery.put("from", from);
            return this;
        }

        public Builder setTo(String to) {
            mQuery.put("to", to);
            return this;
        }

        public Builder setLang(String lang) {
            mQuery.put("lang", lang);
            return this;
        }

        public Builder setPage(int page) {
            mQuery.put("page", String.valueOf(page));
            return this;
        }

        public Builder setDate(String date) {
            mQuery.put("date", date);
            return this;
        }

        public Builder setTransport(String type) {
            mQuery.put("transport_types", type);
            return this;
        }

        public Map<String, String> build() {
            return mQuery;
        }
    }
}
