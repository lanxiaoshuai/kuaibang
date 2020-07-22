package com.witkey.witkeyhelp.view.impl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MyPostAdapter;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.bean.MyPostBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/7/3.
 */

public class MyPostFragment extends BaseTexrFragment {
    private RefreshLayout refreshLayout;
    private View view;
    private RecyclerView mPullLoadMoreRecyclerView;
    private boolean aBoolean;
    private int pageNum = 1;
    //获取的任务列表数据
    private int missionResponse;
    private int arg_title;
    private List<MyPostBean.ReturnObjectBean.RowsBean> missionList;
    private MyPostAdapter myPostAdapter;
    private User user;
    private IModel.AsyncCallback callback;
    private String businessType;

    @Override
    public View initView() {

        view = getLayoutInflater().inflate(R.layout.fragment_mypost, null);
        return view;
    }

    @Override
    public void initData() {
        arg_title = getArguments().getInt("ARG_TITLE", 0);

        mPullLoadMoreRecyclerView = view.findViewById(R.id.pullLoadMoreRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mPullLoadMoreRecyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        user = SpUtils.getObject(getActivity(), "LOGIN");
        if (user == null) {
            return;
        }
        if (arg_title == 0) {
            businessType = null;
        } else {
            businessType = arg_title + "";
        }

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
                ToastUtils.showTestShort(getContext(), data.toString());
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
        missionList = new ArrayList<>();
        myPostAdapter = new MyPostAdapter(getContext(), missionList,this);
        mPullLoadMoreRecyclerView.setAdapter(myPostAdapter);
        refreshLayout.autoRefresh();//自动刷新

    }


    /**
     * 获取数据
     */
    private void allGet() {

        MyAPP.getInstance().getApi().userMyRelease(user.getUserId(), pageNum, 10, businessType).enqueue(new Callback(callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {
                MyPostBean beanFromJson = JsonUtils.getBeanFromJson(body, MyPostBean.class);

                List<MyPostBean.ReturnObjectBean.RowsBean> rows = beanFromJson.getReturnObject().getRows();
                missionResponse = beanFromJson.getReturnObject().getTotal();
                if (aBoolean) {
                    refreshLayout.finishLoadMore(/*,false*/);//传入false表示刷新失败
                    missionList.addAll(rows);
                } else {
                    refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
                    missionList.clear();
                    missionList.addAll(rows);

                }
                myPostAdapter.notifyDataSetChanged();
            }


        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            aBoolean = false;
            pageNum = 1;
            allGet();
        }
    }
}
