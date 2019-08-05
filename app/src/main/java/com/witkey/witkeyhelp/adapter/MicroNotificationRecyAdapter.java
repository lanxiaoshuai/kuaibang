package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MicroNotificationBean;
import com.witkey.witkeyhelp.view.impl.MicroNotificationDetailActivity;

import java.util.List;

public class MicroNotificationRecyAdapter extends BaseRecyAdapter<MicroNotificationBean> {

    public MicroNotificationRecyAdapter(Context context, List data) {
        super(context, data);
    }
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_lv_lost_found, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {
        final MicroNotificationBean microNotificationBean = data.get(position);
//        ((ViewHolder) holder).iv_avatar.setText(lostFoundBean.get);
        ((ViewHolder) holder).tv_title.setText(microNotificationBean.getTitle());
        ((ViewHolder) holder).tv_content.setText(microNotificationBean.getContent());
        ((ViewHolder) holder).tv_date.setText(microNotificationBean.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/7/9 跳转界面
                Intent i = new Intent(context, MicroNotificationDetailActivity.class);
                i.putExtra("EXTRA_MICRO_NOTIFICATION_BEAN", microNotificationBean);
                context.startActivity(i);
            }
        });
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_avatar;
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_date;

        public ViewHolder(View v) {
            super(v);
            iv_avatar = (ImageView) v.findViewById(R.id.iv_avatar);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            tv_content = (TextView) v.findViewById(R.id.tv_content);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
        }
    }
}
