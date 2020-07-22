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

import java.util.List;

/**
 * Created by jie on 2020/4/3.
 */

public class DiamondPicturesAdapter extends BaseAdapter<String> {
    public DiamondPicturesAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoider holder;
        if (convertView == null) {

            convertView = View.inflate(
                    (Activity) context,
                    R.layout.diamondpictures_item,
                    null);
            holder = new ViewHoider();
            holder.imageView= convertView.findViewById(R.id.imagview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoider) convertView.getTag();
        }
        Glide.with(context).load(data.get(position)).into(holder.imageView);
        return convertView;
    }
    class ViewHoider{
        private ImageView imageView;
    }
}
