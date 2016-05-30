package com.postnov.android.yaschedule.utils;

import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_ARRIVAL;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_CARRIER_CODE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_CARRIER_TITLE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_DEPARTURE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_FROM_STATION;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_TO_STATION;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_TRANSPORT_NUMBER;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_TRANSPORT_TYPE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_UID;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_VEHICLE;

/**
 * Created by platon on 21.05.2016.
 */
public interface Const
{
    String API_KEY = "222222";
    String RESULT_LIMIT = "6";
    String LANG_RU = "ru";
    String FORMAT_JSON = "json";

    String[] PROJECTION = {

            COLUMN_UID,
            COLUMN_FROM_STATION,
            COLUMN_TO_STATION,
            COLUMN_DEPARTURE,
            COLUMN_ARRIVAL,
            COLUMN_CARRIER_TITLE,
            COLUMN_CARRIER_CODE,
            COLUMN_VEHICLE,
            COLUMN_TRANSPORT_TYPE,
            COLUMN_TRANSPORT_NUMBER
    };

    int COLUMN_UID_INDEX = 1;
    int COLUMN_FROM_STATION_INDEX = 2;
    int COLUMN_TO_STATION_INDEX = 3;
    int COLUMN_DEPARTURE_INDEX = 4;
    int COLUMN_ARRIVAL_INDEX = 7;
    int COLUMN_TRANSPORT_TYPE_INDEX = 11;
    int COLUMN_TRANSPORT_NUMBER_INDEX = 12;

    int COLUMN_CARRIER_TITLE_INDEX = 13;
    int COLUMN_CARRIER_CODE_INDEX = 14;
    int COLUMN_VEHICLE_INDEX = 15;

    int DEC = 11;
    int JAN = 0;
    int FEB = 1;
    int MAR = 2;
    int APR = 3;
    int MAY = 4;
    int JUN = 5;
    int JUL = 6;
    int AUG = 7;
    int SEP = 8;
    int OCT = 9;
    int NOV = 10;
}
