package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MicroNotificationRecyAdapter;
import com.witkey.witkeyhelp.bean.MicroNotificationResponse;
import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.IMicroNotificationPresenter;
import com.witkey.witkeyhelp.presenter.impl.MicroNotificationPresenterImpl;
import com.witkey.witkeyhelp.util.IntentUtil;
import com.witkey.witkeyhelp.util.callback.ITextViewCallback;
import com.witkey.witkeyhelp.view.IMicroNotificationView;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;

import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/26 18:45
 * @description 微通知list
 */
public class MicroNotificationActivity extends BaseListActivity implements IMicroNotificationView {

    private List<MicroNotifyManagerBean> microNotificationList;
    private MicroNotificationResponse microNotificationResponse;
    private int page = 1;

    private IMicroNotificationPresenter presenter;
    private boolean isLoading;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
            if (microNotificationResponse != null) {
                int totalPage = microNotificationResponse.getTotal() / 10;
                if (microNotificationResponse.getTotal() % 10 != 0) {
                    totalPage += 1;
                }
                if (totalPage > page) {
                    page += 1;
                    isLoading = true; // 在获取前修改状态
                    allGet();
                }
            }
        }
    }

    @Override
    protected void onRefresh() {
        getData();
    }

    private void getData() {
        if (microNotificationList != null) {
            microNotificationList.clear();
            microNotificationList = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        page = 1;
        allGet();
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    private void allGet() {
        presenter.getMicroNotificationList(user.getUserId());
    }

    @Override
    protected int setRecyDividerHeight() {
        return 10;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MicroNotificationPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_refresh_recyclerview;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    protected void initWidget() {
        super.initWidget();
    }

    private void showAdapter() {
        if (adapter == null) {
            adapter = new MicroNotificationRecyAdapter(mActivity, microNotificationList);
            recyclerView.setAdapter(adapter);
        } else {
            ((MicroNotificationRecyAdapter) adapter).setData(microNotificationList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        setIncludeTitle("微通知");
        setShowConfirm("新增", new ITextViewCallback() {
            @Override
            public void onClick() {
                IntentUtil.startActivity(mActivity, AddMicroNotificationActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 2019/8/6  问题 ,不加载
        if (user != null) {
            getData();
        }
    }

    @Override
    public void showMicroNootificationList(MicroNotificationResponse microNotificationResponse) {
        getSuc();
        this.microNotificationResponse = microNotificationResponse;
        if (this.microNotificationResponse != null) {
            if (isLoading) {
                microNotificationList.addAll(microNotificationResponse.getRows());
                isLoading = false;
            } else {
                microNotificationList = microNotificationResponse.getRows();
            }
            showAdapter();
        }
        Log.d(TAG, "showMicroNotificationList: " + this.microNotificationResponse.toString());
    }

    @Override
    protected boolean isLight() {
        return true;
    }
}
