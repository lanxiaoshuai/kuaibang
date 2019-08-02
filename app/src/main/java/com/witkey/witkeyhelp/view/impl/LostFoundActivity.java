package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.LostFoundRecyAdapter;
import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.bean.LostFoundResponse;
import com.witkey.witkeyhelp.presenter.ILostFoundPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.LostFoundPresenterImpl;
import com.witkey.witkeyhelp.util.IntentUtil;
import com.witkey.witkeyhelp.util.callback.ITextViewCallback;
import com.witkey.witkeyhelp.view.ILostFoundView;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;

import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/26 18:45
 * @description 失误招领list
 */
public class LostFoundActivity extends BaseListActivity implements ILostFoundView {

    private List<LostFoundBean> lostFoundList;
    private LostFoundResponse lostFoundRequest;
    private int page = 1;

    private ILostFoundPresenter presenter;
    private int state;//类型
    private LostFoundBean missionBean;
    private boolean isRelease; //是否为发布任务
    private boolean isLoading;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
        isRelease = argIntent.getBooleanExtra("EXTRA_IS_RELEASE", false);
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
            if (lostFoundRequest != null) {
                int totalPage = lostFoundRequest.getTotal() / 10;
                if (lostFoundRequest.getTotal() % 10 != 0) {
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
        if (lostFoundList != null) {
            lostFoundList.clear();
            lostFoundList = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        page = 1;
        allGet();
    }

    private void allGet() {
        presenter.getLostFoundList(missionBean);
    }

    @Override
    protected int setRecyDividerHeight() {
        return 10;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new LostFoundPresenterImpl();
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
            adapter = new LostFoundRecyAdapter(mActivity, lostFoundList);
            recyclerView.setAdapter(adapter);
        } else {
            ((LostFoundRecyAdapter) adapter).setData(lostFoundList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        setIncludeTitle("失物招领");
        setShowConfirm("新增", new ITextViewCallback() {
            @Override
            public void onClick() {
                IntentUtil.startActivity(mActivity, AddLostFoundActivity.class);
            }
        });
        findViewById(R.id.tvBack).setVisibility(View.VISIBLE);
        getData();
    }

    @Override
    public void showLostFoundList(LostFoundResponse lostFoundRequest) {
        getSuc();
        this.lostFoundRequest = lostFoundRequest;
        if (this.lostFoundRequest != null) {
            if (isLoading) {
                lostFoundList.addAll(lostFoundRequest.getRows());
                isLoading = false;
            } else {
                lostFoundList = lostFoundRequest.getRows();
            }
            showAdapter();
        }
        Log.d(TAG, "showLostFoundList: " + this.lostFoundRequest.toString());
    }

    @Override
    protected boolean isLight() {
        return true;
    }
}
