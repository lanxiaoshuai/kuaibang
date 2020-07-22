package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.widget.imagepicker.SelectDialog;

import java.util.List;

/**
 * Created by jie on 2020/6/18.
 */

public class MyCiclesAdapter extends BaseAdapter<CicleBean.ReturnObjectBean.RowsBean> {
    public MyCiclesAdapter(Context context, List<CicleBean.ReturnObjectBean.RowsBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "getView: " + context);
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.my_cicles_item,
                    null);
            holder = new ViewHolder();
            holder.circle = (TextView) convertView.findViewById(R.id.circle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (data.get(position).isSelected()) {
            holder.circle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.circle.setTextColor(context.getResources().getColor(R.color.font_color));
        }
        holder.circle.setText(data.get(position).getAbbreviation());
        return convertView;
    }

    private class ViewHolder {
        private TextView circle;


    }


}
