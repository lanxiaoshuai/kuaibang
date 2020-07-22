package com.witkey.witkeyhelp.view.impl.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.util.DialogCreator;
import com.witkey.witkeyhelp.util.FileHelper;
import com.witkey.witkeyhelp.util.SharePreferenceManager;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.StatusbarColorUtils;
import com.witkey.witkeyhelp.util.SystemBarTintManager;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public class BaseMessageToActivity extends SwipeBackActivity {

    protected int mWidth;
    protected int mHeight;
    protected float mDensity;
    protected int mDensityDpi;
    private TextView mJmui_title_tv;
    private ImageButton mReturn_btn;
    private TextView mJmui_title_left;
    public Button mJmui_commit_btn;
    protected int mAvatarSize;
    protected float mRatio;
    private Dialog dialog;

    /**
     * 判断状态栏的字体要亮色还是暗色
     *
     * @return true亮色, false暗色
     */
    protected boolean isLight() {
        return false;
    }
    private ViewGroup fwRootLayout;

    /**
     * 配置状态栏
     */
    private SystemBarTintManager tintManager;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //注册sdk的event用于接收各种event事件
        JMessageClient.registerEventReceiver(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);
        initSystemBar();
    }

    //初始化各个activity的title
    public void initTitle(boolean returnBtn, boolean titleLeftDesc, String titleLeft, String title, boolean save, String desc) {
        mReturn_btn = (ImageButton) findViewById(R.id.return_btn);
        mJmui_title_left = (TextView) findViewById(R.id.jmui_title_left);
        mJmui_title_tv = (TextView) findViewById(R.id.jmui_title_tv);
        mJmui_commit_btn = (Button) findViewById(R.id.jmui_commit_btn);

        if (returnBtn) {
            mReturn_btn.setVisibility(View.VISIBLE);
            mReturn_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        if (titleLeftDesc) {
            mJmui_title_left.setVisibility(View.VISIBLE);
            mJmui_title_left.setText(titleLeft);
        }
        mJmui_title_tv.setText(title);
        if (save) {
            mJmui_commit_btn.setVisibility(View.VISIBLE);
            mJmui_commit_btn.setText(desc);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    public void goToActivity(Context context, Class toActivity) {
        Intent intent = new Intent(context, toActivity);
        startActivity(intent);
        finish();
    }

//    public void onEventMainThread(LoginStateChangeEvent event) {
//        final LoginStateChangeEvent.Reason reason = event.getReason();
//        UserInfo myInfo = event.getMyInfo();
//        if (myInfo != null) {
//            String path;
//            File avatar = myInfo.getAvatarFile();
//            if (avatar != null && avatar.exists()) {
//                path = avatar.getAbsolutePath();
//            } else {
//                path = FileHelper.getUserAvatarPath(myInfo.getUserName());
//            }
//            SharePreferenceManager.setCachedUsername(myInfo.getUserName());
//            SharePreferenceManager.setCachedAvatarPath(path);
//            JMessageClient.logout();
//        }
//        switch (reason) {
//            case user_logout:
//                View.OnClickListener listener = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (v.getId()) {
//                            case R.id.jmui_cancel_btn:
//                                JMessageClient.logout();
//                                MyAPP.getInstance().exit();
//                                Intent intent = new Intent(BaseMessageToActivity.this, LoginActivity.class);
//                                intent.putExtra("type", "0");
//                                startActivity(intent);
//                                SpUtils.putObject(BaseMessageToActivity.this, "LOGIN", null);
//                                dialog.dismiss();
//                                finish();
//                                break;
//                            case R.id.jmui_commit_btn:
//                                User user = SpUtils.getObject(BaseMessageToActivity.this, "LOGIN");
//                                JMessageClient.login(user.getUserName(), "123456", new BasicCallback() {
//                                    @Override
//                                    public void gotResult(int responseCode, String responseMessage) {
//                                        if (responseCode == 0) {
//                                            ToastUtils.showTestShort(BaseMessageToActivity.this, "登录成功");
//                                            dialog.dismiss();
//                                        } else {
//                                            ToastUtils.showTestShort(BaseMessageToActivity.this, "登录失败，请重新登录");
//                                            JMessageClient.logout();
//                                            MyAPP.getInstance().exit();
//                                            Intent intent = new Intent(BaseMessageToActivity.this, LoginActivity.class);
//                                            intent.putExtra("type", "0");
//                                            startActivity(intent);
//                                            SpUtils.putObject(BaseMessageToActivity.this, "LOGIN", null);
//                                            dialog.dismiss();
//                                            finish();
//                                        }
//                                    }
//                                });
//                                break;
//                        }
//                    }
//                };
//                dialog = DialogCreator.createLogoutStatusDialog(BaseMessageToActivity.this, "您的账号在其他设备上登陆", listener);
//                dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
//                break;
//            case user_password_change:
//                Intent intent = new Intent(BaseMessageToActivity.this, LoginActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }
    @Override
    public void onDestroy() {
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }


}
