package com.witkey.witkeyhelp.view.impl;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;


import android.support.v7.widget.RecyclerView;
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
import com.witkey.witkeyhelp.adapter.ReleaseListAdapter;

import com.witkey.witkeyhelp.bean.ReleaseBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
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

import java.util.ArrayList;
import java.util.List;

import static com.witkey.witkeyhelp.model.IModel.gson;


/**
 * Created by asus on 2020/3/19.
 */

public class ActivityLostFound extends BaseActivity {

    private ImageView tv_confirm;

    private PopupWindow relievepopupWindow;
    private int pageNum = 1;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private boolean aBoolean;
    //获取的任务列表数据
    private int missionResponse;
    private List<ReleaseBean.ReturnObjectBean.RowsBean> missionList;
    private ReleaseListAdapter adapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private RelativeLayout publish_list;
    private RelativeLayout mypublish_list;
    private TextView select_location;
    private String city = "";
    private String province = "";
    private double latitude;
    private double longitude;
    private int type;
    private List<ReleasePhotoBean> photoListTwo = new ArrayList<>();
    private UICustomDialog2 dialog2;

    @Override
    protected boolean isGetUser() {
        return true;
    }

    private String userid;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_lost_found);
        publish_list = findViewById(R.id.publish_list);
        mypublish_list = findViewById(R.id.mypublish_list);
        select_location = findViewById(R.id.select_location);

        type = getIntent().getIntExtra("type", 0);

        if (type == 0) {
            publish_list.setVisibility(View.GONE);
            mypublish_list.setVisibility(View.VISIBLE);
            userid = user.getUserId() + "";
            setIncludeTitle("我的发布");
        } else if (type == 1) {
            mypublish_list.setVisibility(View.GONE);
            publish_list.setVisibility(View.VISIBLE);
            userid = null;

        }
        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLostFound.this, ActivityShare.class);
                intent.putExtra("city", city);
                intent.putExtra("province", province);
                startActivityForResult(intent, 100);
                MobclickAgent.onEvent(ActivityLostFound.this, "foundposition");
            }
        });
        tv_confirm = findViewById(R.id.tv_confirm_a);

        missionList = new ArrayList<>();
        popubWiodowTask();
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                relievepopupWindow.showAsDropDown(tv_confirm, 0, 0, Gravity.CENTER);
            }
        });
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);


        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                aBoolean = false;
                pageNum = 1;
                loadFound();
                if (type == 0) {
                    MobclickAgent.onEvent(ActivityLostFound.this, "myRefresh");
                } else {
                    MobclickAgent.onEvent(ActivityLostFound.this, "foundRefresh");
                }
            }

            @Override
            public void onLoadMore() {
                if (type == 0) {
                    MobclickAgent.onEvent(ActivityLostFound.this, "myLoadMore");
                } else {
                    MobclickAgent.onEvent(ActivityLostFound.this, "foundLoadMore");
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
        boolean miui = isMIUI();
        if (miui) {
            boolean oPen = GPSIsOpenUtil.isOPen(ActivityLostFound.this);
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

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("huawei".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }

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
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    if (aoiName == null || aoiName.equals("")) {
                        select_location.setText(aMapLocation.getPoiName() + "");
                    } else {
                        select_location.setText(aoiName + "");
                    }
                    loadFound();
                } else {
                    select_location.setText("定位失败");
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
    protected void onResume() {
        super.onResume();

    }

    private void popubWiodowTask() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popubmenu_layout, null, false);//引入弹窗布局

        TextView release_text = vPopupWindow.findViewById(R.id.release_text);

        TextView my_published = vPopupWindow.findViewById(R.id.my_published);


        release_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                relievepopupWindow.dismiss();
                Intent intent = new Intent(ActivityLostFound.this, AcitivityReleaseFound.class);
                startActivityForResult(intent, 10000);
                // startActivity(intent);
            }
        });
        my_published.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                relievepopupWindow.dismiss();
                Intent intent = new Intent(ActivityLostFound.this, ActivityLostFound.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });

        relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        relievepopupWindow.setBackgroundDrawable(new BitmapDrawable());

        relievepopupWindow.setOutsideTouchable(true);

        relievepopupWindow.setFocusable(true);


    }

    private void loadFound() {

        DialogUtil.showProgress(ActivityLostFound.this);

        MyAPP.getInstance().getApi().lostAriclelist(pageNum, 10, userid, longitude + "", latitude + "").enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {


                ReleaseBean beanFromJson = JsonUtils.getBeanFromJson(body, ReleaseBean.class);
                missionResponse = beanFromJson.getReturnObject().getTotal();
                DialogUtil.dismissProgress();
                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();


                if (aBoolean) {
                    missionList.addAll(beanFromJson.getReturnObject().getRows());
                } else {
                    missionList.clear();
                    missionList.addAll(beanFromJson.getReturnObject().getRows());

                }
                if (adapter == null) {
                    adapter = new ReleaseListAdapter(ActivityLostFound.this, missionList);
                    headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
                    headerAndFooterWrapper.addFootView(getLayoutInflater().inflate(R.layout.add_foodview, null, false));
                    mPullLoadMoreRecyclerView.setAdapter(headerAndFooterWrapper);
                } else {
                    headerAndFooterWrapper.notifyDataSetChanged();
                }
                adapter.setOnItemClickListener(new ReleaseListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (PventQuickClick.isFastDoubleClick()) {
                            return;
                        }
                        Intent intent = new Intent(ActivityLostFound.this, ActivityLostFoundDetails.class);
                        intent.putExtra("id", missionList.get(position).getId());
                        intent.putExtra("type", type);
                        startActivity(intent);
                    }
                });
            }


        });
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
                        select_location.setText(data.getStringExtra("name"));
                        city = data.getStringExtra("city");
                        aBoolean = false;
                        latitude = data.getDoubleExtra("latitude", 0);
                        longitude = data.getDoubleExtra("longitude", 0);
                        loadFound();

                    }
                }
                break;
            case 10000:
                mPullLoadMoreRecyclerView.setPushRefreshEnable(true);
                aBoolean = false;
                pageNum = 1;
                loadFound();
                break;

        }
    }

}
