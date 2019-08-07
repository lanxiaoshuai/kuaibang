package com.witkey.witkeyhelp.view.impl.base;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.InternetDia;
import com.witkey.witkeyhelp.services.ReceiveMsgService;
import com.witkey.witkeyhelp.util.CalendarUtil;
import com.witkey.witkeyhelp.util.StatusbarColorUtils;
import com.witkey.witkeyhelp.util.SystemBarTintManager;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IView;
import com.witkey.witkeyhelp.util.Error;


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

    private Bundle savedInstanceState;

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // fragment:getActivity()空指针解决方法
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
            //注意：基类是Activity时参数为android:fragments， 一定要在super.onCreate函数前执行！！！
        }
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
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
        bind();
    }

    /**
     * 是否需要user信息
     * @return
     *  false 不需要
     *  true 需要
     */
    protected boolean isGetUser() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGetUser()) {
            user = MyAPP.getInstance().getUser();
        }
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


    /**
     * 绑定服务
     */
    private void bind() {
        Intent intent = new Intent(BaseActivity.this, ReceiveMsgService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 监控网络的service
     */
    protected ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            receiveMsgService = ((ReceiveMsgService.MyBinder) service)
                    .getService();
            receiveMsgService.setOnGetConnectState(new ReceiveMsgService.GetConnectState() { // 添加接口实例获取连接状态
                @Override
                public void GetState(boolean isConnected) {
                    if (conncetState != isConnected) { // 如果当前连接状态与广播服务返回的状态不同才进行通知显示
                        conncetState = isConnected;
                        if (conncetState) {// 已连接
                            handler.sendEmptyMessage(1);
                        } else {// 未连接
                            handler.sendEmptyMessage(2);
                        }
                    }
                }
            });
        }
    };

    //监听网络
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
                    ToastUtils.showShort(mActivity, "网络已经连接,请刷新", 3);
                    //网络恢复,自动刷新界面
//                    startRefresh(targetView, callback);
                    break;
                case 2:// 未连接
                    Log.d(TAG, "handleMessage:网络未连接 ");
                    if (!mActivity.isFinishing()) {
                        dialog.show();
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
     * 当前界面关闭时的操作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog = null;
        MyAPP.getInstance().activityList.remove(this);
    }

    /**
     * 发生错误时操作
     */
    @Override
    public void onError(String error) {
        DialogUtil.dismissProgress();
        if (!"请确认网络已连接~~".equals(error)) {
            Log.d(TAG, "onError: " + error);
            Error.showError(error, mActivity);
        }
//        // ReplaceView: 进行覆盖errorView
//        if (callback != null) {
//            if (callback.isDefaultError()) {
//                showErrorView();
//            }
//        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//update date: 2019/7/12 12:25
//author:lingxu

    //替换view的util
//    public ReplaceViewHelper replaceViewHelper;
//    /**
//     * ReplaceView: 覆盖上errorView
//     */
//    protected void showErrorView() {
//        if (replaceViewHelper != null) {
//            //删除refreshView
//            removeOtherView();
//            //显示errorView
//            replaceViewHelper.toReplaceView(targetView, R.layout.layout_error);
//            isMainView = false;
//            //刷新操作
//            replaceViewHelper.getView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //判断是否联网,联网才进行刷新
//                    if (MyAPP.getInstance().isNetContect()) {
//                        //错误时
//                        startRefresh(targetView, callback);
//                    } else {
//                        Toast("请检查网络哦~", 3);
//                    }
//                }
//            });
//        }
//    }
//
//    /**
//     * 当前是否为原本的view
//     */
//    private boolean isMainView = true;
//
//    public interface IIsDefaultCallback {
//        /**
//         * 判断是否显示为默认的错误
//         *
//         * @return true 默认 false 非默认,手动调用showErrorView()
//         */
//        boolean isDefaultError();
//
//        /**
//         * 刷新操作
//         */
//        void refreshWork();
//    }
//
//    private IIsDefaultCallback callback;
//
//    /**
//     * ReplaceView: 开启刷新
//     *
//     * @param targetView 要覆盖的view
//     */
//    protected void startRefresh(View targetView, IIsDefaultCallback callback) {
//        if (targetView != null) {
//            this.callback = callback;
//            this.targetView = targetView;
//            if (targetView != null) {
//                //刷新操作
//                if (replaceViewHelper == null) {
//                    replaceViewHelper = new ReplaceViewHelper(mActivity);
//                }
//                if (!isMainView) {
//                    removeOtherView();
//                }
//                replaceViewHelper.toReplaceView(targetView, R.layout.layout_refresh);
//                callback.refreshWork();
//                isMainView = false;
//            }
//        }
//    }
//
//    /**
//     * 成功后删除
//     * ReplaceView: 删除覆盖的View
//     */
//    protected void removeOtherView() {
//        if (replaceViewHelper != null) {
//            replaceViewHelper.removeView();
//            isMainView = true;
//        }
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //封装通用toast方法
    protected void Toast(String info, int state) {
        //0 不显示 1 success 2 fail 3 alert
        ToastUtils.showShort(this, info, state);
    }

    /**
     * 获取当前年月日
     */
//    protected void getTime() {
//        Calendar cal = Calendar.getInstance();
//        monthOfYear = cal.get(Calendar.MONTH);
//        dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//        year = cal.get(Calendar.YEAR);
//    }


    private DatePickerDialog datePickerDialog;

    /**
     * 显示日历dialog
     *
     * @param tv 要显示到那个textview
     */
    protected DatePickerDialog showCalendarDialog(final TextView tv) {
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(mActivity, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tv.setText(setTimeShow(year, monthOfYear, dayOfMonth));
                }
            }, CalendarUtil.getTime()[0], CalendarUtil.getTime()[1], CalendarUtil.getTime()[2]);
        }
        datePickerDialog.show();
        return datePickerDialog;
    }

    /**
     * 设置时间显示
     *
     * @return xxxx-xx-xx
     */
    protected String setTimeShow(int year, int monthOfYear, int dayOfMonth) {
        String format = "%02d";
        return year + "-" + String.format(format, (monthOfYear + 1)) + "-" + String.format(format, dayOfMonth);
    }

//    /**
//     * 跳转输入界面
//     *
//     * @param tv          传入数据所在的tv
//     * @param requestCode 设置返回值
//     */
//    protected void intentChangeInfo(TextView tv, int requestCode) {
//        String str = tv.getText().toString();
//        Intent i = new Intent(getApplicationContext(), ChangeInfoActivity.class);
//        i.putExtra(Contacts.EXTRAS_CHANGE_INFO, requestCode);
//        i.putExtra(Contacts.EXTRAS_CHANGE_INFO_DETAIL, str.equals("请填写") ? null : str);
//        startActivityForResult(i, requestCode);
//    }
//
//    protected void intentChangeInfo(ChangeInfoBean changeInfoBean) {
//        Intent i = new Intent(getApplicationContext(), ChangeInfoActivity.class);
//        i.putExtra(Contacts.EXTRAS_CHANGE_INFO_DETAIL, changeInfoBean);
//        startActivityForResult(i, changeInfoBean.getDifCode());
//    }

}
