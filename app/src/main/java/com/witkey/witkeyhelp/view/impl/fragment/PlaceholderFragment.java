package com.witkey.witkeyhelp.view.impl.fragment;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.Interfacecallback.Modifystate;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.ConditionAdapter;
import com.witkey.witkeyhelp.adapter.MissionRecyAdapter;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.UnpublishFeedbackBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.event.ResultEvent;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.IReawardHallFragPresenter;
import com.witkey.witkeyhelp.presenter.impl.ReawardHallFragPresenterImpl;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.view.IReawardHallFragView;
import com.witkey.witkeyhelp.view.impl.ActivityShare;
import com.witkey.witkeyhelp.view.impl.LoginActivity;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.widget.HeaderAndFooterWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Egos on 2017/11/11.
 */

public class PlaceholderFragment extends BaseTexrFragment implements IReawardHallFragView, Modifystate {
    private static final String ARG_TITLE = "section_number";
    private TextView cicleposition;
    private PopupWindow filterPopup;
    private ConditionAdapter primaryAdapter;
    private ConditionAdapter intermediateAdapter;
    private ConditionAdapter advancedAdapter;
    private TextView filter_text;

    private Set<IPresenter> mAllPresenters = new HashSet<>(1);

    //控制数据的变量
    private MissionBean missionBean;

    private int pageNum = 1;


    private MissionRecyAdapter adapter;

    private RecyclerView mPullLoadMoreRecyclerView;

    private List<MissionBean> missionList;

    private boolean aBoolean;

    //获取的任务列表数据
    private PagingResponse missionResponse;

    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private View constraintLayout;
    private List<UnpublishFeedbackBean> browseDemand;
    private ConditionAdapter browseAdapter;
    private View view;
    private String circleId;
    private User user;
    private RefreshLayout refreshLayout;


    @Override
    public View initView() {
        view = getLayoutInflater().inflate(R.layout.fragment_main, null);
        //Sliding();

        if (getArguments().getBoolean("SEARCH")) {
            circleId = getArguments().getString(ARG_TITLE);


            loadData();
        }else {

        }
        return view;
    }

    @Override
    public void initData() {
        circleId = getArguments().getString(ARG_TITLE);
        loadData();

    }

    public void loadData() {
        CircleFragment parentFragment = (CircleFragment) getParentFragment();

        if(parentFragment!=null){
            parentFragment.setCallBack(this);
        }


        user = SpUtils.getObject(getActivity(), "LOGIN");

        addPresenters();
        Sliding();


        boolean miui = isMIUI();
        if (miui) {
            boolean oPen = GPSIsOpenUtil.isOPen(getActivity());
            if (oPen) {
                getLocationClient();
            } else {
                showLocationMissingPermissionDialog();
            }
        } else {
            getLocationClient();
        }

    }


    private void Sliding() {


        missionBean = new MissionBean();
        missionBean.setPageNum(pageNum);

        mPullLoadMoreRecyclerView = view.findViewById(R.id.pullLoadMoreRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mPullLoadMoreRecyclerView.setLayoutManager(linearLayoutManager);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                aBoolean = false;
                pageNum = 1;
                allGet();
                MobclickAgent.onEvent(getActivity(), "rewardHallRefresh");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {

                MobclickAgent.onEvent(getActivity(), "rewardHallLoadMore");
                aBoolean = true;
                if (missionResponse != null) {
                    int totalPage = missionResponse.getTotal() / 10;
                    if (missionResponse.getTotal() % 10 != 0) {
                        totalPage += 1;
                    }
                    if (totalPage > pageNum) {
                        pageNum += 1;
                        allGet();
                    } else {
                        refreshlayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }
        });


        missionList = new ArrayList<>();

        adapter = new MissionRecyAdapter(getActivity(), missionList, 0);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.ciclerecyview, null, false);
        cicleposition = inflate.findViewById(R.id.cicleposition);
        filter_text = inflate.findViewById(R.id.filter_text);

        cicleposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ActivityShare.class);
                intent.putExtra("city", city);
                intent.putExtra("province", province);
                startActivityForResult(intent, 100);

            }
        });
        //筛选
        getReadydialog();
        filter_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                display();
            }
        });

        //    headerAndFooterWrapper.addFootView(getLayoutInflater().inflate(R.layout.add_foodview, null, false));
        headerAndFooterWrapper.addHeaderView(inflate);
        mPullLoadMoreRecyclerView.setAdapter(headerAndFooterWrapper);

        refreshLayout.autoRefresh();//自动刷新

    }

    private void addPresenters() {
        IPresenter[] presenters = getPresenters();
        if (presenters != null) {
            for (int i = 0; i < presenters.length; i++) {
                mAllPresenters.add(presenters[i]);
            }
        }
        presenter.init(this);
    }

    private IReawardHallFragPresenter presenter;

    protected IPresenter[] getPresenters() {
        presenter = new ReawardHallFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    /**
     * 获取数据
     */
    private void allGet() {


        missionBean.setPageNum(pageNum);
        presenter.getMissionList(missionBean, circleId);
        // TODO: 2019/7/9 显示刷新
    }

    private UICustomDialog2 dialog2;

    private void showLocationMissingPermissionDialog() {
        if (dialog2 == null) {
            dialog2 = new UICustomDialog2(getActivity(), "GPS未打开,去开启", "3");
            dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog2.hide();
                    getLocationClient();
                }
            });
            dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog2.hide();
                    startAppSettings();
                }
            });
            dialog2.show();
        }

    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private int GPS_REQUEST_CODE = 10;

    protected void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, GPS_REQUEST_CODE);
    }


    private String city = "";
    private String province = "";

    private double latitude;
    private double longitude;

    public AMapLocation aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void getLocationClient() {

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    city = aMapLocation.getCity();
                    province = aMapLocation.getProvince();
                    aMapLocation.getAddress();
                    String aoiName = aMapLocation.getAoiName();
                    if (aoiName == null || aoiName.equals("")) {
                        cicleposition.setText(aMapLocation.getPoiName() + "");
                    } else {
                        cicleposition.setText(aoiName + "");
                    }
                    missionBean.setLatitude(aMapLocation.getLatitude() + "");
                    missionBean.setLongitude(aMapLocation.getLongitude() + "");
                } else {
                    cicleposition.setText("定位失败");
                }
                aBoolean = false;
                pageNum = 1;
                allGet();
            }
        };
//初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();


        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();


    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;

        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("huawei".equalsIgnoreCase(manufacturer) || "Xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case 10:
                dialog2.hide();
                getLocationClient();
                break;
            case 100:
                if (data != null) {
                    if (data.getStringExtra("name") != null) {
                        cicleposition.setText(data.getStringExtra("name"));
                        city = data.getStringExtra("city");

                        latitude = data.getDoubleExtra("latitude", 0);
                        longitude = data.getDoubleExtra("longitude", 0);
                        aBoolean = false;
                        allGet();

                    }
                }
                break;


        }

    }


    private List<UnpublishFeedbackBean> primaryDemand;
    private List<UnpublishFeedbackBean> intermediateDemand;
    private List<UnpublishFeedbackBean> advancedDemand;

    private void getReadydialog() {


        View vPopupWindow = getLayoutInflater().inflate(R.layout.condition_popubwindow, null, false);//引入弹窗布局
        if (filterPopup == null) {
            filterPopup = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }
        //设置进出动画
        filterPopup.setAnimationStyle(R.style.PopupTopAnim);

        //引入依附的布局
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        constraintLayout = view.findViewById(R.id.constraintLayout);
        // addBackgroundPopub(filterPopup);

        ImageView colose_screen = vPopupWindow.findViewById(R.id.colose_screen);
        RelativeLayout remaining_display = vPopupWindow.findViewById(R.id.remaining_display);

        colose_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterPopup.dismiss();
            }
        });

        TextView carry_out = vPopupWindow.findViewById(R.id.carry_out);

        TextView reset = vPopupWindow.findViewById(R.id.reset);

        TextView browse_circles_text = vPopupWindow.findViewById(R.id.browse_circles_text);


        remaining_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPopup.dismiss();
            }
        });

        GridView screening_criteria = vPopupWindow.findViewById(R.id.screening_criteria);

        GridView browse_content = vPopupWindow.findViewById(R.id.browse_content);

        GridView reward_type = vPopupWindow.findViewById(R.id.reward_type);

        GridView browse_circles = vPopupWindow.findViewById(R.id.browse_circles);

        if ("0".equals(circleId)) {
            browse_circles.setVisibility(View.VISIBLE);
            browse_circles_text.setVisibility(View.VISIBLE);
        } else {
            browse_circles.setVisibility(View.GONE);
            browse_circles_text.setVisibility(View.GONE);
        }
        primaryDemand = new ArrayList<>();

        intermediateDemand = new ArrayList<>();

        advancedDemand = new ArrayList<>();

        browseDemand = new ArrayList<>();

        primaryDemand.add(new UnpublishFeedbackBean(true, "发布时间"));

        primaryDemand.add(new UnpublishFeedbackBean(false, "距离近"));

        primaryDemand.add(new UnpublishFeedbackBean(false, "价格降序"));

        primaryDemand.add(new UnpublishFeedbackBean(false, "价格升序"));


        intermediateDemand.add(new UnpublishFeedbackBean(true, "全部"));

        intermediateDemand.add(new UnpublishFeedbackBean(false, "帮忙"));

        intermediateDemand.add(new UnpublishFeedbackBean(false, "信息咨询"));

        advancedDemand.add(new UnpublishFeedbackBean(true, "全部"));

        advancedDemand.add(new UnpublishFeedbackBean(false, "人民币"));

        advancedDemand.add(new UnpublishFeedbackBean(false, "钻石"));

        browseDemand.add(new UnpublishFeedbackBean(true, "全部"));

        browseDemand.add(new UnpublishFeedbackBean(false, "我关注的圈子"));


        primaryAdapter = new ConditionAdapter(getContext(), primaryDemand);

        screening_criteria.setAdapter(primaryAdapter);

        intermediateAdapter = new ConditionAdapter(getContext(), intermediateDemand);

        browse_content.setAdapter(intermediateAdapter);

        advancedAdapter = new ConditionAdapter(getContext(), advancedDemand);

        reward_type.setAdapter(advancedAdapter);

        browseAdapter = new ConditionAdapter(getContext(), browseDemand);

        browse_circles.setAdapter(browseAdapter);
        screening_criteria.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        for (UnpublishFeedbackBean bean : primaryDemand) {//全部设为未选中
                            bean.setChecked(false);
                        }

                        primaryDemand.get(position).setChecked(true);//点击的设为选中
                        primaryAdapter.notifyDataSetChanged();
                    }
                }
        );
        browse_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (UnpublishFeedbackBean bean : intermediateDemand) {//全部设为未选中
                    bean.setChecked(false);
                }

                intermediateDemand.get(position).setChecked(true);//点击的设为选中
                intermediateAdapter.notifyDataSetChanged();
            }
        });
        reward_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (UnpublishFeedbackBean bean : advancedDemand) {//全部设为未选中
                    bean.setChecked(false);
                }

                advancedDemand.get(position).setChecked(true);//点击的设为选中
                advancedAdapter.notifyDataSetChanged();
            }
        });
        browse_circles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (user == null) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                }
                for (UnpublishFeedbackBean bean : browseDemand) {//全部设为未选中
                    bean.setChecked(false);
                }

                browseDemand.get(position).setChecked(true);//点击的设为选中
                browseAdapter.notifyDataSetChanged();
            }
        });
        carry_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < primaryDemand.size(); i++) {
                    if (primaryDemand.get(i).isChecked()) {
                        String msg = primaryDemand.get(i).getMsg();
                        switch (msg) {
                            case "发布时间":
                                missionBean.setMflag("4");
                                break;
                            case "距离近":
                                missionBean.setMflag("3");
                                break;
                            case "价格降序":
                                missionBean.setMflag("2");
                                break;
                            case "价格升序":
                                missionBean.setMflag("1");
                                break;
                        }
                        break;
                    }

                }

                for (int i = 0; i < intermediateDemand.size(); i++) {
                    if (intermediateDemand.get(i).isChecked()) {
                        String msg = intermediateDemand.get(i).getMsg();
                        switch (msg) {
                            case "全部":
                                missionBean.setBusinessType("");
                                break;
                            case "咨询":
                                missionBean.setBusinessType("1");
                                break;
                            case "帮忙":
                                missionBean.setBusinessType("2");
                                break;

                        }
                        break;
                    }

                }

                for (int i = 0; i < advancedDemand.size(); i++) {
                    if (advancedDemand.get(i).isChecked()) {
                        String msg = advancedDemand.get(i).getMsg();
                        switch (msg) {
                            case "全部":
                                missionBean.setPaymentType("");
                                break;
                            case "人民币":
                                missionBean.setPaymentType("1");
                                break;
                            case "钻石":
                                missionBean.setPaymentType("2");
                                break;
                        }
                        break;
                    }
                }

                for (int i = 0; i < browseDemand.size(); i++) {
                    if (browseDemand.get(i).isChecked()) {
                        String msg = browseDemand.get(i).getMsg();
                        switch (msg) {
                            case "全部":
                                missionBean.setCreateUserId(0);
                                break;
                            case "我关注的圈子":
                                missionBean.setCreateUserId(user.getUserId());
                                break;

                        }
                        break;
                    }
                }
                aBoolean = false;
                pageNum = 1;
                allGet();
                filterPopup.dismiss();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primaryDemand.set(0, new UnpublishFeedbackBean(true, "发布时间"));

                primaryDemand.set(1, new UnpublishFeedbackBean(false, "距离近"));

                primaryDemand.set(2, new UnpublishFeedbackBean(false, "价格降序"));

                primaryDemand.set(3, new UnpublishFeedbackBean(false, "价格升序"));

                intermediateDemand.set(0, new UnpublishFeedbackBean(true, "全部"));

                intermediateDemand.set(1, new UnpublishFeedbackBean(false, "帮忙"));

                intermediateDemand.set(2, new UnpublishFeedbackBean(false, "信息咨询"));

                advancedDemand.set(0, new UnpublishFeedbackBean(true, "全部"));

                advancedDemand.set(1, new UnpublishFeedbackBean(false, "人民币"));

                advancedDemand.set(2, new UnpublishFeedbackBean(false, "钻石"));

                browseDemand.set(0, new UnpublishFeedbackBean(true, "全部"));

                browseDemand.set(1, new UnpublishFeedbackBean(false, "我关注的圈子"));

                primaryAdapter.notifyDataSetChanged();
                intermediateAdapter.notifyDataSetChanged();
                advancedAdapter.notifyDataSetChanged();
                browseAdapter.notifyDataSetChanged();
                missionBean.setMflag("4");

                missionBean.setBusinessType("");
                missionBean.setPaymentType("");
                missionBean.setCreateUserId(0);
                aBoolean = false;
                pageNum = 1;
                allGet();
                filterPopup.dismiss();
            }
        });
    }


    @Override
    public void onError(String error) {
        if (aBoolean) {
            refreshLayout.finishLoadMore(false);//结束加载（加载失败）
        } else {
            refreshLayout.finishRefresh(false);//结束刷新（刷新失败）
        }


        // ToastUtils.showTestShort(getActivity(), error);
    }

    @Override
    public void showMissionList(PagingResponse missionResponse) {

        mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
        this.missionResponse = missionResponse;
        if (aBoolean) {
            refreshLayout.finishLoadMore(/*,false*/);//传入false表示刷新失败
            missionList.addAll(missionResponse.getRows());
        } else {
            refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
            missionList.clear();
            missionList.addAll(missionResponse.getRows());
            //  ToastUtils.showTestShort(getActivity(), "刷新完成");
        }

        headerAndFooterWrapper.notifyDataSetChanged();
        showAdapter();
    }

    private void showAdapter() {
        adapter.setOnItemClickListener(new MissionRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }

                if (user == null) {

                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else {
                    Intent intent = new Intent(getActivity(), MissionDetailActivity.class);
                    intent.putExtra("EXTRA_BUSINESS_ID", missionList.get(position - 1).getBusinessId());
                    intent.putExtra("TASKDETAILS", missionBean.getOrderState());
                    startActivity(intent);
                }
            }
        });
    }

    private void display() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] mLocation = new int[2];
            constraintLayout.getLocationInWindow(mLocation);
            filterPopup.showAtLocation(constraintLayout, Gravity.NO_GRAVITY, 0, mLocation[1] + constraintLayout.getHeight());
        } else {
            filterPopup.showAsDropDown(constraintLayout, 0, 0);
        }

    }


    @Override
    public void Modifiable() {
        refreshLayout.autoRefresh();//自动刷新
        LinearLayoutManager mLayoutManager =  (LinearLayoutManager) mPullLoadMoreRecyclerView.getLayoutManager();
        mLayoutManager.scrollToPositionWithOffset(0, 0);

    }
    @Override
    public void pageBack(int index) {

    }

    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(ResultEvent event) {
        // 响应事件
        refreshLayout.autoRefresh();//自动刷新
        LinearLayoutManager mLayoutManager =  (LinearLayoutManager) mPullLoadMoreRecyclerView.getLayoutManager();
        mLayoutManager.scrollToPositionWithOffset(0, 0);

    }

    public void  refreshTop(){
        refreshLayout.autoRefresh();//自动刷新
        LinearLayoutManager mLayoutManager =  (LinearLayoutManager) mPullLoadMoreRecyclerView.getLayoutManager();
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }
}