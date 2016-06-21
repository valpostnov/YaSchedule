package com.postnov.android.yaschedule.schedule;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;

import com.postnov.android.yaschedule.Injection;
import com.postnov.android.yaschedule.MainActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.schedule.interfaces.SchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;
import com.postnov.android.yaschedule.stations.StationsActivity;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;
import com.postnov.android.yaschedule.utils.NetworkManager;
import com.postnov.android.yaschedule.utils.SearchQueryBuilder;
import com.postnov.android.yaschedule.utils.TransportTypes;
import com.postnov.android.yaschedule.utils.Utils;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionError;

import java.util.Map;

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;

public class ScheduleActivity extends AppCompatActivity implements ScheduleView,
        ScheduleAdapter.OnItemClickListener, DatePickerDialog.OnDateSetListener,
        ScheduleAdapter.OnEndlessListener {

    private static final String TAG = "ScheduleActivity";

    public static final String EXTRA_UID = "com.postnov.schedule.UID";
    public static final String EXTRA_DATE = "com.postnov.schedule.SELECTED)DATE";
    public static final String EXTRA_CODE_FROM = "com.postnov.schedule.FROM_CODE";
    public static final String EXTRA_CODE_TO = "com.postnov.schedule.TO_CODE";

    private ScheduleAdapter mAdapter;
    private SchedulePresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mSubHeaderText;

    private AppCompatRadioButton mAllTt;
    private AppCompatRadioButton mBusTt;
    private AppCompatRadioButton mTrainTt;
    private AppCompatRadioButton mPlaneTt;
    private AppCompatRadioButton mSuburbanTt;

    private String mCityFromCode;
    private String mCityToCode;
    private String mTransport;
    private String mDate;

    private Response mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mPresenter = new SchedulePresenterImpl(
                Injection.provideScheduleDataSource(),
                Injection.provideRecentDataSource(getApplicationContext()),
                NetworkManager.getInstance(getApplicationContext()));

        initViews();
        initToolbar();

        mTransport = TransportTypes.ALL;
        mCityFromCode = getIntent().getStringExtra(MainActivity.EXTRA_FROM_CODE);
        mCityToCode = getIntent().getStringExtra(MainActivity.EXTRA_TO_CODE);
        mDate = getIntent().getStringExtra(MainActivity.EXTRA_QUERY_DATE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.bind(this);
        mPresenter.search(createQuery());
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
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_swap:
                swapCities();
                return true;

            case R.id.action_set_date:
                showDatePicker();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        switch (mBottomSheetBehavior.getState())
        {
            case STATE_EXPANDED:
                showHideBottomSheetFilter(null);
                break;
        }
        super.onBackPressed();
    }

    @Override
    public void showList(Response response)
    {
        if (response != null)
        {
            mResponse = response;
            mAdapter.swapList(response.getRoutes());
            return;
        }
        mAdapter.swapList(null);
    }

    @Override
    public void showError(Throwable e)
    {
        if (e instanceof NetworkConnectionError)
        {
            Utils.showToast(this, e.getMessage());
        }
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

    public void showHideBottomSheetFilter(View view)
    {
        switch (mBottomSheetBehavior.getState())
        {
            case STATE_EXPANDED:
                mBottomSheetBehavior.setState(STATE_COLLAPSED);
                break;

            case STATE_COLLAPSED:
                mBottomSheetBehavior.setState(STATE_EXPANDED);
                break;
        }
    }

    public void swapCities()
    {
        String fromTmp = mCityFromCode;
        mCityFromCode = mCityToCode;
        mCityToCode = fromTmp;
        mPresenter.search(createQuery());
    }

    public void showDatePicker()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Utils.getYear(),
                Utils.getMonthOfYear() - 1,
                Utils.getDayOfMonth());

        datePickerDialog.getDatePicker().setMinDate(Utils.getMinDayInYear());
        datePickerDialog.getDatePicker().setMaxDate(Utils.getMaxDayInYear());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        mDate = Utils.formatDateReverse(dayOfMonth, monthOfYear + 1, year);
        mPresenter.search(createQuery());
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


    @Override
    public void onLoadMore(int page)
    {

    }

    public void applyFilter(View view)
    {
        String transportTitle = "";

        if (mAllTt.isChecked())
        {
            mTransport = TransportTypes.ALL;
            transportTitle = getString(R.string.filter_all);
        }
        else if (mBusTt.isChecked())
        {
            mTransport = TransportTypes.BUS;
            transportTitle = getString(R.string.bus);
        }
        else if (mTrainTt.isChecked())
        {
            mTransport = TransportTypes.TRAIN;
            transportTitle = getString(R.string.train);
        }
        else if (mPlaneTt.isChecked())
        {
            mTransport = TransportTypes.PLANE;
            transportTitle = getString(R.string.plane);
        }
        else if (mSuburbanTt.isChecked())
        {
            mTransport = TransportTypes.SUBURBAN;
            transportTitle = getString(R.string.suburban);
        }

        mPresenter.search(createQuery());
        mSubHeaderText.setText(transportTitle);
        showHideBottomSheetFilter(null);
    }

    private void initViews()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        View emptyView = findViewById(R.id.schedule_emptyview);

        mAdapter = new ScheduleAdapter();
        mAdapter.setEmptyView(emptyView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnEndlessListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.schedule_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.loading_schedule));

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        mSubHeaderText = (TextView) findViewById(R.id.schedule_subheader_text);
        mAllTt = (AppCompatRadioButton) findViewById(R.id.all_filter);
        mBusTt = (AppCompatRadioButton) findViewById(R.id.bus_filter);
        mTrainTt = (AppCompatRadioButton) findViewById(R.id.train_filter);
        mPlaneTt = (AppCompatRadioButton) findViewById(R.id.plane_filter);
        mSuburbanTt = (AppCompatRadioButton) findViewById(R.id.suburban_filter);
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.schedule_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Map<String, String> createQuery()
    {
        return SearchQueryBuilder
                .builder()
                .setTransport(mTransport)
                .setFrom(mCityFromCode)
                .setTo(mCityToCode)
                .setDate(mDate)
                .build();
    }
}