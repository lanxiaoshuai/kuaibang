package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.presenter.IMyMissionPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MyMissionPresenterImpl;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;
import com.witkey.witkeyhelp.view.impl.fragment.ReleaseFragment;

import java.util.List;

/**
 * Created by jie on 2020/1/13.
 */

public class ReceiveMissionActivity extends BaseListActivity {



    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private List<MissionBean> missionList;


    private IMyMissionPresenter presenter;


    private boolean isRelease; //是否为发布任务


    private ReleaseFragment rb1fragment;
    private ReleaseFragment rb2fragment;
    private ReleaseFragment rb3fragment;

    private FragmentTransaction transaction;


    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
        isRelease = argIntent.getBooleanExtra("EXTRA_IS_RELEASE", false);

    }

    @Override
    protected boolean isGetUser() {
        return true;
    }


    @Override
    protected void onLoadMore() {

    }

    @Override
    protected void onRefresh() {
        //  getData();
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
        // presenter.init(this);
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

                                setChoiceItem(0);
                                break;
                            case R.id.rb2:

                                setChoiceItem(1);
                                break;
                            case R.id.rb3:

                                setChoiceItem(2);
                                break;
                            case R.id.rb4:

                                setChoiceItem(3);
                                break;
                        }
                        //page = 1;
                        // allGet();
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
        RelativeLayout tvBack= findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(ReceiveMissionActivity.this, HomeActivity.class);
//                intent.putExtra("type", "0");
//                startActivity(intent);
            }
        });
    }


    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        String action = getIntent().getStringExtra("action");
        if ("".equals(action) || null == action) {
            setChoiceItem(0);
        } else {
            switch (action) {
                default:
                    setChoiceItem(0);
                    break;
            }


        }

        if (isRelease) {
            setIncludeTitle("发布的任务");
            rb1.setText("进行中");
            rb2.setText("已完成");
            rb3.setText("已发布");
            // rb4.setText("已发布");
            rb4.setVisibility(View.GONE);
            findViewById(R.id.view).setVisibility(View.GONE);

        } else {
            setIncludeTitle("领取的任务");
            rb1.setText("进行中");
            rb2.setText("已提交");
            rb3.setText("已完成");
            rb4.setVisibility(View.GONE);
            findViewById(R.id.view).setVisibility(View.GONE);


        }
        findViewById(R.id.tvBack).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onError(String error) {


        ToastUtils.showTestShort(this, error);
    }

    public void setChoiceItem(int index) {
        transaction = getSupportFragmentManager().beginTransaction();

        //隐藏所有Fragment
        hideFragments(transaction);
        switch (index) {
            case 0:

                transaction.show(rb1fragment);
                break;
            case 1:

                transaction.show(rb2fragment);
                break;
            case 2:

                transaction.show(rb3fragment);
                break;
            case 3:

                break;
        }
        transaction.commit();
    }

    //隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (rb1fragment == null) {
            rb1fragment = new ReleaseFragment();
            Bundle bundle = new Bundle();
            bundle.putString("start", "1");
            bundle.putInt("userId", user.getUserId());
            bundle.putBoolean("boo", isRelease);
            rb1fragment.setArguments(bundle);
            transaction.add(R.id.fragment, rb1fragment);
        }
        if (rb2fragment == null) {
            rb2fragment = new ReleaseFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("start", "2");
            bundle2.putBoolean("boo", isRelease);
            bundle2.putInt("userId", user.getUserId());
            rb2fragment.setArguments(bundle2);
            transaction.add(R.id.fragment, rb2fragment);

        }
        if (rb3fragment == null) {
            rb3fragment = new ReleaseFragment();
            Bundle bundle3 = new Bundle();
            if (isRelease) {
                bundle3.putString("start", "4");
            } else {
                bundle3.putString("start", "3");
            }

            bundle3.putBoolean("boo", isRelease);
            bundle3.putInt("userId", user.getUserId());
            rb3fragment.setArguments(bundle3);
            transaction.add(R.id.fragment, rb3fragment);

        }
//        if (rb4fragment == null) {
//            rb4fragment = new ReleaseFragment();
//            Bundle bundle4 = new Bundle();
//            bundle4.putBoolean("boo", isRelease);
//            bundle4.putInt("userId", user.getUserId());
//            bundle4.putString("start", "4");
//            rb4fragment.setArguments(bundle4);
//            transaction.add(R.id.fragment, rb4fragment);

//        }
        transaction.hide(rb1fragment);
        transaction.hide(rb2fragment);
        transaction.hide(rb3fragment);
        //    transaction.hide(rb4fragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}
