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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        holder.bind(route);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.route)       TextView route;
        @BindView(R.id.startTime)   TextView startTime;
        @BindView(R.id.finishTime)  TextView finishTime;
        @BindView(R.id.startDate)   TextView startDate;
        @BindView(R.id.finishDate)  TextView finishDate;
        @BindView(R.id.stationFrom) TextView stationFrom;
        @BindView(R.id.stationTo)   TextView stationTo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bind(Route route) {
            RouteOptions routeOptions = route.getRouteOptions();
            Station from = route.getFromStation();
            Station to = route.getToStation();

            int drawable = Utils.getTransportImage(routeOptions.getTransportType());
            String title = String.format("%s %s", routeOptions.getNumber(), routeOptions.getTitle()).trim();

            this.route.setText(title);
            this.route.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
            startTime.setText(Utils.getOnlyTime(route.getDeparture()));
            finishTime.setText(Utils.getOnlyTime(route.getArrival()));
            startDate.setText(Utils.toShortDate(route.getDeparture()));
            finishDate.setText(Utils.toShortDate(route.getArrival()));
            stationFrom.setText(Utils.splitTitle(from.getTitle()));
            stationTo.setText(Utils.splitTitle(to.getTitle()));
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
