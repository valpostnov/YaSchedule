package com.postnov.android.yaschedule.data.source.schedule.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_ARRIVAL;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_ARRIVAL_PLATFORM;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_ARRIVAL_TERMINAL;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_CARRIER_CODE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_CARRIER_TITLE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_DEPARTURE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_DEPARTURE_PLATFORM;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_DEPARTURE_TERMINAL;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_DURATION;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_FROM_STATION;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_TO_STATION;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_TRANSPORT_NUMBER;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_TRANSPORT_TYPE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_UID;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.COLUMN_VEHICLE;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.TABLE_NAME;
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry._ID;

/**
 * Created by platon on 29.05.2016.
 */
public class ScheduleDBHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "schedule.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_SCHEDULE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_UID + TYPE_TEXT + COMMA_SEP +
                    COLUMN_FROM_STATION + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TO_STATION + TYPE_TEXT + COMMA_SEP +
                    COLUMN_DEPARTURE + TYPE_TEXT + COMMA_SEP +
                    COLUMN_DEPARTURE_PLATFORM + TYPE_TEXT + COMMA_SEP +
                    COLUMN_DEPARTURE_TERMINAL + TYPE_TEXT + COMMA_SEP +
                    COLUMN_ARRIVAL + TYPE_TEXT + COMMA_SEP +
                    COLUMN_ARRIVAL_PLATFORM + TYPE_TEXT + COMMA_SEP +
                    COLUMN_ARRIVAL_TERMINAL + TYPE_TEXT + COMMA_SEP +
                    COLUMN_DURATION + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TRANSPORT_TYPE + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TRANSPORT_NUMBER + TYPE_TEXT + COMMA_SEP +
                    COLUMN_CARRIER_TITLE + TYPE_TEXT + COMMA_SEP +
                    COLUMN_CARRIER_CODE + TYPE_TEXT + COMMA_SEP +
                    COLUMN_VEHICLE + TYPE_TEXT +
                    ")";

    private static final String SQL_DELETE_SCHEDULE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ScheduleDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_SCHEDULE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_SCHEDULE);
        onCreate(db);
    }
}
