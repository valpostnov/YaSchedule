package com.postnov.android.yaschedule.recent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.postnov.android.yaschedule.App;
import com.postnov.android.yaschedule.base.BaseActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.postnov.android.yaschedule.recent.interfaces.RecentPresenter;
import com.postnov.android.yaschedule.recent.interfaces.RecentView;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;

import java.util.List;

public class RecentActivity extends AppCompatActivity implements RecentView, RecentAdapter.OnItemClickListener {
    private RecentAdapter mAdapter;
    private RecentPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        mPresenter = new RecentPresenterImpl(App.get(this).recentDataSource());
        iniToolbar();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bind(this);
        mPresenter.fetchRecentList();
    }

    @Override
    protected void onPause() {
        mPresenter.unbind();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadRecentList(List<RecentRoute> routes) {
        mAdapter.swapList(routes);
    }


    public void clearRecentList(MenuItem item) {
        mPresenter.clearRecentList();
    }

    @Override
    public void showError(Throwable e) {
        e.printStackTrace();
    }

    private void iniToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.recent_toolbar);
        toolbar.setTitle(R.string.fave_activity_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recent_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        TextView mEmptyView = (TextView) findViewById(R.id.recent_emptyview);

        mAdapter = new RecentAdapter();
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        RecentRoute route = mAdapter.getList().get(position);

        Intent intent = new Intent();
        intent.putExtra(BaseActivity.EXTRA_RECENT_ROUTE, route);
        setResult(RESULT_OK, intent);
        finish();
    }
}
