package com.postnov.android.yaschedule.recent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.recent.RecentRoute;

import java.util.List;

/**
 * Created by platon on 29.05.2016.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder>
{
    private List<RecentRoute> mRoutes;
    private View mEmptyView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_last_route, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        RecentRoute route = getList().get(position);
        String title = route.getFrom() + " - " + route.getTo();
        holder.mTitle.setText(title);
    }

    @Override
    public int getItemCount()
    {
        if (null == mRoutes) return 0;
        return mRoutes.size();
    }

    public void swapList(List<RecentRoute> newList)
    {
        mRoutes = newList;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public List<RecentRoute> getList()
    {
        return mRoutes;
    }

    public void setEmptyView(View view)
    {
        mEmptyView = view;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mTitle;

        public ViewHolder(View view)
        {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.last_route_title);
        }
    }
}