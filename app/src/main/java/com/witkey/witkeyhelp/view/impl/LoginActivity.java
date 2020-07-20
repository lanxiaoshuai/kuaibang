package com.witkey.witkeyhelp.view.impl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sh.sdk.shareinstall.ShareInstall;
import com.sh.sdk.shareinstall.listener.AppGetInstallListener;
import com.sh.sdk.shareinstall.listener.AppGetWakeUpListener;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.LoginRequest;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.event.LoginEvent;
import com.witkey.witkeyhelp.presenter.ILoginPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.LoginPresenterImpl;
import com.witkey.witkeyhelp.util.CountDownUtil;
import com.witkey.witkeyhelp.util.ExampleUtil;
import com.witkey.witkeyhelp.util.FormatUtil;
import com.witkey.witkeyhelp.util.GetDeviceId;
import com.witkey.witkeyhelp.util.SharedPreferencesUtil;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.util.wight.TextWatcherAdapter;
import com.witkey.witkeyhelp.view.ILoginView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;


public class LoginActivity extends InitPresenterBaseActivity implements View.OnClickListener, ILoginView {
    private EditText etName;
    private EditText etPass;
    private EditText etCode;
    private RelativeLayout rl_code;


    private TextView bt_code;

    //    private CheckBox cb_rm_pass; //记住密码不要
    private TextView tv_forget_pass;


    private ImageView iv_login_qq;
    private ImageView iv_login_wechat;

    private Button bt_login;
    private TextView tv_change_state;

    private boolean isPass = true; //是否为密码登录
//    private boolean isSave = true; //默认记住密码

    private ILoginPresenter presenter;
    private String verifyCode; //生成的验证码
    private boolean home;
    private String extra_page_type;
    private RelativeLayout yaoqing_layout;
    private EditText et_nvitation_code;
    private LinearLayout regis_agreement;
    private CheckBox cb_is;
    private boolean checkRegister;
    private TextView registrationagreement;
    private ImageView eyesClosed;

    private boolean aBoolean;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }


    @Override
    protected IPresenter[] getPresenters() {
        presenter = new LoginPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }


    @Override
    public void initEvent() {
        //切换显示
        tv_change_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPass == true) {
                    isPass = false;
                    etName.setHint("请输入手机号");
                    etName.setInputType(InputType.TYPE_CLASS_PHONE);
                    tv_change_state.setText("密码登录");
                    tv_forget_pass.setVisibility(View.GONE);
                    bt_login.setText("注 册");
                    rl_code.setVisibility(View.VISIBLE);
                    yaoqing_layout.setVisibility(View.VISIBLE);
                    regis_agreement.setVisibility(View.VISIBLE);
                } else {
                    isPass = true;
                    etName.setHint("请输入账号/手机号/邮箱");
                    etName.setInputType(InputType.TYPE_CLASS_TEXT);
                    tv_change_state.setText("快速注册");
                    tv_forget_pass.setVisibility(View.VISIBLE);
                    bt_login.setText("登 录");
                    rl_code.setVisibility(View.GONE);
                    yaoqing_layout.setVisibility(View.GONE);
                    regis_agreement.setVisibility(View.GONE);

                }
                etPass.setText("");
                etCode.setText("");
            }
        });
        bt_code.setOnClickListener(this);
        bt_login.setOnClickListener(this);
//        修改数据
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etPass.setText("");
                etCode.setText("");
            }
        });
        //记住密码操作
//        cb_rm_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                登录成功是否保存密码
//                isSave = isChecked;
//            }
//        });
        etPass.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    eyesClosed.setVisibility(View.VISIBLE);
                } else {
                    eyesClosed.setVisibility(View.GONE);
                }
            }
        });
        tv_forget_pass.setOnClickListener(this);
        iv_login_qq.setOnClickListener(this);
        iv_login_wechat.setOnClickListener(this);

        getLocationClient();
    }


    @Override
    protected void initViewExceptPresenter() {
        //显示已保存的用户名跟密码
        etName.setText((String) SharedPreferencesUtil.getNamePass(getApplicationContext()).get("name"));
        etPass.setText((String) SharedPreferencesUtil.getNamePass(getApplicationContext()).get("pass"));
//        if (!MyAPP.ISRELEASE) {
//            etName.setText("");
//            etPass.setText("");
//        }
    }

    @Override
    protected void initWidget() {


        home = getIntent().getBooleanExtra("home", false);

        extra_page_type = getIntent().getStringExtra("EXTRA_PAGE_TYPE");

        tv_change_state = (TextView) findViewById(R.id.tv_change_state);
        etName = (EditText) findViewById(R.id.et_name);
        etPass = (EditText) findViewById(R.id.et_pass);
        etCode = (EditText) findViewById(R.id.et_code);
        rl_code = (RelativeLayout) findViewById(R.id.rl_code);
        bt_code = (TextView) findViewById(R.id.bt_code);
        bt_login = (Button) findViewById(R.id.bt_login);
//        cb_rm_pass = findViewById(R.id.cb_rm_pass);
        tv_forget_pass = findViewById(R.id.tv_forget_pass);
        iv_login_qq = findViewById(R.id.iv_login_qq);
        iv_login_wechat = findViewById(R.id.iv_login_wechat);

        yaoqing_layout = findViewById(R.id.yaoqing_layout);
        et_nvitation_code = findViewById(R.id.et_nvitation_code);
        regis_agreement = findViewById(R.id.regis_agreement);
        eyesClosed = findViewById(R.id.eyesClosed);
        eyesClosed.setOnClickListener(this);
        cb_is = findViewById(R.id.cb_is);
        cb_is.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                // TODO Auto-generated method stub
                Log.e("tag", isChecked + "");
                if (isChecked) {
                    checkRegister = isChecked;

                } else {
                    checkRegister = isChecked;

                }
            }
        });

        registrationagreement = findViewById(R.id.registrationagreement);
        registrationagreement.setOnClickListener(this);

        onInvitationCode();
    }

    private String locationName;

    @Override
    public void onClick(View v) {
        String phone = etName.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String nvitation_code = et_nvitation_code.getText().toString().trim();

        switch (v.getId()) {
            case R.id.bt_login:
                //密码登录
                if (isPass) {
//                    if (FormatUtil.isPassword(pass)) {
//                        startGetData(false);
                    DialogUtil.showProgress(this);
                    presenter.Login(new LoginRequest(phone, pass, isPass));
//                    } else {
//                        Toast("请输入6位以上的数字+字母", 3);
//                    }
                } else {

                    String code = etCode.getText().toString().trim();
                    //验证码登录
                    if (FormatUtil.isMobileNO(phone)) {
                        if (FormatUtil.isFourNumber(code)) {
                            if (FormatUtil.isPassword(pass)) {

                                if (checkRegister) {
                                    startGetData(false);
                                    presenter.register(new LoginRequest(phone, pass, isPass, nvitation_code, verifyCode, locationName, getUniquePsuedoID()));
                                } else {
                                    ToastUtils.showTestShort(LoginActivity.this, "请您勾选注册协议");
                                }

                            } else {
                                Toast("请输入6-12位的数字+字母的密码", 3);
                            }
                        } else {
                            Toast("请输入验证码", 3);
                        }
                    } else {
                        Toast("请输入正确的手机号", 3);
                    }
                }
                break;
            case R.id.bt_code:
                //获取验证码
                if (FormatUtil.isMobileNO(phone)) {
                    startGetData(true);

                    presenter.GetCode(new LoginRequest(phone));
                } else {
                    Toast("请输入正确的手机号", 3);
                }
                break;
            case R.id.tv_forget_pass:
                // TODO: 2019/7/17 忘记密码
                Intent intent = new Intent(this, ActivityPassword.class);
                startActivity(intent);
                break;
            case R.id.iv_login_qq:
                // TODO: 2019/7/17 qq登录
                break;
            case R.id.iv_login_wechat:
                // TODO: 2019/7/17 微信登录
                break;
//            case R.id.login1:
            //测试账号
//                if (MyAPP.ISUSENEW) {
//                    presenter.Login(getRequestBean().setLoginParams("18552432822", "111111a", Contacts.PASSLOGIN));
//                } else {
//                    presenter.Login(getRequestBean().setLoginParams("18018142820", "111111a", Contacts.PASSLOGIN));
//                }
//                break;
            case R.id.registrationagreement:
                startActivity(new Intent(this, RegistrationActivity.class).putExtra("weburl", URL.versionUrl + "api/share/terms"));
                break;
            case R.id.eyesClosed:
                if (aBoolean) {
                    aBoolean = false;
                    eyesClosed.setImageResource(R.mipmap.eyesclosed);

                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPass.setSelection(etPass.length());//将光标移至文字末尾
                } else {
                    aBoolean = true;
                    eyesClosed.setImageResource(R.mipmap.eyeimg);
                    etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPass.setSelection(etPass.length());//将光标移至文字末尾
                }
                break;

        }
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


                    String aoiName = aMapLocation.getAoiName();

                    if (aoiName == null || aoiName.equals("")) {
                        locationName = aMapLocation.getProvince() + "  " + aMapLocation.getCity() + "  " + aMapLocation.getPoiName();
                    } else {
                        locationName = aMapLocation.getProvince() + "  " + aMapLocation.getCity() + "  " + aoiName;
                    }

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

    /**
     * 开始获取数据时,不可以操作按钮
     *
     * @param isGetCode 是否为获取code时
     */
    private void startGetData(boolean isGetCode) {
        DialogUtil.showProgress(mActivity);

//        if (isGetCode) {
//            bt_code.setEnabled(false);
//        } else {
//            bt_login.setEnabled(false);
//        }
    }


    private void intentClass(Class<?> c) {
        Intent intent = new Intent(getApplicationContext(), c);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * 密码登录成功
     */
    @Override
    public void passSuccess(final User user) {


        user.setPassword(etPass.getText().toString().trim());


        JMessageClient.login(user.getUserName().trim(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                DialogUtil.dismissProgress();
                setAlias(user.getUserName());
                DialogUtil.dismissProgress();
                SpUtils.putObject(LoginActivity.this, "LOGIN", user);
                Toast("登录成功", 1);
                String type = getIntent().getStringExtra("type");
                MyAPP.getInstance().finishSingleActivity(MainActivity.class);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                if ("0".equals(type)) {
                    intent.putExtra("type", "0");
                }
                startActivity(intent);
                finish();
            }
        });


    }

    /**
     * 注册成功
     */
    @Override
    public void passRegisterSuccess() {
        final String phone = etName.getText().toString().trim();
        final String pass = etPass.getText().toString().trim();

        final String nvitation_code = et_nvitation_code.getText().toString().trim();
        JMessageClient.register(phone, "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

                presenter.Login(new LoginRequest(phone, pass, isPass));
            }
        });


    }


    private void isIntent() {
//        intentClass(MainActivity.class);
    }

    /**
     * 验证码登录成功
     */
    @Override
    public void codeSuccess(User user) {


    }

    /**
     * 验证码已发送
     */
    @Override
    public void getCodeSuccess(String code) {
        try {
            JSONObject jsonObject = new JSONObject(code);
            String returnObject = jsonObject.getString("returnObject");
            verifyCode = returnObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DialogUtil.dismissProgress();
        CountDownUtil.countDown(bt_code);
        Toast("验证码已发送", 3);
    }

    @Override
    public void onError(String error) {
        DialogUtil.dismissProgress();
        ToastUtils.showTestShort(this, error);
//        bt_code.setEnabled(true);
//        bt_login.setEnabled(true);
    }


    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias(String name) {
        // EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
        String alias = name;

        if (TextUtils.isEmpty(alias)) {
            // Toast.makeText(this,R.string.error_alias_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            //  Toast.makeText(this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
            return;
        }

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

    private void Identifies() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //获取保存在sd中的 设备唯一标识符
//                    String readDeviceID = GetDeviceId.readDeviceID(LoginActivity.this);
//                    //获取缓存在  sharepreference 里面的 设备唯一标识
//                    String string = SimplePreference.getPreference(this).getString(SpConstant.SP_DEVICES_ID, readDeviceID);
//                    //判断 app 内部是否已经缓存,  若已经缓存则使用app 缓存的 设备id
//                    if (string != null) {
//                        //app 缓存的和SD卡中保存的不相同 以app 保存的为准, 同时更新SD卡中保存的 唯一标识符
//                        if (StringUtil.isBlank(readDeviceID) && !string.equals(readDeviceID)) {
//                            // 取有效地 app缓存 进行更新操作
//                            if (StringUtil.isBlank(readDeviceID) && !StringUtil.isBlank(string)) {
//                                readDeviceID = string;
//                                Utils.saveDeviceID(readDeviceID, WelcomeActivity.this);
//                            }
//                        }
//                    }
//                    // app 没有缓存 (这种情况只会发生在第一次启动的时候)
//                    if (StringUtil.isBlank(readDeviceID)) {
//                        //保存设备id
//                        readDeviceID = Utils.getDeviceId(WelcomeActivity.this);
//                    }
//                    //左后再次更新app 的缓存
//                    SimplePreference.getPreference(WelcomeActivity.this).saveString(SpConstant.SP_DEVICES_ID, readDeviceID);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();

    }


    private void onInvitationCode() {

        ShareInstall.getInstance().getInstallParams(new AppGetInstallListener() {
            @Override
            public void onGetInstallFinish(String info) {
                // 客户端获取到的参数是json字符串格式
               Log.e("ShareInstall", "info = " + info);
                try {
                    JSONObject object = new JSONObject(info);
                    // 通过该方法拿到设置的渠道值，剩余值为自定义的其他参数
                    String invitationCode = object.getString("invitationCode");
                    if (!"".equals(invitationCode) && null != invitationCode) {
                        et_nvitation_code.setText(invitationCode);
                    }else {
                        Log.e("ShareInstall", "invitationCode = " + invitationCode);
                    }



                } catch (JSONException e) {
                    Log.e("ShareInstall", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }
}
