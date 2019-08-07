package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MicroNotifyManagerBottomRecyAdapter;
import com.witkey.witkeyhelp.adapter.MicroNotifyManagerTopRecyAdapter;
import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;
import com.witkey.witkeyhelp.presenter.IMicroNotifyManagerPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MicroNotifyManagerPresenterImpl;
import com.witkey.witkeyhelp.util.InitRecyUtil;
import com.witkey.witkeyhelp.util.callback.ITextViewCallback;
import com.witkey.witkeyhelp.view.IMicroNotifyManagerView;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

import drawthink.expandablerecyclerview.bean.RecyclerViewData;

/**
 * @author lingxu
 * @date 2019/7/26 18:45
 * @description 微通知管理
 */
public class MicroNotifyManagerActivity extends BaseListActivity implements IMicroNotifyManagerView {
    private RecyclerView hor_recyclerView;
    private LinearLayoutManager layoutManager;
    private List<MicroNotifyManagerBean> topMicroNotifyManagerList;

    private List<RecyclerViewData> datas;
    private List<MicroNotifyManagerBean> bottomMicroNotifyManagerList;
    private MicroNotifyManagerBottomRecyAdapter bottomRecyAdapter;

    private IMicroNotifyManagerPresenter presenter;


    private boolean isTopData = true;
    private int currentCatalogId;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        hor_recyclerView = findViewById(R.id.hor_recyclerView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }


    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        if (layoutManager == null) {
            layoutManager = InitRecyUtil.initHorListRecy(mActivity, hor_recyclerView, 3);
        }
        setIncludeTitle("微通知");
        setShowConfirm("发送", new ITextViewCallback() {
            @Override
            public void onClick() {
                // TODO: 2019/8/6 弹出对话框
//                IntentUtil.startActivity(mActivity, AddMicroNotificationActivity.class);
            }
        });
        findViewById(R.id.tvBack).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user != null) {
            getData();
        } else {
            getSuc();
        }
    }

    private void getData() {
        if (isTopData) {
            presenter.getMicroNotifyManagerList(user.getUserId(), 0);
        } else {
            presenter.getMicroNotifyManagerList(user.getUserId(), currentCatalogId);
        }
    }


    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MicroNotifyManagerPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_micro_notify_manager;
    }

    @Override
    public void showMicroNotifyManager(ArrayList<MicroNotifyManagerBean> microNotifyManagerBeans) {
        getSuc();
        if (isTopData) {
            isTopData = false;
            this.topMicroNotifyManagerList = microNotifyManagerBeans;
            showUpAdapter();
        } else {
            this.bottomMicroNotifyManagerList = microNotifyManagerBeans;
                if (datas != null) {
                    datas = null;
                }
                datas = new ArrayList<>();
                for (MicroNotifyManagerBean bean : bottomMicroNotifyManagerList) {

                    datas.add(new RecyclerViewData<MicroNotifyManagerBean, MicroNotifyManagerBean>(bean, bean.getManagerBeanList(), false));
                }
            showBottomAdapter();
            Log.d(TAG, "showMicroNotifyManager: " + bottomMicroNotifyManagerList.toString());
        }
    }

    private void showBottomAdapter() {
        if (bottomRecyAdapter == null) {
            bottomRecyAdapter = new MicroNotifyManagerBottomRecyAdapter(mActivity, datas);
            recyclerView.setAdapter(bottomRecyAdapter);
        } else {
            bottomRecyAdapter.setAllDatas(datas);
            adapter.notifyDataSetChanged();
        }
    }

    private void showUpAdapter() {
        if (adapter == null) {
            adapter = new MicroNotifyManagerTopRecyAdapter(mActivity, topMicroNotifyManagerList);
            hor_recyclerView.setAdapter(adapter);
            ((MicroNotifyManagerTopRecyAdapter) adapter).setCurrentCatalogid(topMicroNotifyManagerList.get(0).getCatalogId());
            setCurrentcatalogId(topMicroNotifyManagerList.get(0).getCatalogId());
            ((MicroNotifyManagerTopRecyAdapter) adapter).setCheckListener(new MicroNotifyManagerTopRecyAdapter.CheckListener() {
                @Override
                public void onCheck(int catalogId) {
                    setCurrentcatalogId(catalogId);
                }
            });
        } else {
            ((MicroNotifyManagerTopRecyAdapter) adapter).setData(topMicroNotifyManagerList);
            adapter.notifyDataSetChanged();
        }
    }

    private void setCurrentcatalogId(int catalogId2) {
        currentCatalogId = catalogId2;
        getData();
    }

    @Override
    protected int setRecyDividerHeight() {
        return 2;
    }

    @Override
    protected boolean isLight() {
        return true;
    }

    @Override
    protected boolean isUseSwipeRefreshLayout() {
        return false;
    }

    @Override
    protected void onLoadMore() {

    }

    @Override
    protected void onRefresh() {
    }


}
