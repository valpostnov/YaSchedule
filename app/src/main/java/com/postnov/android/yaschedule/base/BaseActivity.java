package com.postnov.android.yaschedule.base;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;
import com.postnov.android.yaschedule.recent.RecentActivity;
import com.postnov.android.yaschedule.schedule.ScheduleActivity;
import com.postnov.android.yaschedule.search.SearchActivity;
import com.postnov.android.yaschedule.utils.Utils;

import butterknife.BindView;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_DATE = "normDate";
    public static final String EXTRA_QUERY_DATE = "queryDate";
    public static final String EXTRA_HINT = "hint";

    public static final int REQUEST_CODE_FROM = 0;
    public static final int REQUEST_CODE_TO = 1;
    public static final int REQUEST_RECENT_ROUTE = 2;

    public static final String EXTRA_CITY = "city";
    public static final String EXTRA_CODE = "cityCode";

    public static final String EXTRA_FROM_CODE = "fromCode";
    public static final String EXTRA_TO_CODE = "toCode";
    public static final String EXTRA_RECENT_ROUTE = "recentRoute";

    private String cityFromCode;
    private String cityToCode;

    private String reversedDate;
    private String normalDate;

    @BindView(R.id.from)            TextInputEditText fromView;
    @BindView(R.id.to)              TextInputEditText toView;
    @BindView(R.id.when)            TextInputEditText dateView;

    @BindView(R.id.hintFrom)        TextInputLayout fromInputLayout;
    @BindView(R.id.hintTo)          TextInputLayout toInputLayout;
    @BindView(R.id.hintWhen)        TextInputLayout dateInputLayout;
    @BindView(R.id.toolbar_main)    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            cityFromCode = savedInstanceState.getString(EXTRA_FROM_CODE);
            cityToCode = savedInstanceState.getString(EXTRA_TO_CODE);
            reversedDate = savedInstanceState.getString(EXTRA_QUERY_DATE);
        }
        initViews();
    }

    public void showSchedule(View view) {
        if (fromView.length() == 0) {
            showHintError(true, fromInputLayout);
        } else if (toView.length() == 0) {
            showHintError(true, toInputLayout);
        } else if (dateView.length() == 0) {
            showHintError(true, dateInputLayout);
        } else {
            Intent intent = new Intent(this, ScheduleActivity.class);
            intent.putExtra(EXTRA_QUERY_DATE, reversedDate);
            intent.putExtra(EXTRA_DATE, normalDate);
            intent.putExtra(EXTRA_FROM_CODE, cityFromCode);
            intent.putExtra(EXTRA_TO_CODE, cityToCode);

            showHintError(false, fromInputLayout, toInputLayout, dateInputLayout);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_FROM_CODE, cityFromCode);
        outState.putString(EXTRA_TO_CODE, cityToCode);
        outState.putString(EXTRA_QUERY_DATE, reversedDate);
        super.onSaveInstanceState(outState);
    }

    public void showSearchActivity(String hint, int requestCode) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(EXTRA_HINT, hint);
        startActivityForResult(intent, requestCode);
    }

    public void showRecentActivity(MenuItem item) {
        Intent intent = new Intent(this, RecentActivity.class);
        startActivityForResult(intent, REQUEST_RECENT_ROUTE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String city = data.getStringExtra(EXTRA_CITY);

            switch (requestCode) {
                case REQUEST_CODE_FROM:
                    fromView.setText(city);
                    cityFromCode = data.getStringExtra(EXTRA_CODE);
                    break;

                case REQUEST_CODE_TO:
                    toView.setText(city);
                    cityToCode = data.getStringExtra(EXTRA_CODE);
                    break;

                case REQUEST_RECENT_ROUTE:
                    RecentRoute route = data.getParcelableExtra(EXTRA_RECENT_ROUTE);
                    fromView.setText(route.getFrom());
                    toView.setText(route.getTo());
                    cityFromCode = route.getFromCode();
                    cityToCode = route.getToCode();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.from:
                showSearchActivity(getString(R.string.hint_from), REQUEST_CODE_FROM);
                break;
            case R.id.to:
                showSearchActivity(getString(R.string.hint_to), REQUEST_CODE_TO);
                break;
            case R.id.when:
                showDatePickerDialog();
                break;
        }
        showHintError(false, fromInputLayout, toInputLayout, dateInputLayout);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        reversedDate = Utils.formatDateReverse(dayOfMonth, monthOfYear + 1, year);
        normalDate = Utils.toShortDate(dayOfMonth, monthOfYear);
        dateView.setText(normalDate);
    }

    public void swapCities(MenuItem item) {
        if (fromView.length() != 0 && toView.length() != 0) {
            String fromTmp = fromView.getText().toString();
            String toTmp = toView.getText().toString();
            String codeFromTmp = cityFromCode;

            cityFromCode = cityToCode;
            cityToCode = codeFromTmp;

            fromView.setText(toTmp);
            toView.setText(fromTmp);
        }
    }

    private void initViews() {
        fromView.setOnClickListener(this);
        toView.setOnClickListener(this);
        dateView.setOnClickListener(this);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Utils.getYear(), Utils.getMonthOfYear() - 1, Utils.getDayOfMonth());
        datePickerDialog.getDatePicker().setMinDate(Utils.getMinDayInYear());
        datePickerDialog.getDatePicker().setMaxDate(Utils.getMaxDayInYear());

        datePickerDialog.show();
    }

    private void showHintError(boolean show, TextInputLayout... layouts) {
        if (show) {
            for (TextInputLayout l : layouts) l.setError(getString(R.string.hint_error_text));
            return;
        }

        for (TextInputLayout l : layouts) l.setError("");
    }
}
