package com.postnov.android.yaschedule.search;

import com.postnov.android.yaschedule.data.entity.codes.Cities;
import com.postnov.android.yaschedule.data.source.codes.ICodesDataSource;
import com.postnov.android.yaschedule.search.interfaces.ISearchPresenter;
import com.postnov.android.yaschedule.search.interfaces.ISearchView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.NetworkManager;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionError;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 24.05.2016.
 */
public class SearchPresenterImpl implements ISearchPresenter
{
    private ISearchView mView;
    private CompositeSubscription mSubscriptions;
    private ICodesDataSource mDataSource;
    private NetworkManager mNetworkManager;

    public SearchPresenterImpl(ICodesDataSource dataSource, NetworkManager networkManager)
    {
        mSubscriptions = new CompositeSubscription();
        mDataSource = dataSource;
        mNetworkManager = networkManager;
    }

    @Override
    public void search(String city, String limit)
    {
        if (mNetworkManager.networkIsAvailable())
        {
            mView.showProgressView();
            mSubscriptions.add(mDataSource
                    .getList(city, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Cities>()
                    {
                        @Override
                        public void onCompleted()
                        {
                            mView.hideProgressView();
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            mView.hideProgressView();
                            mView.showCities(null);
                            mView.showError(e);
                        }

                        @Override
                        public void onNext(Cities cityCodes)
                        {
                            mView.showCities(cityCodes.getSuggests());
                        }

                    }));
        }
        else
        {
            mView.showError(new NetworkConnectionError(Const.ERROR_NO_CONNECTION));
        }
    }

    @Override
    public void bind(ISearchView view)
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
        mSubscriptions.clear();
    }
}
