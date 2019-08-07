package com.witkey.witkeyhelp.adapter.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;

import drawthink.expandablerecyclerview.holder.BaseViewHolder;

public class CatalogParentViewHolder extends BaseViewHolder {
    //parent
    public CheckBox cb_is_parent;
    public TextView tv_title;
    public TextView tv_num;

    //child
    public CheckBox cb_is_child;
    public TextView tv_child_title;
    public TextView tv_remark;
    public TextView tv_btn_manage;


    public CatalogParentViewHolder(Context ctx, View itemView, int viewType) {
        super(ctx, itemView, viewType);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_num = itemView.findViewById(R.id.tv_num);

        tv_child_title = itemView.findViewById(R.id.tv_child_title);
        tv_remark = itemView.findViewById(R.id.tv_remark);
        tv_btn_manage = itemView.findViewById(R.id.tv_btn_manage);
    }

    @Override
    public int getChildViewResId() {
        return R.id.rl_child;
    }

    @Override
    public int getGroupViewResId() {
        return R.id.rl_parent;
    }
}
