package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.UnpublishFeedbackBean;

import java.util.List;

/**
 * Created by jie on 2020/6/16.
 */

public class ConditionAdapter extends BaseAdapter<UnpublishFeedbackBean> {


    public ConditionAdapter(Context context, List<UnpublishFeedbackBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoider holder;

        if(convertView==null){
            Log.d(TAG, "getView: "+context);
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.condition_item,
                    null);
            holder = new ViewHoider();
            holder.condition_item = (TextView) convertView.findViewById(R.id.condition_item);
            holder.condition_layout = (RelativeLayout) convertView.findViewById(R.id.condition_layout);
            convertView.setTag(holder);
        }else {
            holder = (ViewHoider) convertView.getTag();
        }
        if (data.get(position).isChecked()) {
            holder.condition_layout.setBackground(context.getResources().getDrawable(R.drawable.shape_gray_home_true));
            holder.condition_item.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.condition_layout.setBackground(context.getResources().getDrawable(R.color.white));
            holder.condition_item.setTextColor(context.getResources().getColor(R.color.price_bli_color));
        }
        holder.condition_item.setText(data.get(position).getMsg());
        return convertView;
    }

    private class ViewHoider {
        private TextView condition_item;
        private RelativeLayout condition_layout;



    }




}
