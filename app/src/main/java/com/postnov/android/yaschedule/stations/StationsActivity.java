package com.postnov.android.yaschedule.stations;

import android.app.ProgressDialog;
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
import com.postnov.android.yaschedule.data.entity.stations.Stop;
import com.postnov.android.yaschedule.schedule.ScheduleActivity;
import com.postnov.android.yaschedule.stations.interfaces.IStationsPresenter;
import com.postnov.android.yaschedule.stations.interfaces.IStationsView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;
import com.postnov.android.yaschedule.utils.StationsQueryBuilder;

import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

public class StationsActivity extends AppCompatActivity implements IStationsView {
    private IStationsPresenter presenter;
    private StationsAdapter stationsAdapter;

    private ProgressDialog progressDialog;
    private String mUID;
    private String mDate;
    private String mFromCode;
    private String mToCode;

    @BindView(R.id.stations_recyclerview)   RecyclerView rv;
    @BindView(R.id.stations_empty_view)     View emptyView;
    @BindView(R.id.toolbar_stations)        Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        Timber.tag("StationsActivity");

        presenter = new StationsPresenter(App.get(this).stationsDataSource());

        mUID = getIntent().getStringExtra(ScheduleActivity.EXTRA_UID);
        mDate = getIntent().getStringExtra(ScheduleActivity.EXTRA_DATE);
        mFromCode = getIntent().getStringExtra(ScheduleActivity.EXTRA_CODE_FROM);
        mToCode = getIntent().getStringExtra(ScheduleActivity.EXTRA_CODE_TO);
        iniToolbar();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.fetchStations(StationsQueryBuilder
                .builder()
                .setDate(mDate)
                .setLang(Const.LANG_RU)
                .setUID(mUID)
                .build());
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_hide_stations:
                stationsAdapter.showAllStops();
                emptyView.setVisibility(stationsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadStations(List<Stop> stopList) {
        stationsAdapter.swapList(stopList);
        emptyView.setVisibility(stationsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(Throwable e) {
        Timber.wtf(e, e.getMessage());
    }

    private void initViews() {
        stationsAdapter = new StationsAdapter(mFromCode, mToCode);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rv.setAdapter(stationsAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading_stations));
    }

    private void iniToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setTitle(R.string.station_activity_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}