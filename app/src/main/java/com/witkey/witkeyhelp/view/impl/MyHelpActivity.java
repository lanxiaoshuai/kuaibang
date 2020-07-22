package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MyHelpAdapter;
import com.witkey.witkeyhelp.adapter.MyReplyAdapter;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.MyReplyBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.UserTaskBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MyHelpActivity extends BaseActivity {
    private boolean aBoolean;
    private int pageNum = 1;
    private SmartRefreshLayout refreshLayout;
    private IModel.AsyncCallback callback;
    private List<UserTaskBean.ReturnObjectBean.RowsBean> mlist;
    private MyHelpAdapter myReplyAdapter;

    private int total;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_my_reply);
        setIncludeTitle("我的帮忙");

        refreshLayout = findViewById(R.id.refreshLayout);
        RecyclerView mPullLoadMoreRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mPullLoadMoreRecyclerView.setLayoutManager(linearLayoutManager);
        mlist = new ArrayList<>();
        myReplyAdapter = new MyHelpAdapter(this, mlist);
        mPullLoadMoreRecyclerView.setAdapter(myReplyAdapter);


        callback = new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {


            }

            @Override
            public void onError(Object data) {
                if (aBoolean) {
                    refreshLayout.finishLoadMore(false);//结束加载（加载失败）
                } else {
                    refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
                }
                ToastUtils.showTestShort(MyAPP.getInstance(), data.toString());
            }
        };
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                aBoolean = false;
                pageNum = 1;
                allGet();

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {


                aBoolean = true;

                int totalPage = total / 10;
                if (total % 10 != 0) {
                    totalPage += 1;
                }
                if (totalPage > pageNum) {
                    pageNum += 1;
                    allGet();
                } else {
                    refreshlayout.finishLoadMoreWithNoMoreData();
                }

            }
        });

        refreshLayout.autoRefresh();//自动刷新
    }

    private void allGet() {
        MyAPP.getInstance().getApi().businessReceivelist(user.getUserId(), pageNum, 10).enqueue(new Callback(callback, "获取评论失败") {


            @Override
            public void getSuc(String body) {

                UserTaskBean beanFromJson = JsonUtils.getBeanFromJson(body, UserTaskBean.class);
                UserTaskBean.ReturnObjectBean publishList = beanFromJson.getReturnObject();


                total = publishList.getTotal();
                if (aBoolean) {
                    refreshLayout.finishLoadMore(/*,false*/);//传入false表示刷新失败
                    mlist.addAll(publishList.getRows());
                } else {
                    refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
                    mlist.clear();
                    mlist.addAll(publishList.getRows());
                    //  ToastUtils.showTestShort(getActivity(), "刷新完成");
                }
                myReplyAdapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            aBoolean = false;
            pageNum = 1;
            allGet();
        }

    }
}
