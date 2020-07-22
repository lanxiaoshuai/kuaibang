package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.view.impl.fragment.PlaceholderFragment;
import com.witkey.witkeyhelp.widget.drag.DragAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Egos on 2017/11/7.
 */

public class MyDragAdapter extends DragAdapter {

    private Context mContext;

    private List<CicleBean.ReturnObjectBean.RowsBean> undragLst;
    private List<CicleBean.ReturnObjectBean.RowsBean> selectLst;
    private List<CicleBean.ReturnObjectBean.RowsBean> unselectLst;
    private List<Fragment> fragments;


    private static final String ARG_TITLE = "section_number";

    public MyDragAdapter(Context context, List<CicleBean.ReturnObjectBean.RowsBean> undragLst, List<CicleBean.ReturnObjectBean.RowsBean> selectLst,
                         List<CicleBean.ReturnObjectBean.RowsBean> unselectLst, List<Fragment> fragments) {
        mContext = context;
        this.undragLst = undragLst;
        this.selectLst = selectLst;
        this.unselectLst = unselectLst;
        this.fragments = fragments;
    }

    @Override
    public View getSelectView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        ((TextView) view.findViewById(R.id.text)).setText(selectLst.get(position).getAbbreviation());

        return view;
    }

    @Override
    public View getUnselectView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        ((TextView) view.findViewById(R.id.text)).setText(unselectLst.get(position).getAbbreviation());
        ((ImageView) view.findViewById(R.id.add_delete)).setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public View getUndragView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        ((TextView) view.findViewById(R.id.text)).setText(undragLst.get(position).getAbbreviation());
        view.findViewById(R.id.bbb).setBackground(mContext.getResources().getDrawable(R.drawable.item_cicle_bg));
        return view;
    }

    @Override
    public int getSelectCount() {
        return selectLst == null ? 0 : selectLst.size();
    }

    @Override
    public int getUnselectCount() {
        return unselectLst == null ? 0 : unselectLst.size();
    }

    @Override
    public int getUndragCount() {
        return undragLst == null ? 0 : undragLst.size();
    }

    @Override
    public void removeSelect(int position) {
        fragments.remove(position);
        CicleBean.ReturnObjectBean.RowsBean item = selectLst.remove(position);

        unselectLst.add(0, item);
    }

    @Override
    public void removeUnselect(int position) {
        CicleBean.ReturnObjectBean.RowsBean item = unselectLst.remove(position);
        selectLst.add(item);
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, item.getCircleId());
        fragment.setArguments(bundle);
        fragments.add(fragment);

    }

    @Override
    public void reAdd(int srcPosition, int dstPosition) {
        CicleBean.ReturnObjectBean.RowsBean item = selectLst.remove(srcPosition);
        selectLst.add(dstPosition, item);
        Fragment fragment = fragments.remove(srcPosition);
        fragments.add(dstPosition, fragment);
        Log.e("tag", item.getCircleName());
    }


    public void editCircle(boolean hetherwEdit, List<View> selectList, List<View> undragList) {
        Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.rotate);//加载动画资源文件  
        if (undragList == null || undragList.size() == 0) {
            for (int i = 0; i < selectList.size(); i++) {
                if (hetherwEdit) {
                    selectList.get(i).findViewById(R.id.add_delete).setVisibility(View.VISIBLE);

                    selectList.get(i).findViewById(R.id.bbb).setBackground(mContext.getResources().getDrawable(R.drawable.item_cicle_bg));
                    selectList.get(i).findViewById(R.id.bbb).startAnimation(shake);//给组件播放动画效果 

                } else {
                    selectList.get(i).findViewById(R.id.add_delete).setVisibility(View.INVISIBLE);
                    selectList.get(i).findViewById(R.id.bbb).setBackground(mContext.getResources().getDrawable(R.drawable.item_bg));
                    selectList.get(i).findViewById(R.id.bbb).clearAnimation();
                }
            }
        } else {
            for (int i = undragList.size(); i < selectList.size(); i++) {
                if (hetherwEdit) {
                    selectList.get(i).findViewById(R.id.add_delete).setVisibility(View.VISIBLE);
                    selectList.get(i).findViewById(R.id.bbb).setBackground(mContext.getResources().getDrawable(R.drawable.item_cicle_bg));
                    selectList.get(i).findViewById(R.id.bbb).startAnimation(shake);//给组件播放动画效果 

                } else {
                    selectList.get(i).findViewById(R.id.add_delete).setVisibility(View.INVISIBLE);
                    selectList.get(i).findViewById(R.id.bbb).setBackground(mContext.getResources().getDrawable(R.drawable.item_bg));
                    selectList.get(i).findViewById(R.id.bbb).clearAnimation();
                }
            }
        }

    }
}


