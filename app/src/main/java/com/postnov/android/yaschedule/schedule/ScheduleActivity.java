package com.postnov.android.yaschedule.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.postnov.android.yaschedule.Injection;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mPresenter = new SchedulePresenterImpl(Injection.provideDataSource());
        initViews();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        mPresenter.getSchedule(SearchQuery.builder().setApiKey(Const.API_KEY).build());
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

        mAdapter = new ScheduleAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showList(Response response)
    {
        mAdapter.swapList(response.getRoutes());
    }

    @Override
    public void showError()
    {
        Toast.makeText(ScheduleActivity.this, "Error", Toast.LENGTH_SHORT).show();
    }
}
