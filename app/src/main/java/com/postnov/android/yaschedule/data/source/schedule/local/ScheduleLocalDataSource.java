package com.postnov.android.yaschedule.data.source.schedule.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.postnov.android.yaschedule.data.entity.schedule.Carrier;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.data.entity.schedule.Route;
import com.postnov.android.yaschedule.data.entity.schedule.RouteOptions;
import com.postnov.android.yaschedule.data.entity.schedule.Station;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.utils.Const;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Map;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
import static com.postnov.android.yaschedule.data.source.schedule.local.ScheduleContract.ScheduleEntry.TABLE_NAME;

/**
 * Created by platon on 20.05.2016.
 */
public class ScheduleLocalDataSource implements IScheduleDataSource
{
    private final BriteDatabase mDatabaseHelper;
    private Func1<Cursor, Route> mRouteMapperFunction;

    public ScheduleLocalDataSource(Context context)
    {
        SqlBrite sqlBrite = SqlBrite.create();
        ScheduleDBHelper dbHelper = new ScheduleDBHelper(context);
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());

        mRouteMapperFunction = new Func1<Cursor, Route>()
        {
            @Override
            public Route call(Cursor cursor)
            {
                Route route = new Route();
                RouteOptions routeOptions = new RouteOptions();
                Station fromStation = new Station();
                Station toStation = new Station();
                Carrier carrier = new Carrier();

                String uid = cursor.getString(Const.COLUMN_UID_INDEX);
                String fStation = cursor.getString(Const.COLUMN_FROM_STATION_INDEX);
                String tStation = cursor.getString(Const.COLUMN_TO_STATION_INDEX);
                String departure = cursor.getString(Const.COLUMN_DEPARTURE_INDEX);
                String arrival = cursor.getString(Const.COLUMN_ARRIVAL_INDEX);
                String tt = cursor.getString(Const.COLUMN_TRANSPORT_TYPE_INDEX);
                String tt_number = cursor.getString(Const.COLUMN_TRANSPORT_NUMBER_INDEX);
                String carrierTitle = cursor.getString(Const.COLUMN_CARRIER_TITLE_INDEX);
                String carrierCode = cursor.getString(Const.COLUMN_CARRIER_CODE_INDEX);
                String vehicle = cursor.getString(Const.COLUMN_VEHICLE_INDEX);

                fromStation.setTitle(fStation);
                toStation.setTitle(tStation);

                carrier.setTitle(carrierTitle);
                carrier.setCode(carrierCode);

                routeOptions.setTransportType(tt);
                routeOptions.setNumber(tt_number);
                routeOptions.setUid(uid);
                routeOptions.setVehicle(vehicle);
                routeOptions.setCarrier(carrier);

                route.setRouteOptions(routeOptions);
                route.setArrival(arrival);
                route.setDeparture(departure);
                route.setFromStation(fromStation);
                route.setFromStation(toStation);

                return route;
            }
        };
    }

    @Override
    public Observable<Response> search(Map<String, String> options) {
        return null;
    }

    @Override
    public Observable<Route> getRoute(int id)
    {
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", Const.PROJECTION), TABLE_NAME, COLUMN_UID);
        return mDatabaseHelper.createQuery(TABLE_NAME, sql, String.valueOf(id))
                .mapToOneOrDefault(mRouteMapperFunction, null);
    }

    @Override
    public void save(Route route)
    {
        ContentValues value = new ContentValues();
        value.put(COLUMN_FROM_STATION, route.getFromStation().getTitle());
        value.put(COLUMN_TO_STATION, route.getToStation().getTitle());
        value.put(COLUMN_DEPARTURE, route.getDeparture());
        value.put(COLUMN_ARRIVAL, route.getArrival());
        value.put(COLUMN_UID, route.getRouteOptions().getUid());
        value.put(COLUMN_VEHICLE, route.getRouteOptions().getVehicle());
        value.put(COLUMN_CARRIER_TITLE, route.getRouteOptions().getCarrier().getTitle());
        value.put(COLUMN_CARRIER_CODE, route.getRouteOptions().getCarrier().getCode());
        value.put(COLUMN_TRANSPORT_TYPE, route.getRouteOptions().getTransportType());
        value.put(COLUMN_TRANSPORT_NUMBER, route.getRouteOptions().getNumber());

        mDatabaseHelper.insert(TABLE_NAME, value, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void delete(String routeId)
    {
        String[] args = null;
        mDatabaseHelper.delete(TABLE_NAME, null, args);
    }
}
