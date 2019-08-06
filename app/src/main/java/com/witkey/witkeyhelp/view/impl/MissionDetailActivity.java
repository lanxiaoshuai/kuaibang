package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.dialog.ReceiptSucDia;
import com.witkey.witkeyhelp.presenter.IMissionDetailPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MissionDetailPresenterImpl;
import com.witkey.witkeyhelp.view.IMissionDetailView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

/**
 * @author lingxu
 * @date 2019/7/26 15:13
 * @description 任务详情
 */
public class MissionDetailActivity extends InitPresenterBaseActivity implements View.OnClickListener, IMissionDetailView {
    private LinearLayout ll_share;
    private TextView tv_mission_type;
    private TextView tv_describe;
    private TextView tv_date;
    private TextView tv_money;
    private TextView tv_bargainingType;
    private TextView tv_contact;
    private TextView tv_chat;
    private TextView tv_collect;
    private TextView tv_report;
    private TextView tv_commit;

    private IMissionDetailPresenter presenter;
    private int businessId;
    private int orderId;
    private MissionBean missionBean;

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MissionDetailPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        businessId = getIntent().getIntExtra("EXTRA_BUSINESS_ID", 0);
        orderId = getIntent().getIntExtra("EXTRA_ORDER_ID", 0);
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void initViewExceptPresenter() {
        presenter.getMissionDetail(businessId + "");
    }

    @Override
    protected void initWidget() {
        ll_share = findViewById(R.id.ll_share);
        tv_mission_type = findViewById(R.id.tv_mission_type);
        tv_describe = findViewById(R.id.tv_describe);
        tv_date = findViewById(R.id.tv_date);
        tv_money = findViewById(R.id.tv_money);
        tv_bargainingType = findViewById(R.id.tv_bargainingType);
        tv_contact = findViewById(R.id.tv_contact);
        tv_chat = findViewById(R.id.tv_chat);
        tv_collect = findViewById(R.id.tv_collect);
        tv_report = findViewById(R.id.tv_report);
        tv_commit = findViewById(R.id.tv_commit);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mission_detail;
    }

    @Override
    protected void initEvent() {
        ll_share.setOnClickListener(this);
        tv_chat.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        tv_report.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_share:
                break;
            case R.id.tv_chat:
                break;
            case R.id.tv_collect:
                break;
            case R.id.tv_report:
                break;
            case R.id.tv_commit:

                presenter.receipt(orderId, user.getUserId());
                break;
        }
    }

    @Override
    public void showMission(MissionBean missionBean) {
        if (missionBean != null) {
            this.missionBean = missionBean;
            tv_mission_type.setText(missionBean.getProductType() == null ? "" : Integer.parseInt(missionBean.getProductType()) == 1 ? "普通任务" : "竞标任务");
            tv_describe.setText(missionBean.getDescribes());
            tv_date.setText(missionBean.getEndDate());
            tv_money.setText("￥:" + missionBean.getPrice());
            tv_bargainingType.setText(missionBean.getBiddingType() == null ? "" : Integer.parseInt(missionBean.getBiddingType()) == 1 ? "是" : "否");
            tv_contact.setText(missionBean.getContactsPhone());
        }
    }

    @Override
    public void receiptSuc() {
        ReceiptSucDia receiptSucDia = new ReceiptSucDia(mActivity);
        receiptSucDia.show();
    }
}
