package com.witkey.witkeyhelp.util.wight;

import android.view.ViewGroup;

import com.witkey.witkeyhelp.adapter.EmoticonsAdapter;


public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent,EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
