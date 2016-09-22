package com.postnov.android.yaschedule.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.postnov.android.yaschedule.App;
import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.MainActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.codes.Suggest;
import com.postnov.android.yaschedule.search.SearchResultAdapter.OnItemClickListener;
import com.postnov.android.yaschedule.search.interfaces.ISearchPresenter;
import com.postnov.android.yaschedule.search.interfaces.ISearchView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.NetworkManager;
import com.postnov.android.yaschedule.utils.Utils;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionError;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class SearchActivity extends AppCompatActivity implements ISearchView, OnItemClickListener
{
    private static final String TAG = "SearchActivity";
    private SearchResultAdapter mAdapter;
    private ISearchPresenter mPresenter;
    //private ProgressBar mProgressView;
    private EditText mSearchView;
    private Subscription mUISubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mPresenter = new SearchPresenterImpl(App.get(this).codesDataSource());

        initViews();
        initToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        subscribeOnTextChange();
    }

    @Override
    protected void onPause()
    {
        mUISubscription.unsubscribe();
        mPresenter.unsubscribe();
        mPresenter.unbind();
        super.onPause();
    }

    @Override
    public void showCities(List<Suggest> suggests)
    {
        mAdapter.swapList(suggests);
    }

    @Override
    public void showProgressView()
    {
        //mEmptyView.setVisibility(View.GONE);
        //mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressView()
    {
        //mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable e)
    {
        if (e instanceof NetworkConnectionError)
        {
            Utils.showToast(this, e.getMessage());
        }
        Log.e(TAG, e.getMessage());
    }

    private void initViews()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        View mEmptyView = findViewById(R.id.search_empty_view);

        mAdapter = new SearchResultAdapter();
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        String searchHint = getIntent().getStringExtra(MainActivity.EXTRA_HINT);
        mSearchView = (EditText) findViewById(R.id.search_view);
        mSearchView.setHint(searchHint);
        //mProgressView = (ProgressBar) findViewById(R.id.search_progressview);
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void subscribeOnTextChange()
    {
        mUISubscription = RxTextView
                .textChanges(mSearchView)
                .doOnNext(new Action1<CharSequence>()
                {
                    @Override
                    public void call(CharSequence query)
                    {
                        if (query.length() == 0) mAdapter.swapList(null);
                    }
                })
                .subscribe(new Action1<CharSequence>()
                {
                    @Override
                    public void call(CharSequence query)
                    {
                        if (query.length() != 0)
                            mPresenter.search(query.toString(), Const.RESULT_LIMIT);
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Suggest suggest = mAdapter.getList().get(position);
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EXTRA_CITY, suggest.getTitleRu());
        intent.putExtra(MainActivity.EXTRA_CODE, suggest.getPointKey());
        setResult(RESULT_OK, intent);
        finish();
    }
}
