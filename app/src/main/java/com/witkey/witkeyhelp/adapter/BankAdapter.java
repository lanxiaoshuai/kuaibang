package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.AllCardBean;

import java.util.List;

/**
 * Created by jie on 2019/12/7.
 */

public class BankAdapter extends BaseAdapter<AllCardBean> {
    public BankAdapter(Context context, List<AllCardBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "getView: " + context);
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.bank_item,
                    null);
            holder = new BankAdapter.ViewHolder();
            holder.bank_img = (ImageView) convertView.findViewById(R.id.back_img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(URL.getImgPath+data.get(position).getImgUrl()).into(holder.bank_img);
    //  holder.bank_img.setImageResource(data.get(position).);

        return convertView;
    }

    class ViewHolder {
        private ImageView bank_img;

    }
}
