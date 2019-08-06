package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;

import java.util.List;

public class MicroNotifyManagerTopRecyAdapter extends BaseRecyAdapter<MicroNotifyManagerBean> {
    public int currentCatalogid;

    public MicroNotifyManagerTopRecyAdapter(Context context, List data) {
        super(context, data);
    }

    public void setCurrentCatalogid(int currentCatalogid) {
        this.currentCatalogid = currentCatalogid;
        this.notifyDataSetChanged();
    }

    public int getCurrentCatalogid() {
        return currentCatalogid;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_lv_micro_notify_manager, null);
        return new MicroNotifyManagerTopRecyAdapter.ViewHolder(view);
    }

    public CheckListener checkListener;

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MicroNotifyManagerBean microNotificationBean = data.get(position);
        ((ViewHolder) holder).tv.setText(microNotificationBean.getName());
        if(currentCatalogid==microNotificationBean.getCatalogId()){
            ((ViewHolder) holder).tv.setChecked(true);
        }else{
            ((ViewHolder) holder).tv.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentCatalogid(microNotificationBean.getCatalogId());
                if (checkListener != null) {
                    checkListener.onCheck(microNotificationBean.getCatalogId());
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox tv;

        public ViewHolder(View v) {
            super(v);
            tv = (CheckBox) v.findViewById(R.id.tv);
        }
    }

    public interface CheckListener {
        void onCheck(int catalogId);
    }
}
