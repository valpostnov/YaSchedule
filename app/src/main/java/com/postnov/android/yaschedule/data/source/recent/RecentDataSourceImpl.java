package com.postnov.android.yaschedule.data.source.recent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.provider.BaseColumns._ID;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_FROM;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_FROM_STATION;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_TO;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.COLUMN_TO_STATION;
import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.TABLE_NAME;

/**
 * Created by platon on 03.06.2016.
 */
public class RecentDataSourceImpl implements IRecentDataSource
{
    private static RecentDataSourceImpl sInstance;

    private final BriteDatabase mDatabaseHelper;
    private Func1<Cursor, RecentRoute> mRouteMapperFunction;
    private List<RecentRoute> mCachedListRoute;

    private static final String[] PROJECTION = {
            _ID,
            COLUMN_FROM_STATION,
            COLUMN_TO_STATION,
            COLUMN_FROM,
            COLUMN_TO
    };

    public static RecentDataSourceImpl getInstance(Context context)
    {
        if (sInstance == null)
        {
            sInstance = new RecentDataSourceImpl(context);
        }

        return sInstance;
    }

    private RecentDataSourceImpl(Context context)
    {
        SqlBrite sqlBrite = SqlBrite.create();
        RecentDbHelper dbHelper = new RecentDbHelper(context);
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());

        mRouteMapperFunction = new Func1<Cursor, RecentRoute>()
        {
            @Override
            public RecentRoute call(Cursor cursor)
            {
                String fromCode = cursor.getString(1);
                String toCode = cursor.getString(2);
                String from = cursor.getString(3);
                String to = cursor.getString(4);

                RecentRoute route = new RecentRoute();
                route.setFrom(from);
                route.setTo(to);
                route.setFromCode(fromCode);
                route.setToCode(toCode);

                return route;
            }
        };

        mCachedListRoute = new ArrayList<>();
    }

    @Override
    public Observable<List<RecentRoute>> getLastRoutes()
    {
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", PROJECTION), TABLE_NAME);
        return mDatabaseHelper.createQuery(TABLE_NAME, sql).mapToList(mRouteMapperFunction);
    }

    @Override
    public void save(RecentRoute route)
    {
        if (mCachedListRoute.isEmpty() || !mCachedListRoute.contains(route))
        {
            insert(route);
            mCachedListRoute.add(route);
        }
    }

    private void insert(RecentRoute route)
    {
        ContentValues values = new ContentValues();

        values.put(COLUMN_FROM, route.getFrom());
        values.put(COLUMN_TO, route.getTo());
        values.put(COLUMN_FROM_STATION, route.getFromCode());
        values.put(COLUMN_TO_STATION, route.getToCode());

        mDatabaseHelper.insert(TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
