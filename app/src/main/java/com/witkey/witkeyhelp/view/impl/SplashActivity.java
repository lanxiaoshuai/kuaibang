package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.smarx.notchlib.NotchScreenManager;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;

import com.witkey.witkeyhelp.bean.User;

import com.witkey.witkeyhelp.presenter.ILoginPresenter;

import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.SpUtils;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by jie on 2019/12/12.
 */

public class SplashActivity extends AppCompatActivity {
    protected String TAG = "llx";
    private ILoginPresenter presenter;
    // private ImageView iv_splash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        NotchScreenManager.getInstance().setDisplayInNotch(this);
        if (SpUtils.getBoolean(this, "protocol", false)) {
            enter();
        } else {
            popub();
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
                    Log.e(TAG, logs);
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
                    Log.d("tag", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i("tag", "Unhandled msg - " + msg.what);
            }
        }
    };


    private void popub() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.protocol_popub, null, false);//引入弹窗布局


        final SpannableStringBuilder style = new SpannableStringBuilder();

        style.append("亲爱的用户，欢迎使用本软件，请您使用前\n" +
                "务必充分阅读我们的\"隐私政策和用户协议\"" +
                "\n各条款，在您使用\"酷爱帮\"APP时，我们" +
                "\n将严格按照隐私政策和用户协议为您提" +
                "供服务，保护您的个人信息。\n" +
                "如您同意，请点击\"同意使用\"开始使用我们的\n" +
                "产品和服务");
        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {

                startActivity(new Intent(SplashActivity.this, RegistrationActivity.class).putExtra("weburl", URL.versionUrl + "api/share/terms"));
            }
        };
        style.setSpan(clickableSpan, 30, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3AA3FF"));
        style.setSpan(foregroundColorSpan, 30, 39, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView agreement = vPopupWindow.findViewById(R.id.agreement);

        agreement.setMovementMethod(LinkMovementMethod.getInstance());
        agreement.setText(style);
        //设置部分文字颜色
        TextView notusednow = vPopupWindow.findViewById(R.id.notusednow);
        TextView greeuse = vPopupWindow.findViewById(R.id.greeuse);

        final View parentView = LayoutInflater.from(this).inflate(R.layout.activity_splash, null);
        final PopupWindow popupWindow = new PopupWindow(vPopupWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        notusednow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                finish();
            }
        });
        greeuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                SpUtils.putBoolean(SplashActivity.this, "protocol", true);
                enter();
            }
        });
        findViewById(R.id.img).post(new Runnable() {
            @Override
            public void run() {


                try {
                    addBackgroundPopub(popupWindow);
                    popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

    }

    private void addBackgroundPopub(PopupWindow popup) {


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void enter() {
        MyAPP.getInstance().addActivity(this);
//        iv_splash = findViewById(R.id.iv_splash);
//        iv_splash.setImageResource(R.mipmap.iv_splash);
        if (lacksPermissions(this, permissionsREAD)) {//地理位置权限
            ActivityCompat.requestPermissions(this, permissionsREAD, 0);
        } else {
            Thread myThread = new Thread() {//创建子线程
                @Override
                public void run() {
                    try {
                        boolean guide = SpUtils.getBoolean(SplashActivity.this, "GUIDE", false);
                        if (guide) {
                            final User user = SpUtils.getObject(SplashActivity.this, "LOGIN");
                            if (user == null) {
                                sleep(500);//使程序休眠一秒
                                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(it);
                                overridePendingTransition(0, 0);
                                finish();//关闭当前活动
                            } else {
                                sleep(500);//使程序休眠一秒
                                JMessageClient.login(user.getUserName(), "123456", new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (responseCode == 0) {

                                            setAlias(user.getUserName());

                                        }
                                    }
                                });

                                Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(it);
                                overridePendingTransition(0, 0);
                                finish();//关闭当前活动
//                        presenter.Login(new LoginRequest(user.getUserName(), user.getPassword(), true));
                            }

                        } else {
                            sleep(1000);//使程序休眠一秒
                            Intent it = new Intent(getApplicationContext(), ActivityBootPage.class);
                            startActivity(it);
                            overridePendingTransition(0, 0);
                            finish();//关闭当前活动
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();//启动线程

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {

                    // 权限被拒绝，弹出dialog 提示去开启权限
                    //         showPermissions();

                    break;
                }

            }


            Thread myThread = new Thread() {//创建子线程
                @Override
                public void run() {
                    try {
                        boolean guide = SpUtils.getBoolean(SplashActivity.this, "GUIDE", false);
                        if (guide) {
                            final User user = SpUtils.getObject(SplashActivity.this, "LOGIN");
                            if (user == null) {
                                sleep(500);//使程序休眠一秒
                                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(it);
                                overridePendingTransition(0, 0);
                                finish();//关闭当前活动
                            } else {
                                sleep(500);//使程序休眠一秒
//                                JMessageClient.login(user.getUserName(), "123456", new BasicCallback() {
//                                    @Override
//                                    public void gotResult(int responseCode, String responseMessage) {
//                                        if (responseCode == 0) {
//
//                                            setAlias(user.getUserName());
//
//                                        }
//                                    }
//                                });

                                Intent it = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(it);
                                overridePendingTransition(0, 0);
                                finish();//关闭当前活动
//                        presenter.Login(new LoginRequest(user.getUserName(), user.getPassword(), true));
                            }

                        } else {
                            sleep(1000);//使程序休眠一秒
                            Intent it = new Intent(getApplicationContext(), ActivityBootPage.class);
                            startActivity(it);
                            overridePendingTransition(0, 0);
                            finish();//关闭当前活动
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();//启动线程

        }

    }


    /**
     * 照相权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREAD = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

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

}
