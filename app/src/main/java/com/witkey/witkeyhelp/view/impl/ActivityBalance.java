package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.presenter.IMeFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MeFragPresenterImpl;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMeFragView;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

/**
 * Created by jie on 2019/12/7.
 */

public class ActivityBalance extends InitPresenterBaseActivity implements View.OnClickListener, IMeFragView {

    private Button ash_withdrawal;
    private TextView tv_confirm;
    private TextView balance;
    private IMeFragPresenter presenter;

    @Override
    protected void initWidget() {


        ash_withdrawal = findViewById(R.id.ash_withdrawal);
        ash_withdrawal.setOnClickListener(this);
        tv_confirm = findViewById(R.id.tv_confirm);
        balance = findViewById(R.id.balance);
        tv_confirm.setOnClickListener(this);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_balance;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogUtil.showProgress(this);
        presenter.getAcount(user.getUserId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ash_withdrawal:
                if (PventQuickClick.isLastFastDoubleClick()) {
                    break;
                }
                Intent intent = new Intent(this, ActivityCashWithdrawal.class);
                intent.putExtra("amountmoney", "余额");

                startActivity(intent);

                break;
            case R.id.tv_confirm:
                if (PventQuickClick.isLastFastDoubleClick()) {
                    break;
                }
                MobclickAgent.onEvent(this, "balanceBill");
                intent = new Intent(this, ActivityNews.class);
                intent.putExtra("amountmoney", "余额");
                startActivity(intent);
                break;


        }
    }

    @Override
    public void showUser(User user) {

    }

    @Override
    public void showAcount(Acount data) {
        balance.setText(data.getBalance() + "");
    }

    @Override
    public void showDeductionData(String data) {

    }

    @Override
    public void updateUserInfo(String data) {

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
        setIncludeTitle("余额");

    }

    @Override
    protected boolean isGetUser() {
        return true;
    }
}
