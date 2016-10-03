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

    private String mCityFromCode;
    private String mCityToCode;

    private String mReversedDate;
    private String mNormalDate;

    private TextInputEditText mFromView;
    private TextInputEditText mToView;
    private TextInputEditText mDateView;

    private TextInputLayout mFromInputLayout;
    private TextInputLayout mToInputLayout;
    private TextInputLayout mDateInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mCityFromCode = savedInstanceState.getString(EXTRA_FROM_CODE);
            mCityToCode = savedInstanceState.getString(EXTRA_TO_CODE);
            mReversedDate = savedInstanceState.getString(EXTRA_QUERY_DATE);
        }
        initToolbar();
        initViews();
    }

    public void showSchedule(View view) {
        if (mFromView.length() == 0) showHintError(true, mFromInputLayout);
        else if (mToView.length() == 0) showHintError(true, mToInputLayout);
        else if (mDateView.length() == 0) showHintError(true, mDateInputLayout);
        else {
            Intent intent = new Intent(this, ScheduleActivity.class);
            intent.putExtra(EXTRA_QUERY_DATE, mReversedDate);
            intent.putExtra(EXTRA_DATE, mNormalDate);
            intent.putExtra(EXTRA_FROM_CODE, mCityFromCode);
            intent.putExtra(EXTRA_TO_CODE, mCityToCode);

            showHintError(false, mFromInputLayout, mToInputLayout, mDateInputLayout);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_FROM_CODE, mCityFromCode);
        outState.putString(EXTRA_TO_CODE, mCityToCode);
        outState.putString(EXTRA_QUERY_DATE, mReversedDate);
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
                    mFromView.setText(city);
                    mCityFromCode = data.getStringExtra(EXTRA_CODE);
                    break;

                case REQUEST_CODE_TO:
                    mToView.setText(city);
                    mCityToCode = data.getStringExtra(EXTRA_CODE);
                    break;

                case REQUEST_RECENT_ROUTE:
                    RecentRoute route = data.getParcelableExtra(EXTRA_RECENT_ROUTE);
                    mFromView.setText(route.getFrom());
                    mToView.setText(route.getTo());
                    mCityFromCode = route.getFromCode();
                    mCityToCode = route.getToCode();
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
        showHintError(false, mFromInputLayout, mToInputLayout, mDateInputLayout);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mReversedDate = Utils.formatDateReverse(dayOfMonth, monthOfYear + 1, year);
        mNormalDate = Utils.toShortDate(dayOfMonth, monthOfYear);
        mDateView.setText(mNormalDate);
    }

    public void swapCities(MenuItem item) {
        if (mFromView.length() != 0 && mToView.length() != 0) {
            String fromTmp = mFromView.getText().toString();
            String toTmp = mToView.getText().toString();
            String codeFromTmp = mCityFromCode;

            mCityFromCode = mCityToCode;
            mCityToCode = codeFromTmp;

            mFromView.setText(toTmp);
            mToView.setText(fromTmp);
        }
    }

    private void initViews() {
        mFromView = (TextInputEditText) findViewById(R.id.from);
        mFromView.setOnClickListener(this);

        mToView = (TextInputEditText) findViewById(R.id.to);
        mToView.setOnClickListener(this);

        mDateView = (TextInputEditText) findViewById(R.id.when);
        mDateView.setOnClickListener(this);

        mFromInputLayout = (TextInputLayout) findViewById(R.id.hintFrom);
        mToInputLayout = (TextInputLayout) findViewById(R.id.hintTo);
        mDateInputLayout = (TextInputLayout) findViewById(R.id.hintWhen);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
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
