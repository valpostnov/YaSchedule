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
class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private List<RecentRoute> recentRoutes;
    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecentRoute route = getList().get(position);
        String title = route.getFrom() + " - " + route.getTo();
        holder.mTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        if (null == recentRoutes) return 0;
        return recentRoutes.size();
    }

    public void swapList(List<RecentRoute> newList) {
        recentRoutes = newList;
        notifyDataSetChanged();
    }

    public List<RecentRoute> getList() {
        return recentRoutes;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;

        ViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.recent_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onItemClickListener.onItemClick(v, adapterPosition);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
}