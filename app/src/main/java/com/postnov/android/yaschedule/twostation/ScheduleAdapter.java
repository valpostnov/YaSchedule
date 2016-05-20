package com.postnov.android.yaschedule.twostation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.Route;
import com.postnov.android.yaschedule.data.entity.RouteOptions;
import com.postnov.android.yaschedule.data.entity.Station;

import java.util.List;

/**
 * Created by platon on 20.05.2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>
{
    private List<Route> mRoutes;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Route route = getList().get(position);
        RouteOptions routeOptions = route.getRouteOptions();
        Station from = route.getFromStation();
        Station to = route.getToStation();

        holder.mRoute.setText(routeOptions.getTitle());
        holder.mStartTime.setText(route.getDeparture());
        holder.mFinishTime.setText(route.getArrival());
        holder.mStationFrom.setText(from.getTitle());
        holder.mStationTo.setText(to.getTitle());
    }

    @Override
    public int getItemCount()
    {
        if (null == mRoutes) return 0;
        return mRoutes.size();
    }

    public void swapList(List<Route> variantsList)
    {
        mRoutes = variantsList;
        notifyDataSetChanged();
    }

    public List<Route> getList()
    {
        return mRoutes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mRoute;
        public TextView mStartTime;
        public TextView mFinishTime;
        public TextView mStationFrom;
        public TextView mStationTo;

        public ViewHolder(View view)
        {
            super(view);
            mRoute = (TextView) view.findViewById(R.id.route);
            mStartTime = (TextView) view.findViewById(R.id.startTime);
            mFinishTime = (TextView) view.findViewById(R.id.finishTime);
            mStationFrom = (TextView) view.findViewById(R.id.stationFrom);
            mStationTo = (TextView) view.findViewById(R.id.stationTo);
        }
    }
}
