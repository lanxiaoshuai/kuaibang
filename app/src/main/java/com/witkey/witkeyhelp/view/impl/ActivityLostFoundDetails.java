package com.witkey.witkeyhelp.view.impl;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.ReleaseDetailsAdapter;
import com.witkey.witkeyhelp.adapter.ReleaseListAdapter;
import com.witkey.witkeyhelp.bean.FullImageInfo;
import com.witkey.witkeyhelp.bean.ReleasBeanDetails;
import com.witkey.witkeyhelp.bean.ReleaseBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SharedPrefHelper;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.HeaderAndFooterWrapper;
import com.witkey.witkeyhelp.widget.RecycleGridDivider;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Created by asus on 2020/3/21.
 */

public class ActivityLostFoundDetails extends BaseActivity {

    private RoundImageView iv_avatar;
    private TextView releasename;
    private TextView releasetime;
    private TextView releasecontent;
    private TextView position;
    private TextView contact_information;
    private RelativeLayout telephone_dial;
    private TextView task_report;
    private Button tv_commitbutton;

    private String contactsPhone;
    private ReleasBeanDetails beanFromJson;
    private int type;
    private UICustomDialog2 dialog2;
    private int id;
    private LinearLayout user_lianxi;
    private String imgUrl;
    private RecyclerView photolist;
    private ReleaseDetailsAdapter photoAdapter;

    private List<ReleasePhotoBean> photoList;
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_lostfound_details);
        setIncludeTitle("失物详情");

        iv_avatar = findViewById(R.id.iv_avatar);
        releasename = findViewById(R.id.releasename);
        releasetime = findViewById(R.id.releasetime);
        releasecontent = findViewById(R.id.releasecontent);
        position = findViewById(R.id.position);
        contact_information = findViewById(R.id.contact_information);
        telephone_dial = findViewById(R.id.telephone_dial);
        task_report = findViewById(R.id.task_report);
        tv_commitbutton = findViewById(R.id.tv_commitbutton);

        user_lianxi = findViewById(R.id.user_lianxi);


        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            task_report.setVisibility(View.GONE);
            tv_commitbutton.setText("完成认领");
            user_lianxi.setVisibility(View.GONE);
        } else {
            task_report.setVisibility(View.VISIBLE);
            tv_commitbutton.setText("立即联系");
            user_lianxi.setVisibility(View.VISIBLE);
        }
        loadFound();
        telephone_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                if (contact_information.getText().toString().trim().length() == 11) {
                    diallPhone(contactsPhone);
                } else {

                }
            }
        });
        tv_commitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                if (type == 0) {

                    if(beanFromJson.getReturnObject().getStatus().equals("1")){
                        dialog2 = new UICustomDialog2(ActivityLostFoundDetails.this, "确认完成认领", "3");
                        dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (PventQuickClick.isFastDoubleClick()) {
                                    return;
                                }
                                dialog2.hide();
                            }
                        });
                        dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (PventQuickClick.isFastDoubleClick()) {
                                    return;
                                }
                                DialogUtil.showProgress(ActivityLostFoundDetails.this);

                                dialog2.hide();
                                inishfClaim(id);

                            }
                        });
                        dialog2.show();
                    }else {

                    }


                } else {
                    if (type != 0)
                        if (user.getUserName().equals(beanFromJson.getReturnObject().getUser().getUserName())) {
                            ToastUtils.showTestShort(ActivityLostFoundDetails.this, "不能与自己本身聊天");
                        } else {
                            liaotian();
                        }

                }

            }
        });
        task_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(ActivityLostFoundDetails.this, ProblemFeedbackActivity.class);
                intent.putExtra("type", 3);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        photolist = findViewById(R.id.photolist);
        photolist.setLayoutManager(new GridLayoutManager(this, 3));
        int spanCount = 3; // 3 columns
        int spacing = 36; // 50px
        boolean includeEdge = false;
     //   photolist.addItemDecoration(new RecycleGridDivider(spanCount, spacing, includeEdge));
        photoList = new ArrayList<>();

        photoAdapter = new ReleaseDetailsAdapter(this, photoList);
        photolist.setAdapter(photoAdapter);
        photolist.setVisibility(View.GONE);

    }
    private void onclilk(View view) {

        int location[] = new int[2];
        view.getLocationOnScreen(location);
        FullImageInfo fullImageInfo = new FullImageInfo();
        fullImageInfo.setLocationX(location[0]);
        fullImageInfo.setLocationY(location[1]);
        fullImageInfo.setWidth(view.getWidth());
        fullImageInfo.setHeight(view.getHeight());
        fullImageInfo.setImageUrl(imgUrl);
        Intent intent = new Intent(this, FullImageActivity.class);
        intent.putExtra("photo", fullImageInfo);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    private void loadFound() {
        DialogUtil.showProgress(ActivityLostFoundDetails.this);

        MyAPP.getInstance().getApi().lostAricleSelectById(id).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getSuc(String body) {
                DialogUtil.dismissProgress();
                beanFromJson = JsonUtils.getBeanFromJson(body, ReleasBeanDetails.class);
                id = beanFromJson.getReturnObject().getId();
                if (type == 0) {
                    if (!beanFromJson.getReturnObject().getStatus().equals("1")) {
                        tv_commitbutton.setText("认领已完成");
                        tv_commitbutton.setBackground(getResources().getDrawable(R.drawable.shape_gray_details_renl));
                        tv_commitbutton.setTextColor(getResources().getColor(R.color.white));
                    }
                }
                contactsPhone = beanFromJson.getReturnObject().getContactsPhone();
                if (null != contactsPhone) {
                    if (contactsPhone.length() == 11) {
                        contact_information.setText(hidePhoneNum(contactsPhone));
                    }

                }

                Glide.with(ActivityLostFoundDetails.this).load(URL.getImgPath + beanFromJson.getReturnObject().getUser().getHeadUrl()).into(iv_avatar);
                releasename.setText(beanFromJson.getReturnObject().getUser().getRealName());
                releasetime.setText(beanFromJson.getReturnObject().getCreateTime());
                releasecontent.setText(beanFromJson.getReturnObject().getDescribes());




                if (null == beanFromJson.getReturnObject().getImgUrl() || beanFromJson.getReturnObject().getImgUrl().equals("")) {
                    photolist.setVisibility(View.GONE);
                } else {
                    photolist.setVisibility(View.VISIBLE);
                    if (beanFromJson.getReturnObject().getImgUrl().contains(",")) {

                        String[] split = beanFromJson.getReturnObject().getImgUrl().split(",");
                        for (int i = 0; i < split.length; i++) {
                            photoList.add(new ReleasePhotoBean(URL.getImgPath + split[i], true));
                        }

                    } else {
                        photoList.add(new ReleasePhotoBean(URL.getImgPath + beanFromJson.getReturnObject().getImgUrl(), true));

                        //  Glide.with(this).load(URL.getImgPath + missionBean.getBusinessImgUrl()).into(iv_pic);
                    }
                    photoAdapter.notifyDataSetChanged();

                }

                position.setText(beanFromJson.getReturnObject().getPlaceName());
            }


        });
    }

    private void inishfClaim(int id) {
        DialogUtil.showProgress(ActivityLostFoundDetails.this);

        MyAPP.getInstance().getApi().lostAricleConfirm(id).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getSuc(String body) {
                DialogUtil.dismissProgress();
                tv_commitbutton.setText("认领已完成");
                tv_commitbutton.setBackground(getResources().getDrawable(R.drawable.shape_gray_details_an));
                tv_commitbutton.setTextColor(getResources().getColor(R.color.colortext_size));
                beanFromJson.getReturnObject().setStatus("0");
            }


        });
    }
    /**
     * 隐藏部分手机号码
     *
     * @param phone
     * @return
     */
    public String hidePhoneNum(String phone) {
        String result = "";
        if (phone != null && !"".equals(phone)) {

            result = phone.substring(0, 3) + "****" + phone.substring(7);

        }
        return result;
    }

    private void liaotian() {
        Conversation conv = JMessageClient.getSingleConversation(beanFromJson.getReturnObject().getUser().getUserName(), SharedPrefHelper.getInstance().getAppKey());
        //如果会话为空，使用EventBus通知会话列表添加新会话
        if (conv == null) {
            Conversation.createSingleConversation(beanFromJson.getReturnObject().getUser().getUserName(), SharedPrefHelper.getInstance().getAppKey());
            Log.e("TAG", beanFromJson.getReturnObject().getUser().getUserName());
//                    EventBus.getDefault().post(new Event.Builder()
//                            .setType(EventType.createConversation)
//                            .setConversation(conv)
//                            .build());
        }
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("USERNAME", beanFromJson.getReturnObject().getUser().getUserName());

        intent.putExtra(MyAPP.TARGET_ID, beanFromJson.getReturnObject().getUser().getUserName());
        intent.putExtra(MyAPP.TARGET_APP_KEY, SharedPrefHelper.getInstance().getAppKey());
        intent.putExtra(MyAPP.CONV_TITLE, beanFromJson.getReturnObject().getUser().getRealName());

        startActivity(intent);
    }
}
