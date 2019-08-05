package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MessageRecyAdapter;
import com.witkey.witkeyhelp.bean.Message;
import com.witkey.witkeyhelp.bean.MessageResponse;
import com.witkey.witkeyhelp.bean.MicroNotificationBean;
import com.witkey.witkeyhelp.presenter.IMicroNotificationDetailPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MicroNotificationDetailPresenterImpl;
import com.witkey.witkeyhelp.util.callback.ITextViewCallback;
import com.witkey.witkeyhelp.view.IMicroNotificationDetailView;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;

import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/26 18:45
 * @description 微通知detail
 */
public class MicroNotificationDetailActivity extends BaseListActivity implements IMicroNotificationDetailView {

    private List<Message> messageList;
    private MessageResponse messageResponse;
    private int page = 1;

    private IMicroNotificationDetailPresenter presenter;
    private boolean isLoading;

    private ImageView iv_avatar;
    private TextView tv_title;
    private TextView tv_content;
    private TextView tv_date;
    private TextView tv_btn_manage;
    private RadioGroup rg_choose;
    private RadioButton rb_check;
    private RadioButton rb_uncheck;
    private MicroNotificationBean microNotificationBean;
    private boolean isCheck;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
        microNotificationBean = (MicroNotificationBean) argIntent.getSerializableExtra("EXTRA_MICRO_NOTIFICATION_BEAN");
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
            if (messageResponse != null) {
                int totalPage = messageResponse.getTotal() / 10;
                if (messageResponse.getTotal() % 10 != 0) {
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
        if (messageList != null) {
            messageList.clear();
            messageList = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        page = 1;
        allGet();
    }

    private void allGet() {
        presenter.getMicroNotificationDetail(isCheck);
    }

    @Override
    protected int setRecyDividerHeight() {
        return 1;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MicroNotificationDetailPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_micro_notification_detail;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        tv_btn_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/8/5 跳转管理页面
            }
        });
        rg_choose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_check:
                        isCheck = true;
                        break;
                    case R.id.rb_uncheck:
                        isCheck = false;
                        break;
                }
                allGet();
            }
        });
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_title = findViewById(R.id.tv_title);
        tv_content = findViewById(R.id.tv_content);
        tv_date = findViewById(R.id.tv_date);
        tv_btn_manage = findViewById(R.id.tv_btn_manage);
        rg_choose = findViewById(R.id.rg_choose);
    }

    private void showAdapter() {
        if (adapter == null) {
            adapter = new MessageRecyAdapter(mActivity, messageList);
            recyclerView.setAdapter(adapter);
        } else {
            ((MessageRecyAdapter) adapter).setData(messageList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        setIncludeTitle("微通知");
        setShowConfirm("钻石通知", new ITextViewCallback() {
            @Override
            public void onClick() {
//                IntentUtil.startActivity(mActivity, AddMicroNotificationActivity.class);
            }
        });
//        iv_avatar
        tv_title.setText(microNotificationBean.getTitle());
        tv_content.setText(microNotificationBean.getContent());
        tv_date.setText(microNotificationBean.getDate());
        getData();
    }

    @Override
    public void showMicroNotificationDetail(MessageResponse messageList) {
        getSuc();
        this.messageResponse = messageList;
        if (this.messageResponse != null) {
            if (isLoading) {
                this.messageList.addAll(messageList.getRows());
                isLoading = false;
            } else {
                this.messageList = messageList.getRows();
            }
            showAdapter();
        }
    }

    @Override
    protected boolean isLight() {
        return true;
    }
}
