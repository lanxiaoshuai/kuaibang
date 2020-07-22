package com.witkey.witkeyhelp.view.impl;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MyReplyAdapter;
import com.witkey.witkeyhelp.bean.MyReplyBean;
import com.witkey.witkeyhelp.bean.ReplyBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.TimeUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyReplyActivity extends BaseActivity {
    private boolean aBoolean;
    private int pageNum = 1;
    private SmartRefreshLayout refreshLayout;
    private IModel.AsyncCallback callback;
    private List<MyReplyBean.ReturnObjectBean.RowsBean> mlist;
    private MyReplyAdapter myReplyAdapter;
    private int missionResponse;
    private boolean reply;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_my_reply);

        reply = getIntent().getBooleanExtra("reply", false);
        if(reply){
            setIncludeTitle("回复我的");
        }else {
            setIncludeTitle("我的回复");
        }
        refreshLayout = findViewById(R.id.refreshLayout);
        RecyclerView mPullLoadMoreRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mPullLoadMoreRecyclerView.setLayoutManager(linearLayoutManager);
        mlist = new ArrayList<>();
        myReplyAdapter = new MyReplyAdapter(this, mlist,reply);
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

                int totalPage = missionResponse / 10;
                if (missionResponse % 10 != 0) {
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
        if (reply){
            MyAPP.getInstance().getApi().commentReplyMe(user.getUserId(), pageNum, 10).enqueue(new Callback(callback, "获取评论失败") {

                @Override
                public void getSuc(String body) {
                    MyReplyBean beanFromJson = JsonUtils.getBeanFromJson(body, MyReplyBean.class);

                    missionResponse = beanFromJson.getReturnObject().getTotal();
                    if (aBoolean) {
                        refreshLayout.finishLoadMore(/*,false*/);//传入false表示刷新失败
                        mlist.addAll(beanFromJson.getReturnObject().getRows());
                    } else {
                        refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
                        mlist.clear();
                        mlist.addAll(beanFromJson.getReturnObject().getRows());
                        //  ToastUtils.showTestShort(getActivity(), "刷新完成");
                    }
                    myReplyAdapter.notifyDataSetChanged();
                }

            });
        }else {
            MyAPP.getInstance().getApi().commentMyReply(user.getUserId(), pageNum, 10).enqueue(new Callback(callback, "获取评论失败") {

                @Override
                public void getSuc(String body) {
                    MyReplyBean beanFromJson = JsonUtils.getBeanFromJson(body, MyReplyBean.class);

                    missionResponse = beanFromJson.getReturnObject().getTotal();
                    if (aBoolean) {
                        refreshLayout.finishLoadMore(/*,false*/);//传入false表示刷新失败
                        mlist.addAll(beanFromJson.getReturnObject().getRows());
                    } else {
                        refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
                        mlist.clear();
                        mlist.addAll(beanFromJson.getReturnObject().getRows());
                        //  ToastUtils.showTestShort(getActivity(), "刷新完成");
                    }
                    myReplyAdapter.notifyDataSetChanged();
                }

            });
        }

    }

    @Override
    protected boolean isGetUser() {
        return true;
    }
}
