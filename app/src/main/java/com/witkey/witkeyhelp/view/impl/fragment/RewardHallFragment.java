package com.witkey.witkeyhelp.view.impl.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;


import android.util.Log;
import android.view.View;

import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MissionRecyAdapter;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.MissionFilterDialog;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.event.ResultEvent;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.IReawardHallFragPresenter;
import com.witkey.witkeyhelp.presenter.impl.ReawardHallFragPresenterImpl;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;

import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SpUtils;

import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IReawardHallFragView;
import com.witkey.witkeyhelp.view.impl.ActivityShare;
import com.witkey.witkeyhelp.view.impl.LoginActivity;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;

import com.witkey.witkeyhelp.widget.HeaderAndFooterWrapper;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;


import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 悬赏大厅fragment
 */
public class RewardHallFragment extends BaseFragment implements IReawardHallFragView {
    private NiceSpinner spin_classify;
    private NiceSpinner spin_order;
    private TextView tv_filter;
    private String[] classifyData = {"全部", "信息咨询", "悬赏帮忙"};
    private String[] orderData = {"默认排序", "价格升序", "价格降序", "发布时间"};

    private IReawardHallFragPresenter presenter;
    private boolean isLoading = false;
    //控制数据的变量
    private MissionBean missionBean;
    private String searchKeyWord;

    private MissionBean chooseMissionBean;

    //获取的任务列表数据
    private PagingResponse missionResponse;
    private List<MissionBean> missionList;

    //高级筛选dialog
    private MissionFilterDialog missionFilterDialog;

    private int pageNum = 1;
    private TextView ivBarLeftname;
    private MissionRecyAdapter adapter;

    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;

    private boolean aBoolean;

    private String city = "";
    private String province = "";

    private double latitude;
    private double longitude;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private UICustomDialog2 dialog2;

    @Override
    protected int getContentView() {
        return R.layout.fragment_reward_hall;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new ReawardHallFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                aBoolean = false;
                pageNum = 1;
                allGet();
                MobclickAgent.onEvent(getActivity(), "rewardHallRefresh");
            }

            @Override
            public void onLoadMore() {
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


        spin_order.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                pageNum = 1;
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                aBoolean = false;
                if (orderData[position].equals("价格升序")) {
                    missionBean.setMflag("1");
                    MobclickAgent.onEvent(getActivity(), "rewardAscending");
                } else if (orderData[position].equals("价格降序")) {
                    missionBean.setMflag("2");
                    MobclickAgent.onEvent(getActivity(), "rewardDescending");
                } else if (orderData[position].equals("发布时间")) {
                    missionBean.setMflag("4");
                    MobclickAgent.onEvent(getActivity(), "rewardtime");
                } else if (orderData[position].equals("默认排序")) {
                    missionBean.setMflag("10");


                }
                DialogUtil.showProgress(getActivity());
                allGet();
            }
        });

        spin_classify.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                pageNum = 1;
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                aBoolean = false;
                if (classifyData[position].equals("信息咨询")) {
                    missionBean.setBusinessType(1 + "");
                    MobclickAgent.onEvent(getActivity(), "rewardinformation");
                } else if (classifyData[position].equals("悬赏帮忙")) {
                    missionBean.setBusinessType(2 + "");
                    MobclickAgent.onEvent(getActivity(), "rewardReward");
                } else if (classifyData[position].equals("全部")) {
                    missionBean.setBusinessType("");
                    MobclickAgent.onEvent(getActivity(), "rewardwhole");
                }

                DialogUtil.showProgress(getActivity());
                allGet();
            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionFilterDialog = new MissionFilterDialog(getActivity(), missionBean, findViewById(R.id.ll_spinner));
                missionFilterDialog.setOnclickListener(new MissionFilterDialog.ICommitOnclickListener() {
                    @Override
                    public void onCommit(MissionBean missionFilter) {
                        pageNum = 1;
                        mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                        aBoolean = false;
                        missionBean.setBondType(missionFilter.getBondType());
                        missionBean.setPaymentType(missionFilter.getPaymentType());

                        missionBean.setUnareaType(missionFilter.getUnareaType());
                        DialogUtil.showProgress(getActivity());


                        allGet();
                    }
                });
                //   addTooBackground();
                missionFilterDialog.show();
            }
        });


        initReceiver();
    }


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                dialog2.hide();
                getLocationClient();
                break;
            case 100:
                if (data != null) {
                    if (data.getStringExtra("name") != null) {
                        ivBarLeftname.setText(data.getStringExtra("name"));
                        city = data.getStringExtra("city");
                        aBoolean = false;
                        latitude = data.getDoubleExtra("latitude", 0);
                        longitude = data.getDoubleExtra("longitude", 0);

                        missionBean.setLatitude(latitude + "");
                        missionBean.setLongitude(longitude + "");
                        missionBean.setPageNum(1);
                        allGet();
                    }
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        Log.e("tag", manufacturer);
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("huawei".equalsIgnoreCase(manufacturer) || "Xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    @Override
    protected void initViewExceptPresenter() {

        // TODO: 2019/7/9 刷新操作
    }

    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(ResultEvent event) {
        // 响应事件
        aBoolean = false;
        pageNum = 1;
        missionBean.setPageNum(pageNum);
        allGet();
    }

    /**
     * 获取数据
     */
    private void allGet() {
        // DialogUtil.showProgress(getActivity());
        missionBean.setPageNum(pageNum);
        presenter.getMissionList(missionBean, searchKeyWord);
        // TODO: 2019/7/9 显示刷新
    }

    @Override
    protected void initWidget() {

        setIncludeTitle("悬赏大厅");
        spin_classify = (NiceSpinner) findViewById(R.id.spin_classify);
        spin_order = (NiceSpinner) findViewById(R.id.spin_order);
        tv_filter = (TextView) findViewById(R.id.tv_filter);
        //     et_search = (EditText) findViewById(R.id.et_search);
        ivBarLeftname = (TextView) findViewById(R.id.ivBarLeftname);

        spin_classify.attachDataSource(Arrays.asList(classifyData));
        spin_order.attachDataSource(Arrays.asList(orderData));
        tv_filter.setText("高级筛选");

        missionBean = new MissionBean();
        missionBean.setPageNum(1);
        missionBean.setUnareaType("1");
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setVisibility(View.INVISIBLE);
        missionList = new ArrayList<>();


        ivBarLeftname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }

                Intent intent = new Intent(getActivity(), ActivityShare.class);
                intent.putExtra("city", city);
                intent.putExtra("province", province);
                startActivityForResult(intent, 100);
                MobclickAgent.onEvent(getActivity(), "rewardHallposition");
            }
        });
        adapter = new MissionRecyAdapter(getActivity(), missionList, 0);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        headerAndFooterWrapper.addFootView(getLayoutInflater().inflate(R.layout.add_foodview, null, false));
        mPullLoadMoreRecyclerView.setAdapter(headerAndFooterWrapper);


    }


    @Override
    public void onError(String error) {
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        ToastUtils.showTestShort(getActivity(), error);
    }

    @Override
    public void showMissionList(PagingResponse missionResponse) {
        mPullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
        DialogUtil.dismissProgress();
        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        this.missionResponse = missionResponse;
        if (aBoolean) {
            missionList.addAll(missionResponse.getRows());
        } else {
            missionList.clear();
            missionList.addAll(missionResponse.getRows());
            ToastUtils.showTestShort(getActivity(), "刷新完成");
        }
        //  if (adapter == null) {

//        } else {
        headerAndFooterWrapper.notifyDataSetChanged();
        //     }
        if (pageNum == 1) {
            mPullLoadMoreRecyclerView.scrollToTop();
        }
        showAdapter();
    }

    private void showAdapter() {
        adapter.setOnItemClickListener(new MissionRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                User user = SpUtils.getObject(getActivity(), "LOGIN");
                if (user == null) {

                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else {
                    Intent intent = new Intent(getActivity(), MissionDetailActivity.class);
                    intent.putExtra("EXTRA_BUSINESS_ID", missionList.get(position).getBusinessId());
                    intent.putExtra("TASKDETAILS", 1);
                    intent.putExtra("TASK", missionList.get(position));
                    startActivity(intent);
                }
            }
        });
    }

    public AMapLocation aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void getLocationClient() {
        aBoolean = false;
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                DialogUtil.showProgress(getActivity());
                if (aMapLocation.getErrorCode() == 0) {
                    city = aMapLocation.getCity();
                    province = aMapLocation.getProvince();
                    aMapLocation.getAddress();
                    String aoiName = aMapLocation.getAoiName();

                    if (aoiName == null || aoiName.equals("")) {
                        ivBarLeftname.setText(aMapLocation.getPoiName() + "");
                    } else {
                        ivBarLeftname.setText(aoiName + "");
                    }
                    missionBean.setPageNum(1);
                    missionBean.setLatitude(aMapLocation.getLatitude() + "");
                    missionBean.setLongitude(aMapLocation.getLongitude() + "");
                    allGet();
                } else {
                    ivBarLeftname.setText("定位失败");
                    missionBean.setPageNum(1);
                    allGet();
                }


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


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private NetworkReceiver mReceiver;

    private void initReceiver() {
        mReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getActivity().registerReceiver(mReceiver, filter);
    }

    //监听网络状态的广播
    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (null == activeInfo) {
                } else {
                    Log.e("cccccccccc", "123");
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
            }
        }
    }
}
