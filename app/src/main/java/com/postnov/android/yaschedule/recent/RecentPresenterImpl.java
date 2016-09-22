package com.postnov.android.yaschedule.recent;

import com.postnov.android.yaschedule.data.source.recent.IRecentDataSource;
import com.postnov.android.yaschedule.recent.interfaces.RecentPresenter;
import com.postnov.android.yaschedule.recent.interfaces.RecentView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by platon on 29.05.2016.
 */
public class RecentPresenterImpl implements RecentPresenter {
    private final IRecentDataSource recentDataSource;
    private Subscription subscription;
    private RecentView recentView;
    private final Action1<Throwable> onError = e -> recentView.showError(e);

    public RecentPresenterImpl(IRecentDataSource recentDataSource) {
        this.recentDataSource = recentDataSource;
    }

    @Override
    public void fetchRecentList() {
        subscription = recentDataSource
                .getRecentRoutes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(routes -> recentView.loadRecentList(routes), onError);
    }

    @Override
    public void clearRecentList() {
        recentDataSource.clear();
        recentView.loadRecentList(null);
    }

    @Override
    public void bind(RecentView view) {
        recentView = view;
    }

    @Override
    public void unbind() {
        subscription.unsubscribe();
        recentView = null;
    }
}
