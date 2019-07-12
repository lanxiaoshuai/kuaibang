package com.witkey.witkeyhelp.adapter;

import android.content.Context;

import com.witkey.witkeyhelp.presenter.IPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected List<T> data;
    protected Context context;
    protected IPresenter presenter;
    protected  String TAG = "llx";


    public void setData(List<T> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public BaseAdapter(Context context, List<T> data) {
        this.data = data;
        this.context = context;
    }

    public List<T> getData() {
        return data;
    }

    public BaseAdapter(Context context, List<T> data, IPresenter presenter) {
        this.data = data;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
