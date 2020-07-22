package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.DiamondPicturesAdapter;
import com.witkey.witkeyhelp.bean.AdDetailsBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.CountDownUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.NoScrollListview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/4/7.
 */

public class ActivitySelfAdvertisement extends BaseActivity {

    private TextView issue_title;
    private TextView issue_content;
    private TextView amount_money;
    private TextView payment_method;
    private TextView number;
    private TextView ange;
    private Button btn_publish;
    private List<String> mlst;
    private NoScrollListview main_listView;
    private DiamondPicturesAdapter diamondPicturesAdapter;
    private int id;
    private List<ReleasePhotoBean> photoMlst;
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_mydiarel);
        setIncludeTitle("详情");
        id = getIntent().getIntExtra("id", 0);
        issue_title = findViewById(R.id.issue_title);
        issue_content = findViewById(R.id.issue_content);
        amount_money = findViewById(R.id.amount_money);
        payment_method = findViewById(R.id.payment_method);
        number = findViewById(R.id.number);
        ange = findViewById(R.id.ange);
        btn_publish = findViewById(R.id.btn_publish);
        mlst = new ArrayList<>();
//        mlst.add(R.mipmap.banner1);
//        mlst.add(R.mipmap.banner2);
//        mlst.add(R.mipmap.banner1);
        main_listView = findViewById(R.id.main_listView);
        diamondPicturesAdapter = new DiamondPicturesAdapter(this, mlst);
        main_listView.setAdapter(diamondPicturesAdapter);

        photoMlst = new ArrayList<>();
        main_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //   onclilk(view.findViewById(R.id.imagview), mlst.get(position));
                photoMlst.clear();
                for (int i = 0; i <mlst.size() ; i++) {
                    photoMlst.add(new ReleasePhotoBean( mlst.get(i),true));

                }

                Intent intent = new Intent(ActivitySelfAdvertisement.this, PhotoActivity.class);
                intent.putExtra("photo", (Serializable) photoMlst);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        loadData();
    }
    private void loadData() {
        MyAPP.getInstance().getApi().advertisingSelectById(id, user.getUserId()).enqueue(new Callback(IModel.callback, "钻石通知广告详情失败") {
            @Override
            public void getSuc(String body) {

                AdDetailsBean beanFromJson = JsonUtils.getBeanFromJson(body, AdDetailsBean.class);

                issue_title.setText(beanFromJson.getReturnObject().getTitle() + "");

                issue_content.setText(beanFromJson.getReturnObject().getContent() + "");

                amount_money.setText(beanFromJson.getReturnObject().getPutBalance() + "");
                switch (beanFromJson.getReturnObject().getAmountType()) {
                    case "0":
                        payment_method.setText("钻石");
                        break;
                    case "1":
                        payment_method.setText("余额");
                        break;
                    case "2":
                        payment_method.setText("微信支付");
                        break;
                }
                number.setText(beanFromJson.getReturnObject().getPutNum() + "");
                switch (beanFromJson.getReturnObject().getType()) {
                    case 1:
                        ange.setText(beanFromJson.getReturnObject().getPutArea() + "");
                        break;
                    case 2:
                        ange.setText(beanFromJson.getReturnObject().getPutArea() + "");
                        break;
                    case 3:
                        ange.setText( beanFromJson.getReturnObject().getPutLocation()+"   方圆"+ beanFromJson.getReturnObject().getPutScope() + "km");
                        break;
                }


                beanFromJson.getReturnObject().getParams().getResidue();
                if (beanFromJson.getReturnObject().getParams().getResidue() > 0) {
                    btn_publish.setText("剩余" + beanFromJson.getReturnObject().getParams().getResidue() + "人");
                } else {
                    btn_publish.setText("广告已全部发送");
                }
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
            }
        });
    }
    @Override
    protected boolean isGetUser() {
        return true;
    }
}
