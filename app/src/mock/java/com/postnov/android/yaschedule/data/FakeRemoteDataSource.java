package com.postnov.android.yaschedule.data;

import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.data.entity.schedule.Route;
import com.postnov.android.yaschedule.data.entity.schedule.RouteOptions;
import com.postnov.android.yaschedule.data.entity.schedule.Station;
import com.postnov.android.yaschedule.data.source.schedule.IScheduleDataSource;
import com.postnov.android.yaschedule.utils.TransportTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by platon on 21.05.2016.
 */
public class FakeRemoteDataSource implements IScheduleDataSource
{
    @Override
    public Observable<Response> search(Map<String, String> options)
    {
        return Observable.just(getResponse()).delay(2000, TimeUnit.MILLISECONDS);
    }

    private Response getResponse()
    {
        Response response = new Response();
        response.setRoutes(getRoutes());

        return response;
    }

    private List<Route> getRoutes()
    {
        List<Route> routes = new ArrayList<>();
        Route route1 = new Route();

        route1.setArrival("10:15");
        route1.setDeparture("06:00");
        route1.setDuration("8100.0");
        route1.setRouteOptions(getRouteOptions());
        route1.setFromStation(getFrom());
        route1.setToStation(getTo());

        routes.add(route1);
        routes.add(route1);

        return routes;
    }

    private RouteOptions getRouteOptions()
    {
        RouteOptions options = new RouteOptions();
        options.setTitle("31UF Москва - Нижний Новгород");
        options.setTransportType(TransportTypes.SEA);

        return options;
    }

    private Station getFrom()
    {
        Station station = new Station();
        station.setTitle("Ярославский вокзал");

        return station;
    }

    private Station getTo()
    {
        Station station = new Station();
        station.setTitle("Нижний Новгород (Московский вокзал)");

        return station;
    }
}
