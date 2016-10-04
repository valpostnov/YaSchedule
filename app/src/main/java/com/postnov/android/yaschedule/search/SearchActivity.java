package com.postnov.android.yaschedule.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.postnov.android.yaschedule.App;
import com.postnov.android.yaschedule.base.BaseActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.codes.Suggest;
import com.postnov.android.yaschedule.search.SearchResultAdapter.OnItemClickListener;
import com.postnov.android.yaschedule.search.interfaces.ISearchPresenter;
import com.postnov.android.yaschedule.search.interfaces.ISearchView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.Utils;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import timber.log.Timber;

public class SearchActivity extends AppCompatActivity implements ISearchView, OnItemClickListener {
    private SearchResultAdapter resultAdapter;
    private ISearchPresenter presenter;
    private Subscription uiSubscription;

    @BindView(R.id.toolbar_search)          Toolbar toolbar;
    @BindView(R.id.search_view)             EditText searchView;
    @BindView(R.id.search_empty_view)       View emptyView;
    @BindView(R.id.search_recyclerview)     RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Timber.tag("SearchActivity");
        presenter = new SearchPresenter(App.get(this).codesDataSource());

        initViews();
        initToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
        subscribeOnTextChange();
    }

    @Override
    protected void onPause() {
        uiSubscription.unsubscribe();
        presenter.unbind();
        super.onPause();
    }

    @Override
    public void showCities(List<Suggest> suggests) {
        resultAdapter.swapList(suggests);
        emptyView.setVisibility(resultAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressView() {
        //mEmptyView.setVisibility(View.GONE);
        //mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressView() {
        //mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof NetworkConnectionException) {
            Utils.showToast(this, e.getMessage());
        }
        Timber.wtf(e, e.getMessage());
    }

    private void initViews() {
        resultAdapter = new SearchResultAdapter();
        resultAdapter.setOnItemClickListener(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(resultAdapter);

        String searchHint = getIntent().getStringExtra(BaseActivity.EXTRA_HINT);
        searchView.setHint(searchHint);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void subscribeOnTextChange() {
        uiSubscription = RxTextView
                .textChanges(searchView)
                .doOnNext(query -> {
                    if (query.length() == 0) resultAdapter.swapList(null);
                })
                .subscribe(query -> {
                    if (query.length() != 0)
                        presenter.search(query.toString(), Const.RESULT_LIMIT);
                });
    }

    @Override
    public void onItemClick(View view, int position) {
        Suggest suggest = resultAdapter.getList().get(position);
        Intent intent = new Intent();
        intent.putExtra(BaseActivity.EXTRA_CITY, suggest.getTitleRu());
        intent.putExtra(BaseActivity.EXTRA_CODE, suggest.getPointKey());
        setResult(RESULT_OK, intent);
        finish();
    }
}
