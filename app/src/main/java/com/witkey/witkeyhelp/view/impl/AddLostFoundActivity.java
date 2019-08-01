package com.witkey.witkeyhelp.view.impl;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.AddLostFoundPresenterImpl;
import com.witkey.witkeyhelp.presenter.IAddLostFoundPresenter;
import com.witkey.witkeyhelp.view.IAddLostFoundView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

/**
 * @author lingxu
 * @date 2019/8/1 14:38
 * @description 添加失误招领
 */
public class AddLostFoundActivity extends InitPresenterBaseActivity implements IAddLostFoundView {
    private RelativeLayout rl_pic_defalut;
    private TextView tv_add_pic;
    private EditText et_describe;
    private EditText et_contact;
    private EditText et_hint;
    private TextView tv_add_lost;

    private boolean isShowPic;

    private IAddLostFoundPresenter presenter;


    @Override
    protected IPresenter[] getPresenters() {
        presenter=new AddLostFoundPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {

    }

    @Override
    protected void initWidget() {
        rl_pic_defalut = findViewById(R.id.rl_pic_defalut);
        tv_add_pic = findViewById(R.id.tv_add_pic);
        et_describe = findViewById(R.id.et_describe);
        et_contact = findViewById(R.id.et_contact);
        et_hint = findViewById(R.id.et_hint);
        tv_add_lost = findViewById(R.id.tv_add_lost);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_lost_found;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void addSuc() {

    }
}
