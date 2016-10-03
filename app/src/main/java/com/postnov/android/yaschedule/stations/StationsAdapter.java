package com.postnov.android.yaschedule.stations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.schedule.Station;
import com.postnov.android.yaschedule.data.entity.stations.Stop;
import com.postnov.android.yaschedule.utils.Utils;

import java.util.List;

/**
 * Created by platon on 20.05.2016.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {
    private List<Stop> stops;
    private List<Stop> allStops;

    private String fromCode;
    private String toCode;

    private static final int MAIN_STATION_DRAWABLE = R.drawable.ic_dot;
    private static final int SUB_STATION_DRAWABLE = R.drawable.ic_dot_border;

    public StationsAdapter(String fromCode, String toCode) {
        this.fromCode = fromCode;
        this.toCode = toCode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Stop stop = getList().get(position);
        Station station = stop.getStation();

        if (station.getCode().equals(fromCode) || station.getCode().equals(toCode)) {
            holder.mImageView.setImageResource(MAIN_STATION_DRAWABLE);
        } else {
            holder.mImageView.setImageResource(SUB_STATION_DRAWABLE);
        }

        holder.mTitle.setText(stop.getStation().getTitle());

        if (stop.getDeparture() == null) holder.mDeparture.setVisibility(View.GONE);
        else {
            StringBuilder date = new StringBuilder();
            date.append("Отправление: ");
            date.append(Utils.getOnlyTime(stop.getDeparture()));
            date.append(", ");
            date.append(Utils.toShortDate(stop.getDeparture()));

            holder.mDeparture.setVisibility(View.VISIBLE);
            holder.mDeparture.setText(date);
        }

        if (stop.getStopTime() == null) holder.mStopTime.setVisibility(View.GONE);
        else {
            StringBuilder stopTime = new StringBuilder();
            stopTime.append("Стоянка: ");
            stopTime.append(Utils.convertSecToMinutes(stop.getStopTime()));

            holder.mStopTime.setVisibility(View.VISIBLE);
            holder.mStopTime.setText(stopTime);
        }
    }

    @Override
    public int getItemCount() {
        if (null == stops) return 0;
        return stops.size();
    }

    public void swapList(List<Stop> stopList) {
        allStops = stopList;
        stops = getListWithoutExtraStations(stopList);
        notifyDataSetChanged();
    }

    public void showAllStops() {
        if (stops != null && !stops.equals(allStops)) {
            stops = allStops;
            notifyDataSetChanged();
        }
    }

    public List<Stop> getList() {
        return stops;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDeparture;
        public TextView mStopTime;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.station_image);
            mTitle = (TextView) view.findViewById(R.id.station_title);
            mDeparture = (TextView) view.findViewById(R.id.station_departure);
            mStopTime = (TextView) view.findViewById(R.id.station_stop_time);
        }
    }

    private List<Stop> getListWithoutExtraStations(List<Stop> stops) {
        if (stops != null) {
            int indexFrom = 0;
            int indexTo = 0;

            for (int i = 0; i < stops.size(); i++) {
                if (stops.get(i).getStation().getCode().equals(fromCode)) indexFrom = i;
                if (stops.get(i).getStation().getCode().equals(toCode)) indexTo = i;
            }

            return stops.subList(indexFrom, indexTo + 1);
        }
        return null;
    }
}
