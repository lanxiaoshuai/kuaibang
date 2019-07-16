package com.witkey.witkeyhelp.view.impl;

import com.witkey.witkeyhelp.presenter.IPresenter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/1.
 * 继承只加载界面 加载presenter,获取数据或上传数据
 */

public abstract class InitPresenterBaseActivity extends InitViewBaseActivity {
    /**
     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
     */
    protected abstract IPresenter[] getPresenters();

    /**
     * 初始化presenters
     */
    protected abstract void onInitPresenters();

    private Set<IPresenter> mAllPresenters = new HashSet<>(1);

    private void addPresenters() {
        IPresenter[] presenters = getPresenters();
        if (presenters != null) {
            for (int i = 0; i < presenters.length; i++) {
                mAllPresenters.add(presenters[i]);
            }
        }
    }

    //上传数据
//    private RequestBean bean;
//    protected RequestBean getRequestBean() {
//        if (bean == null) {
//            bean = new RequestBean(getClassName());
//        }
//        return bean;
//    }
    protected String getClassName(){
        return "page--ActivityName=" + mActivity.getClass().getName();
    }

    @Override
    protected void initView() {
        addPresenters();
        onInitPresenters();
        initViewExceptPresenter();
    }

    /**
     * 加载界面除了presenter
     */
    protected abstract void initViewExceptPresenter();
}
