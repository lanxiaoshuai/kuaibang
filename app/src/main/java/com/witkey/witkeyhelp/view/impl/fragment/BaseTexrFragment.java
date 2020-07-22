package com.witkey.witkeyhelp.view.impl.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.mm.opensdk.utils.Log;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by jie on 2020/6/18.
 */

public abstract class BaseTexrFragment extends Fragment {

    protected View mRootView;
    public Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    public BaseTexrFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.d("TAG", "fragment->setUserVisibleHint");
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
//        Log.d("TAG", "fragment->onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView();
        }
//        Log.d("TAG", "fragment->onCreateView");
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.d("TAG", "fragment->onActivityCreated");
        isPrepared = true;
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }

        initData();
        isFirst = false;
    }

    //do something
    protected void onInvisible() {


    }

    public abstract View initView();

    public abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JMessageClient.unRegisterEventReceiver(this);
        EventBus.getDefault().unregister(this);
    }
}
