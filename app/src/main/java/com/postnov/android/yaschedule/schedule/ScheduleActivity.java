package com.postnov.android.yaschedule.schedule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.MainActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.schedule.interfaces.SchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;
import com.postnov.android.yaschedule.utils.Const;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;
import com.postnov.android.yaschedule.utils.SearchQueryBuilder;
import com.postnov.android.yaschedule.utils.TransportTypes;
import com.postnov.android.yaschedule.utils.Utils;

public class ScheduleActivity extends AppCompatActivity implements ScheduleView
{
    private ScheduleAdapter mAdapter;
    private SchedulePresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mEmptyView;
    private TextView mScheduleSubHeaderText;

    private AppCompatRadioButton mAllFilter;
    private AppCompatRadioButton mBusFilter;
    private AppCompatRadioButton mTrainFilter;
    private AppCompatRadioButton mPlaneFilter;
    private AppCompatRadioButton mSuburbanFilter;
    private AppCompatRadioButton mSeaFilter;

    private String mCityFromCode;
    private String mCityToCode;
    private String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        mPresenter = new SchedulePresenterImpl(Injection.provideScheduleDataSource());
        initViews();
        initToolbar();
        mCityFromCode = getIntent().getStringExtra(MainActivity.EXTRA_FROM_CODE);
        mCityToCode = getIntent().getStringExtra(MainActivity.EXTRA_TO_CODE);
        mDate = getIntent().getStringExtra(MainActivity.EXTRA_QUERY_DATE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        mPresenter.getSchedule(
                SearchQueryBuilder.builder()
                    .setApiKey(Const.API_KEY)
                    .setFormat(Const.FORMAT_JSON)
                    .setFrom(mCityFromCode)
                    .setTo(mCityToCode)
                    .setPage(1)
                    .setDate(mDate)
                    .build());
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

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mAdapter = new ScheduleAdapter();
        mAdapter.setEmptyView(mEmptyView);
        recyclerView.setAdapter(mAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.loading_title));

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mScheduleSubHeaderText = (TextView) findViewById(R.id.schedule_subheader_text);

        mAllFilter = (AppCompatRadioButton) findViewById(R.id.all_filter);
        mBusFilter = (AppCompatRadioButton) findViewById(R.id.bus_filter);
        mTrainFilter = (AppCompatRadioButton) findViewById(R.id.train_filter);
        mPlaneFilter = (AppCompatRadioButton) findViewById(R.id.plane_filter);
        mSuburbanFilter = (AppCompatRadioButton) findViewById(R.id.suburban_filter);
        mSeaFilter = (AppCompatRadioButton) findViewById(R.id.sea_filter);
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.scheduleToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
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
    public void onBackPressed()
    {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
        {
            collapseBottomSheet();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showList(Response response)
    {
        mAdapter.swapList(response.getRoutes());
    }

    @Override
    public void showError(Throwable e)
    {
        Utils.showToast(this, e.getMessage());
    }

    @Override
    public void showProgressDialog()
    {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog()
    {
        mProgressDialog.dismiss();
    }

    public void showBottomSheetFilter(View view)
    {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
        {
            expandBottomSheet();
            return;
        }
        collapseBottomSheet();
    }

    private void expandBottomSheet()
    {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void collapseBottomSheet()
    {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void applyFilter(View view)
    {
        String transport = "";
        String transportTitle = "";

        if (mAllFilter.isChecked())
        {
            transportTitle = getString(R.string.filter_all);
        }
        else if (mBusFilter.isChecked())
        {
            transport = TransportTypes.BUS;
            transportTitle = getString(R.string.bus);
        }
        else if (mTrainFilter.isChecked())
        {
            transport = TransportTypes.TRAIN;
            transportTitle = getString(R.string.train);
        }
        else if (mPlaneFilter.isChecked())
        {
            transport = TransportTypes.PLANE;
            transportTitle = getString(R.string.plane);
        }
        else if (mSuburbanFilter.isChecked())
        {
            transport = TransportTypes.SUBURBAN;
            transportTitle = getString(R.string.suburban);
        }
        else if (mSeaFilter.isChecked())
        {
            transport = TransportTypes.SEA;
            transportTitle = getString(R.string.sea);
        }

        mPresenter.getSchedule(SearchQueryBuilder
                .builder()
                .setApiKey(Const.API_KEY)
                .setFormat(Const.FORMAT_JSON)
                .setTransport(transport)
                .setFrom(mCityFromCode)
                .setTo(mCityToCode)
                .setPage(1)
                .setDate(mDate)
                .build());

        mScheduleSubHeaderText.setText(transportTitle);
        collapseBottomSheet();
    }
}
