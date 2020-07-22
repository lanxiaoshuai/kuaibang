package com.witkey.witkeyhelp.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;

import com.witkey.witkeyhelp.adapter.DiamondPicturesAdapter;

import com.witkey.witkeyhelp.bean.AdDetailsBean;

import com.witkey.witkeyhelp.bean.FullImageInfo;

import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.CountDownUtil;
import com.witkey.witkeyhelp.util.JsonUtils;

import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.NoScrollListview;
import com.witkey.witkeyhelp.widget.takevideo.utils.SPUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by jie on 2020/4/3.
 */

public class ActivityDiamondsDetails extends BaseActivity {
    private int id;
    private TextView detailtitle;
    private ImageView detailstheHead;
    private TextView detailsName;
    private TextView details_content;
    private NoScrollListview main_listView;
    private DiamondPicturesAdapter diamondPicturesAdapter;
    private List<String> mlst;
    private TextView count_down;
    private TextView count_down_cc;
    private LinearLayout money_reward;
    private List<ReleasePhotoBean> photoMlst;
    private String isRead;
    private static CountDownTimer timer;


    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_posting_details);
        setIncludeTitle("详情");
        id = getIntent().getIntExtra("id", 0);
        count_down = findViewById(R.id.count_down);
        count_down_cc = findViewById(R.id.count_down_cc);
        detailtitle = findViewById(R.id.detailtitle);
        detailstheHead = findViewById(R.id.detailstheHead);
        detailsName = findViewById(R.id.detailsName);
        details_content = findViewById(R.id.details_content);
        money_reward = findViewById(R.id.money_reward);
        mlst = new ArrayList<>();

        main_listView = findViewById(R.id.main_listView);
        diamondPicturesAdapter = new DiamondPicturesAdapter(this, mlst);
        main_listView.setAdapter(diamondPicturesAdapter);
        loadData();
        photoMlst = new ArrayList<>();
        main_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //   onclilk(view.findViewById(R.id.imagview), mlst.get(position));
                photoMlst.clear();
                for (int i = 0; i < mlst.size(); i++) {
                    photoMlst.add(new ReleasePhotoBean(mlst.get(i), true));

                }

                Intent intent = new Intent(ActivityDiamondsDetails.this, PhotoActivity.class);
                intent.putExtra("photo", (Serializable) photoMlst);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        if (SpUtils.getBoolean(this, user.getUserName() + "Advertisement", false)) {
            money_reward.setVisibility(View.GONE);
        } else {
            money_reward.setVisibility(View.VISIBLE);
        }


    }

    private void onclilk(final View view, String imagePath) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        FullImageInfo fullImageInfo = new FullImageInfo();
        fullImageInfo.setLocationX(location[0]);
        fullImageInfo.setLocationY(location[1]);
        fullImageInfo.setWidth(view.getWidth());
        fullImageInfo.setHeight(view.getHeight());
        fullImageInfo.setImageUrl(imagePath);
        Intent intent = new Intent(this, FullImageActivity.class);
        intent.putExtra("photo", fullImageInfo);
        startActivity(intent);

        overridePendingTransition(0, 0);
    }

    private void loadData() {
        MyAPP.getInstance().getApi().advertisingSelectById(id, user.getUserId()).enqueue(new Callback(IModel.callback, "钻石通知广告详情失败") {
            @Override
            public void getSuc(String body) {

                AdDetailsBean beanFromJson = JsonUtils.getBeanFromJson(body, AdDetailsBean.class);

                detailtitle.setText(beanFromJson.getReturnObject().getTitle() + "");
                //    detailsName.setText();
                detailsName.setText(beanFromJson.getReturnObject().getUser().getRealName());
                Glide.with(ActivityDiamondsDetails.this).load(URL.getImgPath + beanFromJson.getReturnObject().getUser().getHeadUrl()).into(detailstheHead);
                details_content.setText(beanFromJson.getReturnObject().getContent());
                if (null == beanFromJson.getReturnObject().getImgUrl() || beanFromJson.getReturnObject().getImgUrl().equals("")) {
                    main_listView.setVisibility(View.GONE);
                } else {
                    main_listView.setVisibility(View.VISIBLE);
                    if (beanFromJson.getReturnObject().getImgUrl().contains(",")) {

                        String[] split = beanFromJson.getReturnObject().getImgUrl().split(",");
                        for (int i = 0; i < split.length; i++) {
                            mlst.add(URL.getImgPath + split[i]);
                        }
                    } else {
                        mlst.add(URL.getImgPath + beanFromJson.getReturnObject().getImgUrl());
                    }
                    diamondPicturesAdapter.notifyDataSetChanged();

                }
                isRead = beanFromJson.getReturnObject().getParams().getIsRead();
                if ("0".equals(beanFromJson.getReturnObject().getParams().getIsRead())) {
                    if (SpUtils.getBoolean(ActivityDiamondsDetails.this, user.getUserName() + "Advertisement", false)) {
                        money_reward.setVisibility(View.GONE);
                    } else {
                        money_reward.setVisibility(View.VISIBLE);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //CountDownUtil.
                    countDownMoneyReward(user.getUserName(), count_down, count_down_cc, user.getUserId(), id, money_reward);

                } else {
                    count_down.setVisibility(View.GONE);
                    count_down_cc.setVisibility(View.VISIBLE);
                    money_reward.setVisibility(View.GONE);
                }

            }
        });
    }

    private boolean type;

    public boolean countDownMoneyReward(final String username, final TextView tvGetCode, final TextView countdown, final int useriId, final int id, final LinearLayout linearLayout) {
        //  tvGetCode.setText("赏金已到账");
        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText((millisUntilFinished / 1000) + "s");

                tvGetCode.setEnabled(false);

            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);


                type = true;

                IModel.AsyncCallback callback = new IModel.AsyncCallback() {
                    @Override
                    public void onSuccess(Object data) {
                    }
                    @Override
                    public void onError(Object data) {
                        countdown.setVisibility(View.VISIBLE);
                        countdown.setText("内容已下架");
                        tvGetCode.setVisibility(View.GONE);
                        ToastUtils.showTestShort(MyAPP.getInstance(), data.toString());
                        DialogUtil.dismissProgress();
                    }
                };

                MyAPP.getInstance().getApi().advertisingGetReward(id, useriId).enqueue(new Callback(callback, "领取赏金失败") {
                    @Override
                    public void getSuc(String body) {
                        countdown.setVisibility(View.VISIBLE);
                        tvGetCode.setVisibility(View.GONE);
                        tvGetCode.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        SpUtils.putBoolean(MyAPP.getInstance(), username + "Advertisement", true);


                    }
                });
            }
        };
        timer.start();

        return true;
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != timer) {
            timer.cancel();
        }

        if (null != isRead) {
            if (isRead.equals("0")) {

                if (type) {
                    MobclickAgent.onEvent(this, "finishreading");

                } else {
                    MobclickAgent.onEvent(this, "notfinished");

                }
            }


        }
    }
}
