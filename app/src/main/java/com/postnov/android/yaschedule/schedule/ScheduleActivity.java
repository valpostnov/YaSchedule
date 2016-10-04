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

import com.postnov.android.yaschedule.App;
import com.postnov.android.yaschedule.base.BaseActivity;
import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Response;
import com.postnov.android.yaschedule.schedule.interfaces.ISchedulePresenter;
import com.postnov.android.yaschedule.schedule.interfaces.ScheduleView;
import com.postnov.android.yaschedule.stations.StationsActivity;
import com.postnov.android.yaschedule.utils.DividerItemDecoration;
import com.postnov.android.yaschedule.utils.SearchQueryBuilder;
import com.postnov.android.yaschedule.utils.TransportTypes;
import com.postnov.android.yaschedule.utils.Utils;
import com.postnov.android.yaschedule.utils.exception.NetworkConnectionException;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;

public class ScheduleActivity extends AppCompatActivity implements ScheduleView,
        ScheduleAdapter.OnItemClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "ScheduleActivity";

    public static final String EXTRA_UID = "com.postnov.schedule.UID";
    public static final String EXTRA_DATE = "com.postnov.schedule.SELECTED)DATE";
    public static final String EXTRA_CODE_FROM = "com.postnov.schedule.FROM_CODE";
    public static final String EXTRA_CODE_TO = "com.postnov.schedule.TO_CODE";

    private ScheduleAdapter scheduleAdapter;
    private ISchedulePresenter presenter;
    private ProgressDialog progressDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.schedule_emptyview)          View emptyView;
    @BindView(R.id.schedule_subheader_text)     TextView subHeaderText;
    @BindView(R.id.schedule_toolbar)            Toolbar toolbar;
    @BindView(R.id.schedule_recyclerview)       RecyclerView rv;

    @BindView(R.id.all_filter)      AppCompatRadioButton allTt;
    @BindView(R.id.bus_filter)      AppCompatRadioButton busTt;
    @BindView(R.id.train_filter)    AppCompatRadioButton trainTt;
    @BindView(R.id.plane_filter)    AppCompatRadioButton planeTt;
    @BindView(R.id.suburban_filter) AppCompatRadioButton suburbanTt;

    private String cityFromCode;
    private String cityToCode;
    private String transport;
    private String date;

    private Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        Timber.tag("ScheduleActivity");

        presenter = new SchedulePresenter(
                App.get(this).scheduleDataSource(),
                App.get(this).recentDataSource(),
                App.get(this).networkManager());

        initViews();
        initToolbar();

        transport = TransportTypes.ALL;
        cityFromCode = getIntent().getStringExtra(BaseActivity.EXTRA_FROM_CODE);
        cityToCode = getIntent().getStringExtra(BaseActivity.EXTRA_TO_CODE);
        date = getIntent().getStringExtra(BaseActivity.EXTRA_QUERY_DATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.search(createQuery());
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    public void onBackPressed() {
        switch (bottomSheetBehavior.getState()) {
            case STATE_EXPANDED:
                showHideBottomSheetFilter(null);
                break;

            default:
                super.onBackPressed();
        }
    }

    @Override
    public void showList(Response response) {
        if (response != null) {
            this.response = response;
            scheduleAdapter.swapList(response.getRoutes());
            return;
        }
        scheduleAdapter.swapList(null);
        emptyView.setVisibility(scheduleAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable e) {
        if (e instanceof NetworkConnectionException) {
            Utils.showToast(this, e.getMessage());
        }
        Timber.wtf(e, e.getMessage());
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public void showHideBottomSheetFilter(View view) {
        switch (bottomSheetBehavior.getState()) {
            case STATE_EXPANDED:
                bottomSheetBehavior.setState(STATE_COLLAPSED);
                break;

            case STATE_COLLAPSED:
                bottomSheetBehavior.setState(STATE_EXPANDED);
                break;
        }
    }

    public void swapCities() {
        String fromTmp = cityFromCode;
        cityFromCode = cityToCode;
        cityToCode = fromTmp;
        presenter.search(createQuery());
    }

    public void showDatePicker() {
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
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date = Utils.formatDateReverse(dayOfMonth, monthOfYear + 1, year);
        presenter.search(createQuery());
    }

    @Override
    public void onItemClick(View view, int position) {
        String uid = response.getRoutes().get(position).getRouteOptions().getUid();
        String from = response.getRoutes().get(position).getFromStation().getCode();
        String to = response.getRoutes().get(position).getToStation().getCode();

        Intent intent = new Intent(this, StationsActivity.class);
        intent.putExtra(EXTRA_UID, uid);
        intent.putExtra(EXTRA_CODE_FROM, from);
        intent.putExtra(EXTRA_CODE_TO, to);
        intent.putExtra(EXTRA_DATE, date);

        startActivity(intent);
    }

    public void applyFilter(View view) {
        String transportTitle = "";

        if (allTt.isChecked()) {
            transport = TransportTypes.ALL;
            transportTitle = getString(R.string.filter_all);
        } else if (busTt.isChecked()) {
            transport = TransportTypes.BUS;
            transportTitle = getString(R.string.bus);
        } else if (trainTt.isChecked()) {
            transport = TransportTypes.TRAIN;
            transportTitle = getString(R.string.train);
        } else if (planeTt.isChecked()) {
            transport = TransportTypes.PLANE;
            transportTitle = getString(R.string.plane);
        } else if (suburbanTt.isChecked()) {
            transport = TransportTypes.SUBURBAN;
            transportTitle = getString(R.string.suburban);
        }

        presenter.search(createQuery());
        subHeaderText.setText(transportTitle);
        showHideBottomSheetFilter(null);
    }

    private void initViews() {
        scheduleAdapter = new ScheduleAdapter();
        scheduleAdapter.setOnItemClickListener(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rv.setAdapter(scheduleAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading_schedule));

        View bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private Map<String, String> createQuery() {
        return SearchQueryBuilder
                .builder()
                .setTransport(transport)
                .setFrom(cityFromCode)
                .setTo(cityToCode)
                .setDate(date)
                .build();
    }
}