package com.postnov.android.yaschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.postnov.android.yaschedule.schedule.ScheduleActivity;

public class MainActivity extends AppCompatActivity
{
    public static final String EXTRA_ROUTE = "route";
    public static final String EXTRA_DATE = "date";

    private EditText mFrom;
    private EditText mTo;
    private TextView mDate;

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
        mTo = (EditText) findViewById(R.id.to);
        mDate = (TextView) findViewById(R.id.when);
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

        String date = mDate.getText().toString();

        intent.putExtra(EXTRA_ROUTE, route.toString());
        intent.putExtra(EXTRA_DATE, date);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
