package com.witkey.witkeyhelp.view.impl;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.presenter.IMeFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MeFragPresenterImpl;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMeFragView;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

/**
 * Created by jie on 2019/12/16.
 */

public class DiamondDeductionActivity extends InitPresenterBaseActivity implements View.OnClickListener, IMeFragView {

    private IMeFragPresenter presenter;
    private Switch s_v;

    @Override
    protected void initWidget() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_swich_diamonds;
    }

    @Override
    protected void initEvent() {
        s_v = findViewById(R.id.s_v);
        s_v.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DialogUtil.showProgress(DiamondDeductionActivity.this);
                if (isChecked) {
                    presenter.getDeductionData(user.getUserId(), 0);
                } else {
                    presenter.getDeductionData(user.getUserId(), 1);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onError(String error) {
        ToastUtils.showTestShort(this, error);
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

    }

    @Override
    public void showDeductionData(String data) {
        Log.e("tag", "修改成功");
        ToastUtils.showTestShort(this, data);
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
        setIncludeTitle("账户设置");
    }
}
