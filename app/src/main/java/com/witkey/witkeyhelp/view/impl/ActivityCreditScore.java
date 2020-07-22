package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.CreditScoreAdapter;
import com.witkey.witkeyhelp.adapter.NewsDetailsAdapter;
import com.witkey.witkeyhelp.bean.BillFlowBean;
import com.witkey.witkeyhelp.bean.CreditScoreBean;
import com.witkey.witkeyhelp.presenter.ActivityCreditScorePresenter;
import com.witkey.witkeyhelp.presenter.ActivityNewsPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.ActivityCrediScorePresenterImpl;
import com.witkey.witkeyhelp.presenter.impl.ActivityNewsPresenterImpl;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.ActivityCreditScoreView;
import com.witkey.witkeyhelp.view.ActivityNewsView;
import com.witkey.witkeyhelp.view.impl.base.BaseListNoIntervalActivity;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityCreditScore extends BaseListNoIntervalActivity implements ActivityCreditScoreView {


    private ActivityCreditScorePresenter presenter;


    //获取的任务列表数据
    private CreditScoreBean.ReturnObjectBean missionResponse;
    private List<CreditScoreBean.ReturnObjectBean.RowsBean> missionList;

    private boolean isLoading = false;

    private int pageNum = 1;

    private String amountmoney;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        amountmoney = argIntent.getStringExtra("amountmoney");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new ActivityCrediScorePresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
            if (missionResponse != null) {
                int totalPage = missionResponse.getTotal() / 15;
                if (missionResponse.getTotal() % 15 != 0) {
                    totalPage += 1;
                }
                if (totalPage > pageNum) {
                    pageNum += 1;
                    isLoading = true; // 在获取前修改状态
                    allGet();
                }
            }
        }
    }

    @Override
    protected void onRefresh() {

        pageNum = 1;
        allGet();
    }

    @Override
    protected int setRecyDividerHeight() {
        return 10;
    }


    private void showAdapter() {
        if (adapter == null) {
            adapter = new CreditScoreAdapter(this, missionList);
            recyclerView.setAdapter(adapter);
        } else {
            ((CreditScoreAdapter) adapter).setData(missionList);
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    protected void initWidget() {
        super.initWidget();


    }

    private void allGet() {


        setIncludeTitle("信誉分");
        presenter.showBillDData(pageNum, 15, user.getUserId());


    }


    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        pageNum = 1;
        allGet();
    }

    @Override
    public void onError(String error) {
        getSuc();
        ToastUtils.showTestShort(this, error);
    }

    @Override
    public void showBill(CreditScoreBean beanFromJson) {
        getSuc();
        CreditScoreBean.ReturnObjectBean returnObject = beanFromJson.getReturnObject();

        missionResponse = returnObject;
        List<CreditScoreBean.ReturnObjectBean.RowsBean> missionList = returnObject.getRows();

        if (isLoading) {
            this.missionList.addAll(missionList);
            isLoading = false;
        } else {
            this.missionList = missionList;
        }
        showAdapter();

    }

}
