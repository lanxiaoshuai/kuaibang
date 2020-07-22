package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.AllCardBean;
import com.witkey.witkeyhelp.bean.MyCardBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.presenter.AmountMoneyPresenter;
import com.witkey.witkeyhelp.presenter.IConsultPresenter;
import com.witkey.witkeyhelp.presenter.IMeFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.AmountMoneyPresenterImpl;
import com.witkey.witkeyhelp.presenter.impl.ConsultPresenterImpl;
import com.witkey.witkeyhelp.presenter.impl.MeFragPresenterImpl;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.AmountMoneyView;
import com.witkey.witkeyhelp.view.IConsultView;
import com.witkey.witkeyhelp.view.IMeFragView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

import java.util.List;

/**
 * Created by jie on 2019/12/7.
 */

public class ActivityBond extends InitPresenterBaseActivity implements View.OnClickListener, IMeFragView {

    private IMeFragPresenter presenter;
    private LinearLayout withdraw;
    private LinearLayout cancel;
    private TextView tv_confirm;
    private TextView bond_text;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.withdraw:
                if (PventQuickClick.isLastFastDoubleClick()) {
                    break;
                }
                intent = new Intent(this, ActivityCashWithdrawal.class);
                intent.putExtra("amountmoney", "保证金");
                startActivity(intent);
                break;
            case R.id.cancel:
                if (PventQuickClick.isLastFastDoubleClick()) {
                    break;
                }
                intent = new Intent(this, ActivityMarginRecharge.class);
                startActivity(intent);
                break;
            case R.id.tv_confirm:
                if (PventQuickClick.isLastFastDoubleClick()) {
                    break;
                }

                intent = new Intent(this, ActivityNews.class);
                intent.putExtra("amountmoney", "保证金");
                startActivity(intent);
                MobclickAgent.onEvent(this, "bondBill");
                break;
            default:
                break;
        }
    }


    @Override
    protected void initWidget() {
        withdraw = findViewById(R.id.withdraw);
        cancel = findViewById(R.id.cancel);
        tv_confirm = findViewById(R.id.tv_confirm);
        bond_text = findViewById(R.id.bond_text);
        //  String price = getIntent().getStringExtra("price");


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_bond;
    }

    @Override
    protected void initEvent() {
        withdraw.setOnClickListener(this);
        cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MeFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        setIncludeTitle("保证金");

    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogUtil.showProgress(this);
        presenter.getAcount(user.getUserId());
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    public void showUser(User user) {

    }

    @Override
    public void showAcount(Acount data) {
        bond_text.setText(data.getDeposit() + "");

    }

    @Override
    public void showDeductionData(String data) {

    }

    @Override
    public void updateUserInfo(String data) {

    }
}
