package com.witkey.witkeyhelp.view.impl.fragment;


import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import android.os.IBinder;
import android.provider.Settings;

import android.support.annotation.NonNull;

import android.support.v4.content.ContextCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.stx.xhb.xbanner.XBanner;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;

import com.witkey.witkeyhelp.adapter.MissionRecyAdapter;
import com.witkey.witkeyhelp.bean.BannerBean;
import com.witkey.witkeyhelp.bean.CarouselBean;
import com.witkey.witkeyhelp.bean.DiamondBean;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.event.GeographyBean;

import com.witkey.witkeyhelp.event.InternetEvent;
import com.witkey.witkeyhelp.event.LoginEvent;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IPresenter;

import com.witkey.witkeyhelp.services.DownloadServise;

import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SharedPrefHelper;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.SystemUtil;
import com.witkey.witkeyhelp.util.ToastUtils;


import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;

import com.witkey.witkeyhelp.view.IHomeFragView;
import com.witkey.witkeyhelp.view.impl.ActivityDiamond;


import com.witkey.witkeyhelp.view.impl.ActivityLostFound;


import com.witkey.witkeyhelp.view.impl.ConsultActivity;
import com.witkey.witkeyhelp.view.impl.FriendInvitationActivity;
import com.witkey.witkeyhelp.view.impl.LoginActivity;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.view.impl.NoviceHelpActivity;
import com.witkey.witkeyhelp.view.impl.ProblemFeedbackActivity;
import com.witkey.witkeyhelp.view.impl.SystemMessageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 首页fragment
 */
public class HomeFragment extends BaseFragment implements IHomeFragView, View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private RelativeLayout rl_consult;
    private RelativeLayout rl_help;
    private GridView gv_function;

    private ArrayList<Map<String, Object>> funtionData;
    private String[] functionStrs = {"紧急求助", "失物招领", "微通知", "意见反馈"};
    private int[] functionImg = {R.mipmap.ic_home_emergencyhelp, R.mipmap.ic_home_lostfound, R.mipmap.micro_notification, R.mipmap.problemfeedback};
    private SimpleAdapter adapter_function;
    private ImageView image_icon;
    private OptionsPickerView pvOptions;
    private RecyclerView pullLoadMoreRecyclerView;
    private MissionBean missionBean;
    private MissionRecyAdapter adapter;
    private XBanner mXBanner;
    private ScrollView scrollView;
    private LinearLayout lostfound;
    private LinearLayout zuanshi_tongzhi;
    private UICustomDialog2 dialog2;
    private TextView tv_msg_count;
    private double latitude;
    private double longitude;
    private String city;
    private String province;
    private int acs;
    private TextView news_red_dot;
    private Dialog relievepopupWindow;
    private boolean download;
    private ProgressBar pb;
    private JSONObject jsonObject;
    private LinearLayout update_layout;
    private String downloadUrl;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
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
        Intent intent = getActivity().getIntent();
        acs = intent.getIntExtra("ACS", 0);


        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        update();

        rl_consult.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        lostfound.setOnClickListener(this);
        zuanshi_tongzhi.setOnClickListener(this);
        image_icon.setOnClickListener(this);
        gv_function.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //紧急求助
                    //  startActivity("3");
                    if (PventQuickClick.isFastDoubleClick()) {
                        return;
                    }
                    //  Log.e("tag", "暂未开发");
                    ToastUtils.showTestShort(getActivity(), "暂未开发");
                } else if (position == 1) {
                    //失物招领
                    //  IntentUtil.startActivity(getActivity(), LostFoundActivity.class);
                    if (PventQuickClick.isFastDoubleClick()) {
                        return;
                    }
                    // Log.e("tag", "暂未开发");
                    ToastUtils.showTestShort(getActivity(), "暂未开发");
                } else if (position == 2) {
                    //微通知
                    //IntentUtil.startActivity(getActivity(), MicroNotificationActivity.class);
                    if (PventQuickClick.isFastDoubleClick()) {
                        return;
                    }
                    //  Log.e("tag", "暂未开发");
                    ToastUtils.showTestShort(getActivity(), "暂未开发");
                } else if (position == 3) {
                    //意见反馈
                    if (PventQuickClick.isFastDoubleClick()) {
                        return;
                    }

                    User user = SpUtils.getObject(getContext(), "LOGIN");
                    if (user == null) {
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
//                        Intent intent = new Intent(getActivity(), ProblemFeedbackActivity.class);
//                        intent.putExtra("type", 0);
//                        startActivity(intent);
                    }

                }
            }
        });
        User user = SpUtils.getObject(getActivity(), "LOGIN");
        if (user != null) {
            updatePhoto();
        }
        mXBanner = (XBanner) findViewById(R.id.banner);


    }


    @Override
    public void onResume() {
        super.onResume();

        if (lacksPermissions(getContext(), permissionsREAD)) {//地理位置权限
            HomeFragment.this.requestPermissions(permissionsREAD, 0);
        } else {
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

        weidu();
    }

    private void banner() {


        MyAPP.getInstance().getApi().apiBanener(1).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {
                BannerBean beanFromJson = JsonUtils.getBeanFromJson(body, BannerBean.class);

                List<BannerBean.ReturnObjectBean> returnObject = beanFromJson.getReturnObject();
                List<CarouselBean> images = new ArrayList<>();
                for (int i = 0; i < returnObject.size(); i++) {
                    BannerBean.ReturnObjectBean returnObjectBean = returnObject.get(i);
                    images.add(new CarouselBean(URL.getImgPath + returnObjectBean.getUrl()));
                }

                mXBanner.setBannerData(images);//setData（）方法已过时，推荐使用setBannerData（）方法，具体参照demo使用
                mXBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(XBanner banner, Object model, View view, int position) {
                        if (PventQuickClick.isFastDoubleClick()) {
                            return;
                        }
                        switch (position) {
                            case 0:
                                startActivity(new Intent(getActivity(), ActivityDiamond.class).putExtra("type", 1));
                                break;
                            case 1:
                                Intent i1 = new Intent(getActivity(), NoviceHelpActivity.class);
                                startActivity(i1);
                                buryingPoint("novicehelp");
                                break;
                            case 2:
                                User user = SpUtils.getObject(getActivity(), "LOGIN");
                                if (user == null) {
                                    Intent i = new Intent(getActivity(), LoginActivity.class);
                                    i.putExtra("type", "0");
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    return;
                                } else {
                                    Intent i = new Intent(getActivity(), FriendInvitationActivity.class);
                                    startActivity(i);
                                    buryingPoint("shareFriends");
                                }
                                break;
                        }
                    }
                });
                //加载广告图片
                mXBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {

                        //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                        //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！

                        RoundedCorners roundedCorners = new RoundedCorners(dip2px(getActivity(), 10));
                        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

                        CarouselBean model1 = (CarouselBean) model;

                        Glide.with(getContext()).applyDefaultRequestOptions(options).load(model1.getUrl()).into((ImageView) view);
                    }
                });

                mXBanner.setAutoPalyTime(2500);
                mXBanner.startAutoPlay();

            }
        });

    }

    boolean aBoolean;

    private void updatePhoto() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = SpUtils.getObject(getActivity(), "LOGIN");

                    UserInfo mMyInfo = JMessageClient.getMyInfo();


                    if (!mMyInfo.getNickname().equals(user.getRealName())) {
                        Log.e("tag", mMyInfo.getNickname());
                        mMyInfo.setNickname(user.getRealName());

                        JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                            @Override
                            public void gotResult(int responseCode, String responseMessage) {
                                if (responseCode == 0) {
                                    Log.e("tag", "修改成功");
                                } else {
                                    Log.e("tag", "修改失败");
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }

    @Override
    protected void initViewExceptPresenter() {


    }

    private List<MissionBean> missionList;


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
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    city = aMapLocation.getCity();
                    province = aMapLocation.getProvince();
                    missionBean.setPageNum(1);
                    missionBean.setLatitude(aMapLocation.getLatitude() + "");
                    missionBean.setLongitude(aMapLocation.getLongitude() + "");
                    load();
                    recommended();
                } else {
                    recommended();
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
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();

    }

    private void recommended() {

        MyAPP.getInstance().getApi().apibusinessRecommend(missionBean.getPageNum(),
                10, longitude + "", latitude + "").enqueue(new Callback(IModel.callback, "获得任务列表失败") {
            @Override
            public void getSuc(String body) {

                DialogUtil.dismissProgress();
                missionList.clear();
                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(IModel.gson, JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);

                missionList.addAll(missionResponse.getRows());
                if (missionList.size() == 0) {
                    pullLoadMoreRecyclerView.setVisibility(View.GONE);
                } else {
                    pullLoadMoreRecyclerView.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                aBoolean = false;
                adapter.setOnItemClickListener(new MissionRecyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (PventQuickClick.isFastDoubleClick()) {
                            return;
                        }

                        User user = SpUtils.getObject(getActivity(), "LOGIN");
                        if (user == null) {

                            Intent i = new Intent(getActivity(), LoginActivity.class);

                            startActivity(i);

                        } else {
                            Intent intent = new Intent(getActivity(), MissionDetailActivity.class);
                            intent.putExtra("EXTRA_BUSINESS_ID", missionList.get(position).getBusinessId());
                            intent.putExtra("TASKDETAILS", 1);
                            startActivity(intent);
                        }

                    }
                });
            }


        });


    }

    private void weidu() {
        User user = SpUtils.getObject(getContext(), "LOGIN");
        if (user != null) {
            MyAPP.getInstance().getApi().apiReadList(user.getUserId()).enqueue(new Callback(IModel.callback, "获取未读信息失败") {
                @Override
                public void getSuc(String body) {
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        int conut = jsonObject.getJSONObject("returnObject").getInt("count");
                        if (conut > 0) {
                            news_red_dot.setVisibility(View.VISIBLE);
                        } else {
                            news_red_dot.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            scrollView.scrollTo(0, 0);

        }
    }

    protected void initWidget() {
        setIncludeTitle("酷爱帮");
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        rl_consult = (RelativeLayout) findViewById(R.id.rl_consult);
        rl_help = (RelativeLayout) findViewById(R.id.rl_help);
        gv_function = (GridView) findViewById(R.id.gv_function);
        image_icon = (ImageView) findViewById(R.id.image_icon);
        zuanshi_tongzhi = (LinearLayout) findViewById(R.id.zuanshi_tongzhi);
        tv_msg_count = (TextView) findViewById(R.id.tv_msg_count);

        lostfound = (LinearLayout) findViewById(R.id.lostfound);


        news_red_dot = (TextView) findViewById(R.id.news_red_dot);

        LinearLayout information = (LinearLayout) findViewById(R.id.information);
        information.setOnClickListener(this);
        LinearLayout reward = (LinearLayout) findViewById(R.id.reward);
        reward.setOnClickListener(this);
        pullLoadMoreRecyclerView = (RecyclerView) findViewById(R.id.pullLoadMoreRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pullLoadMoreRecyclerView.setLayoutManager(layoutManager);
        missionBean = new MissionBean();
        missionBean.setPageNum(1);
        missionBean.setUnareaType("1");
        missionList = new ArrayList<>();


        initData();
        adapter = new MissionRecyAdapter(getActivity(), missionList,0);
        pullLoadMoreRecyclerView.setAdapter(adapter);
        pullLoadMoreRecyclerView.setVisibility(View.GONE);

        String[] testDeviceInfo = getTestDeviceInfo(getActivity());
        for (int i = 0; i < testDeviceInfo.length; i++) {
            Log.e("qqq", testDeviceInfo[i]);
        }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:

                if (lacksPermissions(getContext(), permissionsREAD)) {//地理位置权限
                    requestPermissions(permissionsREAD, 0);
                } else {
                    getLocationClient();
                }

                break;


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

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        Log.e("tag", manufacturer);
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("huawei".equalsIgnoreCase(manufacturer) || "Xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    private void initData() {
        funtionData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < functionStrs.length; i++) {
            setData(functionImg[i], functionStrs[i], funtionData);
        }
        adapter_function = new SimpleAdapter(getActivity(), funtionData, R.layout.item_gv_home, new String[]{"icon", "str"}, new int[]{R.id.iv, R.id.tv});
        gv_function.setAdapter(adapter_function);
    }

    private void setData(int icon, String str, List data) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("icon", icon);
        hashMap.put("str", str);
        data.add(hashMap);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.image_icon:


                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }

                User user = SpUtils.getObject(getContext(), "LOGIN");
                if (user == null) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else {


                    startActivity(new Intent(getActivity(), SystemMessageActivity.class));
                    buryingPoint("systemMessages");
                }


                break;
            case R.id.information:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                buryingPoint("information");
                startActivity("1");
                break;
            case R.id.reward:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                buryingPoint("rewardhelp");

                startActivity("2");
                break;
            case R.id.lostfound:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                user = SpUtils.getObject(getContext(), "LOGIN");
                if (user == null) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else {


                    startActivity(new Intent(getActivity(), ActivityLostFound.class).putExtra("type", 1));

                }

                break;
            case R.id.zuanshi_tongzhi:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                user = SpUtils.getObject(getContext(), "LOGIN");
                if (user == null) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else {


                    startActivity(new Intent(getActivity(), ActivityDiamond.class).putExtra("type", 1));

                }
                break;


        }

    }


    @Override
    public void onStart() {
        super.onStart();
        // banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //banner.stopAutoPlay();
    }

    private void startActivity(String s) {

        Intent i = new Intent(getActivity(), ConsultActivity.class);
        i.putExtra("EXTRA_PAGE_TYPE", s);
        startActivity(i);
    }

    private void buryingPoint(String eventId) {
        MobclickAgent.onEvent(getActivity(), eventId);

    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
        }
        return deviceInfo;
    }


    @Override
    public void onError(String error) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(GeographyBean event) {

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

    private void load() {
        User user = SpUtils.getObject(getContext(), "LOGIN");
        if (user != null) {
            MyAPP.getInstance().getApi().advertisinglist(user.getUserId(), longitude + "", latitude + "", 1 + "", 10 + "", province, city).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
                @Override
                public void getSuc(String body) {

                    DiamondBean beanFromJson = JsonUtils.getBeanFromJson(body, DiamondBean.class);

                    int missionResponse = beanFromJson.getReturnObject().getTotal();
                    if (missionResponse > 0) {
                        tv_msg_count.setVisibility(View.VISIBLE);
                    } else {
                        tv_msg_count.setVisibility(View.GONE);
                    }
                }


            });
        }

    }

    /**
     * 照相权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREAD = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION

    };


    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public static boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {

        for (String permission : permissionsREAD) {
            if (lacksPermission(mContexts, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 照相权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREADWrite = {

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {

                    // 权限被拒绝，弹出dialog 提示去开启权限
                    //         showPermissions();
                    break;
                }
            }
            downLoadCs(downloadUrl);
            update_layout.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
        }
        if (requestCode == 0) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {

                    // 权限被拒绝，弹出dialog 提示去开启权限
                    //         showPermissions();
                    break;
                }
            }
            getLocationClient();
        }

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
                    banner();


                }
            }
        }
    }


    private void task_submission_fail(Context context, String versioncontent, String siezecontent, String updatelogu, String timeupdate, final String downloadUrl, String constraint) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popub_upgrade, null, false);//引入弹窗布局

        ImageView expression = vPopupWindow.findViewById(R.id.expression);

        TextView version_name = vPopupWindow.findViewById(R.id.version_name);
        TextView packagesize = vPopupWindow.findViewById(R.id.packagesize);

        TextView update_content = vPopupWindow.findViewById(R.id.update_content);

        TextView not_new = vPopupWindow.findViewById(R.id.not_new);
        pb = vPopupWindow.findViewById(R.id.pb);

        final TextView update_text = vPopupWindow.findViewById(R.id.update_text);

        TextView updatetime = vPopupWindow.findViewById(R.id.updatetime);
        View middle_line = vPopupWindow.findViewById(R.id.middle_line);
        update_layout = vPopupWindow.findViewById(R.id.update_layout);
        updatetime.setText("更新时间 :   " + timeupdate);
        version_name.setText("版本 :   " + versioncontent);
        packagesize.setText("包大小 :   " + siezecontent + "MB");
        update_content.setText(updatelogu);

        relievepopupWindow = new Dialog(getActivity(), R.style.CustomDialog);

        if (constraint.equals("1")) {
            middle_line.setVisibility(View.GONE);
            not_new.setVisibility(View.GONE);
            relievepopupWindow.setCancelable(false); // false 禁止返回键 和下边的touch必须同时使用
            relievepopupWindow.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失

            relievepopupWindow.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失


            update_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // new DownloadUtils(getActivity(), downloadUrl, "kuaibang.apk");


                    if (lacksPermissions(getContext(), permissionsREADWrite)) {//读写权限
                        HomeFragment.this.requestPermissions(permissionsREADWrite, 1);
                    } else {
                        downLoadCs(downloadUrl);
                        update_layout.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                    }


                }
            });


            not_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showTestShort(getActivity(), "此次更新为强制更新");
                }
            });
            expression.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showTestShort(getActivity(), "此次更新为强制更新");
                }
            });
        } else {

            update_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (lacksPermissions(getContext(), permissionsREADWrite)) {//读写权限
                        HomeFragment.this.requestPermissions(permissionsREADWrite, 1);
                    } else {
                        downLoadCs(downloadUrl);
                        update_layout.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                    }
                }
            });

            not_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relievepopupWindow.dismiss();
                }
            });
            expression.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relievepopupWindow.dismiss();
                }
            });
        }


        // 添加动态内容
        relievepopupWindow.setContentView(vPopupWindow, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relievepopupWindow.show();


    }


    private void update() {
        MyAPP.getInstance().getApi().versionUpdate().enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    jsonObject = new JSONObject(response.body().toString());
                    String constraint = jsonObject.getString("updateStatus");
                    String versionCode = jsonObject.getString("versionCode");
                    //下载地址
                    downloadUrl = jsonObject.getString("downloadUrl");

                    if (Integer.parseInt(versionCode) > getVersionCode(getActivity())) {
                        task_submission_fail(getActivity(), jsonObject.getString("versionName"), jsonObject.getString("apkSize"), jsonObject.getString("modifyContent"), jsonObject.getString("uploadTime"), jsonObject.getString("downloadUrl"), constraint);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }


        });
    }

    private void downLoadCs(String downloadUrl) {  //下载调用

        Intent intent = new Intent(getActivity(), DownloadServise.class);
        intent.putExtra(DownloadServise.BUNDLE_KEY_DOWNLOAD_URL, downloadUrl);
        isBindService = getActivity().bindService(intent, conn, getActivity().BIND_AUTO_CREATE); //绑定服务即开始下载 调用onBind()
    }


    private boolean isBindService;
    private ServiceConnection conn = new ServiceConnection() { //通过ServiceConnection间接可以拿到某项服务对象

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadServise.DownloadBinder binder = (DownloadServise.DownloadBinder) service;
            final DownloadServise downloadServise = binder.getService();

            //接口回调，下载进度
            downloadServise.setOnProgressListener(new DownloadServise.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {
                    if ((int) (fraction * 100) >= 100) {
                        Log.e("tag", 100 + "qqqqqqqq");
                        pb.setProgress(100);
                    } else {
                        Log.e("tag", (int) (fraction * 100) + "qqqqqqqq");
                        pb.setProgress((int) (fraction * 100));
                    }


                    //判断是否真的下载完成进行安装了，以及是否注册绑定过服务
                    if (fraction == downloadServise.UNBIND_SERVICE && isBindService) {

                        getActivity().unbindService(conn);
                        isBindService = false;
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
