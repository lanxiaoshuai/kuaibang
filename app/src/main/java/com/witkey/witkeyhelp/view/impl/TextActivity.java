package com.witkey.witkeyhelp.view.impl;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.ImageView;

import android.widget.PopupWindow;

import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.donkingliang.labels.LabelsView;
import com.gyf.immersionbar.ImmersionBar;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;


import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.ViewPagerAdapter;
import com.witkey.witkeyhelp.bean.LabelBean;

import com.witkey.witkeyhelp.bean.UserBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.AppBarStateChangeListener;

import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PventQuickClick;

import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.fragment.TaskEvaluatFragment;
import com.witkey.witkeyhelp.widget.RoundImageView;
import com.witkey.witkeyhelp.widget.tablayout.XTabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jie on 2019/11/29.
 */

public class TextActivity extends BaseActivity {

    private AppBarLayout app_bar_topic;
    private ImageView iv_back_topic;
    private Toolbar toolbar_topic;


    private CardView tab_background;
    private View viewone;
    private View viewtwo;
    private XTabLayout mTabLayout;
    private LabelsView labelsView;
    private CheckBox load_more;
    private TextView personal_name;
    private TextView personal_credit;

    private List<LabelBean> tegers;
    private ArrayList<String> label;
    private ArrayList<String> labe2;
    private ImageView aliHide_icon;
    private TextView aliHide_text;
    private TextView aliHide_text1;
    private RelativeLayout aliHide_layout;
    private int type;
    private TextView signature;
    private ImageView signature_icon;
    private String phone;
    private TextView username_text;
    private TextView credit_score;
    private RoundImageView profile_picture;
    private TextView score;
    private ImageView userBackground;
    private ImageView gender;
    private JSONArray returnObject1;


    @Override
    protected void onCreateActivity() {

        setContentView(R.layout.activity_invitingfriends);


        ImmersionBar.with(this).transparentStatusBar().init();

        phone = getIntent().getStringExtra("phone");


        type = getIntent().getIntExtra("type", 0);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (XTabLayout) findViewById(R.id.tablayout);


        mTabLayout.setxTabDisplayNum(2);
        TaskEvaluatFragment release = new TaskEvaluatFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("title", 0);
        bundle.putInt("type", type);
        bundle.putString("phone", phone);
        release.setArguments(bundle);

        TaskEvaluatFragment receive = new TaskEvaluatFragment();

        Bundle bundle1 = new Bundle();
        bundle1.putInt("title", 1);
        bundle1.putInt("type", type);
        bundle1.putString("phone", phone);
        receive.setArguments(bundle1);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addItem(release, "发布的");
        viewPagerAdapter.addItem(receive, "领取的");
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        app_bar_topic = (AppBarLayout) findViewById(R.id.app_bar_topic);
        iv_back_topic = (ImageView) findViewById(R.id.iv_back_topic);
        toolbar_topic = (Toolbar) findViewById(R.id.toolbar_topic);
        tab_background = findViewById(R.id.tab_background);
        viewone = findViewById(R.id.viewone);
        viewtwo = findViewById(R.id.viewtwo);

        personal_name = findViewById(R.id.personal_name);
        personal_credit = findViewById(R.id.personal_credit);

        aliHide_icon = findViewById(R.id.aliHide_icon);
        aliHide_text = findViewById(R.id.aliHide_text);
        aliHide_layout = findViewById(R.id.aliHide_layout);
        signature = findViewById(R.id.signature);
        signature_icon = findViewById(R.id.signature_icon);
        username_text = findViewById(R.id.username_text);

        credit_score = findViewById(R.id.credit_score);

        score = findViewById(R.id.score);


        profile_picture = findViewById(R.id.profile_picture);

        userBackground = findViewById(R.id.userBackground);

        gender = findViewById(R.id.gender);

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(TextActivity.this, UsetInfoActivity.class);
                startActivity(intent);
            }
        });


        labelsView = (LabelsView) findViewById(R.id.labels);
        load_more = findViewById(R.id.load_more);


        load_more.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    labelsView.setMaxLines(0);

                } else {
                    labelsView.setMaxLines(1);

                }
            }
        });
        if (type == 0) {

            labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
                @Override
                public void onLabelClick(TextView label, Object data, int position) {
                    if (position == 0) {

                        getReadydialog();
                    } else {

                    }
                }
            });
            signature_icon.setVisibility(View.VISIBLE);

        } else {
            signature_icon.setVisibility(View.GONE);
        }


        initappBar();

        aliHide_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }

                getPopubWindow();
            }
        });

        iv_back_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signature_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }

                Intent intent = new Intent(TextActivity.this, UsetInfoActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initappBar() {
        app_bar_topic.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    iv_back_topic.setImageResource(R.mipmap.back_white);
                    toolbar_topic.setBackgroundColor(Color.argb((int) 0, 0, 0, 0));
                    viewone.setVisibility(View.GONE);
                    viewtwo.setVisibility(View.GONE);
                    tab_background.setVisibility(View.VISIBLE);
                    personal_name.setVisibility(View.GONE);
                    personal_credit.setVisibility(View.GONE);
                    ImmersionBar.with(TextActivity.this)
                            .statusBarDarkFont(false, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                            .init();
                    aliHide_layout.setVisibility(View.GONE);
                    //展开状态
                } else if (state == State.COLLAPSED) {

                    iv_back_topic.setImageResource(R.mipmap.search_back);
                    toolbar_topic.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    viewone.setVisibility(View.VISIBLE);
                    viewtwo.setVisibility(View.VISIBLE);
                    tab_background.setVisibility(View.INVISIBLE);
                    //     tab_background.setPadding(0, 0, 0, 0);
                    if (type == 0) {
                        aliHide_layout.setVisibility(View.GONE);
                    } else {
                        aliHide_layout.setVisibility(View.GONE);
                    }

                    personal_name.setVisibility(View.VISIBLE);
                    personal_credit.setVisibility(View.VISIBLE);


                    ImmersionBar.with(TextActivity.this)
                            .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                            .init();
                    //折叠状态
                } else {
                    aliHide_layout.setVisibility(View.GONE);
                    iv_back_topic.setImageResource(R.mipmap.back_white);
                    toolbar_topic.setBackgroundColor(Color.argb((int) 0, 0, 0, 0));
                    viewone.setVisibility(View.GONE);
                    viewtwo.setVisibility(View.GONE);
                    tab_background.setVisibility(View.VISIBLE);
                    //       tab_background.setPadding(0, DensityUtils.dip2px(TextActivity.this,6), 0, 0);
                    personal_name.setVisibility(View.GONE);
                    personal_credit.setVisibility(View.GONE);


                    ImmersionBar.with(TextActivity.this)
                            .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                            .init();
                    //中间状态

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        label = new ArrayList<>();
        MyAPP.getInstance().getApi().userHomePage(phone).enqueue(new Callback(IModel.callback, "获取标签失败") {
            @Override
            public void getSuc(String body) {
                UserBean beanFromJson = JsonUtils.getBeanFromJson(body, UserBean.class);
                UserBean.ReturnObjectBean returnObject = beanFromJson.getReturnObject();
                UserBean.ReturnObjectBean.ParamsBean params = returnObject.getParams();

                username_text.setText(returnObject.getRealName() + "");
                credit_score.setText("信誉分  " + returnObject.getReputationNum());
                signature.setText(returnObject.getPSignature() + "");

                score.setText("总评分 " + params.getGrade());

                Glide.with(TextActivity.this).load(URL.getImgPath + returnObject.getHeadUrl()).into(profile_picture);
                Glide.with(TextActivity.this).load(URL.getImgPath + returnObject.getHeadUrl()).into(userBackground);
                if (returnObject.getSex() != null) {
                    if (returnObject.getSex().equals("0")) {
                        gender.setImageResource(R.mipmap.mable_icon);
                    } else if (returnObject.getSex().equals("1")) {
                        gender.setImageResource(R.mipmap.gender_nv);
                    }

                }

                List<String> tag = params.getTag();
                label.addAll(tag);
                if (type == 0) {
                    getAllTag();
                    if (label.size() == 0) {
                        label.add("＋ 添加我的标签");
                        ViewGroup.LayoutParams layoutParams = labelsView.getLayoutParams();
                        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        labelsView.setLayoutParams(layoutParams);
                    } else {
                        label.add(0, "＋ 添加");
                        ViewGroup.LayoutParams layoutParams = labelsView.getLayoutParams();
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        labelsView.setLayoutParams(layoutParams);
                    }
                } else {

                }

                if (label.size() < 4) {
                    load_more.setVisibility(View.GONE);
                } else {
                    load_more.setVisibility(View.VISIBLE);
                }

                labelsView.setLabels(label, new LabelsView.LabelTextProvider<String>() {
                    @Override
                    public CharSequence getLabelText(TextView label, int position, String data) {

                        // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。

                        //根据data和position返回label需要显示的数据。
                        if (type == 0) {
                            if (position == 0) {

                                label.setBackground(getResources().getDrawable(R.drawable.lable_back_select));
                            }
                        }

                        return data;
                    }
                });
            }
        });
    }

    private PopupWindow popupWindow;

    private void getReadydialog() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popub_lable, null, false);//引入弹窗布局

        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        //设置进出动画
        popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        View parentView = LayoutInflater.from(this).inflate(R.layout.activity_invitingfriends, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移

        LabelsView labels_popub = vPopupWindow.findViewById(R.id.labels_popub);


        labels_popub.setLabels(labe2);
        tegers = new ArrayList<>();
        for (int i = 0; i < labe2.size(); i++) {
            tegers.add(new LabelBean(i, false, labe2.get(i)));
        }

        labels_popub.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
            @Override
            public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {

                tegers.set(position, new LabelBean(position, isSelect, data.toString()));
            }
        });
        TextView determine = vPopupWindow.findViewById(R.id.determine);
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < tegers.size(); i++) {
                    if (tegers.get(i).isCheck()) {


                        int id = 0;
                        try {
                            id = returnObject1.getJSONObject(i).getInt("id");
                            stringBuffer.append(id + ",");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //  }
                    }
                }

                String substring = stringBuffer.toString().substring(0, stringBuffer.toString().length());

               if(substring.equals("")){
                   ToastUtils.showTestShort(TextActivity.this,"请选择标签");
                   return;
               }
                addTag(substring);

            }
        });


        addBackground(popupWindow);
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

    }

    private void addTag(String labelId) {
        MyAPP.getInstance().getApi().userAddTag(phone, labelId).enqueue(new Callback(IModel.callback, "添加标签失败") {
            @Override
            public void getSuc(String body) {
                popupWindow.dismiss();
                ToastUtils.showTestShort(TextActivity.this, "添加标签成功");

                for (int i = 0; i < tegers.size(); i++) {
                    if (tegers.get(i).isCheck()) {
                        if (label.contains(tegers.get(i).getLable())) {

                        } else {
                            label.add(tegers.get(i).getLable());

                        }
                    }
                }

                label.set(0, "＋ 添加");
                if (label.size() < 4) {
                    load_more.setVisibility(View.GONE);
                } else {
                    load_more.setVisibility(View.VISIBLE);
                }

                labelsView.setLabels(label, new LabelsView.LabelTextProvider<String>() {
                    @Override
                    public CharSequence getLabelText(TextView label, int position, String data) {

                        // label就是标签项，在这里可以对标签项单独设置一些属性，比如文本样式等。

                        //根据data和position返回label需要显示的数据。
                        if (position == 0) {

                            label.setBackground(getResources().getDrawable(R.drawable.lable_back_select));
                        }
                        return data;
                    }
                });
                if (label.size() < 3) {
                    ViewGroup.LayoutParams layoutParams = labelsView.getLayoutParams();

                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    labelsView.setLayoutParams(layoutParams);
                }
            }
        });
    }


    private void getPopubWindow() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.hide_display_popub, null, false);//引入弹窗布局

        PopupWindow popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        //设置进出动画
        //     popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        View parentView = LayoutInflater.from(this).inflate(R.layout.activity_invitingfriends, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移


        RelativeLayout hide_display_pop = vPopupWindow.findViewById(R.id.hide_display_pop);

        ImageView hide_display_icon = vPopupWindow.findViewById(R.id.hide_display_icon);
        TextView hide_display_content = vPopupWindow.findViewById(R.id.hide_display_content);
        hide_display_content.setText("隐藏全部悬赏                                  ");
        hide_display_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        addBackground(popupWindow);
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

    }

    private void addBackground(PopupWindow popupWindow) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void getAllTag() {
        labe2 = new ArrayList<>();

        MyAPP.getInstance().getApi().getAllTag().enqueue(new Callback(IModel.callback, "获取标签失败") {
            @Override
            public void getSuc(String body) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject returnObject = jsonObject.getJSONObject("returnObject");

                    returnObject1 = returnObject.getJSONArray("rows");

                    for (int i = 0; i < returnObject1.length(); i++) {
                        String classifyName = returnObject1.getJSONObject(i).getString("classifyName");
                        labe2.add(classifyName);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
