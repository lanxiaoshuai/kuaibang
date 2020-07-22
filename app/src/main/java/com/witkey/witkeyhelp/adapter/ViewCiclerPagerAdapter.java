package com.witkey.witkeyhelp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.view.impl.fragment.PlaceholderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/6/20.
 */

public class ViewCiclerPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<CicleBean.ReturnObjectBean.RowsBean> titles = new ArrayList<>();
    private FragmentManager fm;
    private PlaceholderFragment fragment;

    public ViewCiclerPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<CicleBean.ReturnObjectBean.RowsBean> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {


        return fragments.get(position);
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public long getItemId(int position) {
        return fragments.get(position).hashCode();
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? null : titles.get(position).getAbbreviation();
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        fragment=(PlaceholderFragment)object;
        super.setPrimaryItem(container, position, object);
    }

    public PlaceholderFragment getCurrentFragment() {
        return fragment;
    }
}
