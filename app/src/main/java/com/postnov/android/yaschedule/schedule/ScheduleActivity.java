package com.postnov.android.yaschedule.schedule;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.MainActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.Response;
import com.postnov.android.yaschedule.schedule.interfaces.SchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.SearchQuery;

public class ScheduleActivity extends AppCompatActivity implements ScheduleView
{
    private ScheduleAdapter mAdapter;
    private SchedulePresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mPresenter = new SchedulePresenterImpl(Injection.provideDataSource());
        initViews();
        initToolbar();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        mPresenter.getSchedule(SearchQuery.builder().build());
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mPresenter.unsubscribe();
        mPresenter.unbind();
    }

    private void initViews()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scheduleList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mAdapter = new ScheduleAdapter();
        mAdapter.setEmptyView(mEmptyView);
        recyclerView.setAdapter(mAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.loading_title));
    }

    private void initToolbar()
    {
        String title = getIntent().getStringExtra(MainActivity.EXTRA_ROUTE);
        String subtitle = getIntent().getStringExtra(MainActivity.EXTRA_DATE);

        TextView titleView = (TextView) findViewById(R.id.schedule_title);
        TextView subtitleView = (TextView) findViewById(R.id.schedule_subtitle);

        titleView.setText(title);
        subtitleView.setText(subtitle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.scheduleToolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showList(Response response)
    {
        mAdapter.swapList(response.getRoutes());
    }

    @Override
    public void showError(Throwable e)
    {
        Toast.makeText(ScheduleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.hide();
    }
}
