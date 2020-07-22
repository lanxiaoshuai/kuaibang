package com.witkey.witkeyhelp.view.impl.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;


import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MissionRecyAdapter;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;

import com.witkey.witkeyhelp.presenter.IMyMissionPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MyMissionPresenterImpl;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMyMissionView;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.widget.HeaderAndFooterWrapper;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jie on 2019/11/27.
 */

public class ReleaseFragment extends BaseFragment implements IMyMissionView {


    private List<MissionBean> missionList;
    private PagingResponse missionResponse;
    private int page = 1;

    private IMyMissionPresenter presenter;
    private int state;//类型
    private MissionBean missionBean;
    private boolean isRelease; //是否为发布任务
    // private boolean isLoading;
    private String start;
    private MissionRecyAdapter adapter;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private boolean aBoolean;

    @Override
    protected int getContentView() {
        return R.layout.fragment_release;
    }

    @Override
    protected IPresenter[] getPresenters() {

        presenter = new MyMissionPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initEvent() {
        mPullLoadMoreRecyclerView.setFooterViewText("loading");

        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);

                aBoolean = false;
                getData();
            }

            @Override
            public void onLoadMore() {
                aBoolean = true;
                if (missionResponse != null) {
                    int totalPage = missionResponse.getTotal() / 10;
                    if (missionResponse.getTotal() % 10 != 0) {
                        totalPage += 1;
                    }
                    if (totalPage > page) {

                        page += 1;
                        allGet();
                    } else {

                        //  mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                            }
                        }, 1000);

                    }
                }
            }
        });
    }

    @Override
    protected void initViewExceptPresenter() {
        Bundle arguments = getArguments();
        start = arguments.getString("start");

        int userId = arguments.getInt("userId", 0);
        isRelease = getArguments().getBoolean("boo");

        missionBean = new MissionBean(userId, page, start);

        DialogUtil.showProgress(getActivity());




    }

    @Override
    protected void initWidget() {
        mPullLoadMoreRecyclerView = rootView.findViewById(R.id.pullLoadMoreRecyclerView);
        mPullLoadMoreRecyclerView.setLinearLayout();


        missionList = new ArrayList<>();
    }

    private HeaderAndFooterWrapper headerAndFooterWrapper;

    @Override
    public void showMissionList(PagingResponse missionResponse) {

        DialogUtil.dismissProgress();
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();


        this.missionResponse = missionResponse;

        if (aBoolean) {
            missionList.addAll(missionResponse.getRows());
        } else {
            missionList.clear();
            missionList.addAll(missionResponse.getRows());

        }
        if (adapter == null) {
            adapter = new MissionRecyAdapter(getActivity(), missionList,1);
//            headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
//            headerAndFooterWrapper.addFootView(getLayoutInflater().inflate(R.layout.add_foodview, null, false));
            mPullLoadMoreRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        showAdapter();

    }

    @Override
    public void onError(String error) {
        super.onError(error);
        DialogUtil.dismissProgress();
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
    }

    private void allGet() {

        if (isRelease) {
            missionBean.setPageNum(page);
            presenter.getReleaseMissionList(missionBean);
        } else {
            missionBean.setPageNum(page);
            presenter.getReceiveMissionList(missionBean);
        }
    }

    private void getData() {

        page = 1;
        allGet();
    }


    private void showAdapter() {


        adapter.setOnItemClickListener(new MissionRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), MissionDetailActivity.class);
                int businessId = missionList.get(position).getBusinessId();
                int orderId = missionList.get(position).getOrderId();

                if (isRelease) {
                    switch (start) {

                        case "1":
                            //任务发布进行中  传2
                            i.putExtra("EXTRA_BUSINESS_ID", businessId);
                            i.putExtra("TASKDETAILS", 2);
                            i.putExtra("ORDERID", orderId);

                            startActivity(i);
                            break;
                        case "2":
                            //任务已完成 传3
                            i.putExtra("EXTRA_BUSINESS_ID", businessId);
                            i.putExtra("TASKDETAILS", 3);
                            i.putExtra("ORDERID", orderId);
                            startActivityForResult(i, 100);

                            break;
                        case "3":
                            startActivityForResult(i, 100);
                            break;
                        case "4":
                            //任务已发布  传4
                            i.putExtra("EXTRA_BUSINESS_ID", businessId);
                            i.putExtra("TASKDETAILS", 4);
                            i.putExtra("ORDERID", orderId);
                            startActivityForResult(i, 100);
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (start) {
                        case "1":

                            //任务接单  传5
                            i.putExtra("EXTRA_BUSINESS_ID", businessId);
                            i.putExtra("TASKDETAILS", 5);
                            i.putExtra("ORDERID", orderId);
                            startActivityForResult(i, 100);
                            break;
                        case "2":
                            //任务提交  传6
                            i.putExtra("EXTRA_BUSINESS_ID", businessId);
                            i.putExtra("TASKDETAILS", 6);
                            i.putExtra("ORDERID", orderId);
                            startActivityForResult(i, 100);
                            break;
                        case "3":

                            //任务已完成  传6
                            i.putExtra("EXTRA_BUSINESS_ID", businessId);
                            i.putExtra("TASKDETAILS", 7);
                            i.putExtra("ORDERID", orderId);
                            startActivityForResult(i, 100);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();

            return;
        } else {  // 在最前端显示 相当于调用了onResume();
            //网络数据刷新

            getData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            aBoolean = false;
            getData();
        }
    }
}
