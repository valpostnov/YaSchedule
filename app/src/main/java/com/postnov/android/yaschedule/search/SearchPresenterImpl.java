package com.postnov.android.yaschedule.search;

import com.postnov.android.yaschedule.data.entity.codes.CityCodes;
import com.postnov.android.yaschedule.data.source.codes.ICodesDataSource;
import com.postnov.android.yaschedule.search.interfaces.ISearchPresenter;
import com.postnov.android.yaschedule.search.interfaces.ISearchView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 24.05.2016.
 */
public class SearchPresenterImpl implements ISearchPresenter
{
    private ISearchView mSearchView;
    private CompositeSubscription mSubscriptions;
    private ICodesDataSource mDataSource;

    public SearchPresenterImpl(ICodesDataSource dataSource)
    {
        mSubscriptions = new CompositeSubscription();
        mDataSource = dataSource;
    }

    @Override
    public void search(String city, String limit)
    {
        Subscription subscription = mDataSource.getList(city, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityCodes>()
                {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e)
                    {
                        mSearchView.showError(e);
                    }

                    @Override
                    public void onNext(CityCodes cityCodes)
                    {
                        mSearchView.showCities(cityCodes.getSuggests());
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void bind(ISearchView view)
    {
        mSearchView = view;
    }

    @Override
    public void unbind()
    {
        mSearchView = null;
    }

    @Override
    public void unsubscribe()
    {
        mSubscriptions.clear();
    }
}
