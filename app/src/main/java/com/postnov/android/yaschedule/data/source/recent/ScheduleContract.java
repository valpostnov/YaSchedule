package com.postnov.android.yaschedule.data.source.recent;

import android.provider.BaseColumns;

/**
 * Created by platon on 29.05.2016.
 */
public class ScheduleContract
{
    public static class RecentEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "recent";
        public static final String COLUMN_TO_STATION = "to_station";
        public static final String COLUMN_FROM_STATION = "from_station";
        public static final String COLUMN_TO = "to_city";
        public static final String COLUMN_FROM = "from_city";
    }
}
