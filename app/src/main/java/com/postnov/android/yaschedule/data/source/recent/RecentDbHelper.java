package com.postnov.android.yaschedule.data.source.recent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_FROM;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_FROM_STATION;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_TO;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_TO_STATION;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.TABLE_NAME;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry._ID;


/**
 * Created by platon on 29.05.2016.
 */
public class RecentDbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "schedule.db";

    private static final String TYPE_TEXT = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_SCHEDULE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_FROM_STATION + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TO_STATION + TYPE_TEXT + COMMA_SEP +
                    COLUMN_FROM + TYPE_TEXT + COMMA_SEP +
                    COLUMN_TO + TYPE_TEXT +
                    ")";

    private static final String SQL_DELETE_SCHEDULE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public RecentDbHelper(Context context)
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
