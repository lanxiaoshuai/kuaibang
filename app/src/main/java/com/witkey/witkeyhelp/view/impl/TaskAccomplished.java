package com.witkey.witkeyhelp.view.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IMissionDetailPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MissionDetailPresenterImpl;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.SharedPrefHelper;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMissionDetailView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;


/**
 * Created by jie on 2020/1/17.
 */

public class TaskAccomplished extends InitPresenterBaseActivity implements View.OnClickListener, IMissionDetailView, AdapterView.OnItemClickListener {

    private IMissionDetailPresenter presenter;
    private int type;
    private String message;
    private String reason;
    private String phone;
    private String businessId;
    private String realName;
    private String orderId;
    private PopupWindow jubaoepopupWindow;
    private String userId;

    @Override
    protected void initWidget() {

        String content = getIntent().getStringExtra("content");
        try {
            JSONObject jsonObject = new JSONObject(content);
            type = Integer.parseInt(jsonObject.getString("type"));
            if (type == 4 || type == 6) {
                realName = jsonObject.getString("realName");
                phone = jsonObject.getString("phone");

            }
            reason = jsonObject.getString("reason");
            businessId = jsonObject.getString("businessId");
            message = jsonObject.getString("message");
            userId = jsonObject.getString("userId");
            if (type == 1 || type == 3 || type == 4 || type == 5 || type == 6) {
                orderId = jsonObject.getString("orderId");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (type) {
            case 0:
                break;
            case 1:
                popubWiodowTermination(this);
                break;
            case 2:
                break;
            case 3:
                popubWiodowTermination(this);
                break;
            case 4:
                popubWiodow(this);
                break;
            case 5:
                popubWiodow(this);
                break;
            case 6:
                task_submission_fail(this);
                break;
            default:
                break;
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.task_view;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MissionDetailPresenterImpl();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                break;
            case R.id.withdraw:
                break;
            case R.id.content_one:
                break;
            case R.id.content_two:
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void showMission(MissionBean missionBean) {

    }

    @Override
    public void receiptSuc() {

    }

    @Override
    public void getCollection() {

    }

    @Override
    public void getCancelCollection() {

    }

    @Override
    public void taskRelieving(String data) {

    }

    @Override
    public void issionmAccomplished(String data) {

    }

    @Override
    public void showAcount(Acount data) {

    }

    @Override
    public void UploadFeedback(String data) {
        jubaoepopupWindow.dismiss();

        popubWiodowTask(this, type);


    }


    private PopupWindow popupWindow;

    private void popubWiodow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.task_withdrawal_layout, null, false);//引入弹窗布局

        TextView ways_release = vPopupWindow.findViewById(R.id.ways_release);
        ImageView receipt_image = vPopupWindow.findViewById(R.id.receipt_image);
        ImageView expression = vPopupWindow.findViewById(R.id.expression);
        TextView receipt_title = vPopupWindow.findViewById(R.id.receipt_title);

        ways_release.setText(message);
        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        LinearLayout withdraw = vPopupWindow.findViewById(R.id.withdraw);
        LinearLayout content_two = vPopupWindow.findViewById(R.id.content_two);
        LinearLayout content_one = vPopupWindow.findViewById(R.id.content_one);


        if (popupWindow == null) {
            popupWindow = new PopupWindow(vPopupWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        }
        if (type == 4) {
            receipt_image.setImageResource(R.mipmap.task_received);
            cancel.setVisibility(View.VISIBLE);
            withdraw.setVisibility(View.VISIBLE);
            content_two.setVisibility(View.GONE);
            content_one.setVisibility(View.GONE);
            receipt_title.setText("您的悬赏任务已被领取！");
        } else if (type == 5) {

            receipt_image.setImageResource(R.mipmap.submitted_completed);
            cancel.setVisibility(View.GONE);
            withdraw.setVisibility(View.GONE);
            content_one.setVisibility(View.VISIBLE);
            content_two.setVisibility(View.VISIBLE);
            receipt_title.setText("帮忙者已提交完成！");
        }
        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                finish();
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                finish();
            }
        });
        content_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showProgress(TaskAccomplished.this);
                MyAPP.getInstance().getApi().rewardConfirmationTask(Integer.parseInt(orderId), 0).enqueue(new Callback(IModel.callback, "上传失败") {
                    @Override
                    public void getSuc(String body) {
                        DialogUtil.dismissProgress();
                        ToastUtils.showTestShort(TaskAccomplished.this, "已通知帮忙者");
                        popupWindow.dismiss();
                        finish();
                    }
                });


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Conversation conv = JMessageClient.getSingleConversation(phone, SharedPrefHelper.getInstance().getAppKey());
                //如果会话为空，使用EventBus通知会话列表添加新会话
                if (conv == null) {
                    Conversation.createSingleConversation(phone, SharedPrefHelper.getInstance().getAppKey());
                    Log.e("TAG", phone);
//                    EventBus.getDefault().post(new Event.Builder()
//                            .setType(EventType.createConversation)
//                            .setConversation(conv)
//                            .build());
                }
                Intent intent = new Intent(TaskAccomplished.this, ChatActivity.class);
                intent.putExtra("USERNAME", phone);

                intent.putExtra(MyAPP.TARGET_ID, phone);
                intent.putExtra(MyAPP.TARGET_APP_KEY, SharedPrefHelper.getInstance().getAppKey());
                intent.putExtra(MyAPP.CONV_TITLE, realName);
                startActivity(intent);
                popupWindow.dismiss();
                finish();
            }
        });
        content_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showProgress(TaskAccomplished.this);
                MyAPP.getInstance().getApi().rewardConfirmationTask(Integer.parseInt(orderId), 1).enqueue(new Callback(IModel.callback, "上传失败") {
                    @Override
                    public void getSuc(String body) {
                        DialogUtil.dismissProgress();
                        ToastUtils.showTestShort(TaskAccomplished.this, "确认完成");
                        popupWindow.dismiss();
                        finish();
                    }
                });
            }
        });
        popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });
        //  popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        final View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        TextView ace = findViewById(R.id.text);
        //    相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        ace.post(new Runnable() {
            @Override
            public void run() {

                try {
                    addBackgroundPopub(popupWindow);
                    popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.intent_bottom_in, R.anim.bottom_out);
    }


    private void popubWiodowTermination(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.task_withdrawal_layout_two, null, false);//引入弹窗布局
        TextView ways_release = vPopupWindow.findViewById(R.id.ways_release);
        ImageView expression = vPopupWindow.findViewById(R.id.expression);
        TextView reward_content = vPopupWindow.findViewById(R.id.reward_content);
        TextView eason_renwu = vPopupWindow.findViewById(R.id.eason_renwu);
        reward_content.setVisibility(View.VISIBLE);
        ways_release.setVisibility(View.VISIBLE);
        ways_release.setText(message);
        TextView text_jiechu = vPopupWindow.findViewById(R.id.text_jiechu);
        TextView taskrelieving = vPopupWindow.findViewById(R.id.taskrelieving);

        TextView reward_hall_text = vPopupWindow.findViewById(R.id.reward_hall_text);
        TextView report = vPopupWindow.findViewById(R.id.report);
        report.setVisibility(View.VISIBLE);
        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        LinearLayout tyjc = vPopupWindow.findViewById(R.id.tyjc);
        TextView wzdl = vPopupWindow.findViewById(R.id.wzdl);

        if (type == 1) {
            reward_hall_text.setVisibility(View.VISIBLE);
            text_jiechu.setText("1.已扣除帮忙者一分信誉分 \n2.有问题则点举报帮忙者");
            report.setText("举报帮忙者");
            tyjc.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            taskrelieving.setText("任务被解除！");
            eason_renwu.setText("(帮忙者:)" + reason + "");

        } else {
            reward_hall_text.setVisibility(View.GONE);
            text_jiechu.setText("1.同意解除则赏金退悬赏者，并扣除您一分信誉分 \n2.不同意则冻结悬赏者赏金客服介入处理 \n3.48小时不处理则默认同意解除");
            report.setText("不同意，举报悬赏者");
            cancel.setVisibility(View.VISIBLE);
            tyjc.setVisibility(View.GONE);
            wzdl.setText("同意解除");
            taskrelieving.setText("悬赏者发起任务解除！");
            eason_renwu.setText(reason);
        }

        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogUtil.showProgress(TaskAccomplished.this);
                MyAPP.getInstance().getApi().orderCancel(orderId).enqueue(new Callback(IModel.callback, "任务解除失败") {
                    @Override
                    public void getSuc(String body) {
                        DialogUtil.dismissProgress();
                        relievepopupWindow.dismiss();
                        popubWiodowTask(TaskAccomplished.this, 2);
                    }
                });
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                popubReasonTermination(TaskAccomplished.this);
            }
        });
        tyjc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                finish();
            }
        });
        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                finish();

            }
        });
        //  popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        final View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移

        relievepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });

        relievepopupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        relievepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });
        relievepopupWindow.setFocusable(false);
        TextView ace = findViewById(R.id.text);
        ace.post(new Runnable() {
            @Override
            public void run() {
                try {
                    addBackgroundPopub(relievepopupWindow);
                    relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }

    private void addBackgroundPopub(PopupWindow popup) {


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void popubWiodowTask(Context context, int type) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.feedback_task, null, false);//引入弹窗布局

        TextView feedback_task = vPopupWindow.findViewById(R.id.feedback_task);
        ImageView coles_dialog = vPopupWindow.findViewById(R.id.coles_dialog);
        if (type == 1 || type == 6) {
            feedback_task.setText("举报成功！\n我们会尽快做出处理后回复！");
        } else if (type == 2) {
            feedback_task.setText("任务解除成功！\n已退还悬赏者赏金");
        } else if (type == 3) {
            feedback_task.setText("已提交客服\n我们会尽快做出处理\n请耐心等候！");
        }


        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                finish();
            }
        });
        coles_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                finish();
            }
        });

        relievepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });
        //  popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        relievepopupWindow.setFocusable(false);

        try {
            addBackgroundPopub(relievepopupWindow);
            relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
            e.printStackTrace();
        }

    }

    private void popubReasonTermination(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.taskreleasefeedback, null);//引入弹窗布局

        ImageView cancel_les = vPopupWindow.findViewById(R.id.cancel_les);


        jubaoepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        cancel_les.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jubaoepopupWindow.dismiss();
                finish();

            }
        });
        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        final EditText et_content = vPopupWindow.findViewById(R.id.et_content);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_content.getText().toString().equals("")) {
                    ToastUtils.showTestShort(TaskAccomplished.this, "请输入举报原因");
                } else {
                    DialogUtil.showProgress(TaskAccomplished.this);
                    MyAPP.getInstance().getApi().reportOrder(et_content.getText().toString(), Integer.parseInt(userId), Integer.parseInt(orderId), "", 1).enqueue(new Callback(IModel.callback, "任务解除失败") {
                        @Override
                        public void getSuc(String body) {
                            jubaoepopupWindow.dismiss();
                            DialogUtil.dismissProgress();
                            popubWiodowTask(TaskAccomplished.this, type);
                        }
                    });
                }
            }
        });
        final View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        jubaoepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });

        jubaoepopupWindow.setFocusable(true);
        TextView ace = findViewById(R.id.text);
        ace.post(new Runnable() {
            @Override
            public void run() {


                try {
                    addBackgroundPopub(jubaoepopupWindow);
                    jubaoepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }

    private void task_submission_fail(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.task_submission_fail, null, false);//引入弹窗布局
        TextView ways_release = vPopupWindow.findViewById(R.id.ways_releaseaa);
        ImageView expression = vPopupWindow.findViewById(R.id.expression);


        TextView reward_content = vPopupWindow.findViewById(R.id.reward_content);

        reward_content.setVisibility(View.VISIBLE);

        ways_release.setText(message);

        TextView report = vPopupWindow.findViewById(R.id.report);
        report.setVisibility(View.VISIBLE);
        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);


        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Conversation conv = JMessageClient.getSingleConversation(phone, SharedPrefHelper.getInstance().getAppKey());
                //如果会话为空，使用EventBus通知会话列表添加新会话
                if (conv == null) {
                    Conversation.createSingleConversation(phone, SharedPrefHelper.getInstance().getAppKey());
                    Log.e("TAG", phone);
//                    EventBus.getDefault().post(new Event.Builder()
//                            .setType(EventType.createConversation)
//                            .setConversation(conv)
//                            .build());
                }
                Intent intent = new Intent(TaskAccomplished.this, ChatActivity.class);
                intent.putExtra("USERNAME", phone);

                intent.putExtra(MyAPP.TARGET_ID, phone);
                intent.putExtra(MyAPP.TARGET_APP_KEY, SharedPrefHelper.getInstance().getAppKey());
                intent.putExtra(MyAPP.CONV_TITLE, realName);
                startActivity(intent);
                relievepopupWindow.dismiss();
                finish();

            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                popubReasonTermination(TaskAccomplished.this);
            }
        });

        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                finish();

            }
        });
        //  popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        final View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移

        relievepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });

        relievepopupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        relievepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                finish();
            }
        });
        relievepopupWindow.setFocusable(false);
        TextView ace = findViewById(R.id.text);
        ace.post(new Runnable() {
            @Override
            public void run() {
                try {
                    addBackgroundPopub(relievepopupWindow);
                    relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }
}
