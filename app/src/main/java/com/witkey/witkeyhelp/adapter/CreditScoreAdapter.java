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
import com.witkey.witkeyhelp.bean.CreditScoreBean;
import com.witkey.witkeyhelp.view.impl.ActivityBillDetails;

import java.util.List;

/**
 * Created by asus on 2020/2/28.
 */

public class CreditScoreAdapter extends BaseRecyAdapter<CreditScoreBean.ReturnObjectBean.RowsBean> {


    public CreditScoreAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.bill_details_item, null);
        return new CreditScoreAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((CreditScoreAdapter.ViewHolder) holder).news_title.setText(data.get(position).getReason() + "");
        ((CreditScoreAdapter.ViewHolder) holder).news_time.setText(data.get(position).getCreateTime() + "");
        if ("sub".equals(data.get(position).getType())) {
            ((CreditScoreAdapter.ViewHolder) holder).news_price.setText(data.get(position).getNum() + "");
            ((CreditScoreAdapter.ViewHolder) holder).news_price.setTextColor(context.getResources().getColor(R.color.price_bli_color));
            ((CreditScoreAdapter.ViewHolder) holder).new_img.setImageResource(R.mipmap.accountability_icon);
        } else if ("add".equals(data.get(position).getType())) {
            ((CreditScoreAdapter.ViewHolder) holder).news_price.setText(data.get(position).getNum() + "");
            ((CreditScoreAdapter.ViewHolder) holder).news_price.setTextColor(context.getResources().getColor(R.color.color_baozhenjin));
            ((CreditScoreAdapter.ViewHolder) holder).new_img.setImageResource(R.mipmap.getgredit);
        }


//
//        ((CreditScoreAdapter.ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ActivityBillDetails.class);
//                intent.putExtra("BILLDETAILS", data.get(position));
//                context.startActivity(intent);
//            }
//        });


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
