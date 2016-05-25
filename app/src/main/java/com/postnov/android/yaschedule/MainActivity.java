package com.postnov.android.yaschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.postnov.android.yaschedule.schedule.ScheduleActivity;
import com.postnov.android.yaschedule.search.SearchActivity;

public class MainActivity extends AppCompatActivity
{
    public static final String EXTRA_ROUTE = "route";
    public static final String EXTRA_DATE = "date";
    public static final int REQUEST_CODE_FROM = 0;
    public static final int REQUEST_CODE_TO = 1;
    public static final String EXTRA_CITY = "city";
    public static final String EXTRA_CODE = "cityCode";

    public static final String EXTRA_FROM_CODE = "fromCode";
    public static final String EXTRA_TO_CODE = "toCode";

    private String mCityFromCode;
    private String mCityToCode;

    private EditText mFrom;
    private EditText mTo;
    private EditText mDate;

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
        mFrom = (EditText) findViewById(R.id.from);
        mFrom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSearchActivity(REQUEST_CODE_FROM);
            }
        });

        mTo = (EditText) findViewById(R.id.to);
        mTo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showSearchActivity(REQUEST_CODE_TO);
            }
        });

        mDate = (EditText) findViewById(R.id.when);
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
        String date = mDate.getText().toString();

        route.append(mFrom.getText());
        route.append(" - ");
        route.append(mTo.getText());

        intent.putExtra(EXTRA_ROUTE, route.toString());
        intent.putExtra(EXTRA_DATE, date);
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
}
