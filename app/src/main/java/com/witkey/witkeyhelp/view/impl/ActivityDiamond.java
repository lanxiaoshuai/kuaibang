package com.witkey.witkeyhelp.view.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.DiamondAdapter;

import com.witkey.witkeyhelp.bean.DiamondBean;

import com.witkey.witkeyhelp.dialog.UICustomDialog2;


import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;

import com.witkey.witkeyhelp.util.JsonUtils;

import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import com.witkey.witkeyhelp.widget.HeaderAndFooterWrapper;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2020/4/2.
 */

public class ActivityDiamond extends BaseActivity {

    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private List<DiamondBean.ReturnObjectBean.RowsBean> missionList;
    private int pageNum = 1;
    private boolean aBoolean;
    private double latitude;
    private double longitude;
    private PopupWindow relievepopupWindow;
    private int type;
    private RelativeLayout publish_list;
    private RelativeLayout mypublish_list;
    private int missionResponse;
    private DiamondAdapter diamondAdapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private ImageView tv_confirm_a;
    private String city;
    private String province;
    private UICustomDialog2 dialog2;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_diamond);
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
        mPullLoadMoreRecyclerView.setLinearLayout();
        missionList = new ArrayList<>();
        popubWiodowTask();
        initview();
        diliweizhi();
        publish_list = findViewById(R.id.publish_list);
        mypublish_list = findViewById(R.id.mypublish_list);
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            publish_list.setVisibility(View.GONE);
            mypublish_list.setVisibility(View.VISIBLE);

            setIncludeTitle("我的发布");
        } else if (type == 1) {
            mypublish_list.setVisibility(View.GONE);
            publish_list.setVisibility(View.VISIBLE);
            setIncludeTitle("钻石通知");

        } else if (type == 2) {
            publish_list.setVisibility(View.GONE);
            mypublish_list.setVisibility(View.VISIBLE);
            setIncludeTitle("浏览记录");
        }
        tv_confirm_a = findViewById(R.id.tv_confirm_a);
        tv_confirm_a.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                relievepopupWindow.showAsDropDown(tv_confirm_a, 0, 0, Gravity.CENTER);
            }
        });
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
    protected boolean isGetUser() {
        return true;
    }

    private void diliweizhi() {
        boolean miui = isMIUI();
        if (miui) {
            boolean oPen = GPSIsOpenUtil.isOPen(this);
            if (oPen) {
                getLocationClient();
            } else {
                showLocationMissingPermissionDialog();
            }
        } else {
            getLocationClient();
        }

    }

    private int GPS_REQUEST_CODE = 10;

    protected void startAppSettings() {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, GPS_REQUEST_CODE);

    }

    private void showLocationMissingPermissionDialog() {
        dialog2 = new UICustomDialog2(this, "GPS未打开,去开启", "3");
        dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.hide();
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

    public AMapLocation aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void initview() {
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                aBoolean = false;
                pageNum = 1;
                loadFound();
                if (type == 0) {
                    MobclickAgent.onEvent(ActivityDiamond.this, "myDiamondRefresh");
                } else {
                    MobclickAgent.onEvent(ActivityDiamond.this, "DiamondRefresh");
                }
            }

            @Override
            public void onLoadMore() {

                if (type == 0) {
                    MobclickAgent.onEvent(ActivityDiamond.this, "myDiamondLoadMore");
                } else {
                    MobclickAgent.onEvent(ActivityDiamond.this, "DiamondLoadMore");
                }
                aBoolean = true;

                int totalPage = missionResponse / 10;
                if (missionResponse % 10 != 0) {
                    totalPage += 1;
                }
                if (totalPage > pageNum) {

                    pageNum += 1;
                    loadFound();
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
    }

    private void getLocationClient() {
        mLocationListener = new AMapLocationListener() {


            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation.getErrorCode() == 0) {


                    aMapLocation.getAddress();

                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    province = aMapLocation.getProvince();

                    city = aMapLocation.getCity();
                    loadFound();
                } else {
                    loadFound();
                }

            }
        };
//初始化定位
        mLocationClient = new AMapLocationClient(this);
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();


        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();


    }

    private void loadFound() {


        DialogUtil.showProgress(ActivityDiamond.this);
        if (type == 0) {
            MyAPP.getInstance().getApi().advertisingMylist(user.getUserId(), pageNum, 10).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
                @Override
                public void getSuc(String body) {

                    DiamondBean beanFromJson = JsonUtils.getBeanFromJson(body, DiamondBean.class);

                    missionResponse = beanFromJson.getReturnObject().getTotal();
                    DialogUtil.dismissProgress();
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    if (aBoolean) {
                        missionList.addAll(beanFromJson.getReturnObject().getRows());
                    } else {
                        missionList.clear();
                        missionList.addAll(beanFromJson.getReturnObject().getRows());

                    }
                    if (diamondAdapter == null) {
                        diamondAdapter = new DiamondAdapter(ActivityDiamond.this, missionList);
                        //headerAndFooterWrapper = new HeaderAndFooterWrapper(diamondAdapter);
                        //   headerAndFooterWrapper.addFootView(getLayoutInflater().inflate(R.layout.add_foodview, null, false));
                        mPullLoadMoreRecyclerView.setAdapter(diamondAdapter);
                    } else {
                        diamondAdapter.notifyDataSetChanged();
                    }
                    diamondAdapter.setOnItemClickListener(new DiamondAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (PventQuickClick.isFastDoubleClick()) {
                                return;
                            }
                            if (type == 1) {
                                Intent intent = new Intent(ActivityDiamond.this, ActivityDiamondsDetails.class);
                                intent.putExtra("id", missionList.get(position).getId());
                                startActivity(intent);
                            } else {

                                Intent intent = new Intent(ActivityDiamond.this, ActivitySelfAdvertisement.class);
                                intent.putExtra("id", missionList.get(position).getId());

                                startActivity(intent);
                            }
                        }
                    });
                }


            });
        } else if (type == 1) {
            MyAPP.getInstance().getApi().advertisinglist(user.getUserId(), longitude + "", latitude + "", pageNum + "", 10 + "", province, city).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
                @Override
                public void getSuc(String body) {

                    DiamondBean beanFromJson = JsonUtils.getBeanFromJson(body, DiamondBean.class);
                    missionResponse = beanFromJson.getReturnObject().getTotal();
                    DialogUtil.dismissProgress();
                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    if (aBoolean) {
                        missionList.addAll(beanFromJson.getReturnObject().getRows());
                    } else {
                        missionList.clear();
                        missionList.addAll(beanFromJson.getReturnObject().getRows());

                    }
                    if (diamondAdapter == null) {
                        diamondAdapter = new DiamondAdapter(ActivityDiamond.this, missionList);
                        //headerAndFooterWrapper = new HeaderAndFooterWrapper(diamondAdapter);
                        //   headerAndFooterWrapper.addFootView(getLayoutInflater().inflate(R.layout.add_foodview, null, false));
                        mPullLoadMoreRecyclerView.setAdapter(diamondAdapter);
                    } else {
                        diamondAdapter.notifyDataSetChanged();
                    }
                    diamondAdapter.setOnItemClickListener(new DiamondAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (PventQuickClick.isFastDoubleClick()) {
                                return;
                            }
                            if (type == 1) {
                                Intent intent = new Intent(ActivityDiamond.this, ActivityDiamondsDetails.class);
                                intent.putExtra("id", missionList.get(position).getId());

                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(ActivityDiamond.this, ActivitySelfAdvertisement.class);
                                intent.putExtra("id", missionList.get(position).getId());
                                startActivity(intent);
                            }
                        }
                    });
                }
            });
        } else if (type == 2) {

            MyAPP.getInstance().getApi().browseList(user.getUserId(), pageNum, 10).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
                @Override
                public void getSuc(String body) {
                    DialogUtil.dismissProgress();
                    DiamondBean beanFromJson = JsonUtils.getBeanFromJson(body, DiamondBean.class);

                    missionResponse = beanFromJson.getReturnObject().getTotal();

                    mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    if (aBoolean) {
                        missionList.addAll(beanFromJson.getReturnObject().getRows());
                    } else {
                        missionList.clear();
                        missionList.addAll(beanFromJson.getReturnObject().getRows());

                    }
                    if (diamondAdapter == null) {
                        diamondAdapter = new DiamondAdapter(ActivityDiamond.this, missionList);
                        //headerAndFooterWrapper = new HeaderAndFooterWrapper(diamondAdapter);
                        //   headerAndFooterWrapper.addFootView(getLayoutInflater().inflate(R.layout.add_foodview, null, false));
                        mPullLoadMoreRecyclerView.setAdapter(diamondAdapter);
                    } else {
                        diamondAdapter.notifyDataSetChanged();
                    }
                    diamondAdapter.setOnItemClickListener(new DiamondAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            if (PventQuickClick.isFastDoubleClick()) {
                                return;
                            }
                            Intent intent = new Intent(ActivityDiamond.this, ActivityDiamondsDetails.class);
                            intent.putExtra("id", missionList.get(position).getId());
                            startActivity(intent);

                        }
                    });
                }


            });


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                dialog2.hide();
                getLocationClient();
                break;
            case 100:
                if (data != null) {
                    if (data.getStringExtra("name") != null) {

                        aBoolean = false;
                        latitude = data.getDoubleExtra("latitude", 0);
                        longitude = data.getDoubleExtra("longitude", 0);


                    }
                }
                break;
            case 1314:
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                aBoolean = false;
                pageNum = 1;
                loadFound();
                break;

        }
    }

    private void popubWiodowTask() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popubmenu_diamonds, null, false);//引入弹窗布局

        TextView release_text = vPopupWindow.findViewById(R.id.release_text);

        TextView my_published = vPopupWindow.findViewById(R.id.my_published);

        TextView my_browse = vPopupWindow.findViewById(R.id.my_browse);

        release_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                relievepopupWindow.dismiss();
                Intent intent = new Intent(ActivityDiamond.this, ActivityReleaseDiamonds.class);
                startActivityForResult(intent, 1314);

            }
        });
        my_published.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }

                relievepopupWindow.dismiss();
                startActivity(new Intent(ActivityDiamond.this, ActivityDiamond.class).putExtra("type", 0));

            }
        });

        my_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relievepopupWindow.dismiss();
                startActivity(new Intent(ActivityDiamond.this, ActivityDiamond.class).putExtra("type", 2));
            }
        });
        relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        relievepopupWindow.setBackgroundDrawable(new BitmapDrawable());

        relievepopupWindow.setOutsideTouchable(true);

        relievepopupWindow.setFocusable(true);


    }


}
