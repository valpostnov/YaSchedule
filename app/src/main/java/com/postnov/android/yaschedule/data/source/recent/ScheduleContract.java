package com.postnov.android.yaschedule.data.source.recent;

import android.provider.BaseColumns;

/**
 * Created by platon on 29.05.2016.
 */
class ScheduleContract {
    static class RecentEntry implements BaseColumns {
        static final String TABLE_NAME = "recent";
        static final String COLUMN_TO_STATION = "to_station";
        static final String COLUMN_FROM_STATION = "from_station";
        static final String COLUMN_TO = "to_city";
        static final String COLUMN_FROM = "from_city";
    }
}
