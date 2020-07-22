package com.witkey.witkeyhelp.adapter.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.BaseRecyAdapter;
import com.witkey.witkeyhelp.adapter.ReleaseDetailsAdapter;
import com.witkey.witkeyhelp.adapter.ReleasePhotoAdapter;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.view.impl.PhotoActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2020/4/30.
 */

public class FoundPhotoAdapter extends BaseRecyAdapter<ReleasePhotoBean> {



    public FoundPhotoAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.releasephoto_item, null);
        return new FoundPhotoAdapter.ViewHolder(view);
    }



    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.deafult_icon);
        options.error(R.mipmap.deafult_icon);
        Glide.with(context).load(data.get(position).getUrl()).apply(options).into(((FoundPhotoAdapter.ViewHolder) holder).tv_image);
        ((ViewHolder) holder).itemView.setClickable(false);

    }




    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView tv_image;


        public ViewHolder(View v) {
            super(v);

            tv_image = v.findViewById(R.id.iv_pic);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }




}
