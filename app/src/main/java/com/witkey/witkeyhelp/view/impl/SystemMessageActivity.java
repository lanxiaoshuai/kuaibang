package com.witkey.witkeyhelp.view.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;

import com.witkey.witkeyhelp.adapter.SystemMessageRecyAdapter;

import com.witkey.witkeyhelp.bean.SystemMessageBean;
import com.witkey.witkeyhelp.event.AdvertisementEvent;

import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.SystemMessagePresenter;

import com.witkey.witkeyhelp.presenter.impl.SystemMessagePresenterImpl;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.SystemMessageView;
import com.witkey.witkeyhelp.view.impl.base.BaseListNoIntervalActivity;
import com.witkey.witkeyhelp.widget.RatingBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class SystemMessageActivity extends BaseListNoIntervalActivity implements SystemMessageView {


    private SystemMessagePresenter presenter;


    //获取的任务列表数据
    private SystemMessageBean.ReturnObjectBean missionResponse;
    private List<SystemMessageBean.ReturnObjectBean.RowsBean> missionList;

    private boolean isLoading = false;

    private int pageNum = 1;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new SystemMessagePresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
            if (missionResponse != null) {
                int totalPage = missionResponse.getTotal() / 10;
                if (missionResponse.getTotal() % 10 != 0) {
                    totalPage += 1;
                }
                if (totalPage > pageNum) {
                    pageNum += 1;
                    isLoading = true; // 在获取前修改状态
                    allGet();
                }
            }
        }
    }

    @Override
    protected void onRefresh() {
        pageNum = 1;
        allGet();
    }

    @Override
    protected int setRecyDividerHeight() {
        return 10;
    }

    private void showAdapter() {
        if (adapter == null) {
            adapter = new SystemMessageRecyAdapter(this, missionList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(missionList);
            adapter.notifyDataSetChanged();
        }
        SystemMessageRecyAdapter adapter = (SystemMessageRecyAdapter) this.adapter;

        adapter.setOnItemClickListener(new SystemMessageRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getPopubWindow();
            }
        });
    }

    private int ratingCount;

    private void getPopubWindow() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.dialog_evaluation, null, false);//引入弹窗布局

        final PopupWindow popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        //设置进出动画
        //     popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        View parentView = LayoutInflater.from(this).inflate(R.layout.activity_system_message, null);


        final RatingBar ratingBar = (RatingBar) vPopupWindow.findViewById(R.id.rating_bar0);
        ratingBar.setClickable(true);//设置可否点击
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {//点击星星变化后选中的个数
                SystemMessageActivity.this.ratingCount = (int) ratingCount;
            }
        });
        ImageView expression = vPopupWindow.findViewById(R.id.expression);
        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        final EditText evaluation_content = vPopupWindow.findViewById(R.id.evaluation_content);

        TextView to_evaluate = vPopupWindow.findViewById(R.id.to_evaluate);

        to_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (evaluation_content.getText().toString().equals("")) {
                    ToastUtils.showTestShort(SystemMessageActivity.this, "请输入内容");
                    return;
                }
                if (ratingCount <= 0) {
                    ToastUtils.showTestShort(SystemMessageActivity.this, "请给打分");
                }
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

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setIncludeTitle("系统消息");
    }

    private void allGet() {

        presenter.showSystemMessages(pageNum, 10, user.getUserId());


    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        allGet();
    }

    @Override
    public void showSystemMessages(SystemMessageBean.ReturnObjectBean missionResponse) {
        getSuc();
        this.missionResponse = missionResponse;
        if (missionResponse != null) {
            if (isLoading) {
                missionList.addAll(missionResponse.getRows());
                isLoading = false;
            } else {
                missionList = missionResponse.getRows();
            }
            showAdapter();
        }
    }

    @Override
    public void onError(String error) {
        ToastUtils.showTestShort(this, error);
        getSuc();

    }

    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(AdvertisementEvent event) {
        // 响应事件
//ToastUtils.showTestShort(this,"刷新成功");
        pageNum = 1;

        allGet();
    }
}
