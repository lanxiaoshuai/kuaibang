package com.witkey.witkeyhelp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.NoviceHelpBean;

import java.util.List;

/**
 * Created by jie on 2020/4/13.
 */

public class NoviceHelpAdapter extends BaseAdapter<NoviceHelpBean> {
    public NoviceHelpAdapter(Context context, List<NoviceHelpBean> data) {
        super(context, data);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.noice_item,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = convertView.findViewById(R.id.release_task);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(data.get(position).getTitleHome() + "");
        return convertView;
    }

    class ViewHolder {
        private TextView tv_title;

    }
}
