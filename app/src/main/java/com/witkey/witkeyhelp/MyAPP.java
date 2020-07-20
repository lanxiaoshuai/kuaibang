package com.witkey.witkeyhelp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.sh.sdk.shareinstall.ShareInstall;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import com.umeng.commonsdk.UMConfigure;
import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.api.Api;
import com.witkey.witkeyhelp.services.NotificationClickEventReceiver;
import com.witkey.witkeyhelp.util.CrashHandler;

import com.witkey.witkeyhelp.util.Exclude;
import com.witkey.witkeyhelp.util.SharedPreferencesUtil;
import com.witkey.witkeyhelp.util.retrofit.StringConverterFactory;
import com.witkey.witkeyhelp.view.impl.HomeActivity;
import com.witkey.witkeyhelp.widget.CardUtils;
import com.witkey.witkeyhelp.widget.imagepicker.GlideImageLoader;
import com.witkey.witkeyhelp.widget.imagepicker.ImagePicker;
import com.witkey.witkeyhelp.widget.imagepicker.view.CropImageView;
import com.witkey.witkeyhelp.widget.pickerimage.utils.StorageUtil;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


/**
 * Created by Administrator on 2017/3/9.
 */

public class MyAPP extends MultiDexApplication {
    /**
     * 是否在真机,是的话不谈log
     */
    public static final String CONV_TITLE = "conv_title";
    public static final boolean ISRELEASE = false;
    public static boolean ISUSENEW = true;
    public ArrayList<Activity> activityList = new ArrayList<Activity>();
    private static MyAPP instance;
    //获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    //获取到主线程的looper
    private static Looper mMainThreadLooper = null;
    //获取到主线程
    private static Thread mMainThead = null;
    //获取到主线程的id
    private static int mMainTheadId;

    private String token;
    private String verification_key;
    private Gson gson;
    private User user;
    private boolean isNetContect;
    public Retrofit retrofit;
    private Api api;
    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";

    public static List<Activity> activitys = null;

    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";

    public static List<Message> forwardMsg = new ArrayList<>();

    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();


    public static List<GroupInfo> mGroupInfoList = new ArrayList<>();
    public static List<UserInfo> mFriendInfoList = new ArrayList<>();
    public static List<UserInfo> mSearchGroup = new ArrayList<>();
    public static List<UserInfo> mSearchAtMember = new ArrayList<>();
    public static List<Message> ids = new ArrayList<>();
    public static List<UserInfo> alreadyRead = new ArrayList<>();
    public static List<UserInfo> unRead = new ArrayList<>();
    public static List<String> forAddFriend = new ArrayList<>();

    public static final int IMAGE_MESSAGE = 1;
    public static final int TAKE_PHOTO_MESSAGE = 2;
    public static final int TAKE_LOCATION = 3;
    public static final int FILE_MESSAGE = 4;
    public static final int TACK_VIDEO = 5;
    public static final int TACK_VOICE = 6;
    public static final int BUSINESS_CARD = 7;

    public static final int RESULT_CODE_SELECT_PICTURE = 8;

    public static final int RESULT_CODE_BROWSER_PICTURE = 13;


    public static final int REQUEST_CODE_SEND_FILE = 26;


    public static final int RESULT_CODE_EDIT_NOTENAME = 29;
    public static final String NOTENAME = "notename";
    public static final int REQUEST_CODE_AT_MEMBER = 30;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int RESULT_CODE_AT_ALL = 32;
    public static final int SEARCH_AT_MEMBER_CODE = 33;
    public static final String TARGET_ID = "targetId";


    public static final String TARGET_APP_KEY = "targetAppKey";


    public static final String DRAFT = "draft";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String NAME = "name";
    public static final String ATALL = "atall";
    public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
    public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
    public static final String SEARCH_AT_APPKEY = "search_at_appkey";

    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int REQUEST_CODE_SEND_LOCATION = 24;
    public static final int REQUEST_CODE_FRIEND_INFO = 16;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final int ON_GROUP_EVENT = 3004;
    private String baseurl;
    public static List<CicleBean.ReturnObjectBean.RowsBean> selectLst = new ArrayList<>();


    public Api getApi() {


        if (api == null) {
            api = getRetrofit().create(Api.class);
        }
        return api;
    }

    @Override
    public void onCreate() {

//判断当前进程是否是应用的主进程
        if (isMainProcess()) {
            ShareInstall.getInstance().init(getApplicationContext());
        }

        ViewTarget.setTagId(R.id.glide_tag);
        CardUtils.init();
        //有盟统计
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);

        //  Density.setDensity(this, 360);
        MultiDex.install(this);
        //极光

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        StorageUtil.init(this, null);

        Fresco.initialize(getApplicationContext());


        JMessageClient.init(this, true);

        CrashHandler crashHandler = new CrashHandler(this);
        // 设置之后,出了异常,执行框架中的catch
        // 不加,程序出现异常,会直接结束
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        super.onCreate();
        instance = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        this.mMainTheadId = Process.myTid();//主線程id
        Exclude ex = new Exclude();
        gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();


        activitys = new LinkedList<>();


        // CrashReport.initCrashReport(getApplicationContext(), "2727bc43ca", true);

        Bugly.init(getApplicationContext(), "2727bc43ca", false);


        updateAPP();
        initImagePicker();
        closeAndroidPDialog();

        new NotificationClickEventReceiver(getApplicationContext());

    }

    private void updateAPP() {


    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(9);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }

    private Retrofit initRetrofit() {
        baseurl = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request oldRequest = chain.request();

                                HttpUrl newBaseUrl = null;
                                Request.Builder builder = oldRequest.newBuilder();
                                //从request中获取headers，通过给定的键url_name
                                List<String> headerValues = oldRequest.headers("base_url");
                                if (headerValues != null && headerValues.size() > 0) {


                                    newBaseUrl = HttpUrl.parse(URL.PICTUREPATH);
                                    HttpUrl oldHttpUrl = oldRequest.url();

                                    //重建新的HttpUrl，修改需要修改的url部分
                                    HttpUrl newFullUrl = oldHttpUrl

                                            .newBuilder()

                                            .scheme(newBaseUrl.scheme())

                                            .host(newBaseUrl.host())

                                            .port(newBaseUrl.port())

                                            .build();
                                    return chain.proceed(builder.url(newFullUrl).build());
                                }

                                return chain.proceed(oldRequest);
                            }
                        }
                ).retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder();

        Retrofit retrofit = builder
                .addConverterFactory(StringConverterFactory.create())
                .client(client)
                .baseUrl(URL.versionUrl)
                .build();
        return retrofit;

    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {

            retrofit = initRetrofit();
        }
        return retrofit;
    }

    public boolean isNetContect() {
        return isNetContect;
    }

    public void setNetContect(boolean netContect) {
        isNetContect = netContect;
    }

    public String getVerification_key() {
        return verification_key;
    }

    public void setVerification_key(String verification_key) {
        this.verification_key = verification_key;
    }

    public Gson getGson() {
        return gson;
    }

    public void saveLocal(String token, int type) {
        SharedPreferencesUtil.saveLocal(getApplicationContext(), token, type);
    }

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            setToken((String) SharedPreferencesUtil.getLocal(this).get("token"));
            String type = (String) SharedPreferencesUtil.getLocal(this).get("type");
        }
        return token;
    }

    public User getUser() {
        if (user == null) {
            // Error.showError("登陆超时", getInstance());
            return null;
        } else if (user.getUserId() == 0) {
            //   Error.showError("登陆超时", getInstance());
            return null;
        }
//        if (user == null) {
//            return new User(190, "15333003834", "测试用户");
//        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
        //token修改后retrofit没反应过来
        if (retrofit != null) {
            retrofit = null;
        }
//        retrofit = initRetrofit();
    }


    public static MyAPP getInstance() {
        return instance;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void finishActivity() {
        for (Activity activity : activityList) {
            try {
                activity.finish();
            } catch (Exception e) {
                //       Log.e("tag", e.getMessage());
            }
        }

        // 杀死进程,不然不会再次执行oncreate()
        Process.killProcess(Process.myPid());
    }

    public void finishActivityClass() {
        for (Activity activity : activityList) {

            if (activity.getClass() == HomeActivity.class) {

            } else {
                try {
                    activity.finish();
                } catch (Exception e) {
                    //       Log.e("tag", e.getMessage());
                }
            }

        }

        // 杀死进程,不然不会再次执行oncreate()
        Process.killProcess(Process.myPid());
    }


    /**
     * @param activity
     * @方法名称:addActivity
     * @描述: 添加Activity到容器中
     * @创建人：王士杰
     * @创建时间：2014-11-26 下午3:06:54
     * @备注：
     * @返回类型：void
     */
    public void addActivity(Activity activity) {
//        if (activitys != null && activitys.size() > 0) {
//            if (!activitys.contains(activity)) {
//                activitys.add(activity);
//            }
//        } else {
        activitys.add(activity);
        //  }
    }


    /**
     * @param activity
     * @方法名称:removeActivity
     * @描述: 将Activity移出容器中
     * @创建人：王士杰
     * @创建时间：2014-11-26 下午3:06:07
     * @备注：
     * @返回类型：void
     */
    public void removeActivity(Activity activity) {
        if (activitys != null) {
            if (activitys.contains(activity)) {
                activitys.remove(activity);
            }
        }
    }

    public void finishSingleActivity(Class<?> cls) {
        Activity tempActivity = null;
        for (Activity activity : activitys) {
            if (activity.getClass().equals(cls)) {
                tempActivity = activity;
            }
        }
        killActivity(tempActivity);
    }

    private void killActivity(Activity ac) {
        if (ac != null) {
            ac.finish();
        }
    }

    /**
     * @方法名称:exit
     * @描述: 遍历所有Activity并finish
     * @创建人：王士杰
     * @创建时间：2014-11-26 下午3:05:58
     * @备注：
     * @返回类型：void
     */
    public void exit() {
        if (activitys == null || activitys.size() <= 0) {
            return;
        }
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前进程是否是应用的主进程
     *
     * @return
     */
    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }


}
