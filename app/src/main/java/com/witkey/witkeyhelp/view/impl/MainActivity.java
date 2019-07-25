package com.witkey.witkeyhelp.view.impl;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.fragment.HomeFragment;
import com.witkey.witkeyhelp.view.impl.fragment.MeFragment;
import com.witkey.witkeyhelp.view.impl.fragment.MessageFragment;
import com.witkey.witkeyhelp.view.impl.fragment.RewardHallFragment;

import java.util.ArrayList;

/**
 * @author lingxu
 * @date 2019/7/12 14:19
 * @description 主页面
 */
public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, RewardHallFragment.OnFragmentInteractionListener, MessageFragment.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {
    private ArrayList<Fragment> mFragments;
    private int lastIndex;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragmentPosition(0);
                    return true;
                case R.id.navigation_rewardhall:
                    setFragmentPosition(1);
                    return true;
                case R.id.navigation_message:
                    setFragmentPosition(2);
                    return true;
                case R.id.navigation_me:
                    setFragmentPosition(3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_main);
        initViews();
        setTitle(R.string.title_home);
        initBottomNavigation();
        initData();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new RewardHallFragment());
        mFragments.add(new MessageFragment());
        mFragments.add(new MeFragment());
        // 初始化展示MessageFragment
        setFragmentPosition(0);
    }

    private void setFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.content, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    private void initBottomNavigation() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initViews() {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
