 package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;

/**
 * Created by Administrator on 2018/2/1.
 * 只加载界面
 */

public abstract class InitViewBaseActivity extends BaseActivity {
    /**
     * 加载界面
     */
    protected abstract void initView();

    /**
     * 加载控件
     */
    protected abstract void initWidght();

    /**
     * 获取layout的id，具体由子类实现
     */
    protected abstract int getLayoutResId();

    /**
     * 事件监听
     */
    protected abstract void initEvent();

    /**
     * 从intent中解析数据，具体子类来实现
     */
    protected void parseArgumentsFromIntent(Intent argIntent) {
    }


    @Override
    protected void onCreateActivity() {
        //处理界面
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        }
        if (getIntent() != null) {
            parseArgumentsFromIntent(getIntent());
        }
        initWidght();
        initEvent();
        initView();
    }


}
