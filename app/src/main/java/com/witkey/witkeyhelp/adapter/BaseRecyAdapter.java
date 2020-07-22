package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;

import java.util.List;

public abstract class BaseRecyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context context;
    protected List<T> data;
    protected String TAG = "llx";

    private static final int EMPTY_VIEW = 1;


    public BaseRecyAdapter(Context context, List data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getItemCount() {
        return data.size() > 0 ? data.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            View view = LayoutInflater.from(context).inflate(R.layout.include_no_data, parent,
                    false);

            return new EmptyViewHolder(view);
        } else {
            return onCreateViewHolder(parent);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    protected abstract void onBindBiewHolder(RecyclerView.ViewHolder holder, int position);
    public void setCcontent(TextView textView){

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).iv_nodata.setVisibility(View.VISIBLE);
            setCcontent(  ((EmptyViewHolder) holder).nodata);
        } else {
            onBindBiewHolder(holder, position);
        }

    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_nodata;
        TextView nodata;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            iv_nodata = (ImageView) itemView.findViewById(R.id.iv_nodata);
            nodata = (TextView) itemView.findViewById(R.id.nodata);
        }
    }

}