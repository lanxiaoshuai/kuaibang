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
 * Created by jie on 2020/1/21.
 */

public class HistoryAdapter extends BaseAdapter  {

    private List<PositionvBean> addressData;
    private LayoutInflater layoutInflater;

    public HistoryAdapter(Context context, List<PositionvBean> data) {
        super(context, data);
        layoutInflater = LayoutInflater.from(context);
        this.addressData=data;

    }



    @Override
    public int getCount() {
        return addressData.size();
    }

    @Override
    public Object getItem(int position) {
        return addressData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.history_item, null);
            vh.title = (TextView) convertView.findViewById(R.id.search_item_title);
            vh.text = (TextView) convertView.findViewById(R.id.search_item_text);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(addressData.get(position).getName());
        vh.text.setText(addressData.get(position).getAddress());
        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView  text;


    }
}
