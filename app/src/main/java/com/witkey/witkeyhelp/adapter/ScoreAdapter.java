package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;

import java.util.List;

/**
 * Created by jie on 2020/5/16.
 */

public class ScoreAdapter  extends BaseRecyAdapter<String> {
    public ScoreAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.score_item, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
