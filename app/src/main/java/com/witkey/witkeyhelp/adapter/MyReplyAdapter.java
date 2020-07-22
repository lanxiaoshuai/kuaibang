package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.MyReplyBean;
import com.witkey.witkeyhelp.util.TimeUtils;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.List;

public class MyReplyAdapter extends BaseRecyAdapter<MyReplyBean.ReturnObjectBean.RowsBean> {
    private boolean reply;
    public MyReplyAdapter(Context context, List data,boolean reply) {
        super(context, data);
        this.reply=reply;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.my_reply_item, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = ((ViewHolder) holder);
        final MyReplyBean.ReturnObjectBean.RowsBean rowsBean = data.get(position);

        Glide.with(context).load(URL.getImgPath + rowsBean.getHeadUrl()).into(viewHolder.createUserHeadUrl);
        viewHolder.replyname.setText(rowsBean.getUserName());
        viewHolder.replytime.setText(TimeUtils.DateDistance2now(TimeUtils.date2ms(rowsBean.getCreateTime())));
        viewHolder.my_reply_content.setText(rowsBean.getContent());
        viewHolder.replycontent.setText(rowsBean.getDescribes());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MissionDetailActivity.class);
                intent.putExtra("EXTRA_BUSINESS_ID", rowsBean.getBid());
                intent.putExtra("TASKDETAILS", rowsBean.getOrderState());
                if (rowsBean.getOrderId() != null) {
                    intent.putExtra("ORDERID", Integer.parseInt(rowsBean.getOrderId()));
                }


                context.startActivity(intent);
            }
        });
    }

    @Override
    public void setCcontent(TextView textView) {
        super.setCcontent(textView);
        if(reply){
            textView.setText("暂时还没有收到回复");
        }else {
            textView.setText("您还没有回复任何内容哦");
        }

    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView createUserHeadUrl;
        private TextView replyname;
        private TextView replytime;
        private TextView my_reply_content;
        private TextView replycontent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            createUserHeadUrl = itemView.findViewById(R.id.createUserHeadUrl);
            replyname = itemView.findViewById(R.id.replyname);
            replytime = itemView.findViewById(R.id.replytime);
            my_reply_content = itemView.findViewById(R.id.my_reply_content);
            replycontent = itemView.findViewById(R.id.replycontent);
        }
    }
}
