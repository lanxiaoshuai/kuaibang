package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.DiamondBean;
import com.witkey.witkeyhelp.view.ShadowDrawable;
import com.witkey.witkeyhelp.view.impl.ActivityDiamondsDetails;
import com.witkey.witkeyhelp.widget.CardUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jie on 2020/4/7.
 */

public class DiamondAdapter extends BaseRecyAdapter<DiamondBean.ReturnObjectBean.RowsBean> {
    public DiamondAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_diamond, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ShadowDrawable.setShadowDrawable(((ViewHolder) holder).cardView, dp2px(12), context.getResources().getColor(R.color.blacktransparent), dp2px(4), 0, dp2px(4));


        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        ((ViewHolder) holder).notice_title.setText(data.get(position).getTitle());
        ((ViewHolder) holder).notice_content.setText(data.get(position).getContent());

        if (null == data.get(position).getImgUrl() || "".equals(data.get(position).getImgUrl())) {
            ((ViewHolder) holder).iv_avatar.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).iv_avatar.setVisibility(View.VISIBLE);

            if (data.get(position).getImgUrl().contains(",")) {

                String[] split = data.get(position).getImgUrl().split(",");
                for (int i = 0; i < split.length; i++) {
                    Glide.with(context).load(URL.getImgPath + split[0]).into(((ViewHolder) holder).iv_avatar);
                    break;
                }
            } else {
                Glide.with(context).load(URL.getImgPath + data.get(position).getImgUrl()).into(((ViewHolder) holder).iv_avatar);

            }
        }
        if (data.get(position).getAmountType().equals("0")) {
            ((ViewHolder) holder).notice_price_fuhao.setVisibility(View.GONE);
            ((ViewHolder) holder).notice_price_ImageView.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).notice_price.setText("1.00");
            ((ViewHolder) holder).notice_price.setTextColor(context.getResources().getColor(R.color.shape_lan));
        } else {
            ((ViewHolder) holder).notice_price_fuhao.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).notice_price_ImageView.setVisibility(View.GONE);
            ((ViewHolder) holder).notice_price.setText("0.10");
            ((ViewHolder) holder).notice_price.setText("0.10");
            ((ViewHolder) holder).notice_price.setTextColor(context.getResources().getColor(R.color.shape_org));
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView notice_title;
        private TextView notice_content;
        private TextView notice_price;

        private ImageView notice_price_ImageView;
        private LinearLayout cardView;
        private TextView notice_price_fuhao;

        private ImageView iv_avatar;

        public ViewHolder(View v) {
            super(v);
            notice_title = (TextView) v.findViewById(R.id.notice_title);
            notice_content = (TextView) v.findViewById(R.id.notice_content);
            notice_price = (TextView) v.findViewById(R.id.notice_price);

            iv_avatar = v.findViewById(R.id.iv_avatar);
            cardView = v.findViewById(R.id.cardview);
            notice_price_ImageView = v.findViewById(R.id.notice_price_ImageView);
            notice_price_fuhao = v.findViewById(R.id.notice_price_fuhao);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }


}
