package com.witkey.witkeyhelp.view.impl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.view.impl.LoginActivity;

/**
 * Created by jie on 2019/12/28.
 */

public class NotLoggedInFragment extends BaseFragment {

    @Override
    protected int getContentView() {
        return R.layout.fragment_message;
    }

    @Override
    protected IPresenter[] getPresenters() {
        return new IPresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected void initEvent() {

    }


    @Override
    protected void initViewExceptPresenter() {
        setIncludeTitle("消息");
    }

    @Override
    protected void initWidget() {
        findViewById(R.id.logiin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}
