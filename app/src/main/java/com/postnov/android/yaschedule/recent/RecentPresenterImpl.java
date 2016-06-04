package com.postnov.android.yaschedule.recent;

import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.postnov.android.yaschedule.data.source.recent.IRecentDataSource;
import com.postnov.android.yaschedule.recent.interfaces.RecentPresenter;
import com.postnov.android.yaschedule.recent.interfaces.RecentView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by platon on 29.05.2016.
 */
public class RecentPresenterImpl implements RecentPresenter
{
    private IRecentDataSource mFaveDataSource;
    private Subscription mSubscription;
    private RecentView mView;

    public RecentPresenterImpl(IRecentDataSource dataSource)
    {
        mFaveDataSource = dataSource;
    }

    @Override
    public void fetchFaves()
    {
        mSubscription = mFaveDataSource
                .getLastRoutes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RecentRoute>>()
                {
                    @Override
                    public void onCompleted()
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mView.loadFaves(null);
                        mView.showError(e);
                    }

                    @Override
                    public void onNext(List<RecentRoute> routes)
                    {
                        mView.loadFaves(routes);
                    }
                });
    }

    @Override
    public void bind(RecentView view)
    {
        mView = view;
    }

    @Override
    public void unbind()
    {
        mView = null;
    }

    @Override
    public void unsubscribe()
    {
        mSubscription.unsubscribe();
    }
}
