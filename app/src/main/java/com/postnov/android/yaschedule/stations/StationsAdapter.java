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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by platon on 20.05.2016.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder>
{
    private List<Stop> mStops;
    private View mEmptyView;
    private String mFromCode;
    private String mToCode;

    private static final int MAIN_STATION_DRAWABLE = R.drawable.ic_dot;
    private static final int SUB_STATION_DRAWABLE = R.drawable.ic_dot_border;

    public StationsAdapter(String fromCode, String toCode)
    {
        mFromCode = fromCode;
        mToCode = toCode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_station, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Stop stop = getList().get(position);
        Station station = stop.getStation();

        if (station.getCode().equals(mFromCode) || station.getCode().equals(mToCode))
        {
            holder.mImageView.setImageResource(MAIN_STATION_DRAWABLE);
        }
        else
        {
            holder.mImageView.setImageResource(SUB_STATION_DRAWABLE);
        }

        holder.mTitle.setText(stop.getStation().getTitle());

        if (stop.getDeparture() == null) holder.mDeparture.setVisibility(View.GONE);
        else
        {
            StringBuilder date = new StringBuilder();
            date.append("Отправление: ");
            date.append(Utils.getOnlyTime(stop.getDeparture()));
            date.append(", ");
            date.append(Utils.toShortDate(stop.getDeparture()));

            holder.mDeparture.setVisibility(View.VISIBLE);
            holder.mDeparture.setText(date);
        }

        if (stop.getStopTime() == null) holder.mStopTime.setVisibility(View.GONE);
        else
        {
            StringBuilder stopTime = new StringBuilder();
            stopTime.append("Стоянка: ");
            stopTime.append(Utils.convertSecToMinutes(stop.getStopTime()));

            holder.mStopTime.setVisibility(View.VISIBLE);
            holder.mStopTime.setText(stopTime);
        }
    }

    @Override
    public int getItemCount()
    {
        if (null == mStops) return 0;
        return mStops.size();
    }

    public void swapList(List<Stop> stopList)
    {
        mStops = getListWithoutExtraStations(stopList);
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public List<Stop> getList()
    {
        return mStops;
    }

    public void setEmptyView(View view)
    {
        mEmptyView = view;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDeparture;
        public TextView mStopTime;
        public ViewHolder(View view)
        {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.station_image);
            mTitle = (TextView) view.findViewById(R.id.station_title);
            mDeparture = (TextView) view.findViewById(R.id.station_departure);
            mStopTime = (TextView) view.findViewById(R.id.station_stop_time);
        }
    }

    private List<Stop> getListWithoutExtraStations(List<Stop> stops)
    {
        if (stops != null)
        {
            int indexFrom = 0;
            int indexTo = 0;

            for (int i = 0; i < stops.size(); i++)
            {
                if (stops.get(i).getStation().getCode().equals(mFromCode)) indexFrom = i;
                if (stops.get(i).getStation().getCode().equals(mToCode)) indexTo = i;
            }

            return stops.subList(indexFrom, indexTo + 1);
        }
        return null;
    }
}
