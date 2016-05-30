package com.postnov.android.yaschedule.data.source.schedule.local;

import android.provider.BaseColumns;

/**
 * Created by platon on 29.05.2016.
 */
public class ScheduleContract
{
    public static class ScheduleEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "favourite_schedule";
        public static final String COLUMN_UID = "uid";
        public static final String COLUMN_DEPARTURE = "departure";
        public static final String COLUMN_ARRIVAL = "arrival";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_DEPARTURE_TERMINAL = "departure_terminal";
        public static final String COLUMN_DEPARTURE_PLATFORM = "departure_platform";
        public static final String COLUMN_ARRIVAL_TERMINAL = "arrival_terminal";
        public static final String COLUMN_ARRIVAL_PLATFORM = "arrival_platform";
        public static final String COLUMN_TO_STATION = "to_station";
        public static final String COLUMN_FROM_STATION = "from_station";
        public static final String COLUMN_TRANSPORT_TYPE = "transport_type";
        public static final String COLUMN_TRANSPORT_NUMBER = "transport_number";
        public static final String COLUMN_CARRIER_TITLE = "carrier_title";
        public static final String COLUMN_CARRIER_CODE = "carrier_code";
        public static final String COLUMN_VEHICLE = "vehicle";
    }
}
