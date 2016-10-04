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

import com.postnov.android.yaschedule.App;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.base.BaseActivity;
import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.postnov.android.yaschedule.recent.interfaces.IRecentPresenter;
import com.postnov.android.yaschedule.recent.interfaces.IRecentView;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecentActivity extends AppCompatActivity implements IRecentView, RecentAdapter.OnItemClickListener {
    private RecentAdapter recentAdapter;
    private IRecentPresenter presenter;

    @BindView(R.id.recent_emptyview)    View emptyView;
    @BindView(R.id.recent_toolbar)      Toolbar toolbar;
    @BindView(R.id.recent_list)         RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        ButterKnife.bind(this);
        Timber.tag("RecentActivity");

        presenter = new RecentPresenter(App.get(this).recentDataSource());
        iniToolbar();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.fetchRecentList();
    }

    @Override
    protected void onPause() {
        presenter.unbind();
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
        recentAdapter.swapList(routes);
        emptyView.setVisibility(recentAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }


    public void clearRecentList(MenuItem item) {
        presenter.clearRecentList();
    }

    @Override
    public void showError(Throwable e) {
        Timber.wtf(e, e.getMessage());
    }

    private void iniToolbar() {
        toolbar.setTitle(R.string.fave_activity_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        recentAdapter = new RecentAdapter();
        recentAdapter.setOnItemClickListener(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rv.setAdapter(recentAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        RecentRoute route = recentAdapter.getList().get(position);
        Intent intent = new Intent();
        intent.putExtra(BaseActivity.EXTRA_RECENT_ROUTE, route);
        setResult(RESULT_OK, intent);
        finish();
    }
}
