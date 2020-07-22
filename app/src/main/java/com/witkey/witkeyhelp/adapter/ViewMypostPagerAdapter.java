package com.witkey.witkeyhelp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.witkey.witkeyhelp.bean.AppBean;
import com.witkey.witkeyhelp.bean.CicleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/6/20.
 */

public class ViewMypostPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<AppBean> titles = new ArrayList<>();


    public ViewMypostPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<AppBean> titles) {
        super(fm);
        this.fragments=fragments;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {


        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? null : titles.get(position).getFuncName();
    }

}
