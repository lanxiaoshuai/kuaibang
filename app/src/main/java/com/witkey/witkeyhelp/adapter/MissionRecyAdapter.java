package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;

import java.util.List;

public class MissionRecyAdapter extends BaseRecyAdapter<MissionBean> {

    public MissionRecyAdapter(Context context, List data) {
        super(context, data);
    }
    private String[] filterData = {"信息咨询", "悬赏帮忙", "紧急求助", "失物招领"};
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_lv_mission, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {
        final MissionBean mission = data.get(position);
        ((ViewHolder) holder).tv_mission_type.setText(filterData[Integer.parseInt(mission.getBusinessType())-1]);
        ((ViewHolder) holder).tv_mission_title.setText(mission.getTitle());
        ((ViewHolder) holder).tv_mission_content.setText(mission.getDescribes());
        ((ViewHolder) holder).tv_mission_money.setText(mission.getTradeAmt());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MissionDetailActivity.class);
                Log.d(TAG, "onClick: "+mission.getBusinessId());
                i.putExtra("EXTRA_BUSINESS_ID", mission.getBusinessId());
                i.putExtra("EXTRA_ORDER_ID", mission.getOrderId());
                context.startActivity(i);
            }
        });
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_mission_type;
        private TextView tv_mission_title;
        private TextView tv_mission_content;
        private TextView tv_mission_money;

        public ViewHolder(View v) {
            super(v);
            tv_mission_type = (TextView) v.findViewById(R.id.tv_mission_type);
            tv_mission_title = (TextView) v.findViewById(R.id.tv_mission_title);
            tv_mission_content = (TextView) v.findViewById(R.id.tv_mission_content);
            tv_mission_money = (TextView) v.findViewById(R.id.tv_mission_date);
        }
    }
}
