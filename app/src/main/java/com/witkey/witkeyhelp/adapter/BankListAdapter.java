package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.MyCardBean;


import java.util.List;

/**
 * Created by jie on 2019/12/7.
 */

public class BankListAdapter extends BaseAdapter<MyCardBean.ReturnObjectBean.RowsBean> {
    public BankListAdapter(Context context, List<MyCardBean.ReturnObjectBean.RowsBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "getView: " + context);
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.bank_list_item,
                    null);
            holder = new ViewHolder();
            holder.ban_img = (ImageView) convertView.findViewById(R.id.ban_img);
            holder.bankname = (TextView) convertView.findViewById(R.id.bankname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //holder.bankname.setText(data.get(position).getContent());
        if(position==0){
            if (!TextUtils.isEmpty(data.get(position).getCardNo()) && data.get(position).getCardNo().length() >= 4) {


                holder.bankname.setText(data.get(position).getBank().getBankName());
            }
        }else {
            if (!TextUtils.isEmpty(data.get(position).getCardNo()) && data.get(position).getCardNo().length() >= 4) {


                holder.bankname.setText(data.get(position).getBank().getBankName().substring(2,data.get(position).getBank().getBankName().length())+"(" + data.get(position).getCardNo().substring(data.get(position).getCardNo().length() - 4, data.get(position).getCardNo().length()) + ")");
            }
        }

        Glide.with(context).load(URL.getImgPath + data.get(position).getBank().getMiniIcon()).into(holder.ban_img);
        return convertView;
    }

    class ViewHolder {
        // private ImageView bank_img;
        private TextView bankname;
        private ImageView ban_img;

    }
}
