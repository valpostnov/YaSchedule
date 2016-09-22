package com.postnov.android.yaschedule.search;

import com.postnov.android.yaschedule.data.source.codes.ICodesDataSource;
import com.postnov.android.yaschedule.search.interfaces.ISearchPresenter;
import com.postnov.android.yaschedule.search.interfaces.ISearchView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.NetworkManager;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionException;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 24.05.2016.
 */
public class SearchPresenterImpl implements ISearchPresenter {
    private ISearchView searchView;
    private final CompositeSubscription subscription;
    private final ICodesDataSource codesDataSource;
    private NetworkManager networkManager; //Todo init!!!

    public SearchPresenterImpl(ICodesDataSource codesDataSource) {
        subscription = new CompositeSubscription();
        this.codesDataSource = codesDataSource;
    }

    @Override
    public void search(String city, String limit) {
        if (networkManager.networkIsAvailable()) {
            searchView.showProgressView();
            subscription.add(codesDataSource
                    .getList(city, limit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(cities -> searchView.showCities(cities.getSuggests()), onError));
        } else {
            searchView.showError(new NetworkConnectionException(Const.ERROR_NO_CONNECTION));
        }
    }

    @Override
    public void bind(ISearchView view) {
        searchView = view;
    }

    @Override
    public void unbind() {
        subscription.clear();
        searchView = null;
    }

    private Action1<Throwable> onError = e -> {
        searchView.hideProgressView();
        searchView.showCities(null);
        searchView.showError(e);
    };
}
