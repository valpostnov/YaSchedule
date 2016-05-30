package com.postnov.android.yaschedule.fave;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Route;
import com.postnov.android.yaschedule.fave.interfaces.FaveView;

import java.util.List;

public class FaveActivity extends AppCompatActivity implements FaveView
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fave);
    }

    @Override
    public void loadFaves(List<Route> routes)
    {

    }

    @Override
    public void showError(Throwable e)
    {

    }
}
