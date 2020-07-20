package com.witkey.witkeyhelp.view.impl.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.InternetDia;
import com.witkey.witkeyhelp.event.GeographyBean;
import com.witkey.witkeyhelp.event.InternetEvent;
import com.witkey.witkeyhelp.event.LoginEvent;
import com.witkey.witkeyhelp.services.ReceiveMsgService;
import com.witkey.witkeyhelp.util.DialogCreator;
import com.witkey.witkeyhelp.util.FileHelper;
import com.witkey.witkeyhelp.util.SharePreferenceManager;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.StatusbarColorUtils;
import com.witkey.witkeyhelp.util.SystemBarTintManager;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IView;
import com.witkey.witkeyhelp.util.Error;
import com.witkey.witkeyhelp.view.impl.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.Set;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


/**
 * @author lingxu
 * @date 2019/7/12 14:13
 * @description
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {
    /**
     * 上下文
     **/
    protected Activity mActivity;
    protected String TAG = "llx";
    /**
     * 配置状态栏
     */
    private SystemBarTintManager tintManager;
    /**
     * window中的最顶层view
     */
    private ViewGroup fwRootLayout;

    protected User user; //判断是否登录

    /////////////////////////////////////////////////
    //联网操作
    private InternetDia dialog;
    private ReceiveMsgService receiveMsgService;
    /**
     * 记录当前连接状态，因为广播会接收所有的网络状态改变wifi/3g等等，所以需要一个标志记录当前状态
     */
    private boolean conncetState = true;
    //////////////////////////////////////////////////
    protected View targetView;
    protected boolean isRefresh;


    public static boolean NETWORKBOOLEAN = true;

    static {
        //使用svg图片
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected abstract void onCreateActivity();


    // getActivity()空指针解决方法
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //注释掉该方法， 即不保存状态
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * (eg:横竖屏切换)
     * 当一种或者多种配置改变时避免重新启动你的activity
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }



    private  Bundle savedInstanceState;
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    protected float mDensity;
    protected int mDensityDpi;
    protected int mWidth;
    protected int mHeight;
    protected float mRatio;
    protected int mAvatarSize;
    private Context mContext;
    private Dialog dialoglogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState=savedInstanceState;
        // fragment:getActivity()空指针解决方法
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
            //注意：基类是Activity时参数为android:fragments， 一定要在super.onCreate函数前执行！！！
        }
        super.onCreate(savedInstanceState);
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        if (isGetUser()) {
            user = SpUtils.getObject(this, "LOGIN");
        } else {


        }
        mActivity = BaseActivity.this;

        //配置状态栏
        initSystemBar();

        //添加activity到app,进行管理
        try {
            MyAPP app = (MyAPP) getApplication();
            app.activityList.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Activity操作
        onCreateActivity();
        //如果为绑定,即绑定
        //bind();


        JMessageClient.registerEventReceiver(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);


        MyAPP.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
         //initReceiver();
    }


    public void onEventMainThread(LoginStateChangeEvent event) {

        final LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        if (myInfo != null) {
            String path;
            File avatar = myInfo.getAvatarFile();
            if (avatar != null && avatar.exists()) {
                path = avatar.getAbsolutePath();
            } else {
                path = FileHelper.getUserAvatarPath(myInfo.getUserName());
            }
            SharePreferenceManager.setCachedUsername(myInfo.getUserName());
            SharePreferenceManager.setCachedAvatarPath(path);
            JMessageClient.logout();
        }
        switch (reason) {
            case user_logout:
                setAlias("");
                JMessageClient.logout();
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.jmui_cancel_btn:
//                                setAlias("");
//                                JMessageClient.logout();
                                MyAPP.getInstance().exit();
                                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                intent.putExtra("type", "0");
                                startActivity(intent);
                                SpUtils.putObject(BaseActivity.this, "LOGIN", null);
                                dialoglogin.dismiss();
                                finish();
                                break;
                            case R.id.jmui_commit_btn:
                                final User user = SpUtils.getObject(BaseActivity.this, "LOGIN");
                                JMessageClient.login(user.getUserName(), "123456", new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (responseCode == 0) {

                                            setAlias(user.getUserName());
                                            ToastUtils.showTestShort(BaseActivity.this, "登录成功");
//                                            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
//                                            intent.putExtra("type", "0");
                                            dialoglogin.dismiss();
                                            EventBus.getDefault().post(new LoginEvent("响应事件"));
                                        } else {
                                            setAlias("");
                                            ToastUtils.showTestShort(BaseActivity.this, "登录失败，请重新登录");
                                            JMessageClient.logout();
                                            MyAPP.getInstance().exit();
                                            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                                            intent.putExtra("type", "0");
                                            startActivity(intent);
                                            SpUtils.putObject(BaseActivity.this, "LOGIN", null);
                                            dialoglogin.dismiss();
                                            finish();
                                        }
                                    }
                                });
                                dialoglogin.dismiss();
                                break;
                        }
                    }
                };
                dialoglogin = DialogCreator.createLogoutStatusDialog(this, "您的账号在其他设备上登录", listener);
                dialoglogin.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
                dialoglogin.setCanceledOnTouchOutside(false);
                dialoglogin.setCancelable(false);
                dialoglogin.show();
                break;
            case user_password_change:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void setAlias(String name) {
        // EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
        String alias = name;

//        if (TextUtils.isEmpty(alias)) {
//            // Toast.makeText(this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!ExampleUtil.isValidTagAndAlias(alias)) {
//            //  Toast.makeText(this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;

            }
            // ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };



    @Override
    protected void onStart() {
        super.onStart();
        MobclickAgent.onPageStart(getClass().getSimpleName());


    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);

    }

    /**
     * 是否需要user信息
     *
     * @return false 不需要
     * true 需要
     */
    protected boolean isGetUser() {
        return false;
    }


    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(LoginEvent event) {
        // 响应事件

        dialoglogin.dismiss();
    }

    /**
     * 设置字体不随着手机系统设置而变化
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Android点击EditText文本框之外任何地方隐藏键盘的解决办法
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                closeInputMethod();
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 收起键盘
     */
    protected void closeInputMethod() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 是否应该隐藏输入
     */
    protected boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    /**
//     * 绑定服务
//     */
//    private void bind() {
//        Intent intent = new Intent(BaseActivity.this, ReceiveMsgService.class);
//        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//    }

//    /**
//     * 监控网络的service
//     */
//    protected ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            receiveMsgService = ((ReceiveMsgService.MyBinder) service)
//                    .getService();
//            receiveMsgService.setOnGetConnectState(new ReceiveMsgService.GetConnectState() { // 添加接口实例获取连接状态
//                @Override
//                public void GetState(boolean isConnected) {
//                    if (conncetState != isConnected) { // 如果当前连接状态与广播服务返回的状态不同才进行通知显示
//                        conncetState = isConnected;
//                        if (conncetState) {// 已连接
//                            handler.sendEmptyMessage(1);
//                            NETWORKBOOLEAN = true;
//                        } else {// 未连接
//                            handler.sendEmptyMessage(2);
//                            NETWORKBOOLEAN = false;
//                        }
//                    }
//                }
//            });
//        }
//    };
//    private NetworkReceiver mReceiver;
//    private void initReceiver() {
//        mReceiver = new NetworkReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(mReceiver, filter);
//    }

    //监听网络状态的广播
    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (null == activeInfo) {
                    handler.sendEmptyMessage(2);
                    NETWORKBOOLEAN = true;

                } else {
                    handler.sendEmptyMessage(1);
                    NETWORKBOOLEAN = false;

                 EventBus.getDefault().post(new InternetEvent("有网"));
                }
            }
        }
    }
    //监听网络
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (dialog == null) {
                dialog = new InternetDia(mActivity);
            }
            switch (msg.what) {
                case 1:// 已连接
                    if (dialog != null || dialog.isShowing()) {
                        dialog.dismiss();
                    }
                   // ToastUtils.showShort(mActivity, "网络已经连接,请刷新", 3);
                    //网络恢复,自动刷新界面
//                    startRefresh(targetView, callback);
                    break;
                case 2:// 未连接
                    Log.d(TAG, "handleMessage:网络未连接 ");
                    if (!mActivity.isFinishing()) {
                        //dialog.show();
                        onError("请确认网络已连接~~");
                    }
                    break;
            }
        }
    };

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 判断状态栏的字体要亮色还是暗色
     *
     * @return true亮色, false暗色
     */
    protected boolean isLight() {
        return false;
    }

    /**
     * 配置状态栏
     */
    public void initSystemBar() {
        //界面中设置FitsSystemWindows会有灰条
        fwRootLayout = (ViewGroup) getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //FLAG_TRANSLUCENT_STATUS 华为手机不显示
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

           getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            fwRootLayout.setSystemUiVisibility(option);
            //设置不起作用
//            fwRootLayout.setFitsSystemWindows(true);//需要把根布局设置为这个属性 子布局则不会占用状态栏位置
//            fwRootLayout.setClipToPadding(true);//需要把根布局设置为这个属性 子布局则不会占用状态栏位置

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        tintManager = new SystemBarTintManager(this);// 创建状态栏的管理实例
        tintManager.setStatusBarTintEnabled(true);// 激活状态栏设置
        tintManager.setNavigationBarTintEnabled(true);// 激活导航栏设置
//        tintManager.setStatusBarTintColor(0xff111111);//设置状态栏颜色
//        tintManager.setStatusBarTintResource(Color.RED);//设置状态栏颜色

        if (isLight()) {
            tintManager.setStatusBarDarkMode(false, this);//false 状态栏字体颜色是白色 MIUI
            fwRootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); //HUAWEI
            StatusbarColorUtils.setStatusBarDarkIcon(this, false);  //参数 false 白色 true 黑色 MEIZU
        } else {
            tintManager.setStatusBarDarkMode(true, this);//false 状态栏字体 true 颜色是黑色
           fwRootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusbarColorUtils.setStatusBarDarkIcon(this, true);  //参数 false 白色 true 黑色
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置了Onclick 返回
     */
    public void goBack(View v) {
        closeInputMethod();
        finish();
    }


    /**
     * 设置标题
     */
    public void setIncludeTitle(String title) {
        TextView tv = (TextView) findViewById(R.id.include_title);
        if (tv != null)
            tv.setText(title);
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 发生错误时操作
     */
    @Override
    public void onError(String error) {
        DialogUtil.dismissProgress();
        if (!"请确认网络已连接~~".equals(error)) {

            Error.showError(error, mActivity);

        }

    }




    //封装通用toast方法
    protected void Toast(String info, int state) {
        //0 不显示 1 success 2 fail 3 alert
        ToastUtils.showShort(this, info, state);
    }












    /**
     * 获取到权限进行的操作
     */
    protected void startLoad() {
        //  getLocationClient();
        EventBus.getDefault().post(new GeographyBean("获取地理位置成功"));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog = null;
        MyAPP.getInstance().activityList.remove(this);
        EventBus.getDefault().unregister(this);

        //  mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

   //     unbindService(serviceConnection);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //  mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }


}
