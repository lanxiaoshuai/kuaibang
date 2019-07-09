package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Mission;

import java.util.List;

public class MissionRecyAdapter extends BaseRecyAdapter<Mission> {

    public MissionRecyAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_lv_mission, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {
        final Mission mission = data.get(position);
        ((ViewHolder) holder).tv_mission_type.setText(mission.getType());
        ((ViewHolder) holder).tv_mission_title.setText(mission.getTitle());
        ((ViewHolder) holder).tv_mission_content.setText(mission.getContent());
        ((ViewHolder) holder).tv_mission_money.setText(mission.getMoney());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/7/9 跳转界面
//                Intent i = new Intent(context, PatientIntroDetailActivity.class);
//                i.putExtra(EXTRA_INTRO_DETAIL, introHistory.getIntro());
//                context.startActivity(i);
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
            tv_mission_money = (TextView) v.findViewById(R.id.tv_mission_money);
        }
    }
}
