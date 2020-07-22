package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.List;

/**
 * Created by jie on 2020/6/8.
 */

public class MessageHeaderAdapter extends BaseAdapter<String> {
    public MessageHeaderAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "getView: " + context);
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.item_conv_list,
                    null);
            holder = new ViewHolder();
            holder.conv_item_name = (TextView) convertView.findViewById(R.id.conv_item_name);
            holder.msg_item_content = (TextView) convertView.findViewById(R.id.msg_item_content);
            holder.msg_item_head_icon = convertView.findViewById(R.id.msg_item_head_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.conv_item_name.setText("消息通知");
        holder.msg_item_content.setText("1小时以前");
        Glide.with(context).load(R.mipmap.ic_launcher).into(holder.msg_item_head_icon);
        return convertView;
    }

    class ViewHolder {
        private TextView conv_item_name;
        private TextView msg_item_content;
        private RoundImageView msg_item_head_icon;
    }
}
