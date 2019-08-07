package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.viewHolder.CatalogParentViewHolder;
import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;

import java.util.List;

import drawthink.expandablerecyclerview.adapter.BaseRecyclerViewAdapter;
import drawthink.expandablerecyclerview.bean.RecyclerViewData;

public class MicroNotifyManagerBottomRecyAdapter extends BaseRecyclerViewAdapter<MicroNotifyManagerBean, MicroNotifyManagerBean, CatalogParentViewHolder> {
    private Context ctx;
    private List<RecyclerViewData> datas;
    private LayoutInflater mInflater;

    public MicroNotifyManagerBottomRecyAdapter(Context ctx, List<RecyclerViewData> datas) {
        super(ctx, datas);
        mInflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
        this.datas = datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public View getGroupView(ViewGroup parent) {
        return mInflater.inflate(R.layout.item_catalog_rcy_parent, parent, false);
    }

    @Override
    public View getChildView(ViewGroup parent) {
        return mInflater.inflate(R.layout.item_catalog_rcy_child, parent, false);
    }

    @Override
    public CatalogParentViewHolder createRealViewHolder(Context ctx, View view, int viewType) {
        return new CatalogParentViewHolder(ctx, view, viewType);
    }

    @Override
    public void onBindGroupHolder(CatalogParentViewHolder holder, int groupPos, int position, MicroNotifyManagerBean groupData) {
        holder.tv_title.setText(groupData.getName());
//        holder.tv_num.setText(datas.get(groupPos).getGroupItem().getChildDatas().size()+"/"+datas.get(groupPos).getGroupItem().getChildDatas().size());
        holder.tv_num.setText("200/300");
    }

    @Override
    public void onBindChildpHolder(CatalogParentViewHolder holder, int groupPos, int childPos, int position, MicroNotifyManagerBean childData) {
        holder.tv_child_title.setText(childData.getName());
        holder.tv_remark.setText(childData.getGroupRemark());
        holder.tv_btn_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/8/7 群详情界面
            }
        });
    }
}
