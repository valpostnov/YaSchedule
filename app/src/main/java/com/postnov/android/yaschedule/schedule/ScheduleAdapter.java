package com.postnov.android.yaschedule.schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.Route;
import com.postnov.android.yaschedule.data.entity.RouteOptions;
import com.postnov.android.yaschedule.data.entity.Station;
import com.postnov.android.yaschedule.utils.TransportTypes;

import java.util.List;

/**
 * Created by platon on 20.05.2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>
{
    private List<Route> mRoutes;
    private View mEmptyView;

    private static final int TRAIN_DRAWABLE = R.drawable.ic_directions_railway;
    private static final int PLANE_DRAWABLE = R.drawable.ic_flight;
    private static final int BUS_DRAWABLE = R.drawable.ic_directions_bus;

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

        int drawable = 0;
        switch (routeOptions.getTransportType())
        {
            case TransportTypes.TRAIN:
                drawable = TRAIN_DRAWABLE;
                break;
            case TransportTypes.BUS:
                drawable = BUS_DRAWABLE;
                break;
            case TransportTypes.PLANE:
                drawable = PLANE_DRAWABLE;
                break;
        }

        holder.mRoute.setText(routeOptions.getTitle());
        holder.mRoute.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
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
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public List<Route> getList()
    {
        return mRoutes;
    }

    public void setEmptyView(View view)
    {
        mEmptyView = view;
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
