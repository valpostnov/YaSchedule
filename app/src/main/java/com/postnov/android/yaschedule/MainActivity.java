package com.postnov.android.yaschedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.postnov.android.yaschedule.schedule.ScheduleActivity;
import com.postnov.android.yaschedule.search.SearchActivity;
import com.postnov.android.yaschedule.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener
{
    public static final String EXTRA_ROUTE = "route";
    public static final String EXTRA_DATE = "normDate";
    public static final String EXTRA_QUERY_DATE = "queryDate";
    public static final int REQUEST_CODE_FROM = 0;
    public static final int REQUEST_CODE_TO = 1;
    public static final String EXTRA_CITY = "city";
    public static final String EXTRA_CODE = "cityCode";

    public static final String EXTRA_FROM_CODE = "fromCode";
    public static final String EXTRA_TO_CODE = "toCode";

    private String mCityFromCode;
    private String mCityToCode;
    private String mReversedDate;
    private String mNormalDate;

    private TextInputEditText mFrom;
    private TextInputEditText mTo;
    private TextInputEditText mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViews();
    }

    private void initViews()
    {
        mFrom = (TextInputEditText) findViewById(R.id.from);
        mFrom.setOnClickListener(this);

        mTo = (TextInputEditText) findViewById(R.id.to);
        mTo.setOnClickListener(this);

        mDate = (TextInputEditText) findViewById(R.id.when);
        mDate.setOnClickListener(this);
    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    public void showSchedule(View view)
    {
        Intent intent = new Intent(this, ScheduleActivity.class);

        StringBuilder route = new StringBuilder();
        route.append(mFrom.getText());
        route.append(" - ");
        route.append(mTo.getText());

        intent.putExtra(EXTRA_ROUTE, route.toString());
        intent.putExtra(EXTRA_QUERY_DATE, mReversedDate);
        intent.putExtra(EXTRA_DATE, mNormalDate);
        intent.putExtra(EXTRA_FROM_CODE, mCityFromCode);
        intent.putExtra(EXTRA_TO_CODE, mCityToCode);

        startActivity(intent);
    }

    public void showSearchActivity(int requestCode)
    {
        startActivityForResult(new Intent(this, SearchActivity.class), requestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        switch (itemId)
        {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data != null)
        {
            String city = data.getStringExtra(EXTRA_CITY);

            switch (requestCode)
            {
                case REQUEST_CODE_FROM:
                    mFrom.setText(city);
                    mCityFromCode = data.getStringExtra(EXTRA_CODE);
                    break;

                case REQUEST_CODE_TO:
                    mTo.setText(city);
                    mCityToCode = data.getStringExtra(EXTRA_CODE);;
                    break;
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.from:
                showSearchActivity(REQUEST_CODE_FROM);
                break;
            case R.id.to:
                showSearchActivity(REQUEST_CODE_TO);
                break;
            case R.id.when:
                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Utils.getYear(), Utils.getMonthOfYear() - 1, Utils.getDayOfYear());
        datePickerDialog.getDatePicker().setMinDate(Utils.getMinDayInYear());
        datePickerDialog.getDatePicker().setMaxDate(Utils.getMaxDayInYear());

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        mReversedDate = Utils.formatDateReverse(dayOfMonth, monthOfYear + 1, year);
        mNormalDate = Utils.formatDateNorm(dayOfMonth, monthOfYear + 1, year);
        mDate.setText(mNormalDate);
    }
}
