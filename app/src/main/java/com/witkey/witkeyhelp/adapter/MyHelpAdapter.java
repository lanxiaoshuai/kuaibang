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
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.bean.UserTaskBean;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

public class MyHelpAdapter extends BaseRecyAdapter<UserTaskBean.ReturnObjectBean.RowsBean> {
    public MyHelpAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.myhelp_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final UserTaskBean.ReturnObjectBean.RowsBean missionBean = data.get(position);

        viewHolder.helpcontent.setText(missionBean.getDescribes());
        viewHolder.replytime.setText(missionBean.getCreateDate());
        viewHolder.replyname.setText(missionBean.getUserName() + "");
        Glide.with(context).load(URL.getImgPath + missionBean.getHeadUrl()).into(viewHolder.createUserHeadUrl);
        String paymentType = missionBean.getPaymentType();
        viewHolder.tv_mission_money.setText(missionBean.getPrice() + "");
        if (paymentType.equals("1")) {
            viewHolder.tv_mission_money.setTextColor(context.getResources().getColor(R.color.shape_org));
            viewHolder.price_image.setVisibility(View.VISIBLE);
            viewHolder.price_image_diamond.setVisibility(View.GONE);
        } else if (paymentType.equals("2")) {
            viewHolder.price_image.setVisibility(View.GONE);
            viewHolder.price_image_diamond.setVisibility(View.VISIBLE);
            viewHolder.tv_mission_money.setTextColor(context.getResources().getColor(R.color.shape_lan));
        }
        if (null == missionBean.getBusinessImgUrl() || "".equals(missionBean.getBusinessImgUrl())) {
            viewHolder.mission_photo.setVisibility(View.GONE);
        } else {
            viewHolder.mission_photo.setVisibility(View.VISIBLE);
            if (missionBean.getBusinessImgUrl().contains(",")) {
                String[] split = missionBean.getBusinessImgUrl().split(",");
                Glide.with(context).load(URL.getImgPath + split[0]).into(viewHolder.mission_photo);
            } else {
                Glide.with(context).load(URL.getImgPath + missionBean.getBusinessImgUrl()).into(viewHolder.mission_photo);

            }
        }
        switch (missionBean.getOrderState()) {
            case "1":
                viewHolder.task_status.setText("进行中");
                viewHolder.task_status.setTextColor(context.getResources().getColor(R.color.shape_org));
                break;
            case "4":
                viewHolder.task_status.setText("已完成");
                viewHolder.task_status.setTextColor(context.getResources().getColor(R.color.color_b3b3b3));
                break;
            case "6":
                viewHolder.task_status.setText("已提交");
                viewHolder.task_status.setTextColor(context.getResources().getColor(R.color.color_b3b3b3));
                break;
            case "7":
                viewHolder.task_status.setText("悬赏者解除");
                viewHolder.task_status.setTextColor(context.getResources().getColor(R.color.shape_org));
                break;
            case "8":
                viewHolder.task_status.setText("待确认取消");
                viewHolder.task_status.setTextColor(context.getResources().getColor(R.color.shape_org));
                break;

        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MissionDetailActivity.class);
                intent.putExtra("EXTRA_BUSINESS_ID", missionBean.getBusinessId());
                intent.putExtra("TASKDETAILS", missionBean.getOrderState());
                intent.putExtra("ORDERID", missionBean.getOrderId());
                Activity context = (Activity) MyHelpAdapter.this.context;
                context.startActivityForResult(intent, 100);

            }
        });
    }

    @Override
    public void setCcontent(TextView textView) {
        super.setCcontent(textView);
        textView.setText("您还没有接取帮忙哦");
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView mission_photo;
        private TextView helpcontent;
        private TextView tv_mission_money;
        private TextView price_image;
        private TextView price_image_diamond;
        private RoundImageView createUserHeadUrl;
        private TextView replyname;
        private TextView replytime;
        private TextView task_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mission_photo = itemView.findViewById(R.id.mission_photo);
            helpcontent = itemView.findViewById(R.id.helpcontent);
            tv_mission_money = itemView.findViewById(R.id.tv_mission_money);
            price_image = itemView.findViewById(R.id.price_image);
            price_image_diamond = itemView.findViewById(R.id.price_image_diamond);
            createUserHeadUrl = itemView.findViewById(R.id.createUserHeadUrl);
            replyname = itemView.findViewById(R.id.replyname);
            replytime = itemView.findViewById(R.id.replytime);
            task_status = itemView.findViewById(R.id.task_status);
        }
    }
}
