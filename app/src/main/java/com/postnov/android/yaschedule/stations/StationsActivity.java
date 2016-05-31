package com.postnov.android.yaschedule.stations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.stations.Stop;
import com.postnov.android.yaschedule.schedule.ScheduleActivity;
import com.postnov.android.yaschedule.schedule.ScheduleAdapter;
import com.postnov.android.yaschedule.stations.interfaces.StationsPresenter;
import com.postnov.android.yaschedule.stations.interfaces.StationsView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;
import com.postnov.android.yaschedule.utils.StationsQueryBuilder;
import com.postnov.android.yaschedule.utils.Utils;

import java.util.List;

public class StationsActivity extends AppCompatActivity implements StationsView
{
    private StationsPresenter mPresenter;

    private StationsAdapter mAdapter;
    private TextView mEmptyView;
    private String mUID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
        mPresenter = new StationsPresenterImpl(Injection.provideStationsDataSource());
        iniToolbar();
        initViews();
        mUID = getIntent().getStringExtra(ScheduleActivity.EXTRA_UID);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        mPresenter.fetchStations(StationsQueryBuilder.builder().setLang(Const.LANG_RU).setUID(mUID).build());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mPresenter.unsubscribe();
        mPresenter.unbind();
    }

    @Override
    public void loadStations(List<Stop> stopList)
    {
        mAdapter.swapList(stopList);
    }

    @Override
    public void showError(Throwable e)
    {
        Utils.showToast(this, e.getMessage());
    }

    private void initViews()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stations_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        mEmptyView = (TextView) findViewById(R.id.stations_empty_view);

        mAdapter = new StationsAdapter();
        mAdapter.setEmptyView(mEmptyView);
        recyclerView.setAdapter(mAdapter);
    }

    private void iniToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_stations);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
