package com.postnov.android.yaschedule.schedule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.MainActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.schedule.interfaces.SchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;
import com.postnov.android.yaschedule.stations.StationsActivity;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;
import com.postnov.android.yaschedule.utils.SearchQueryBuilder;
import com.postnov.android.yaschedule.utils.TransportTypes;
import com.postnov.android.yaschedule.utils.Utils;

import retrofit2.adapter.rxjava.HttpException;

public class ScheduleActivity extends AppCompatActivity implements ScheduleView, ScheduleAdapter.OnItemClickListener
{
    private static final String TAG = "ScheduleActivity";
    public static final String EXTRA_UID = "uid";
    public static final String EXTRA_DATE = "selectedDate";
    public static final String EXTRA_CODE_FROM = "fromCode";
    public static final String EXTRA_CODE_TO = "toCode";

    private String defaultTt = "";

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

    private String mCityFromCode;
    private String mCityToCode;
    private String mDate;

    private Response mResponse;

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
        search(defaultTt, 1);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mPresenter.unsubscribe();
        mPresenter.unbind();
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
        mResponse = response;
        mAdapter.swapList(response.getRoutes());
    }

    @Override
    public void showError(Throwable e)
    {
        Log.e(TAG, e.getMessage());
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

    @Override
    public void onItemClick(View view, int position)
    {
        String uid = mResponse.getRoutes().get(position).getRouteOptions().getUid();
        String from = mResponse.getRoutes().get(position).getFromStation().getCode();
        String to = mResponse.getRoutes().get(position).getToStation().getCode();

        Intent intent = new Intent(this, StationsActivity.class);
        intent.putExtra(EXTRA_UID, uid);
        intent.putExtra(EXTRA_CODE_FROM, from);
        intent.putExtra(EXTRA_CODE_TO, to);
        intent.putExtra(EXTRA_DATE, mDate);

        startActivity(intent);
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

        search(transport, 1);
        defaultTt = transport;

        mScheduleSubHeaderText.setText(transportTitle);
        collapseBottomSheet();
    }

    private void initViews()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.schedule_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        mEmptyView = (TextView) findViewById(R.id.schedule_emptyview);

        mAdapter = new ScheduleAdapter();
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemClickListener(this);
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
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void expandBottomSheet()
    {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void collapseBottomSheet()
    {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void search(String transport, int page)
    {
        mPresenter.search(SearchQueryBuilder
                .builder()
                .setTransport(transport)
                .setFrom(mCityFromCode)
                .setTo(mCityToCode)
                .setDate(mDate)
                .setPage(page)
                .build());
    }
}