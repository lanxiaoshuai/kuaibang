package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.NewsDetailsAdapter;
import com.witkey.witkeyhelp.adapter.SystemMessageRecyAdapter;
import com.witkey.witkeyhelp.bean.BillFlowBean;
import com.witkey.witkeyhelp.bean.NewsDetailsBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.SystemMessageBean;
import com.witkey.witkeyhelp.presenter.ActivityNewsPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.SystemMessagePresenter;
import com.witkey.witkeyhelp.presenter.impl.ActivityNewsPresenterImpl;
import com.witkey.witkeyhelp.presenter.impl.SystemMessagePresenterImpl;
import com.witkey.witkeyhelp.util.InitRecyUtil;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.ActivityNewsView;
import com.witkey.witkeyhelp.view.SystemMessageView;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;
import com.witkey.witkeyhelp.view.impl.base.BaseListNoIntervalActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityNews extends BaseListNoIntervalActivity implements ActivityNewsView {


    private ActivityNewsPresenter presenter;


    //获取的任务列表数据
    private BillFlowBean.ReturnObjectBean missionResponse;
    private List<BillFlowBean.ReturnObjectBean.RowsBean> missionList;

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
        presenter = new ActivityNewsPresenterImpl();
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
            adapter = new NewsDetailsAdapter(this, missionList);
            recyclerView.setAdapter(adapter);
        } else {
            ((NewsDetailsAdapter) adapter).setData(missionList);
            adapter.notifyDataSetChanged();
        }
   //     String notice = getIntent().getStringExtra("notice");
//        if ("完成".equals(notice)) {
//            Intent intent = new Intent(ActivityNews.this, ActivityBillDetails.class);
//            intent.putExtra("BILLDETAILS", missionList.get(0));
//            startActivity(intent);
//        }
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

        switch (amountmoney) {
            case "余额":
                setIncludeTitle("余额账单");
                presenter.showBillDData(pageNum, 15, user.getUserId(), 1);
                break;
            case "保证金":
                setIncludeTitle("保证金账单");
                presenter.showBillDData(pageNum, 15, user.getUserId(), 2);
                break;
            case "钻石":
                setIncludeTitle("钻石账单");
                presenter.showBillDData(pageNum, 15, user.getUserId(), 0);
                break;
            default:
                break;
        }
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
        ToastUtils.showTestShort(this,error);
    }

    @Override
    public void showBill(BillFlowBean beanFromJson) {
        getSuc();
        BillFlowBean.ReturnObjectBean returnObject = beanFromJson.getReturnObject();

        missionResponse = returnObject;
        List<BillFlowBean.ReturnObjectBean.RowsBean> missionList = returnObject.getRows();

        if (isLoading) {
            this.missionList.addAll(missionList);
            isLoading = false;
        } else {
            this.missionList = missionList;
        }
        showAdapter();

    }

}
