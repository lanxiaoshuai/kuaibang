package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
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
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.UserTaskBean;
import com.witkey.witkeyhelp.view.ShadowDrawable;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/5/14.
 */

public class TaskEvaluationAdapter extends BaseRecyAdapter<UserTaskBean.ReturnObjectBean.RowsBean> {
    private int type;

    public TaskEvaluationAdapter(Context context, List data, int type) {
        super(context, data);
        this.type = type;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.personal_item, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, int position) {
//        if (mOnItemClickListener != null) {
//            //为ItemView设置监听器
//            ((ViewHolder) holder).hide_display.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = ((ViewHolder) holder).getLayoutPosition(); // 1
//                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
//                }
//            });
//
//        }
//
//        if (type == 0) {
//            ((ViewHolder) holder).hide_display.setVisibility(View.VISIBLE);
//        } else {
//            ((ViewHolder) holder).hide_display.setVisibility(View.GONE);
//        }
//        ((ViewHolder) holder).release_time.setText(data.get(position).getCreateDate());
//
//        if (data.get(position).getIsHide() == 0) {
//            ((ViewHolder) holder).mission_content.setText(data.get(position).getDescribes());
//
//            if (data.get(position).getBusinessImgUrl() == null || "".equals(data.get(position).getBusinessImgUrl())) {
//                ((ViewHolder) holder).mission_photo.setVisibility(View.GONE);
//            } else {
//                Glide.with(context).load(URL.getImgPath + data.get(position).getBusinessImgUrl()).into(((ViewHolder) holder).mission_photo);
//            }
//        } else {
//               StringBuffer stringBuffer=new StringBuffer();
//            for (int i = 0; i <data.get(position).getDescribes().length() ; i++) {
//                stringBuffer.append("*");
//            }
//            ((ViewHolder) holder).mission_content.setText(stringBuffer.toString());
//            if (data.get(position).getBusinessImgUrl() == null || "".equals(data.get(position).getBusinessImgUrl())) {
//                ((ViewHolder) holder).mission_photo.setVisibility(View.GONE);
//            } else {
//                Glide.with(context).load(R.mipmap.ic_launcher).into(((ViewHolder) holder).mission_photo);
//            }
//
//        }
//          if("0".equals(data.get(position).getPaymentType())){
//              ((ViewHolder) holder).price_image.setVisibility(View.GONE);
//              ((ViewHolder) holder).mission_price.setTextColor(context.getResources().getColor(R.color.shape_org));
//              ((ViewHolder) holder).mission_price.setText("￥" + data.get(position).getPrice() + "");
//          }else {
//              ((ViewHolder) holder).price_image.setVisibility(View.VISIBLE);
//              ((ViewHolder) holder).mission_price.setTextColor(context.getResources().getColor(R.color.shape_lan));
//              ((ViewHolder) holder).mission_price.setText( data.get(position).getPrice() + "");
//          }
//
//        ((ViewHolder) holder).name_rewarder.setText("");
//
//        ((ViewHolder) holder).valuatione_text.setText(data.get(position).getComment().getContent());
//
//        Glide.with(context).load(URL.getImgPath + data.get(position).getCommentUser().getHeadUrl() + "").into(((ViewHolder) holder).help_photo);
//
//
//
//        if ("".equals(data.get(position).getComment().getContent()) || null == data.get(position).getComment().getContent()) {
//            ((ViewHolder) holder).evaluation.setVisibility(View.GONE);
//        } else {
//            ((ViewHolder) holder).evaluation.setVisibility(View.VISIBLE);
//            Glide.with(context).load(URL.getImgPath + data.get(position).getHeadUrl()).into(((ViewHolder) holder).help_photo);
//
//
//            ((ViewHolder) holder).help_name.setText(data.get(position).getCommentUser().getRealName() + "");
//            if (null == data.get(position).getComment().getScore() || "".equals(data.get(position).getComment().getScore())) {
//
//            } else {
//                if (Integer.parseInt(data.get(position).getComment().getScore()) > 0) {
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                    ((ViewHolder) holder).score_star.setLayoutManager(linearLayoutManager);
//                    List<String> mlst = new ArrayList<>();
//                    for (int i = 0; i < Integer.parseInt(data.get(position).getComment().getScore()); i++) {
//                        mlst.add("");
//                    }
//                    ScoreAdapter scoreAdapter = new ScoreAdapter(context, mlst);
//                    ((ViewHolder) holder).score_star.setAdapter(scoreAdapter);
//                } else {
//
//                }
//            }
//        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RoundImageView photo_rewarder;
        private TextView name_rewarder;
        private TextView release_time;
        private ImageView hide_display;
        private RoundImageView mission_photo;
        private TextView mission_content;
        private TextView mission_price;
        private RoundImageView help_photo;
        private TextView help_name;
        private RecyclerView score_star;
        private TextView valuatione_text;
        private LinearLayout evaluation;
        private ImageView price_image;
        public ViewHolder(View v) {
            super(v);
            photo_rewarder = v.findViewById(R.id.photo_rewarder);

            name_rewarder = v.findViewById(R.id.name_rewarder);

            release_time = v.findViewById(R.id.release_time);

            hide_display = v.findViewById(R.id.hide_display);

            mission_photo = v.findViewById(R.id.mission_photo);

            mission_content = v.findViewById(R.id.mission_content);

            mission_price = v.findViewById(R.id.mission_price);

            help_photo = v.findViewById(R.id.help_photo);

            help_name = v.findViewById(R.id.help_name);

            score_star = v.findViewById(R.id.score_star);

            valuatione_text = v.findViewById(R.id.valuatione_text);

            evaluation = v.findViewById(R.id.evaluation);
            price_image = v.findViewById(R.id.price_image);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
