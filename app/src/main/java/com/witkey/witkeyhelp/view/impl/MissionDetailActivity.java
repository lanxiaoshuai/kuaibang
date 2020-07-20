package com.witkey.witkeyhelp.view.impl;

import android.annotation.TargetApi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;

import android.support.v7.app.ActionBar;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import android.view.Gravity;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.CommentExpandAdapter;
import com.witkey.witkeyhelp.adapter.ReleaseDetailsAdapter;
import com.witkey.witkeyhelp.adapter.SecondaryAdapter;
import com.witkey.witkeyhelp.adapter.UnpublishFeedbackAdapter;
import com.witkey.witkeyhelp.bean.Acount;


import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.ReasonListBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.bean.ReplyBean;
import com.witkey.witkeyhelp.bean.UnpublishFeedbackBean;
import com.witkey.witkeyhelp.dialog.ReceiptSucDia;
import com.witkey.witkeyhelp.dialog.TaskDetailsDialog;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;

import com.witkey.witkeyhelp.event.ResultEvent;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IMissionDetailPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MissionDetailPresenterImpl;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SharedPrefHelper;
import com.witkey.witkeyhelp.util.SharedPreferencesUtil;
import com.witkey.witkeyhelp.util.SoftKeyBoardListener;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.TimeUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.UIUtils;
import com.witkey.witkeyhelp.util.WebLinkMethod;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMissionDetailView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;
import com.witkey.witkeyhelp.widget.CommentExpandableListView;
import com.witkey.witkeyhelp.widget.HeaderAndFooterWrapper;
import com.witkey.witkeyhelp.widget.NoScrollListview;
import com.witkey.witkeyhelp.widget.PopWinShare;
import com.witkey.witkeyhelp.widget.RatingBar;
import com.witkey.witkeyhelp.widget.RecycleGridDivider;
import com.witkey.witkeyhelp.widget.RoundImageView;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;


/**
 * @author lingxu
 * @date 2019/7/26 15:13
 * @description 任务详情
 */
public class MissionDetailActivity extends InitPresenterBaseActivity implements View.OnClickListener, IMissionDetailView, AdapterView.OnItemClickListener {
    private LinearLayout ll_share;

    private TextView tv_chat;
    private TextView tv_collect;
    private TextView tv_report;
    private Button tv_commit;

    private IMissionDetailPresenter presenter;
    private int businessId;

    private MissionBean missionBean;
    private int collections;
    private PopupWindow popupWindow;
    public String orderState;
    private LinearLayout rewardall;
    private LinearLayout has_released;

    private LinearLayout task_release;
    private Button task_relieving;
    private Button mission_accomplished;
    private EditText cancel_reason;
    private Button btn_publish;
    private RelativeLayout groupdeitText;
    private TextView include_title;
    private TextView price;
    private TextView et_content;

    private TextView bond;
    private TextView contact_information;
    private ReceiptSucDia receiptSucDia;
    private Button btn_save;

    private PopupWindow popupWindowTask;
    private TextView chat;
    private String url;
    private TextView task_report;
    private UICustomDialog2 dialog2;

    private int deposit;
    private RelativeLayout telephone_dial;

    private TextView chap_liaotian;
    private double baozhengjin;
    private TaskDetailsDialog detailsDialog;
    private PopupWindow popupShare;
    private IModel.AsyncCallback callback;
    // private ImageView diamonds;
    private ViewTreeObserver mTreeObserver;
    private ViewTreeObserver.OnGlobalLayoutListener mListener;
    private TextView price_left_text;
    private UICustomDialog2 dialog3;
    private RecyclerView photolist;
    private ReleaseDetailsAdapter photoAdapter;
    private List<ReleasePhotoBean> photoList;
    private View line;
    private int[] location;
    private View vPopupWindow;
    private View parentView;
    private String isComment;
    private int orderId;
    private TextView release_time;
    private RoundImageView postavatar;
    private TextView publisher_name;
    // private TextView tv_mission_type;
    private TextView price_image_diamond;
    private boolean taskorder;
    private RelativeLayout bond_layout;
    private View telephone_dial_line;
    private View bond_line;
    private CommentExpandableListView expandableListView;
    private SmartRefreshLayout refreshLayout;
    private List<ReplyBean.ReturnObjectBean.RowsBean> commentsList;
    private CommentExpandAdapter adapter;
    private LinearLayout send_layout;
    private EditText detail_page_do_comment;
    private TextView sendcomment_btn;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private PopupWindow releaseTaskPopob;
    private EditText detail_comment;
    private int numberLevels;

    private AppBarLayout appBarLayout;

    private TextView sendcomment;
    private RecyclerView level_review;
    private LinearLayout orders_relayout;
    private LinearLayout private_chat;
    private LinearLayout bouny_layout;
    private boolean releaserTask;
    private PopupWindow multitasPopub;
    private TextView reply;
    private TextView amount;
    private TextView allocated;
    private PopupWindow relievepopupWindow;
    private TextView price_left;
    private TextView price_image;
    private LinearLayout forward;
    private View popups_id;

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
    protected void parseArgumentsFromIntent(Intent argIntent) {
        businessId = getIntent().getIntExtra("EXTRA_BUSINESS_ID", 0);
        orderState = getIntent().getStringExtra("TASKDETAILS");

        orderId = getIntent().getIntExtra("ORDERID", 0);
        taskorder = getIntent().getBooleanExtra("TASKORDER", false);

        releaserTask = getIntent().getBooleanExtra("TASK", false);

    }

    public void scrollToTop() {
        appBarLayout.setExpanded(false, true);
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void initViewExceptPresenter() {
        //include_title.setText("悬赏详情");
        presenter.getAcount(user.getUserId());


        orders_relayout = findViewById(R.id.orders_relayout);
        appBarLayout = findViewById(R.id.appBarLayout);


        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        refreshLayout = findViewById(R.id.refreshLayout);
        commentsList = new ArrayList<>();

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                MobclickAgent.onEvent(MissionDetailActivity.this, "rewardHallLoadMore");
                int totalPage = numberLevels / 10;
                if (numberLevels % 10 != 0) {
                    totalPage += 1;
                }
                if (totalPage > pageNum) {
                    pageNum += 1;
                    showReply();
                } else {
                    refreshlayout.finishLoadMoreWithNoMoreData();
                }
            }

        });
        callback = new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {


            }

            @Override
            public void onError(Object data) {
                ToastUtils.showTestShort(MissionDetailActivity.this, data.toString());
                Log.e("lrj", data.toString());
                refreshLayout.finishLoadMore(false);
            }
        };
        setSoftKeyBoardListener();

        sendcomment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detail_page_do_comment.getText().toString().trim().equals("")) {
                    return;
                }
                addComment();
                hideInput();
            }
        });
        if (taskorder) {
            scrollToTop();
            send_layout.setVisibility(View.VISIBLE);
            rewardall.setVisibility(View.GONE);
            showInput(detail_page_do_comment);
        }
        if (orderState == null) {
            // presenter.getMissionDetail(businessId + "", user.getUserId());
            MyAPP.getInstance().getApi().getMissionOrder(businessId + "", user.getUserId()).enqueue(new Callback(callback, "获取任务详情失败") {

                @Override
                public void getSuc(String body) {
                    MissionBean missionBean = IModel.gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);
                    showMissionDetail(missionBean);
                }
            });
        } else {
            DialogUtil.showProgress(this);
            getMissionDetail();
        }


    }

    private int pageNum = 1;

    private void showReply() {
        MyAPP.getInstance().getApi().getFindCommentByBusiness(businessId, pageNum, 10).enqueue(new Callback(callback, "获取评论失败") {
            @Override
            public void getSuc(String body) {
                refreshLayout.finishLoadMore(/*,false*/);//传入false表示刷新失败
                ReplyBean beanFromJson = JsonUtils.getBeanFromJson(body, ReplyBean.class);
                numberLevels = beanFromJson.getReturnObject().getTotal();
                commentsList.addAll(beanFromJson.getReturnObject().getRows());
                for (int i = 0; i < commentsList.size(); i++) {
                    expandableListView.expandGroup(i);
                }
                adapter.notifyDataSetChanged();
                multitasking();
            }
        });


    }

    private void addComment() {
        MyAPP.getInstance().getApi().getCommentAdd(user.getUserId(), detail_page_do_comment.getText().toString(), businessId).enqueue(new Callback(callback, "获取评论失败") {

            @Override
            public void getSuc(String body) {

                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String id = jsonObject.getString("returnObject");
                    ReplyBean.ReturnObjectBean.RowsBean rowsBean = new ReplyBean.ReturnObjectBean.RowsBean();
                    rowsBean.setUserId(user.getUserId());
                    rowsBean.setContent(detail_page_do_comment.getText().toString());
                    rowsBean.setUserName(user.getRealName());
                    rowsBean.setHeadUrl(user.getHeadUrl());
                    rowsBean.setCreateTime(TimeUtils.currentTime());
                    rowsBean.setUserPhone(user.getUserName());
                    rowsBean.setId(id);
                    rowsBean.setList(new ArrayList<ReplyBean.ReturnObjectBean.RowsBean.ListBean>());
                    commentsList.add(0, rowsBean);
                    adapter.notifyDataSetChanged();

                    detail_page_do_comment.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /**
     * 添加软键盘监听
     */
    private void setSoftKeyBoardListener() {
        SoftKeyBoardListener softKeyBoardListener = new SoftKeyBoardListener(this);
        //软键盘状态监听
        softKeyBoardListener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //软键盘已经显示，做逻辑

            }

            @Override
            public void keyBoardHide(int height) {
                //软键盘已经隐藏,做逻辑
                send_layout.setVisibility(View.GONE);
                orders_relayout.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<ReplyBean.ReturnObjectBean.RowsBean> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList, user.getUserId(), missionBean.getCreateUserId(), missionBean.getBusinessType(), missionBean.getPaymentType(), missionBean.getReceiverPhone());
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                popubWiodowReply(groupPosition);
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                // Toast.makeText(MainActivity.this, "点击了回复", Toast.LENGTH_SHORT).show();
                popubWiodowReply(groupPosition);
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");
            }
        });
        adapter.setOnItemClickListener(new CommentExpandAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (Double.parseDouble(amount.getText().toString()) > 0) {
                    popubBFounty(MissionDetailActivity.this, commentList.get(position).getId(), position, true, 0);
                } else {
                    ToastUtils.showTestShort(MissionDetailActivity.this, "赏金已分配完");
                }
            }
        });
    }

    private void getMissionDetail() {

        MyAPP.getInstance().getApi().getMissionDetail(orderId, user.getUserId()).enqueue(new Callback(callback, "获取任务详情失败") {

            @Override
            public void getSuc(String body) {

                MissionBean missionBean = IModel.gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);
                showMissionDetail(missionBean);
            }
        });
    }

    private String renwu = "";


    @Override
    protected void initWidget() {

        int version = android.os.Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.O) {
            disableAutoFill();
        }
        ll_share = findViewById(R.id.ll_share);
        reply = findViewById(R.id.reply);
        tv_chat = findViewById(R.id.tv_chat);
        amount = findViewById(R.id.amount);
        price_left = findViewById(R.id.price_left);
        price_image = findViewById(R.id.price_image);

        allocated = findViewById(R.id.allocated);
        tv_collect = findViewById(R.id.tv_collect);
        tv_report = findViewById(R.id.tv_report);

        tv_commit = (Button) findViewById(R.id.tv_commitbutton);

        rewardall = findViewById(R.id.rewardall);

        line = findViewById(R.id.line);

        has_released = findViewById(R.id.has_released);

        task_release = findViewById(R.id.task_release);
        forward = findViewById(R.id.forward);


        task_relieving = findViewById(R.id.task_relieving);
        mission_accomplished = findViewById(R.id.mission_accomplished);
        include_title = findViewById(R.id.include_title);
        price = findViewById(R.id.price);
        et_content = findViewById(R.id.et_content);

        bond = findViewById(R.id.bond);
        contact_information = findViewById(R.id.contact_information);
        btn_save = findViewById(R.id.btn_save);

        chat = findViewById(R.id.chat);
        task_report = findViewById(R.id.task_report);
        telephone_dial = findViewById(R.id.telephone_dial);
        chap_liaotian = findViewById(R.id.chap_liaotian);
        //diamonds = findViewById(R.id.diamonds);
        price_left_text = findViewById(R.id.price_left_text);
        price_image_diamond = findViewById(R.id.price_image_diamond);

        bond_layout = findViewById(R.id.bond_layout);
        telephone_dial_line = findViewById(R.id.telephone_dial_line);

        send_layout = findViewById(R.id.send_layout);
        sendcomment_btn = findViewById(R.id.sendcomment_btn);

        detail_page_do_comment = findViewById(R.id.detail_page_do_comment);


        bond_line = findViewById(R.id.bond_line);
        photolist = findViewById(R.id.photolist);

        release_time = findViewById(R.id.release_time);

        postavatar = findViewById(R.id.postavatar);

        publisher_name = findViewById(R.id.publisher_name);
        //tv_mission_type = findViewById(R.id.tv_mission_type);
        private_chat = findViewById(R.id.private_chat);
        bouny_layout = findViewById(R.id.bouny_layout);


        photolist.setLayoutManager(new GridLayoutManager(this, 3));
        int spanCount = 3; // 3 columns
        int spacing = UIUtils.dip2px(4.5f); // 50px
        boolean includeEdge = false;
        //   photolist.addItemDecoration(new RecycleGridDivider(spanCount, spacing, includeEdge));
        photoList = new ArrayList<>();
        photoAdapter = new ReleaseDetailsAdapter(this, photoList);
        photolist.setAdapter(photoAdapter);
        DialogUtil.showProgress(this);
        mDatas = new ArrayList<UnpublishFeedbackBean>();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_task_details;
    }

    @Override
    protected void initEvent() {
        ll_share.setOnClickListener(this);
        postavatar.setOnClickListener(this);
        reply.setOnClickListener(this);
        forward.setOnClickListener(this);
//        tv_chat.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        private_chat.setOnClickListener(this);
        tv_report.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        task_relieving.setOnClickListener(this);
        mission_accomplished.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        chat.setOnClickListener(this);
        task_report.setOnClickListener(this);
        tv_report.setOnClickListener(this);
        telephone_dial.setOnClickListener(this);
        chap_liaotian.setOnClickListener(this);
        et_content.setOnClickListener(this);
        detail_page_do_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) {
                    sendcomment_btn.setTextColor(getResources().getColor(R.color.defaultsendColor));
                } else {
                    sendcomment_btn.setTextColor(getResources().getColor(R.color.successsendColor));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_share:
                showMultitasking();

                break;
            case R.id.tv_chat:
                break;
            case R.id.tv_collect:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (collections == 0) {

                    presenter.collection(businessId, user.getUserId());
                } else if (collections == 1) {

                    presenter.cancelCollection(businessId, user.getUserId());
                } else {

                    ToastUtils.showTestShort(mActivity, "没有返回");
                }

                break;

            case R.id.tv_commitbutton:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }

                if (orderState == null) {
                    dialog2 = new UICustomDialog2(MissionDetailActivity.this, "任务接单", "3");
                    dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.hide();
                        }
                    });
                    dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DialogUtil.showProgress(MissionDetailActivity.this);
                            presenter.receipt(businessId, user.getUserId());
                            dialog2.hide();

                        }
                    });
                    dialog2.show();
                }
                break;
            case R.id.task_relieving:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (missionBean != null) {
                    if (deposit > 0) {
                        popubBondTermination(MissionDetailActivity.this);
                    } else {
                        showAnimation();
                    }

                } else {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                }

                break;

            case R.id.btn_save:
                if (missionBean == null) {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                    return;
                }
                if (orderState == null) {
                    showAnimation();
                } else {
                    if (!"1".equals(isComment)) {
                        getPopubWindow();
                    }

                }

                break;
            case R.id.mission_accomplished:
                if (missionBean == null) {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                    return;
                }
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                dialog3 = new UICustomDialog2(MissionDetailActivity.this, renwu, "3");
                dialog3.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.hide();
                    }
                });
                dialog3.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogUtil.showProgress(MissionDetailActivity.this);
                        if (missionBean.getCreateUserId() == user.getUserId()) {
                            presenter.confirmsReceiveOrder(missionBean.getOrderId() + "", 2);
                        } else {
                            presenter.confirmsReceiveOrder(missionBean.getOrderId() + "", 5);
                        }

                        dialog3.hide();
                    }
                });
                dialog3.show();
                //   showFinishTaskAnimation();
                break;
            case R.id.chat:
                if (missionBean == null) {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                    return;
                }
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                newChat();
                break;

            case R.id.private_chat:
                if (missionBean == null) {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                    return;
                }
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
//                Intent intent1 = new Intent(this, ProblemFeedbackActivity.class);
//                intent1.putExtra("type", 1);
//                intent1.putExtra("businessId", missionBean.getBusinessId());
//                startActivity(intent1);
                send_layout.setVisibility(View.VISIBLE);
                orders_relayout.setVisibility(View.GONE);
                showInput(detail_page_do_comment);

                break;
            case R.id.tv_report:
                if (missionBean == null) {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                    return;
                }
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                send_layout.setVisibility(View.VISIBLE);
                orders_relayout.setVisibility(View.GONE);
                showInput(detail_page_do_comment);
                break;

            case R.id.telephone_dial:

                if (missionBean == null) {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                    return;
                }
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (contact_information.getText().toString().trim().length() == 11) {
                    if (missionBean != null) {
                        if (missionBean.getContactsPhone() != null) {
                            diallPhone(missionBean.getContactsPhone());
                        }

                    }


                } else {

                }

                break;
            case R.id.chap_liaotian:
//                if (missionBean == null) {
//                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
//                    return;
//                }
//                if (PventQuickClick.isFastDoubleClick()) {
//                    break;
//                }
//
//                send_layout.setVisibility(View.VISIBLE);
//                orders_relayout.setVisibility(View.GONE);
//                showInput(detail_page_do_comment);


                break;
            case R.id.reply:

                send_layout.setVisibility(View.VISIBLE);
                orders_relayout.setVisibility(View.GONE);
                showInput(detail_page_do_comment);
                break;
            case R.id.task_report:
                send_layout.setVisibility(View.VISIBLE);
                orders_relayout.setVisibility(View.GONE);
                showInput(detail_page_do_comment);
                break;
            case R.id.postavatar:
                if (missionBean == null) {
                    ToastUtils.showTestShort(this, "网络出现异常,请稍后再试");
                    return;
                }
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                newChat();
                break;
            case R.id.forward:
                PopWinShare popWinShare = new PopWinShare(1, MissionDetailActivity.this, "快来和我一起帮助他人，赚取赏金吧！", "悬赏" + price.getText().toString() + "元  " + et_content.getText().toString(), "http://a.app.qq.com/o/simple.jsp?pkgname=com.witkey.witkeyhelp");

                //引入依附的布局
                View parentView = LayoutInflater.from(MissionDetailActivity.this).inflate(R.layout.activity_task_details, null);
                //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;//调节透明度
                getWindow().setAttributes(lp);
                //dismiss时恢复原样
                popWinShare.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                popWinShare.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                break;

            default:

                break;
        }

    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    private int ratingCount;
    PopupWindow popupEvaluation;

    //评价
    private void getPopubWindow() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.dialog_evaluation, null, false);//引入弹窗布局
        if (popupEvaluation == null) {

            popupEvaluation = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }


        //设置进出动画
        //     popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        View parentView = LayoutInflater.from(this).inflate(R.layout.activity_system_message, null);


        final RatingBar ratingBar = (RatingBar) vPopupWindow.findViewById(R.id.rating_bar0);
        ratingBar.setClickable(true);//设置可否点击
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
                MissionDetailActivity.this.ratingCount = (int) ratingCount;
            }
        });
        ImageView expression = vPopupWindow.findViewById(R.id.expression);
        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupEvaluation.dismiss();
            }
        });

        final EditText evaluation_content = vPopupWindow.findViewById(R.id.evaluation_content);

        TextView to_evaluate = vPopupWindow.findViewById(R.id.to_evaluate);

        to_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evaluation_content.getText().toString().equals("")) {
                    ToastUtils.showTestShort(MissionDetailActivity.this, "请输入内容");
                    return;
                }
                if (ratingCount <= 0) {
                    ToastUtils.showTestShort(MissionDetailActivity.this, "请给打分");
                    return;
                }
                hitValuatione(popupEvaluation, evaluation_content.getText().toString(), ratingCount + "");
            }
        });
        addBackgroundPopub(popupEvaluation);
        popupEvaluation.showAtLocation(parentView, Gravity.CENTER, 0, 0);

    }

    private void hitValuatione(final PopupWindow popupWindow, String content, String score) {
        MyAPP.getInstance().getApi().commentAdd(user.getUserId() + "", missionBean.getOrderId() + "", content, score).enqueue(new Callback(IModel.callback, "上传失败") {

            @Override
            public void getSuc(String body) {
                DialogUtil.dismissProgress();
                popupWindow.dismiss();
                missionBean.setIsComment("1");
                btn_save.setBackground(getResources().getDrawable(R.drawable.shape_gray_details_renl));
                ToastUtils.showTestShort(MissionDetailActivity.this, "评价成功");
                btn_save.setText("已评价");
            }
        });
    }


    public static String[] linkString(String str) {
        Matcher m = Pattern.compile("([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[wW]{3}.|[wW][aA][pP].|[fF][tT][pP].|[fF][iI][lL][eE].)[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]").matcher(str);
        String[] url = new String[str.length() / 5];
        int count = 0;
        while (m.find()) {
            url[count] = m.group();
            count++;
        }
        return url;
    }


    private void webUrl(String[] url, String linkText) {
        linkText = linkText.replace(" ", "&nbsp;");
        String allUrl = linkText.toString();
        CharSequence charSequence;
        if (url.length > 0) {   //格式拼接
            for (int i = 0; i < url.length; i++) {
                if (!TextUtils.isEmpty(url[i])) {

                    String str = "<pre><font color='#48A7F9'> <a href=\"" + url[i] + "\">" + url[i] + "</a></font></pre>";


                    if (allUrl.contains(str)) {

                    } else {
                        allUrl = allUrl.replace(url[i], str);
                    }


                } else {


                }
            }
            allUrl = parseContent(allUrl);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                charSequence = Html.fromHtml(allUrl, Html.FROM_HTML_MODE_LEGACY);
            } else {
                charSequence = Html.fromHtml(allUrl);
            }
            et_content.setText(charSequence);
        }


        et_content.setMovementMethod(WebLinkMethod.getInstance(this));  //其实就这一句是关键
    }

    private String parseContent(String content) {

        content = content.replace("\n", "<br/>");
        return content;
    }


    private void newChat() {

        if (missionBean.getCreateUserName().equals(user.getUserName())) {
            Log.e("tag", "不能聊天");
            ToastUtils.showTestShort(this, "不能与自己本身聊天");

            return;
        }

        Conversation conv = JMessageClient.getSingleConversation(missionBean.getCreateUserName(), SharedPrefHelper.getInstance().getAppKey());
        //如果会话为空，使用EventBus通知会话列表添加新会话
        if (conv == null) {
            Conversation.createSingleConversation(missionBean.getCreateUserName(), SharedPrefHelper.getInstance().getAppKey());
            Log.e("TAG", missionBean.getCreateUserName());

        }
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("USERNAME", missionBean.getCreateUserName());

        intent.putExtra(MyAPP.TARGET_ID, missionBean.getCreateUserName());
        intent.putExtra(MyAPP.TARGET_APP_KEY, SharedPrefHelper.getInstance().getAppKey());
        intent.putExtra(MyAPP.CONV_TITLE, missionBean.getCreateUserPhone());
        startActivity(intent);
        //  SpUtils.putBoolean(MissionDetailActivity.this, missionBean.getCreateUserPhone(), true);
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


    @Override
    public void showMission(MissionBean missionBean) {
    }

    public void showMissionDetail(MissionBean missionBean) {
        if (missionBean != null) {
            this.missionBean = missionBean;
            deposit = missionBean.getDeposit();
            String businessType = missionBean.getBusinessType();

            bond.setText("￥" + missionBean.getDeposit());
            price.setText(missionBean.getPrice() + ".00");
            if (missionBean.getPaymentType().equals("1")) {

                price_left_text.setVisibility(View.VISIBLE);
                price_image_diamond.setVisibility(View.GONE);
                price.setTextColor(getResources().getColor(R.color.shape_org));
            } else {

                price_image_diamond.setVisibility(View.VISIBLE);
                price_left_text.setVisibility(View.GONE);
                price.setTextColor(getResources().getColor(R.color.shape_lan));
            }
            if ("2".equals(businessType)) {
                bond_layout.setVisibility(View.VISIBLE);
                telephone_dial_line.setVisibility(View.VISIBLE);
                bond_line.setVisibility(View.VISIBLE);
                telephone_dial.setVisibility(View.VISIBLE);
                include_title.setText("悬赏帮忙");
                orders_relayout.setVisibility(View.GONE);
                amount.setVisibility(View.GONE);
                allocated.setVisibility(View.GONE);
                if (orderId == 0) {
                    rewardall.setVisibility(View.VISIBLE);
                } else {
                    rewardall.setVisibility(View.GONE);
                }
                if (user.getUserId() == missionBean.getCreateUserId()) {

                    if (orderState == null) {
                        task_release.setVisibility(View.VISIBLE);
                    } else {
                        switch (orderState) {
                            //悬赏大厅
                            case "1":
                                renwu = "任务完成！";
                                chap_liaotian.setText("回复");
                                // 任务进行中
                                has_released.setVisibility(View.VISIBLE);
                                break;
                            case "4":
                                break;
                            case "8":
                                rewardall.setVisibility(View.VISIBLE);
                                chap_liaotian.setText("回复");
                                tv_commit.setText("等待帮忙者取消中...");
                                tv_commit.setTextColor(getResources().getColor(R.color.colortext_size));
                                tv_commit.setBackground(getResources().getDrawable(R.drawable.shape_gray_details_an));
                                break;
                            case "9":
                                rewardall.setVisibility(View.VISIBLE);
                                chap_liaotian.setText("回复");
                                tv_commit.setText("帮忙者不同意取消...");
                                tv_commit.setTextColor(getResources().getColor(R.color.colortext_size));
                                tv_commit.setBackground(getResources().getDrawable(R.drawable.shape_gray_details_an));
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    if (orderState == null) {
                    } else {
                        switch (orderState) {
                            //悬赏大厅
                            case "1":
                                renwu = "任务提交";
                                chap_liaotian.setText("回复");
                                has_released.setVisibility(View.VISIBLE);
                                mission_accomplished.setText("任务提交");
                                break;
                            //接单已提交完成
                            case "6":
                                rewardall.setVisibility(View.VISIBLE);
                                chap_liaotian.setText("回复");
                                tv_commit.setText("等待悬赏者确认中...");
                                tv_commit.setTextColor(getResources().getColor(R.color.colortext_size));
                                tv_commit.setBackground(getResources().getDrawable(R.drawable.shape_gray_details_an));
                                break;
                            case "3":
                                rewardall.setVisibility(View.VISIBLE);
                                chap_liaotian.setText("回复");
                                tv_commit.setText("等待悬赏者取消中...");
                                tv_commit.setTextColor(getResources().getColor(R.color.colortext_size));
                                tv_commit.setBackground(getResources().getDrawable(R.drawable.shape_gray_details_an));
                                break;
                            case "8":
                                renwu = "任务提交";
                                chap_liaotian.setText("回复");
                                has_released.setVisibility(View.VISIBLE);
                                mission_accomplished.setText("任务提交");
                                break;
                            default:


                                break;
                        }
                    }

                }
            } else if ("1".equals(businessType)) {
                include_title.setText("信息咨询");
                orders_relayout.setVisibility(View.VISIBLE);
                amount.setVisibility(View.VISIBLE);
                amount.setText(missionBean.getResidue() + "");
                if (missionBean.getPaymentType().equals("1")) {
                    price_left.setVisibility(View.VISIBLE);
                    amount.setTextColor(getResources().getColor(R.color.shape_org));
                    price_image.setVisibility(View.GONE);
                } else {
                    price_left.setVisibility(View.GONE);
                    amount.setTextColor(getResources().getColor(R.color.shape_lan));
                    price_image.setVisibility(View.VISIBLE);
                }
                allocated.setVisibility(View.VISIBLE);
                rewardall.setVisibility(View.GONE);
            } else if ("5".equals(businessType)) {
                include_title.setText("动态");
                orders_relayout.setVisibility(View.VISIBLE);
                amount.setVisibility(View.GONE);
                amount.setText(missionBean.getResidue() + "");
                allocated.setVisibility(View.GONE);
                rewardall.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
                price_left_text.setVisibility(View.GONE);
                price_image_diamond.setVisibility(View.GONE);
            }
            isComment = missionBean.getIsComment();


            release_time.setText(TimeUtils.DateDistance2now(TimeUtils.date2ms(missionBean.getCreateDate())));
            Glide.with(this).load(URL.getImgPath + missionBean.getCreateUserHeadUrl()).into(postavatar);

            publisher_name.setText(missionBean.getCreateUserPhone());
            if (!missionBean.getBusinessType().equals("") || missionBean.getBusinessType() != null) {
                if (missionBean.getBusinessType().equals("1")) {
//                    tv_mission_type.setText("信息咨询");
//                    tv_mission_type.setBackground(getResources().getDrawable(R.drawable.shape_org_stroke));
                } else if (missionBean.getBusinessType().equals("2")) {
//                    tv_mission_type.setText("悬赏帮忙");
//                    tv_mission_type.setBackground(getResources().getDrawable(R.drawable.rewardhelp));
                }
            }
            et_content.setText("" + missionBean.getDescribes());

            String[] strings = linkString(et_content.getText().toString());
            webUrl(strings, et_content.getText().toString());

            contact_information.setText(hidePhoneNum(missionBean.getContactsPhone()) + "");
            if (missionBean.getPlaceName() == null || "".equals(missionBean.getPlaceName())) {

            } else {
                tv_chat.setText(missionBean.getPlaceName());
            }

            if (null == missionBean.getBusinessImgUrl() || missionBean.getBusinessImgUrl().equals("")) {
                photolist.setVisibility(View.GONE);
            } else {
                photolist.setVisibility(View.VISIBLE);
                if (missionBean.getBusinessImgUrl().contains(",")) {

                    String[] split = missionBean.getBusinessImgUrl().split(",");
                    for (int i = 0; i < split.length; i++) {
                        photoList.add(new ReleasePhotoBean(URL.getImgPath + split[i], true));
                    }

                } else {
                    photoList.add(new ReleasePhotoBean(URL.getImgPath + missionBean.getBusinessImgUrl(), true));

                    //  Glide.with(this).load(URL.getImgPath + missionBean.getBusinessImgUrl()).into(iv_pic);
                }
                photoAdapter.notifyDataSetChanged();

            }
            url = URL.getImgPath + missionBean.getBusinessImgUrl();
            //添加判断
            if (missionBean.getCreateUserId() == user.getUserId()) {
                if (orderState != null) {
                    MyAPP.getInstance().getApi().reasonList(0).enqueue(new Callback(IModel.callback, "上传失败") {
                        @Override
                        public void getSuc(String body) {
                            Gson gson = MyAPP.getInstance().getGson();
                            PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson, JSONUtil.getValueToString(body, "returnObject"), ReasonListBean.class);
                            List<ReasonListBean> reasonListBeans = missionResponse.getRows();
                            mDatas.add(new UnpublishFeedbackBean(true, reasonListBeans.get(0).getReason()));
                            for (int i = 1; i < reasonListBeans.size(); i++) {
                                mDatas.add(new UnpublishFeedbackBean(false, reasonListBeans.get(i).getReason()));

                            }


                        }
                    });
                }

            } else {

                MyAPP.getInstance().getApi().reasonList(1).enqueue(new Callback(IModel.callback, "上传失败") {
                    @Override
                    public void getSuc(String body) {
                        Gson gson = MyAPP.getInstance().getGson();
                        PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson, JSONUtil.getValueToString(body, "returnObject"), ReasonListBean.class);


                        List<ReasonListBean> reasonListBeans = missionResponse.getRows();
                        mDatas.add(new UnpublishFeedbackBean(true, reasonListBeans.get(0).getReason()));
                        for (int i = 1; i < reasonListBeans.size(); i++) {
                            mDatas.add(new UnpublishFeedbackBean(false, reasonListBeans.get(i).getReason()));

                        }
                    }
                });
            }

            initExpandableListView(commentsList);
            showReply();

            getReadydialog();
        }
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

    /**
     * 设置drawableTop上图片大小
     *
     * @param tv
     */
    public void setDrawableTop(TextView tv, int mipmap) {
        Resources res = getResources();
        Drawable drawable = res.getDrawable(mipmap);
        drawable.setBounds(0, 0, 55, 55);
        tv.setCompoundDrawables(null, drawable, null, null);
    }


    @Override
    public void receiptSuc() {
        detail_page_do_comment.setText("我领取了您发布的帮忙");
        missionBean.setReceiverPhone(user.getUserName());
        addComment();
        receiptSucDia = new ReceiptSucDia(mActivity);
        addTooBackground();
        receiptSucDia.show();
        receiptSucDia.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (MissionDetailActivity.this == null || MissionDetailActivity.this.isDestroyed() || MissionDetailActivity.this.isFinishing()) {

                        return;
                    } else {
                        EventBus.getDefault().post(new ResultEvent("接单成功"));
                        finish();
                    }
                }

            }
        });
    }

    @Override
    public void onError(String msg) {

        if (msg.contains("保证金不足")) {
            detailsDialog = new TaskDetailsDialog(this, 1, 0);
            addBondBackground(detailsDialog);
            detailsDialog.show();
        } else {

            ToastUtils.showTestShort(this, msg);
        }

    }

    private void addBondBackground(TaskDetailsDialog taskDetailsDialog) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样
        taskDetailsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void getCollection() {

        ToastUtils.showTestShort(this, "收藏成功");
        collections = 1;
        //0 未收藏  1已收藏
        if (collections == 0) {
            // Toast.makeText(this, "未收藏", Toast.LENGTH_SHORT).show();
            setDrawableTop(tv_collect, R.mipmap.ic_mission_detail_collect_icon);
        } else {
            //  Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
            setDrawableTop(tv_collect, R.mipmap.collection);
        }
    }

    @Override
    public void getCancelCollection() {

        ToastUtils.showTestShort(this, "取消收藏");
        collections = 0;

        //0 未收藏  1已收藏
        if (collections == 0) {
            // Toast.makeText(this, "未收藏", Toast.LENGTH_SHORT).show();
            setDrawableTop(tv_collect, R.mipmap.ic_mission_detail_collect_icon);
            //  ImgUtil.setDrawableTop(this,R.mipmap.ic_mission_detail_collect_icon,tv_collect);
        } else {
            //  Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
            setDrawableTop(tv_collect, R.mipmap.collection);
            //ImgUtil.setDrawableTop(this,R.mipmap.collection,tv_collect);
        }
    }

    @Override
    public void taskRelieving(String data) {
        TaskDetailsDialog taskDetailsDialog = null;
        if (missionBean.getCreateUserId() == user.getUserId()) {
            if (orderState == null) {
                taskDetailsDialog = new TaskDetailsDialog(this, 3, 0, data);
                addThereBackground(taskDetailsDialog);
                taskDetailsDialog.show();

            } else {
                popubWiodowTermination(this);
            }
        } else {
            popubWiodowTermination(this);
        }


    }


    private void popubWiodowTermination(Context context) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.task_withdrawal_layout_two, null);//引入弹窗布局
        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ImageView expression = vPopupWindow.findViewById(R.id.expression);
        ImageView image_icon = vPopupWindow.findViewById(R.id.image_icon);
        image_icon.setImageResource(R.mipmap.relieve_task);
        TextView eason_renwu = vPopupWindow.findViewById(R.id.eason_renwu);
        eason_renwu.setText(reason);

        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        TextView text_jiechu = vPopupWindow.findViewById(R.id.text_jiechu);
        TextView taskrelieving = vPopupWindow.findViewById(R.id.taskrelieving);

        if (missionBean.getCreateUserId() == user.getUserId()) {

            taskrelieving.setText("已发起任务解除");
            text_jiechu.setText("1.帮忙者同意后将扣除帮忙者一分信誉分并退回赏金\n2.若帮忙者不同意解除则客服介入处理\n3.若帮忙者48小时未处理则默认同意解除");

        } else {

            text_jiechu.setText("1.已扣除您一分信誉分");
            taskrelieving.setText("任务解除成功");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();

            }
        });
        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();

            }
        });


        View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);


        addBackgroundPopub(relievepopupWindow);
        relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

        relievepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setResult(100);
                finish();
            }
        });
    }

    private void addBackgroundPopub(PopupWindow popup) {
        // 设置背景颜色变暗
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
                popups_id.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void issionmAccomplished(String data) {

        TaskDetailsDialog taskDetailsDialog = null;
        if (missionBean.getCreateUserId() == user.getUserId()) {
            //悬赏者确认任务
            taskDetailsDialog = new TaskDetailsDialog(this, 9, 0);
            addThereBackground(taskDetailsDialog);
            taskDetailsDialog.show();
        } else {
            //接单者确认接单
            taskDetailsDialog = new TaskDetailsDialog(this, 8, 0);
            addThereBackground(taskDetailsDialog);
            taskDetailsDialog.show();
        }


    }

    @Override
    public void showAcount(Acount data) {
        baozhengjin = data.getDeposit();
    }

    @Override
    public void UploadFeedback(String data) {
        // finish();
        popubWiodowTask(this);
    }

    private void popubWiodowTask(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.feedback_task, null, false);//引入弹窗布局

        TextView feedback_task = vPopupWindow.findViewById(R.id.feedback_task);
        ImageView coles_dialog = vPopupWindow.findViewById(R.id.coles_dialog);

        feedback_task.setText("举报成功！\n我们会尽快做出处理！");
        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
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


        //  popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移

        addBackgroundPopub(relievepopupWindow);
        relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

    private void addThereBackground(TaskDetailsDialog taskDetailsDialog) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样
        taskDetailsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                Intent intent = new Intent();
                setResult(100, intent);
                finish();
            }
        });
    }

    private void showAnimation() {
        //设置背景透明
        addBackground();
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

    }

    private void getReadydialog() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.unpublish_feedback, null, false);//引入弹窗布局
        if (popupWindow == null) {
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }

        //设置进出动画
        popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        parentView = LayoutInflater.from(MissionDetailActivity.this).inflate(R.layout.activity_task_details, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移

        dialogshowInfo(vPopupWindow);
    }

    private void addBackground() {
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


    private void addTooBackground() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        receiptSucDia.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                EventBus.getDefault().post(new ResultEvent("接单成功"));
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private NoScrollListview mListView;
    private UnpublishFeedbackAdapter mAdapter;
    private List<UnpublishFeedbackBean> mDatas;


    private void dialogshowInfo(View vPopupWindow) {
        if (mAdapter == null) {
            mListView = (NoScrollListview) vPopupWindow.findViewById(R.id.main_listView);
            cancel_reason = (EditText) vPopupWindow.findViewById(R.id.cancel_reason);
            btn_publish = (Button) vPopupWindow.findViewById(R.id.btn_publish);
            groupdeitText = (RelativeLayout) vPopupWindow.findViewById(R.id.groupdeitText);


            mAdapter = new UnpublishFeedbackAdapter(this, mDatas);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(this);
            TextView text_yuanyin = (TextView) vPopupWindow.findViewById(R.id.text_yuanyin);
            if (missionBean.getCreateUserId() == user.getUserId()) {
                if (orderState == null) {
                    text_yuanyin.setVisibility(View.GONE);
                    mDatas.add(new UnpublishFeedbackBean(true, "没有人接单"));
                    mDatas.add(new UnpublishFeedbackBean(false, "已解决"));
                    mDatas.add(new UnpublishFeedbackBean(false, "发布错误"));
                    mDatas.add(new UnpublishFeedbackBean(false, "其他"));

                } else {
                    text_yuanyin.setText("只能因为帮忙者的原因解除任务，且对方同意后赏金才可退回，其他情况请联系客服处理");
                }
            } else {
                String s_question = "只能选择自己的原因进行解除,若因为悬赏者的原因请点击举报。选择自己的原因解除后将扣除自己一分信誉分,信誉分低于95不能接单";
                SpannableStringBuilder style = new SpannableStringBuilder(s_question);

                style.setSpan(new ForegroundColorSpan(Color.BLACK), 49, s_question.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                text_yuanyin.setText(style);
            }

            mAdapter.notifyDataSetChanged();

        }

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == mDatas.size() - 1) {
                    if (cancel_reason.getText().toString().equals("")) {

                        ToastUtils.showTestShort(mActivity, "请填写原因");
                    } else {
                        popupWindow.dismiss();
                        reason = cancel_reason.getText().toString();
                        DialogUtil.showProgress(MissionDetailActivity.this);
                        if (missionBean.getCreateUserId() == user.getUserId()) {

                            if (orderState == null) {
                                presenter.taskRelievingCollection(user.getUserId() + "", missionBean.getOrderId() + "", missionBean.getBusinessId() + "", reason, 4);
                            } else {
                                presenter.taskRelievingCollection(user.getUserId() + "", missionBean.getOrderId() + "", missionBean.getBusinessId() + "", reason, 2);
                            }
                        } else {
                            presenter.taskRelievingCollection(user.getUserId() + "", missionBean.getOrderId() + "", missionBean.getBusinessId() + "", reason, 5);
                        }


                    }
                } else {
                    popupWindow.dismiss();
                    if (reason == null) {
                        reason = mDatas.get(0).getMsg();
                    }
                    DialogUtil.showProgress(MissionDetailActivity.this);
                    if (missionBean.getCreateUserId() == user.getUserId()) {

                        if (orderState == null) {
                            presenter.taskRelievingCollection(user.getUserId() + "", missionBean.getOrderId() + "", missionBean.getBusinessId() + "", reason, 4);
                        } else {
                            presenter.taskRelievingCollection(user.getUserId() + "", missionBean.getOrderId() + "", missionBean.getBusinessId() + "", reason, 2);
                        }
                    } else {
                        presenter.taskRelievingCollection(user.getUserId() + "", missionBean.getOrderId() + "", missionBean.getBusinessId() + "", reason, 5);
                    }


                }
            }
        });
    }

    private void popubBondTermination(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.bond_renwu, null, false);//引入弹窗布局


        ImageView colose_image = vPopupWindow.findViewById(R.id.colose_image);


        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        TextView withdraw = vPopupWindow.findViewById(R.id.withdraw);
        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                //  finish();
                //     popubReasonTermination(MissionDetailActivity.this);
            }
        });
        colose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                //   finish();
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();
                Intent intent1 = new Intent(MissionDetailActivity.this, ProblemFeedbackActivity.class);
                intent1.putExtra("type", 2);
                intent1.putExtra("businessId", missionBean.getBusinessId());
                intent1.putExtra("orderId", missionBean.getOrderId());
                startActivity(intent1);
//                DialogUtil.showProgress(MissionDetailActivity.this);
//                presenter.taskRelievingCollection(user.getUserId() + "", "", missionBean.getBusinessId() + "", reason, taskdetails);
            }
        });
        View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        addBackgroundPopub(relievepopupWindow);
        relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }


    private int position;

    private String reason;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;

        for (UnpublishFeedbackBean bean : mDatas) {//全部设为未选中
            bean.setChecked(false);
        }

        mDatas.get(position).setChecked(true);//点击的设为选中
        mAdapter.notifyDataSetChanged();

        if (position == mDatas.size() - 1) {
            groupdeitText.setVisibility(View.VISIBLE);
        } else {
            groupdeitText.setVisibility(View.GONE);
        }
        reason = mDatas.get(position).getMsg();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new ShareUiListener());
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, new ShareUiListener());
            }
        }
    }

    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            //分享成功
        }

        @Override
        public void onError(UiError uiError) {
            //分享失败
        }

        @Override
        public void onCancel() {
            //分享取消
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private void istributiondBounty(String id, final double price, final int position, final boolean taskorder, final int groupPosition) {

        MyAPP.getInstance().getApi().rewardAdd(id, user.getUserId(), businessId, price).enqueue(new Callback(callback, "获取任务详情失败") {

            @Override
            public void getSuc(String body) {

                relievepopupWindow.dismiss();
                popubBFountySuccessfully(MissionDetailActivity.this);
                amount.setText((Double.parseDouble(amount.getText().toString()) - price) + "");
                if (taskorder) {
                    commentsList.get(position).setRewardMoney(price + "");

                } else {
                    commentsList.get(groupPosition).getList().get(position - 1).setRewardMoney(price + "");
                    headerAndFooterWrapper.notifyDataSetChanged();
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    private void popubBFounty(Context context, final String id, final int position, final boolean taskorder, final int groupPosition) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.bounty_popub_distribution, null, false);//引入弹窗布局
        TextView allocation = vPopupWindow.findViewById(R.id.allocation);
        TextView unassign = vPopupWindow.findViewById(R.id.unassign);
        final EditText ed_distribution = vPopupWindow.findViewById(R.id.ed_distribution);
        ed_distribution.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        ed_distribution.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });

        relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        allocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissInput(vPopupWindow);
                relievepopupWindow.dismiss();
                istributiondBounty(id, Double.parseDouble(ed_distribution.getText().toString()), position, taskorder, groupPosition);
            }
        });

        unassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();

            }
        });
        View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        addBackgroundPopub(relievepopupWindow);
        relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }

    private void popubBFountySuccessfully(Context context) {
        popups_id.setVisibility(View.VISIBLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.bounty_chengon_distribution, null, false);//引入弹窗布局

        LinearLayout distribution = vPopupWindow.findViewById(R.id.distribution);
        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        distribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relievepopupWindow.dismiss();

            }
        });


        View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        addBackgroundPopub(relievepopupWindow);
        relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
    }


    private SecondaryAdapter secondaryAdapter;
    private int subscript = -2;


    @TargetApi(Build.VERSION_CODES.O)
    private void disableAutoFill() {
        getWindow().getDecorView().setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
    }

    private void secondLevel(final View vPopupWindow, final int groupPosition) {

        View inflate = LayoutInflater.from(this).inflate(R.layout.secondary_heard, null, true);

        RoundImageView comment_item_logo = (RoundImageView) inflate.findViewById(R.id.comment_item_logo);
        TextView comment_item_userName = (TextView) inflate.findViewById(R.id.comment_item_userName);
        TextView comment_item_time = (TextView) inflate.findViewById(R.id.comment_item_time);
        TextView comment_item_content = (TextView) inflate.findViewById(R.id.comment_item_content);

        detail_comment = vPopupWindow.findViewById(R.id.detail_comment);
        sendcomment = (TextView) vPopupWindow.findViewById(R.id.comment_btn_pop);


        detail_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) {
                    sendcomment.setTextColor(getResources().getColor(R.color.defaultsendColor));
                } else {
                    sendcomment.setTextColor(getResources().getColor(R.color.successsendColor));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        level_review = (RecyclerView) vPopupWindow.findViewById(R.id.level_review);
        level_review.setLayoutManager(new LinearLayoutManager(this));
        Glide.with(this).load(URL.getImgPath + commentsList.get(groupPosition).getHeadUrl()).into(comment_item_logo);
        comment_item_userName.setText(commentsList.get(groupPosition).getUserName());
        comment_item_time.setText(commentsList.get(groupPosition).getCreateTime());
        comment_item_content.setText(commentsList.get(groupPosition).getContent());
        commentsList.get(groupPosition).getList();
        secondaryAdapter = new SecondaryAdapter(MissionDetailActivity.this, commentsList.get(groupPosition).getList(), missionBean.getCreateUserId(), releaseTaskPopob, user.getUserId(), missionBean.getBusinessType(), missionBean.getPaymentType(), missionBean.getReceiverPhone());
        headerAndFooterWrapper = new HeaderAndFooterWrapper(secondaryAdapter);

        headerAndFooterWrapper.addHeaderView(inflate);
        level_review.setAdapter(headerAndFooterWrapper);

        secondaryAdapter.setOnItemClickListener(new SecondaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                showInput(detail_comment);
                subscript = position;
            }
        });
        secondaryAdapter.setOnDistributionItemClickListener(new SecondaryAdapter.OnItemDistributionClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (Double.parseDouble(amount.getText().toString()) > 0) {

                    popups_id.setVisibility(View.VISIBLE);
                    popubBFounty(MissionDetailActivity.this, commentsList.get(groupPosition).getList().get(position - 1).getId(), position, false, groupPosition);
                } else {
                    ToastUtils.showTestShort(MissionDetailActivity.this, "赏金已分配完");
                }

            }
        });
        sendcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detail_comment.getText().toString().equals("")) {
                    return;
                }
                if (subscript == -2) {
                    addCommentLevel(groupPosition);
                } else {
                    addCommentLevel(groupPosition);
                }
                dismissInput(vPopupWindow);
            }
        });
    }

    private void addSecondaryAdapter(final int groupPosition, String id) {
        ReplyBean.ReturnObjectBean.RowsBean.ListBean replyDetailBean = new ReplyBean.ReturnObjectBean.RowsBean.ListBean();
        replyDetailBean.setCreateTime(TimeUtils.currentTime());
        replyDetailBean.setHeadUrl(user.getHeadUrl());
        replyDetailBean.setUserName(user.getRealName());
        replyDetailBean.setContent(detail_comment.getText().toString());
        replyDetailBean.setUserId(user.getUserId());
        replyDetailBean.setUserPhone(user.getUserName());
        replyDetailBean.setId(id);
        if (subscript != -2) {
            replyDetailBean.setReplyName(commentsList.get(groupPosition).getList().get(subscript - 1).getUserName());
            replyDetailBean.setReplyUserId(commentsList.get(groupPosition).getList().get(subscript - 1).getUserId() + "");
            replyDetailBean.setUserPhone(commentsList.get(groupPosition).getList().get(subscript - 1).getUserPhone());
        }
        commentsList.get(groupPosition).getList().add(0, replyDetailBean);
        expandableListView.expandGroup(groupPosition);
        headerAndFooterWrapper.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        ToastUtils.showTestShort(this, "回复成功");
        detail_comment.setText("");
        subscript = -2;
    }

    private Integer replyUserId;

    private void addCommentLevel(final int groupPosition) {
        String cid = "";
        if (subscript >= 0) {
            cid = commentsList.get(groupPosition).getList().get(subscript - 1).getId();
            replyUserId = commentsList.get(groupPosition).getList().get(subscript - 1).getUserId();
        } else {
            cid = commentsList.get(groupPosition).getId();
            replyUserId = commentsList.get(groupPosition).getUserId();
        }
        MyAPP.getInstance().getApi().addcommentAddReply(user.getUserId(), cid, detail_comment.getText().toString(), replyUserId, commentsList.get(groupPosition).getId() + "").enqueue(new Callback(IModel.callback, "获取评论失败") {

            @Override
            public void getSuc(String body) {
                try {

                    JSONObject jsonObject = new JSONObject(body);
                    String id = jsonObject.getString("returnObject");
                    addSecondaryAdapter(groupPosition, id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

    }

    private void dismissInput(View view) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


    }

    private void popubWiodowReply(int groupPosition) {

        View vPopupWindow = getLayoutInflater().inflate(R.layout.activity_secondary, null, true);//引入弹窗布局

        RelativeLayout tvBack = vPopupWindow.findViewById(R.id.tvBack);
        TextView include_title = vPopupWindow.findViewById(R.id.include_title);
        popups_id = vPopupWindow.findViewById(R.id.popups_id);
        if (commentsList.get(groupPosition).getList() == null || commentsList.get(groupPosition).getList().size() == 0) {
            include_title.setText("暂无回复");
        } else {
            include_title.setText(commentsList.get(groupPosition).getList().size() + "条回复");
        }

        if (releaseTaskPopob == null) {
            releaseTaskPopob = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }
        secondLevel(vPopupWindow, groupPosition);
        releaseTaskPopob.setAnimationStyle(R.style.anim_pop_bottombar);
        releaseTaskPopob.setOutsideTouchable(true);
        releaseTaskPopob.setFocusable(true);
        releaseTaskPopob.showAtLocation(ll_share, Gravity.CENTER, 0, 0);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseTaskPopob.dismiss();
            }
        });


    }

    private void multitasking() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.multi_function_popub, null, false);
        TextView withdraw = inflate.findViewById(R.id.withdraw);
        TextView forward = inflate.findViewById(R.id.forward);
        TextView report = inflate.findViewById(R.id.report);

        if (missionBean.getCreateUserId() != user.getUserId()) {
            withdraw.setVisibility(View.GONE);
        }
        if (missionBean.getCreateUserId() == user.getUserId() && commentsList.size() > 0 && missionBean.getBusinessType().equals("1")) {
            withdraw.setVisibility(View.GONE);
        }
        if (orderState != null && "4".equals(orderState)) {
            withdraw.setVisibility(View.GONE);
        }
        if (missionBean.getBusinessType().equals("5")) {
            withdraw.setVisibility(View.GONE);
        }
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitasPopub.dismiss();
                PopWinShare popWinShare = new PopWinShare(1, MissionDetailActivity.this, "快来和我一起帮助他人，赚取赏金吧！", "悬赏" + price.getText().toString() + "元  " + et_content.getText().toString(), "http://a.app.qq.com/o/simple.jsp?pkgname=com.witkey.witkeyhelp");

                //引入依附的布局
                View parentView = LayoutInflater.from(MissionDetailActivity.this).inflate(R.layout.activity_task_details, null);
                //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;//调节透明度
                getWindow().setAttributes(lp);
                //dismiss时恢复原样
                popWinShare.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                popWinShare.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitasPopub.dismiss();
                showAnimation();
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MissionDetailActivity.this, ProblemFeedbackActivity.class);
                intent2.putExtra("type", 2);
                intent2.putExtra("businessId", missionBean.getBusinessId());
                intent2.putExtra("orderId", missionBean.getOrderId());
                startActivity(intent2);
                multitasPopub.dismiss();
            }
        });
        if (multitasPopub == null) {
            multitasPopub = new PopupWindow(inflate, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }

        inflate.findViewById(R.id.claner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multitasPopub.dismiss();
            }
        });

        //设置显示隐藏动画
        multitasPopub.setAnimationStyle(R.style.anim_pop_bottombar);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        multitasPopub.setBackgroundDrawable(dw);
        multitasPopub.setFocusable(true);
        multitasPopub.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void showMultitasking() {
        //设置背景透明
        addBackground();
        multitasPopub.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

    }
}
