package com.witkey.witkeyhelp.view.impl;

import android.view.View;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.presenter.ILostFoundDetailPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.LostFoundDetailPresenterImpl;
import com.witkey.witkeyhelp.view.ILostFoundDetailView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

/**
 * @author lingxu
 * @date 2019/8/1 10:22
 * @description 失物招领详情
 */
public class LostFoundDetailActivity extends InitPresenterBaseActivity implements View.OnClickListener, ILostFoundDetailView {
    private TextView tv_describe;
    private TextView tv_contact;
    private TextView tv_chat;
    private TextView tv_collect;
    private TextView tv_report;
    private TextView tv_commit;
    private ILostFoundDetailPresenter presenter;


    @Override
    protected IPresenter[] getPresenters() {
        presenter = new LostFoundDetailPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        presenter.getLostFoundDetail(1);
    }

    @Override
    protected void initWidget() {
        tv_describe = findViewById(R.id.tv_describe);
        tv_contact = findViewById(R.id.tv_contact);
        tv_chat = findViewById(R.id.tv_chat);
        tv_collect = findViewById(R.id.tv_collect);
        tv_report = findViewById(R.id.tv_report);
        tv_commit = findViewById(R.id.tv_commit);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lostfound_detail;
    }

    @Override
    protected void initEvent() {
        tv_chat.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        tv_report.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_chat:

                break;
            case R.id.tv_collect:

                break;
            case R.id.tv_report:

                break;
            case R.id.tv_commit:

                break;
        }
    }

    @Override
    public void showLostFoundDetail(LostFoundBean lostFoundBean) {
        if (lostFoundBean != null) {
            tv_describe.setText(lostFoundBean.getContent());
            tv_contact.setText(lostFoundBean.getContact());
        }
    }
}
