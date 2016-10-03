package com.postnov.android.yaschedule.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.postnov.android.yaschedule.R;
import com.postnov.android.yaschedule.data.entity.codes.Suggest;

import java.util.List;

/**
 * Created by platon on 20.05.2016.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private List<Suggest> suggests;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Suggest suggest = getList().get(position);
        holder.tvResult.setText(suggest.getTitleRu());
    }

    @Override
    public int getItemCount() {
        if (null == suggests) return 0;
        return suggests.size();
    }

    public void swapList(List<Suggest> suggests) {
        this.suggests = suggests;
        notifyDataSetChanged();
    }

    public List<Suggest> getList() {
        return suggests;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvResult;

        public ViewHolder(View view) {
            super(view);
            tvResult = (TextView) view.findViewById(R.id.search_result);
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
