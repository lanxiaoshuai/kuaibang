package com.witkey.witkeyhelp.view.impl;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.event.AttentionEvent;
import com.witkey.witkeyhelp.event.ResultEvent;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.fragment.CircleFragment;
import com.witkey.witkeyhelp.view.impl.fragment.PlaceholderFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by jie on 2020/6/19.
 */

public class CircleActivity extends BaseActivity {

    private static final String ARG_TITLE = "section_number";
    private TextView attention_text;
    private boolean whetherFollow;
    private CicleBean.ReturnObjectBean.RowsBean rowsBean;
    private boolean aBoolean;
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.cicler_activity);
        rowsBean = (CicleBean.ReturnObjectBean.RowsBean) getIntent().getSerializableExtra(ARG_TITLE);
        setIncludeTitle(rowsBean.getCircleName());
        attention_text = findViewById(R.id.attention_text);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        PlaceholderFragment leftFragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, getIntent().getStringExtra("circleId"));
        bundle.putBoolean("SEARCH", true);
        leftFragment.setArguments(bundle);
        transaction.add(R.id.circle_container, leftFragment).commit();
        if (rowsBean.getCircleName().equals("全部") || rowsBean.getCircleName().equals("推荐")) {
            attention_text.setVisibility(View.GONE);
        } else {
            attention_text.setVisibility(View.VISIBLE);
            if (MyAPP.selectLst != null || MyAPP.selectLst.size() != 0) {
                for (int i = 0; i < MyAPP.selectLst.size(); i++) {
                    if (MyAPP.selectLst.get(i).getCircleId().equals(getIntent().getStringExtra("circleId"))) {
                        whetherFollow = true;
                        break;
                    }
                }
                if (whetherFollow) {
                    attention_text.setBackground(getResources().getDrawable(R.drawable.shape_gray_cicle_false));
                    attention_text.setTextColor(getResources().getColor(R.color.attentionColor));
                    attention_text.setText("已关注");
                } else {
                    attention_text.setBackground(getResources().getDrawable(R.drawable.shape_gray_cicle));
                    attention_text.setTextColor(getResources().getColor(R.color.lableColor));
                }
            }
        }
        attention_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showProgress(CircleActivity.this);
                MyAPP.getInstance().getApi().circleIsAttention(user.getUserId(), getIntent().getStringExtra("circleId")).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
                    @Override
                    public void getSuc(String body) {
                        aBoolean=true;
                        DialogUtil.dismissProgress();
                        if (whetherFollow) {
                            rowsBean.setSelected(false);
                            //    EventBus.getDefault().post(rowsBean);
                            whetherFollow = false;
                            attention_text.setBackground(getResources().getDrawable(R.drawable.shape_gray_cicle));
                            attention_text.setTextColor(getResources().getColor(R.color.lableColor));
                            attention_text.setText("关注");
                            ToastUtils.showTestShort(CircleActivity.this, "取消成功");

                        } else {
                            rowsBean.setSelected(true);
                            //EventBus.getDefault().post(rowsBean);
                            whetherFollow = true;
                            attention_text.setBackground(getResources().getDrawable(R.drawable.shape_gray_cicle_false));
                            attention_text.setTextColor(getResources().getColor(R.color.attentionColor));
                            attention_text.setText("已关注");
                            ToastUtils.showTestShort(CircleActivity.this, "关注成功");
                        }
                    }


                });
            }
        });
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     if(aBoolean){
         EventBus.getDefault().post(rowsBean);
     }


    }
}
