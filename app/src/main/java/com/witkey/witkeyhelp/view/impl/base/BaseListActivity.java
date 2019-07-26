package com.witkey.witkeyhelp.view.impl.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.BaseRecyAdapter;
import com.witkey.witkeyhelp.util.InitRecyUtil;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.ToolbarBaseActivity;

/**
 * Created by Administrator on 2017/3/29.
 */

public abstract class BaseListActivity extends ToolbarBaseActivity {
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;
    protected BaseRecyAdapter adapter;


    @Override
    protected void initEvent() {
        //通过recyclerView的onscrolllistener的监听来实现上拉加载更多的功能
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //滚动的三种状态包括SCROLL_STATE_IDEL 离开状态 SCROLL_STATE_DRAGGING 手指触摸 SCROLL_STATE_SETLING 加速滑动的时候
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("llx", "StateChanged = " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("llx", "onScrolled");
                // 获取最后一个可见条目
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (adapter != null) {
                    Log.d(TAG, "onScrolled: lastVisibleItemPosition=" + lastVisibleItemPosition + ",itemcount=" + adapter.getItemCount());
                    if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        Log.d("test", "loading executed");
                        //获取刷新状态
                        boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                        if (isRefreshing) {
                            Log.d(TAG, "isRefreshing: ");
                            adapter.notifyItemRemoved(adapter.getItemCount());
                            return;
                        }
                        onLoadMore();
                    }
                }
            }
        });
        //设置下拉刷新的监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isUseSwipeRefreshLayout()) {
                    BaseListActivity.this.onRefresh();
                }
            }
        });
    }

    /**
     * 加载更多
     */
    protected abstract void onLoadMore();

    /**
     * 刷新
     */
    protected abstract void onRefresh();

    /**
     * 成功
     */
    protected void getSuc() {
        DialogUtil.dismissProgress();
//        removeOtherView();
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 设置间隔
     */
    protected abstract int setRecyDividerHeight();

    @Override
    protected void initWidget() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.SwipeRefreshLayout);
        if (!isUseSwipeRefreshLayout()) {
            swipeRefreshLayout.setEnabled(false);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    protected void initViewExceptPresenter() {
        //设置加载进度的颜色变化值
        swipeRefreshLayout.setColorSchemeResources(R.color.swipeRefreshLayout_blueStatus, R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        //设置一进入开始刷新
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        if (layoutManager == null) {
            layoutManager = InitRecyUtil.initListRecy(mActivity, recyclerView, setRecyDividerHeight());
        }
    }

    protected boolean isUseSwipeRefreshLayout() {
        return true;
    }
}

