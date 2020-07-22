package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.BillFlowBean;
import com.witkey.witkeyhelp.bean.NewsDetailsBean;
import com.witkey.witkeyhelp.view.impl.ActivityBillDetails;
import com.witkey.witkeyhelp.view.impl.ActivityNewsDetails;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class NewsDetailsAdapter extends BaseRecyAdapter<BillFlowBean.ReturnObjectBean.RowsBean> {
    public NewsDetailsAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.bill_details_item, null);
        return new NewsDetailsAdapter.ViewHolder(view);
    }
    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).news_title.setText(data.get(position).getTitle() + "");
        ((ViewHolder) holder).news_time.setText(data.get(position).getCreateTime() + "");
        if ("sub".equals(data.get(position).getType())) {
            if ("抵扣平台费".equals(data.get(position).getTitle())) {
                ((ViewHolder) holder).news_price.setText(data.get(position).getAmount() + "");

            } else {
                ((ViewHolder) holder).news_price.setText("-" + data.get(position).getAmount() + "");
            }

            ((ViewHolder) holder).news_price.setTextColor(context.getResources().getColor(R.color.price_bli_color));

        } else if ("add".equals(data.get(position).getType())) {
            ((ViewHolder) holder).news_price.setText("+" + data.get(position).getAmount() + "");
            ((ViewHolder) holder).news_price.setTextColor(context.getResources().getColor(R.color.color_baozhenjin));
        }
        switch (data.get(position).getTitle()) {
            case "信息咨询":
                Glide.with(context).load(R.mipmap.news_img).into(((ViewHolder) holder).new_img);
                break;
            case "悬赏帮助":
                Glide.with(context).load(R.mipmap.offerreward).into(((ViewHolder) holder).new_img);
                break;
            case "余额提现":
                Glide.with(context).load(R.mipmap.yuetixian).into(((ViewHolder) holder).new_img);

                break;
            case "保证金提现":
                Glide.with(context).load(R.mipmap.yuetixian).into(((ViewHolder) holder).new_img);

                break;
            case "保证金充值":
                Glide.with(context).load(R.mipmap.marginrecharge_detils).into(((ViewHolder) holder).new_img);
                break;
            case "退回资金":
                Glide.with(context).load(R.mipmap.returnfunds).into(((ViewHolder) holder).new_img);
                break;
            case "保证金退回":
                Glide.with(context).load(R.mipmap.returnfunds).into(((ViewHolder) holder).new_img);
                break;
            case "系统赠送":
                Glide.with(context).load(R.mipmap.system_presentation_dtelis).into(((ViewHolder) holder).new_img);
                break;
            case "抵扣平台费":
                Glide.with(context).load(R.mipmap.returnfunds).into(((ViewHolder) holder).new_img);
                break;
            case "新用户注册赠送":
                Glide.with(context).load(R.mipmap.system_presentation_dtelis).into(((ViewHolder) holder).new_img);
                break;
            case "推广用户赠送":
                Glide.with(context).load(R.mipmap.system_presentation_dtelis).into(((ViewHolder) holder).new_img);
                break;
            case "用户推广赠送":
                Glide.with(context).load(R.mipmap.system_presentation_dtelis).into(((ViewHolder) holder).new_img);
                break;
            case "保证金冻结":
                Glide.with(context).load(R.mipmap.marginrecharge_detils).into(((ViewHolder) holder).new_img);
                break;
            case "解冻保证金":
                Glide.with(context).load(R.mipmap.marginrecharge_detils).into(((ViewHolder) holder).new_img);
                break;
            case "投放广告":
                Glide.with(context).load(R.mipmap.diamondnoticon).into(((ViewHolder) holder).new_img);
                break;
            case "提现手续费":
                Glide.with(context).load(R.mipmap.returnfunds).into(((ViewHolder) holder).new_img);
                break;
            case "浏览广告":
                Glide.with(context).load(R.mipmap.diamondnoticon).into(((ViewHolder) holder).new_img);
                break;
        }
        ((NewsDetailsAdapter.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityBillDetails.class);
                intent.putExtra("BILLDETAILS", data.get(position));
                context.startActivity(intent);
            }
        });
    }

    //    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView new_img;
        private TextView news_title;
        private TextView news_time;
        private TextView news_price;

        public ViewHolder(View v) {
            super(v);
            new_img = (ImageView) v.findViewById(R.id.new_img);
            news_title = (TextView) v.findViewById(R.id.news_title);
            news_time = (TextView) v.findViewById(R.id.news_time);
            news_price = (TextView) v.findViewById(R.id.news_price);
        }
    }
}
