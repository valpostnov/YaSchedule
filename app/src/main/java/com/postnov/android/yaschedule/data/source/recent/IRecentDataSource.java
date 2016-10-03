package com.postnov.android.yaschedule.data.source.recent;

import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;

import java.util.List;

import rx.Observable;

/**
 * Created by platon on 03.06.2016.
 */
public interface IRecentDataSource {
    Observable<List<RecentRoute>> getRecentRoutes();
    void save(RecentRoute route);
    void clear();
}
