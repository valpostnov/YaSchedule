package com.postnov.android.yaschedule.schedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Route;
import com.postnov.android.yaschedule.data.entity.schedule.RouteOptions;
import com.postnov.android.yaschedule.data.entity.schedule.Station;
import com.postnov.android.yaschedule.utils.Utils;

import java.util.List;

/**
 * Created by platon on 20.05.2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<Route> routes;
    private OnItemClickListener onItemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Route route = getList().get(position);
        RouteOptions routeOptions = route.getRouteOptions();
        Station from = route.getFromStation();
        Station to = route.getToStation();

        int drawable = Utils.getTransportImage(routeOptions.getTransportType());

        String title = String.format("%s %s", routeOptions.getNumber(), routeOptions.getTitle()).trim();
        holder.mRoute.setText(title);
        holder.mRoute.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
        holder.mStartTime.setText(Utils.getOnlyTime(route.getDeparture()));
        holder.mFinishTime.setText(Utils.getOnlyTime(route.getArrival()));
        holder.mStartDate.setText(Utils.toShortDate(route.getDeparture()));
        holder.mFinishDate.setText(Utils.toShortDate(route.getArrival()));
        holder.mStationFrom.setText(Utils.splitTitle(from.getTitle()));
        holder.mStationTo.setText(Utils.splitTitle(to.getTitle()));
    }

    @Override
    public int getItemCount() {
        if (null == routes) return 0;
        return routes.size();
    }

    public void swapList(List<Route> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    public List<Route> getList() {
        return routes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mRoute;
        public TextView mStartTime;
        public TextView mFinishTime;
        public TextView mStartDate;
        public TextView mFinishDate;
        public TextView mStationFrom;
        public TextView mStationTo;

        public ViewHolder(View view) {
            super(view);
            mRoute = (TextView) view.findViewById(R.id.route);
            mStartTime = (TextView) view.findViewById(R.id.startTime);
            mFinishTime = (TextView) view.findViewById(R.id.finishTime);
            mStartDate = (TextView) view.findViewById(R.id.startDate);
            mFinishDate = (TextView) view.findViewById(R.id.finishDate);
            mStationFrom = (TextView) view.findViewById(R.id.stationFrom);
            mStationTo = (TextView) view.findViewById(R.id.stationTo);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onItemClickListener.onItemClick(v, adapterPosition);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}
