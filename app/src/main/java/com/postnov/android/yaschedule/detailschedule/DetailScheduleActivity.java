package com.postnov.android.yaschedule.detailschedule;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Route;
import com.postnov.android.yaschedule.data.source.schedule.local.ScheduleLocalDataSource;
import com.postnov.android.yaschedule.detailschedule.interfaces.DetailSchedulePresenter;
import com.postnov.android.yaschedule.detailschedule.interfaces.DetailScheduleView;
import com.postnov.android.yaschedule.schedule.ScheduleActivity;
import com.postnov.android.yaschedule.utils.Utils;

public class DetailScheduleActivity extends AppCompatActivity implements DetailScheduleView
{
    private DetailSchedulePresenter mPresenter;

    private int listPosition;
    private TextView mFromStation;
    private TextView mToStation;
    private TextView mArrival;
    private TextView mDeparture;
    private TextView mFromCity;
    private TextView mToCity;
    private TextView mCarrier;
    private TextView mTransport;
    private TextView mTransportTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initToolbar();
        initViews();
        mPresenter = new DetailSchedulePresenterImpl(
                new ScheduleLocalDataSource(this),
                Injection.provideScheduleDataSource());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int menuItemId = item.getItemId();
        switch (menuItemId)
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        mPresenter.fetchSchedule(listPosition);
    }

    @Override
    protected void onPause()
    {
        mPresenter.unbind();
        super.onPause();
    }

    @Override
    public void loadSchedule(Route route)
    {
        mFromStation.setText(route.getFromStation().getTitle());
        mToStation.setText(route.getToStation().getTitle());

        mDeparture.setText(route.getDeparture());
        mArrival.setText(route.getArrival());

        mCarrier.setText(route.getRouteOptions().getCarrier().getTitle());
        mTransport.setText(String.format("%s %s",
                route.getRouteOptions().getNumber(),
                route.getRouteOptions().getTitle()).trim());

        mTransportTitle.setText(Utils.getTransportTitleRu(route.getRouteOptions().getTransportType()));
        mTransportTitle.setCompoundDrawablesWithIntrinsicBounds(
                Utils.getTransportImage(route.getRouteOptions().getTransportType(), true), 0, 0, 0);
    }

    @Override
    public void showError(Throwable e)
    {
        Utils.showToast(this, e.getMessage());
    }

    public void onClickSave(View view)
    {
        Snackbar snackbar = Snackbar.make(view, "Добавлено в закладки", Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorGrey800);
        snackbar.show();
        /*Route route = null;
        mPresenter.save(route);*/
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailschedule_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews()
    {
        listPosition = getIntent().getIntExtra(ScheduleActivity.EXTRA_POSITION, 0);
        mFromStation = (TextView) findViewById(R.id.detail_schedule_station_from);
        mToStation = (TextView) findViewById(R.id.detail_schedule_station_to);
        mArrival = (TextView) findViewById(R.id.detail_schedule_date_end);
        mDeparture = (TextView) findViewById(R.id.detail_schedule_date_start);
        mFromCity = (TextView) findViewById(R.id.detail_schedule_from);
        mToCity = (TextView) findViewById(R.id.detail_schedule_to);
        mCarrier = (TextView) findViewById(R.id.detail_schedule_carrier);
        mTransport = (TextView) findViewById(R.id.detail_schedule_tt);
        mTransportTitle = (TextView) findViewById(R.id.detail_schedule_tt_title);

        mFromCity.setText(getIntent().getStringExtra(ScheduleActivity.EXTRA_FROM_CITY));
        mToCity.setText(getIntent().getStringExtra(ScheduleActivity.EXTRA_TO_CITY));
    }
}
