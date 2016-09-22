package com.postnov.android.yaschedule.stations;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.postnov.android.yaschedule.App;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.stations.Stop;
import com.postnov.android.yaschedule.schedule.ScheduleActivity;
import com.postnov.android.yaschedule.stations.interfaces.StationsPresenter;
import com.postnov.android.yaschedule.stations.interfaces.StationsView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;
import com.postnov.android.yaschedule.utils.StationsQueryBuilder;

import java.util.List;

public class StationsActivity extends AppCompatActivity implements StationsView {
    private static final String TAG = "StationsActivity";
    private StationsPresenter mPresenter;

    private StationsAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private String mUID;
    private String mDate;
    private String mFromCode;
    private String mToCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        mPresenter = new StationsPresenterImpl(App.get(this).stationsDataSource());
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
        mPresenter.bind(this);
        mPresenter.fetchStations(StationsQueryBuilder
                .builder()
                .setDate(mDate)
                .setLang(Const.LANG_RU)
                .setUID(mUID)
                .build());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unbind();
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
                mAdapter.showAllStops();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadStations(List<Stop> stopList) {
        mAdapter.swapList(stopList);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showError(Throwable e) {
        Log.e(TAG, e.getMessage());
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stations_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        TextView mEmptyView = (TextView) findViewById(R.id.stations_empty_view);

        mAdapter = new StationsAdapter(mFromCode, mToCode);
        mAdapter.setEmptyView(mEmptyView);
        recyclerView.setAdapter(mAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.loading_stations));
    }

    private void iniToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_stations);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setTitle(R.string.station_activity_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}