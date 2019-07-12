package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Tag;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.UIUtils;

import java.util.List;


public class ChoosePicAdapter extends BaseAdapter<Tag> {
    private int selectId;
    private OnClickListener listener;

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        this.notifyDataSetChanged();
    }

    public ChoosePicAdapter(Context context, List<Tag> data) {
        super(context, data);
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        final ViewHolder holder;
        if (v == null) {
            v = View.inflate(context, R.layout.item_lv_choose_pic, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) v.findViewById(R.id.tv_title);
            holder.cb_is = (CheckBox) v.findViewById(R.id.cb_is);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        final Tag d = data.get(position);

        if (d.getPicId() != 0) {
            ImgUtil.setDrawableLeft(context, d.getPicId(), holder.tv_title);
            v.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(15), UIUtils.dip2px(10), UIUtils.dip2px(15));
            holder.cb_is.setEnabled(false);
        }
        holder.tv_title.setText(d.getName());

        if (d.getId() == getSelectId()) {
            holder.cb_is.setChecked(true);
        } else {
            holder.cb_is.setChecked(false);
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectId(d.getId());
                if (listener != null) {
                    listener.onClick(d);
                }
            }
        });
        return v;
    }


    class ViewHolder {
        private TextView tv_title;
        private CheckBox cb_is;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onClick(Tag workIndustry);
    }
}
