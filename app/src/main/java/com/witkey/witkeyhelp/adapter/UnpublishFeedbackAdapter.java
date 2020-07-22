package com.witkey.witkeyhelp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.UnpublishFeedbackBean;
import com.witkey.witkeyhelp.dialog.TaskDetailsDialog;

import java.util.List;

/**
 * Created by jie on 2019/11/29.
 */

public class UnpublishFeedbackAdapter extends BaseAdapter<UnpublishFeedbackBean> {


    public UnpublishFeedbackAdapter(Context context, List<UnpublishFeedbackBean> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "getView: " + context);
            convertView = View.inflate(
                    (Activity) context,
                    R.layout.unpublish_feedback_item,
                    null);
            holder = new UnpublishFeedbackAdapter.ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.cb_is = (CheckBox) convertView.findViewById(R.id.cb_is);
            convertView.setTag(holder);
        } else {
            holder = (UnpublishFeedbackAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(data.get(position).getMsg());
        if (data.get(position).isChecked()) {//状态选中
            holder.cb_is.setChecked(true);
        } else {
            holder.cb_is.setChecked(false);
        }
//        holder.cb_is.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                if (b) {
//                    data.get(position).setChecked(true);
//                } else {
//                    data.get(position).setChecked(false);
//                }
//            }
//        });

        return convertView;
    }

    class ViewHolder {
        private TextView tv_title;
        private CheckBox cb_is;
    }
}
