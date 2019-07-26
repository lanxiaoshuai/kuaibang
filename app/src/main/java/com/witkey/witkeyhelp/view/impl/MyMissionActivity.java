package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MissionRecyAdapter;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.MissionRequest;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.presenter.IMyMissionPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MyMissionPresenterImpl;
import com.witkey.witkeyhelp.view.IMyMissionView;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;

import java.util.List;

public class MyMissionActivity extends BaseListActivity implements IMyMissionView {
    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private List<MissionBean> missionList;
    private MissionRequest missionRequest;
    private int page = 1;

    private IMyMissionPresenter presenter;
    private int state;//类型
    private MissionBean missionBean;
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
            if (missionRequest != null) {
                int totalPage = missionRequest.getTotal() / 10;
                if (missionRequest.getTotal() % 10 != 0) {
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
        if (missionList != null) {
            missionList.clear();
            missionList = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        page = 1;
        allGet();
    }

    private void allGet() {
        if (isRelease) {
            presenter.getReleaseMissionList(missionBean);
        } else {
            presenter.getReceiveMissionList(missionBean);
        }
    }

    @Override
    protected int setRecyDividerHeight() {
        return 10;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MyMissionPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_mission;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.rb1:
                                missionBean.setOrderState(1);
                                break;
                            case R.id.rb2:
                                missionBean.setOrderState(2);
                                break;
                            case R.id.rb3:
                                missionBean.setOrderState(3);
                                break;
                            case R.id.rb4:
                                missionBean.setOrderState(4);
                                break;
                        }
                        page = 1;
                        allGet();
                    }
                }
        );
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        rg = findViewById(R.id.rg);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
    }

    @Override
    public void showMissionList(MissionRequest missionRequest) {
        getSuc();
        this.missionRequest = missionRequest;
        if (missionRequest != null) {
            if (isLoading) {
                missionList.addAll(missionRequest.getRows());
                isLoading = false;
            } else {
                missionList = missionRequest.getRows();
            }
            showAdapter();
        }
    }

    private void showAdapter() {
        if (adapter == null) {
            adapter = new MissionRecyAdapter(mActivity, missionList);
            recyclerView.setAdapter(adapter);
        } else {
            ((MissionRecyAdapter) adapter).setData(missionList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        if (isRelease) {
            setIncludeTitle("发布的任务");
            rb1.setText("进行中");
            rb2.setText("已完成");
            rb3.setText("任务异常");
            rb4.setText("未发布");
        } else {
            setIncludeTitle("领取的任务");
            rb1.setText("进行中");
            rb2.setText("已完成");
            rb3.setText("任务异常");
            rb4.setVisibility(View.GONE);
            findViewById(R.id.view).setVisibility(View.GONE);
        }
        findViewById(R.id.tvBack).setVisibility(View.VISIBLE);
        User user = MyAPP.getInstance().getUser();
        missionBean = new MissionBean(user.getUserId(), page, 1);
        getData();
    }
}
