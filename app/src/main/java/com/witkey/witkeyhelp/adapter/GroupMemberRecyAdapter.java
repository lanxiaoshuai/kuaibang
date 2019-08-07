package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MicroNotifyGroupMember;

import java.util.List;

public class GroupMemberRecyAdapter extends BaseRecyAdapter<MicroNotifyGroupMember> {

    public GroupMemberRecyAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_lv_group_member, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {
        final MicroNotifyGroupMember member = data.get(position);
//        ((ViewHolder) holder).iv_avatar
        ((ViewHolder) holder).tv_mission_title.setText(member.getRealName());
        ((ViewHolder) holder).tv_mission_content.setText(member.getRemark());
        if(member.getType().equals("2"))
        {
            ((ViewHolder) holder).tv_btn_manage.setVisibility(View.GONE);
        }else{
            ((ViewHolder) holder).tv_btn_manage.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).tv_btn_manage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2019/8/7 踢人操作
                }
            });
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_avatar;
        private TextView tv_mission_title;
        private TextView tv_mission_content;
        private TextView tv_btn_manage;

        public ViewHolder(View v) {
            super(v);
            iv_avatar = (ImageView) v.findViewById(R.id.iv_avatar);
            tv_mission_title = (TextView) v.findViewById(R.id.tv_mission_title);
            tv_mission_content = (TextView) v.findViewById(R.id.tv_mission_content);
            tv_btn_manage = (TextView) v.findViewById(R.id.tv_btn_manage);
        }
    }
}
