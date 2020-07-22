package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.LostFoundRecyAdapter;
import com.witkey.witkeyhelp.adapter.MissionRecyAdapter;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.presenter.IMCollectionPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.IMCollectionPresenterImpl;
import com.witkey.witkeyhelp.presenter.impl.IMicroNotificationPresenter;
import com.witkey.witkeyhelp.presenter.impl.MicroNotificationPresenterImpl;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMCollectionView;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

import java.util.List;

/**
 * Created by jie on 2019/11/28.
 */

public class CollectionActivity extends BaseListActivity implements IMCollectionView {

    private IMCollectionPresenter presenter;


    //控制数据的变量
    private MissionBean missionBean;
    private String searchKeyWord;

    private MissionBean chooseMissionBean;

    //获取的任务列表数据
    private PagingResponse missionResponse;
    private List<MissionBean> missionList;

    private boolean isLoading = false;

    private int pageNum = 1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collection;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new IMCollectionPresenterImpl();
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
        allGet();
    }

    @Override
    protected int setRecyDividerHeight() {
        return 10;
    }

    @Override
    public void showCollectionDetail(PagingResponse missionResponse) {
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

    private void showAdapter() {
        if (adapter == null) {
            adapter = new MissionRecyAdapter(this, missionList,0);
            recyclerView.setAdapter(adapter);
        } else {
            ((MissionRecyAdapter) adapter).setData(missionList);
            adapter.notifyDataSetChanged();
        }
        MissionRecyAdapter missadapter = (MissionRecyAdapter) this.adapter;
        missadapter.setOnItemClickListener(new MissionRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(CollectionActivity.this, MissionDetailActivity.class);
                MissionBean missionBean = missionList.get(position);

                i.putExtra("EXTRA_BUSINESS_ID", missionBean.getBusinessId());
                i.putExtra("TASKDETAILS", 6);
                startActivity(i);

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
        setIncludeTitle("我的收藏");

        DialogUtil.showProgress(mActivity);
    }

    private void allGet() {
        if (user != null) {
            presenter.showCollection(pageNum, 10, user.getUserId());
        } else {
            Log.e("tag", "未登录");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        allGet();
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();

    }
}
