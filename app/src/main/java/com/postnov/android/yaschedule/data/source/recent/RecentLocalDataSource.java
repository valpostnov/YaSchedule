package com.postnov.android.yaschedule.data.source.recent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;

import static com.postnov.android.yaschedule.data.source.recent.ScheduleContract.RecentEntry.*;

/**
 * Created by platon on 03.06.2016.
 */
public class RecentLocalDataSource implements IRecentDataSource {
    private static final int MAX_ROWS_COUNT = 7;
    private RecentDBHelper mDbHelper;
    private LinkedList<RecentRoute> mCachedListRoute;
    private static final String[] PROJECTION = {
            _ID,
            COLUMN_FROM_STATION,
            COLUMN_TO_STATION,
            COLUMN_FROM,
            COLUMN_TO
    };

    private static final String SQL = String.format(
            "SELECT %s FROM %s ORDER BY %s DESC",
            TextUtils.join(",", PROJECTION), TABLE_NAME, _ID);

    public RecentLocalDataSource(Context context) {
        mDbHelper = new RecentDBHelper(context);
        mCachedListRoute = initCache();
    }

    @Override
    public Observable<List<RecentRoute>> getRecentRoutes() {
        return Observable.just(mCachedListRoute);
    }

    @Override
    public void save(RecentRoute route) {
        if (mCachedListRoute.isEmpty() || !mCachedListRoute.contains(route)) insert(route);
    }

    @Override
    public void clear() {
        mCachedListRoute.clear();
        mDbHelper.getWritableDatabase().delete(TABLE_NAME, null, null);
    }

    private void insert(RecentRoute route) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_FROM, route.getFrom());
        values.put(COLUMN_TO, route.getTo());
        values.put(COLUMN_FROM_STATION, route.getFromCode());
        values.put(COLUMN_TO_STATION, route.getToCode());

        if (mDbHelper.getReadableDatabase().rawQuery(SQL, null).getCount() == MAX_ROWS_COUNT) {
            deleteRowWithMinId();
            mCachedListRoute.pollLast();
        }
        mDbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        mCachedListRoute.push(route);
    }

    private LinkedList<RecentRoute> initCache() {
        Cursor cursor = mDbHelper.getReadableDatabase().rawQuery(SQL, null);
        LinkedList<RecentRoute> recentRoutes = new LinkedList<>();
        RecentRoute route;

        while (cursor.moveToNext()) {
            route = new RecentRoute();
            route.setFrom(cursor.getString(3));
            route.setTo(cursor.getString(4));
            route.setFromCode(cursor.getString(1));
            route.setToCode(cursor.getString(2));
            recentRoutes.add(route);
        }

        cursor.close();
        return recentRoutes;
    }

    private void deleteRowWithMinId() {
        String sqlDelete = String.format(
                "DELETE FROM %s WHERE %s = (SELECT min(%s) FROM %s)",
                TABLE_NAME, _ID, _ID, TABLE_NAME);

        mDbHelper.getWritableDatabase().execSQL(sqlDelete);
    }
}
