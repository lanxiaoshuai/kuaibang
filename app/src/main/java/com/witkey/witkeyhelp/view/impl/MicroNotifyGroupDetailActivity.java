package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.GroupMemberRecyAdapter;
import com.witkey.witkeyhelp.bean.MicroNotifyGroupMember;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.IMicroNotifyGroupDetailPresenter;
import com.witkey.witkeyhelp.presenter.impl.MicroNotifyGroupDetailPresenterImpl;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.InitRecyUtil;
import com.witkey.witkeyhelp.util.callback.ITextViewCallback;
import com.witkey.witkeyhelp.view.IMicroNotifyGroupDetailView;
import com.witkey.witkeyhelp.view.impl.base.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

public class MicroNotifyGroupDetailActivity extends BaseListActivity implements IMicroNotifyGroupDetailView {
    private ImageView iv_avatar;
    private TextView tv_mission_title;
    private TextView tv_mission_content;

    private RecyclerView manager_recylerview;

    private int catalog_id;

    private List<MicroNotifyGroupMember> groupMemberList;
    private PagingResponse groupMemberListResponse;
    private int page = 1;

    private boolean isLoading;
    private IMicroNotifyGroupDetailPresenter presenter;
    private LinearLayoutManager layoutManager;

    private GroupMemberRecyAdapter managerRecyAdapter;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
        catalog_id = argIntent.getIntExtra("EXTRA_CATALOG_ID", 0);
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
            if (groupMemberListResponse != null) {
                int totalPage = groupMemberListResponse.getTotal() / 10;
                if (groupMemberListResponse.getTotal() % 10 != 0) {
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

    private void allGet() {
        presenter.getGroupMember(catalog_id, page);
    }

    private void getData() {
        if (groupMemberList != null) {
            groupMemberList.clear();
            groupMemberList = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        page = 1;
        allGet();
    }

    @Override
    protected void onRefresh() {
        getData();
    }

    @Override
    protected int setRecyDividerHeight() {
        return 1;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MicroNotifyGroupDetailPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_mission_title = findViewById(R.id.tv_mission_title);
        tv_mission_content = findViewById(R.id.tv_mission_content);
        manager_recylerview = findViewById(R.id.rclView_manager);
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        setIncludeTitle("微通知");
        setShowConfirm("新增", new ITextViewCallback() {
            @Override
            public void onClick() {
                // TODO: 2019/8/7 新增成员
//                IntentUtil.startActivity(mActivity, AddLostFoundActivity.class);
            }
        });
        if (layoutManager == null) {
            layoutManager = InitRecyUtil.initHorListRecy(mActivity, manager_recylerview, 3);
        }
        getData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_micro_notify_group_detail;
    }

    @Override
    protected boolean isLight() {
        return true;
    }

    @Override
    public void showGroupMember(PagingResponse groupMemberListResponse) {
        getSuc();
        this.groupMemberListResponse = groupMemberListResponse;
        if (this.groupMemberListResponse != null) {
            if (isLoading) {
                groupMemberList.addAll(groupMemberListResponse.getRows());
                isLoading = false;
            } else {
                groupMemberList = groupMemberListResponse.getRows();
            }
            if (groupMemberListResponse.getTotal() != 0) {
                showBoss();
                showManagerAdapter();
                showAdapter();
            }
        }
    }

    private void showBoss() {
//        iv_avatar;
        MicroNotifyGroupMember microNotifyGroupMember = null;
        for (MicroNotifyGroupMember member : groupMemberList) {
            if (member.getType().equals("1")) {
                microNotifyGroupMember = member;
            }
        }
       // ImgUtil.loadImg(mActivity, microNotifyGroupMember.getHeadUrl(), iv_avatar);
        tv_mission_title.setText(microNotifyGroupMember.getRealName());
        tv_mission_content.setText(microNotifyGroupMember.getRemark());
    }

    private void showManagerAdapter() {
        ArrayList managerMemberList = getArrayList("2");
        if (managerRecyAdapter == null) {
            managerRecyAdapter = new GroupMemberRecyAdapter(mActivity, managerMemberList);
            manager_recylerview.setAdapter(managerRecyAdapter);
        } else {
            managerRecyAdapter.setData(managerMemberList);
            managerRecyAdapter.notifyDataSetChanged();
        }
    }

    @NonNull
    private ArrayList getArrayList(String type) {
        ArrayList managerMemberList = new ArrayList();
        for (MicroNotifyGroupMember member : groupMemberList) {
            if (member.getType().equals(type)) {
                managerMemberList.add(member);
            }
        }
        return managerMemberList;
    }

    private void showAdapter() {
        ArrayList MemberList = getArrayList("3");
        if (adapter == null) {
            adapter = new GroupMemberRecyAdapter(mActivity, MemberList);
            recyclerView.setAdapter(adapter);
        } else {
            ((GroupMemberRecyAdapter) adapter).setData(MemberList);
            adapter.notifyDataSetChanged();
        }

    }
}
