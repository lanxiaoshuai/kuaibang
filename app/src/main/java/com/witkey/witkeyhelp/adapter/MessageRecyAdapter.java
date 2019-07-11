package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Message;

import java.util.List;

public class MessageRecyAdapter extends BaseRecyAdapter<Message> {

    public MessageRecyAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_lv_message, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {
        final Message message = data.get(position);
//        ((ViewHolder) holder).iv_avatar.
        ((ViewHolder) holder).tv_mission_title.setText(message.getTitle());
        ((ViewHolder) holder).tv_mission_content.setText(message.getContent());
        ((ViewHolder) holder).tv_mission_date.setText(message.getDate());
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
        private ImageView iv_avatar;
        private TextView tv_mission_title;
        private TextView tv_mission_content;
        private TextView tv_mission_date;

        public ViewHolder(View v) {
            super(v);
            iv_avatar = (ImageView) v.findViewById(R.id.iv_avatar);
           tv_mission_title= (TextView) v.findViewById(R.id.tv_mission_title);
           tv_mission_content= (TextView) v.findViewById(R.id.tv_mission_content);
            tv_mission_date= (TextView) v.findViewById(R.id.tv_mission_date);
        }
    }
}
