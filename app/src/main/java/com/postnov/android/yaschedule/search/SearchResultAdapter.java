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
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>
{
    private List<Suggest> mSuggests;
    private View mEmptyView;
    private static OnItemClickListener sOnItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Suggest suggest = getList().get(position);
        holder.mResult.setText(suggest.getTitleRu());
    }

    @Override
    public int getItemCount()
    {
        if (null == mSuggests) return 0;
        return mSuggests.size();
    }

    public void swapList(List<Suggest> suggests)
    {
        mSuggests = suggests;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public List<Suggest> getList()
    {
        return mSuggests;
    }

    public void setEmptyView(View view)
    {
        mEmptyView = view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView mResult;
        public ViewHolder(View view)
        {
            super(view);
            mResult = (TextView) view.findViewById(R.id.search_result);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int adapterPosition = getAdapterPosition();
            sOnItemClickListener.onItemClick(v, adapterPosition);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        sOnItemClickListener = listener;
    }
}
