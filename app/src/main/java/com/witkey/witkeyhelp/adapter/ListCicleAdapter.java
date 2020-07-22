package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.BaseAdapter;
import com.witkey.witkeyhelp.bean.CicleBean;

import java.util.List;

/**
 * Created by jie on 2020/6/20.
 */

public class ListCicleAdapter extends BaseAdapter<CicleBean.ReturnObjectBean.RowsBean> {
    public ListCicleAdapter(Context context, List<CicleBean.ReturnObjectBean.RowsBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "getView: " + context);
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.listcicle_item,
                    null);
            holder = new ViewHolder();
            holder.cicle_name = (TextView) convertView.findViewById(R.id.cicle_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cicle_name.setText(data.get(position).getCircleName());
        return convertView;
    }

    class ViewHolder {
        private TextView cicle_name;

    }
}
