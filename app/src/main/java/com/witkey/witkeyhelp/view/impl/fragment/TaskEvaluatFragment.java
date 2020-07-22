package com.witkey.witkeyhelp.view.impl.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.donkingliang.labels.LabelsView;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.TaskEvaluationAdapter;
import com.witkey.witkeyhelp.bean.LabelBean;
import com.witkey.witkeyhelp.bean.MissionBean;

import com.witkey.witkeyhelp.bean.UserTaskBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.util.DoubleUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.ActivityLostFound;
import com.witkey.witkeyhelp.view.impl.TextActivity;
import com.witkey.witkeyhelp.widget.HeaderAndFooterWrapper;

import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/5/14.
 */

public class TaskEvaluatFragment extends BaseFragment {
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private int type;
    private String phone;
    private TaskEvaluationAdapter evaluationAdapter;
    private List<UserTaskBean.ReturnObjectBean.RowsBean> mlst;
    private TextView task_number;
    private TextView carry_out_number;
    private TextView lift_number;
    private TextView withdraw_number;
    private RelativeLayout withdraw_relayout;
    private PieChart mPieChart;
    private int title;
    private TextView carry_all;
    private PopupWindow popupWindow;
    private TextView task_name;

    private int missionResponse;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    private int pageNum = 1;

    @Override
    protected int getContentView() {
        return R.layout.taskevalutfragment;
    }

    @Override
    protected IPresenter[] getPresenters() {
        return new IPresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected void initEvent() {


    }

    @Override
    protected void initViewExceptPresenter() {

    }

    @Override
    protected void initWidget() {
        loadData();
        mPullLoadMoreRecyclerView = rootView.findViewById(R.id.pullLoadMoreRecyclerView);
        mPullLoadMoreRecyclerView.setLinearLayout();

        mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        mlst = new ArrayList<>();


        evaluationAdapter = new TaskEvaluationAdapter(getContext(), mlst, type);


        headerAndFooterWrapper = new HeaderAndFooterWrapper(evaluationAdapter);
        View inflate = getLayoutInflater().inflate(R.layout.shangxingtu, null);

        task_number = inflate.findViewById(R.id.task_number);
        carry_out_number = inflate.findViewById(R.id.carry_out_number);
        lift_number = inflate.findViewById(R.id.lift_number);
        withdraw_number = inflate.findViewById(R.id.withdraw_number);

        task_name = inflate.findViewById(R.id.task_name);

        carry_all = inflate.findViewById(R.id.carry_all);

        withdraw_relayout = inflate.findViewById(R.id.withdraw_relayout);

        headerAndFooterWrapper.addHeaderView(inflate);
        mPullLoadMoreRecyclerView.setAdapter(headerAndFooterWrapper);


        mPieChart = (PieChart) inflate.findViewById(R.id.barchart);


        // mPieChart.startAnimation();

        evaluationAdapter.setOnItemClickListener(new TaskEvaluationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getPopubWindow(position-1);
            }
        });




        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
//                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
//                aBoolean = false;
//                pageNum = 1;
//                loadFound();
//                if(type==0){
//                    MobclickAgent.onEvent(ActivityLostFound.this,"myRefresh");
//                }else {
//                    MobclickAgent.onEvent(ActivityLostFound.this,"foundRefresh");
//                }
            }

            @Override
            public void onLoadMore() {

             //   aBoolean = true;

                int totalPage = missionResponse / 10;
                if (missionResponse % 10 != 0) {
                    totalPage += 1;
                }
                if (totalPage > pageNum) {

                    pageNum += 1;
                    getData();
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        }
                    }, 1000);

                }

            }
        });
        getData();
    }

    private void loadData() {
        title = getArguments().getInt("title");

        type = getArguments().getInt("type");
        phone = getArguments().getString("phone");


    }

    private void getPopubWindow(final int position) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.hide_display_popub, null, false);//引入弹窗布局

        popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        //设置进出动画
        //     popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        View parentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_release, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移



        RelativeLayout hide_display_pop = vPopupWindow.findViewById(R.id.hide_display_pop);

        ImageView hide_display_icon = vPopupWindow.findViewById(R.id.hide_display_icon);
        TextView hide_display_content = vPopupWindow.findViewById(R.id.hide_display_content);



//        if(mlst.get(position).getIsHide()==0){
//            hide_display_content.setText("隐藏这条悬赏                                  ");
//
//        }else {
//            hide_display_content.setText("显示这条悬赏                                  ");
//        }
        hide_display_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//          if(mlst.get(position).getIsHide()==0){
//              isHide(mlst.get(position).getBusinessId(), 1, position);
//          }else {
//              isHide(mlst.get(position).getBusinessId(), 0, position);
//          }

            }
        });


        addBackground(popupWindow);
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

    }

    private void addBackground(PopupWindow popupWindow) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getActivity().getWindow().setAttributes(lp);
        //dismiss时恢复原样

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    private void getData() {
        if (title == 0) {
            task_name.setText("发布任务数量");
            MyAPP.getInstance().getApi().businessPublishList(phone, pageNum, 10).enqueue(new Callback(IModel.callback, "获取标签失败") {
                @Override
                public void getSuc(String body) {
//                    UserTaskBean beanFromJson = JsonUtils.getBeanFromJson(body, UserTaskBean.class);
//                    missionResponse = beanFromJson.getReturnObject().getPublishList().getTotal();
//                    List<UserTaskBean.ReturnObjectBean.PublishListBean.RowsBean> rows = beanFromJson.getReturnObject().getPublishList().getRows();
//                    mlst.addAll(rows);
//                    headerAndFooterWrapper.notifyDataSetChanged();
//
//
//                    task_number.setText(beanFromJson.getReturnObject().getPublishCount() + "");
//
//                    carry_out_number.setText(beanFromJson.getReturnObject().getPublishFinish() + "");
//
//                    lift_number.setText(beanFromJson.getReturnObject().getPublishRelieve() + "");
//
//                    withdraw_number.setText(beanFromJson.getReturnObject().getPublicshBack() + "");
//                    if (beanFromJson.getReturnObject().getPublishFinish() == 0) {
//
//                        carry_all.setText("0%");
//
//                        mPieChart.addPieSlice(new PieModel("Freetime", beanFromJson.getReturnObject().getPublishFinish(), Color.parseColor("#82E1B8")));
//
//
//                    } else {
//                        carry_all.setText(new Double(DoubleUtil.mul(DoubleUtil.div( beanFromJson.getReturnObject().getPublishFinish(),beanFromJson.getReturnObject().getPublishCount()),100)).intValue() + "%");
//
//
//                        mPieChart.addPieSlice(new PieModel("Freetime", beanFromJson.getReturnObject().getPublishFinish(), Color.parseColor("#82E1B8")));
//                        mPieChart.addPieSlice(new PieModel("Sleep", beanFromJson.getReturnObject().getPublishRelieve(), Color.parseColor("#FFBB00")));
//                        mPieChart.addPieSlice(new PieModel("Withdraw", beanFromJson.getReturnObject().getPublicshBack(), Color.parseColor("#FF98B1")));
//
//                    }
                }
            });
        } else if (title == 1) {
            task_name.setText("领取任务数量");
//            MyAPP.getInstance().getApi().businessReceivelist(phone, pageNum, 10).enqueue(new Callback(IModel.callback, "获取标签失败") {
//                @Override
//                public void getSuc(String body) {
//                    UserTaskBean beanFromJson = JsonUtils.getBeanFromJson(body, UserTaskBean.class);
//                    int publicshBack = beanFromJson.getReturnObject().getPublicshBack();
//                    List<UserTaskBean.ReturnObjectBean.PublishListBean.RowsBean> rows = beanFromJson.getReturnObject().getPublishList().getRows();
//                    mlst.addAll(rows);
//                    headerAndFooterWrapper.notifyDataSetChanged();
//
//                    //  if (publicshBack == 1) {
//
//
//                    task_number.setText(beanFromJson.getReturnObject().getReceiveCount() + "");
//                    carry_out_number.setText(beanFromJson.getReturnObject().getReceiveFinish() + "");
//                    lift_number.setText(beanFromJson.getReturnObject().getReceiveRelieve() + "");
//                    //  withdraw_number.setText(beanFromJson.getReturnObject().getPublicshBack());
//                    withdraw_relayout.setVisibility(View.GONE);
//                    if (beanFromJson.getReturnObject().getReceiveFinish() == 0) {
//                        mPieChart.addPieSlice(new PieModel("Freetime", beanFromJson.getReturnObject().getReceiveFinish(), Color.parseColor("#82E1B8")));
//
//                        carry_all.setText("0%");
//                    } else {
//
//
//                        carry_all.setText(new Double(DoubleUtil.mul(DoubleUtil.div(beanFromJson.getReturnObject().getReceiveFinish(), beanFromJson.getReturnObject().getReceiveCount()), 100)).intValue()+ "%");
//
//                        mPieChart.addPieSlice(new PieModel("Freetime", beanFromJson.getReturnObject().getReceiveFinish(), Color.parseColor("#82E1B8")));
//                        mPieChart.addPieSlice(new PieModel("Sleep", beanFromJson.getReturnObject().getReceiveRelieve(), Color.parseColor("#FFBB00")));
//                    }
//                    //   }
//                }
//            });
        }


    }

    private void isHide(final int businessId,final int isHide, final int position) {
        DialogUtil.showProgress(getActivity());
        MyAPP.getInstance().getApi().businessIsHide(businessId, isHide).enqueue(new Callback(IModel.callback, "隐藏显示任务失败") {
            @Override
            public void getSuc(String body) {
                DialogUtil.dismissProgress();
              //  mlst.get(position).setIsHide(isHide);
                headerAndFooterWrapper.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
    }

}
