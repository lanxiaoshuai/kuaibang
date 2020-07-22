package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.PositionvBean;

import java.util.List;

/**
 * Created by jie on 2020/6/1.
 */

public class NearbyAdapter extends BaseAdapter {

    private List<String> addressData;
    private LayoutInflater layoutInflater;

    public NearbyAdapter(Context context, List<String> data) {
        super(context, data);
        layoutInflater = LayoutInflater.from(context);
        this.addressData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.hot_item, null);
            vh.hot = (TextView) convertView.findViewById(R.id.hot);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.hot.setText(addressData.get(position));

        return convertView;
    }

    private class ViewHolder {
        TextView hot;


    }

}
