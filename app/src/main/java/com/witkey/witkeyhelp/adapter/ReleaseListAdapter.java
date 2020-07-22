package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.viewHolder.FoundPhotoAdapter;
import com.witkey.witkeyhelp.bean.ReleaseBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.widget.RecycleGridDivider;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2020/3/20.
 */

public class ReleaseListAdapter extends BaseRecyAdapter<ReleaseBean.ReturnObjectBean.RowsBean> {
    public ReleaseListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.lostfound_item, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, int position) {
        ReleaseBean.ReturnObjectBean.RowsBean mission = data.get(position);


        ((ReleaseListAdapter.ViewHolder) holder).releasename.setText(mission.getUser().getRealName() + "");
        ((ReleaseListAdapter.ViewHolder) holder).releasetime.setText(mission.getCreateTime() + "");
        ((ReleaseListAdapter.ViewHolder) holder).releasecontent.setText(mission.getDescribes() + "");
        Glide.with(context).load(URL.getImgPath + mission.getUser().getHeadUrl()).into(((ReleaseListAdapter.ViewHolder) holder).iv_avatar);

        ((ReleaseListAdapter.ViewHolder) holder).imagurl.setLayoutManager(new GridLayoutManager(context, 3));


        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ReleaseListAdapter.ViewHolder) holder).getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }

        if (null == data.get(position).getImgUrl() || "".equals(data.get(position).getImgUrl())) {
            ((ReleaseListAdapter.ViewHolder) holder).imagurl.setVisibility(View.GONE);

        } else {
            List<ReleasePhotoBean> photoList = new ArrayList<>();
            FoundPhotoAdapter photoAdapter = new FoundPhotoAdapter(context, photoList);
            ((ReleaseListAdapter.ViewHolder) holder).imagurl.setAdapter(photoAdapter);
            if (data.get(position).getImgUrl().contains(",")) {

                String[] split = data.get(position).getImgUrl().split(",");
                for (int i = 0; i < split.length; i++) {
                    photoList.add(new ReleasePhotoBean(URL.getImgPath + split[i], true));
                }

            } else {
                photoList.add(new ReleasePhotoBean(URL.getImgPath + data.get(position).getImgUrl(), true));
            }
            photoAdapter.notifyDataSetChanged();
            ((ReleaseListAdapter.ViewHolder) holder).imagurl.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView iv_avatar;
        private TextView releasename;
        private TextView releasetime;
        private TextView releasecontent;
        private RecyclerView imagurl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            releasename = itemView.findViewById(R.id.releasename);
            releasetime = itemView.findViewById(R.id.releasetime);
            releasecontent = itemView.findViewById(R.id.releasecontent);
            imagurl = itemView.findViewById(R.id.photolist);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private ReleaseListAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(ReleaseListAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
