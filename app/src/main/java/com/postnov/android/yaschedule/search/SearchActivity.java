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
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.MainActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.codes.Suggest;
import com.postnov.android.yaschedule.search.SearchResultAdapter.OnItemClickListener;
import com.postnov.android.yaschedule.search.interfaces.ISearchPresenter;
import com.postnov.android.yaschedule.search.interfaces.ISearchView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

public class SearchActivity extends AppCompatActivity implements ISearchView, OnItemClickListener
{
    private SearchResultAdapter mAdapter;
    private ISearchPresenter mPresenter;
    private TextView mEmptyView;
    private EditText mSearchView;
    private CompositeSubscription subscriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mPresenter = new SearchPresenterImpl(Injection.provideCodesDataSource());
        subscriptions = new CompositeSubscription();
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

    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        subscribeOnTextChange();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        subscriptions.clear();
        mPresenter.unsubscribe();
        mPresenter.unbind();
    }

    @Override
    public void showCities(List<Suggest> suggests)
    {
        mAdapter.swapList(suggests);
    }

    @Override
    public void showError(Throwable e)
    {
        Utils.showToast(this, e.getMessage());
    }

    private void initViews()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mEmptyView = (TextView) findViewById(R.id.search_empty_view);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new SearchResultAdapter();
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);

        mSearchView = (EditText) findViewById(R.id.search_view);

    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void subscribeOnTextChange()
    {
        subscriptions.add(RxTextView
                .textChanges(mSearchView)
                .delay(500, TimeUnit.MILLISECONDS)
                .map(new Func1<CharSequence, String>()
                {
                    @Override
                    public String call(CharSequence query)
                    {
                        return query.toString().trim();
                    }
                })
                .filter(new Func1<CharSequence, Boolean>()
                {
                    @Override
                    public Boolean call(CharSequence query)
                    {
                        return query.length() != 0;
                    }
                })
                .subscribe(new Action1<CharSequence>()
                {
                    @Override
                    public void call(CharSequence query)
                    {
                        mPresenter.search(query.toString(), Const.RESULT_LIMIT);
                    }
                }));
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
