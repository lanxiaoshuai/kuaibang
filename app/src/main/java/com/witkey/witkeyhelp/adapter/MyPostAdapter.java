package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.MyPostBean;
import com.witkey.witkeyhelp.util.TimeUtils;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jie on 2020/7/3.
 */

public class MyPostAdapter extends BaseRecyAdapter<MyPostBean.ReturnObjectBean.RowsBean> {
    private Fragment fragment;

    public MyPostAdapter(Context context, List data, Fragment fragment) {
        super(context, data);
        this.fragment = fragment;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.mypost_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(RecyclerView.ViewHolder holder, int position) {

        final MyPostBean.ReturnObjectBean.RowsBean rowsBean = data.get(position);

        ((ViewHolder) holder).release_month.setText(TimeUtils.stringToDate(rowsBean.getCreateDate()).get(Calendar.DAY_OF_MONTH) + "");
        ((ViewHolder) holder).release_date.setText("/" + (TimeUtils.stringToDate(rowsBean.getCreateDate()).get(Calendar.MONTH) + 1) + "");

        switch (rowsBean.getBusinessType()) {
            case "1":
                ((ViewHolder) holder).types_of.setText("咨询");
                ((ViewHolder) holder).bounty.setText(rowsBean.getPrice() + "");
                ((ViewHolder) holder).bounty_title.setVisibility(View.VISIBLE);
                break;
            case "2":
                ((ViewHolder) holder).types_of.setText("帮忙");
                ((ViewHolder) holder).bounty_title.setVisibility(View.GONE);
                if (null == rowsBean.getOrderState()) {
                    ((ViewHolder) holder).bounty.setText("未接单");
                    ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.defaultsendColor));
                } else {
                    switch (rowsBean.getOrderState()) {
                        case "1":
                            ((ViewHolder) holder).bounty.setText("进行中");
                            ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.shape_org));
                            break;
                        case "4":
                            ((ViewHolder) holder).bounty.setText("已完成");
                            ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.color_b3b3b3));
                            break;
                        case "6":
                            ((ViewHolder) holder).bounty.setText("帮忙者已提交");
                            ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.shape_org));
                            break;
                        case "8":
                            ((ViewHolder) holder).bounty.setText("等待帮忙者取消中");
                            ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.shape_org));
                            break;
                        case "9":
                            ((ViewHolder) holder).bounty.setText("帮忙者不同意解除");
                            ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.color_b3b3b3));
                            break;

                    }
                }

                break;
            case "5":
                ((ViewHolder) holder).types_of.setText("动态");
                ((ViewHolder) holder).bounty.setText(rowsBean.getPrice() + "");
                ((ViewHolder) holder).bounty_title.setVisibility(View.GONE);
                ((ViewHolder) holder).bounty.setVisibility(View.GONE);
                break;
        }


        ((ViewHolder) holder).task_content.setText(rowsBean.getDescribes());
        if (!rowsBean.getBusinessType().equals("2")) {
            if (rowsBean.getPaymentType().equals("1")) {
                ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.shape_org));
            } else {
                ((ViewHolder) holder).bounty.setTextColor(context.getResources().getColor(R.color.shape_lan));
            }
        }
        if (rowsBean.getBusinessImgUrl() == null || "".equals(rowsBean.getBusinessImgUrl())) {
            ((ViewHolder) holder).mission_picture.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).mission_picture.setVisibility(View.VISIBLE);


            if (rowsBean.getBusinessImgUrl().contains(",")) {
                String[] split = rowsBean.getBusinessImgUrl().split(",");
                Glide.with(context).load(URL.getImgPath + split[0]).into(((ViewHolder) holder).mission_picture);
            } else {
                Glide.with(context).load(URL.getImgPath + rowsBean.getBusinessImgUrl()).into(((ViewHolder) holder).mission_picture);

            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MissionDetailActivity.class);
                intent.putExtra("EXTRA_BUSINESS_ID", rowsBean.getBusinessId());
                intent.putExtra("TASKDETAILS", rowsBean.getOrderState());
                intent.putExtra("ORDERID", rowsBean.getOrderId());
                intent.putExtra("TASK", true);
                fragment.startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    public void setCcontent(TextView textView) {
        super.setCcontent(textView);
        textView.setText("您还没有发布任何内容哦");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView release_month;
        private TextView release_date;
        private TextView types_of;
        private TextView bounty_title;
        private TextView bounty;
        private RoundImageView mission_picture;
        private TextView task_content;
        private TextView privte;

        public ViewHolder(@NonNull View v) {
            super(v);

            release_month = v.findViewById(R.id.release_month);

            release_date = v.findViewById(R.id.release_date);
            types_of = v.findViewById(R.id.types_of);
            bounty_title = v.findViewById(R.id.bounty_title);
            bounty = v.findViewById(R.id.bounty);

            mission_picture = v.findViewById(R.id.mission_picture);

            task_content = v.findViewById(R.id.task_content);

            privte = v.findViewById(R.id.privte);


        }
    }
}
