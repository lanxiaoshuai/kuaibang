package com.witkey.witkeyhelp.view.impl;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.ViewCiclerPagerAdapter;
import com.witkey.witkeyhelp.adapter.ViewMypostPagerAdapter;
import com.witkey.witkeyhelp.bean.AppBean;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.fragment.MyPostFragment;
import com.witkey.witkeyhelp.view.impl.fragment.PlaceholderFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/7/3.
 */

public class ActivityMyPost extends BaseActivity {

    private List<AppBean> mlist;
    private List<Fragment> fragments;
    private TabLayout tabLayout;
    private ViewPager container_pager;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_my_post);
        setIncludeTitle("我的发布");
        tabLayout = findViewById(R.id.release);
        container_pager = findViewById(R.id.container_pager);
        mlist = new ArrayList<>();
        fragments = new ArrayList<>();
        mlist.add(new AppBean(0, "全部"));
        mlist.add(new AppBean(1, "咨询"));
        mlist.add(new AppBean(2, "帮忙"));
        mlist.add(new AppBean(5, "动态"));
        for (int i = 0; i < mlist.size(); i++) {
            MyPostFragment fragment = new MyPostFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("ARG_TITLE", mlist.get(i).getIcon());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        ViewMypostPagerAdapter mViewAdapter = new ViewMypostPagerAdapter(getSupportFragmentManager(), fragments, mlist);
        container_pager.setAdapter(mViewAdapter);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(container_pager);
            }
        });

    }
}
