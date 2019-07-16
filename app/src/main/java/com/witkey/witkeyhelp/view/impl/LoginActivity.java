package com.witkey.witkeyhelp.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.LoginRequest;
import com.witkey.witkeyhelp.presenter.ILoginPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.LoginPresenterImpl;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.ILoginView;

import java.util.List;


public class LoginActivity extends InitPresenterBaseActivity implements View.OnClickListener, ILoginView {
    private EditText etName;
    private EditText etPass;


    private TextView bt_code;

    private CheckBox cb_rm_pass;
    private TextView tv_forget_pass;


    private ImageView iv_login_qq;
    private ImageView iv_login_wechat;

    private Button bt_login;
    private TextView tv_change_state;

    private String[] strs = {"密码登录", "验证码登录"};
    private boolean isPass = true; //是否为密码登录


    private ILoginPresenter presenter;

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
        tv_change_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPass == true) {
                    bt_code.setVisibility(View.VISIBLE);
                    etPass.setHint("请输入验证码");
                    isPass = false;
                    tv_change_state.setText("密码登录");
                } else {
                    bt_code.setVisibility(View.GONE);
                    isPass = true;
                    etPass.setHint("请输入密码");
                    tv_change_state.setText("验证码登录");
                }
                etPass.setText("");
            }
        });
        bt_code.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        //修改数据后停止计时
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(etPass.getText().toString())) {
                        etPass.setText("");
                    }
                }
            }
        });
        cb_rm_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // TODO: 2019/7/16 登录成功保存密码
                } else {
                    // TODO: 2019/7/16  登录成功时不保存密码
                }
            }
        });
        tv_forget_pass.setOnClickListener(this);
        iv_login_qq.setOnClickListener(this);
        iv_login_wechat.setOnClickListener(this);
//        PhoneFormatUtil.formatInput(etName); //不限制与数字
        //关闭在此之前的activity
        List<Activity> activityList = MyAPP.getInstance().getActivityList();
        for (Activity activity : activityList) {
            if (!activity.equals(this)) {
                activity.finish();
            }
        }
    }


    @Override
    protected void initViewExceptPresenter() {
    }

    @Override
    protected void initWidght() {
        tv_change_state = (TextView) findViewById(R.id.tv_change_state);
        etName = (EditText) findViewById(R.id.et_name);
        etPass = (EditText) findViewById(R.id.et_pass);
        bt_code = (TextView) findViewById(R.id.bt_code);
        bt_login = (Button) findViewById(R.id.bt_login);
        cb_rm_pass = findViewById(R.id.cb_rm_pass);
        tv_forget_pass = findViewById(R.id.tv_forget_pass);
        iv_login_qq = findViewById(R.id.iv_login_qq);
        iv_login_wechat = findViewById(R.id.iv_login_wechat);
    }

    @Override
    public void onClick(View v) {
        String phone = etName.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        switch (v.getId()) {
            case R.id.bt_login:
                //密码登录
                if (isPass) {
//                    if (FormatUtil.isMobileNO(phone)) {
//                        if (FormatUtil.isPassword(pass)) {
//                            phone = phone.trim().replaceAll(" ", "");
//                            presenter.Login(getRequestBean().setLoginParams(phone, pass, Contacts.PASSLOGIN));
//                            startGetData(false);
//                        } else {
//                            Toast("请输入6位以上的数字+字母", 3);
//                        }
//                    } else {
//                        Toast("请输入正确的手机号", 3);
//                    }

                    presenter.Login(new LoginRequest(phone,pass));
                } else {
                    //验证码登录
//                    if (FormatUtil.isMobileNO(phone)) {
//                        if (FormatUtil.isFourNumber(pass)) {
//                            phone = phone.trim().replaceAll(" ", "");
//                            presenter.Login(getRequestBean().setLoginParams(phone, pass, Contacts.CODELOGIN));
//                            startGetData(false);
//                        } else {
//                            Toast("请输入验证码", 3);
//                        }
//                    } else {
//                        Toast("请输入正确的手机号", 3);
//                    }
                }
                break;

            case R.id.bt_code:
//                Log.d(TAG, "onClick: bt_code");
//                //获取验证码
//                if (FormatUtil.isMobileNO(phone)) {
//                    phone = phone.trim().replaceAll(" ", "");
//                    presenter.GetCode(getRequestBean().setSmsParams(phone, Contacts.GET_LOGINCODE));
//                    startGetData(true);
//                } else {
//                    Toast("请输入正确的手机号", 3);
//                }
                break;
            case R.id.tv_forget_pass:

                break;
            case R.id.iv_login_qq:

                break;
            case R.id.iv_login_wechat:

                break;
//            case R.id.login1:
//                if (MyAPP.ISUSENEW) {
//                    presenter.Login(getRequestBean().setLoginParams("18552432822", "111111a", Contacts.PASSLOGIN));
//                } else {
//                    presenter.Login(getRequestBean().setLoginParams("18018142820", "111111a", Contacts.PASSLOGIN));
//                }
//                break;
        }
    }


    private void startGetData(boolean isGetCode) {
        DialogUtil.showProgress(mActivity);
        if (isGetCode) {
            bt_code.setEnabled(false);
        } else {
            bt_login.setEnabled(false);
        }
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
    public void passSuccess() {
        Toast("登录成功", 1);
        finish();
        isIntent();
    }

    private void isIntent() {
//        int type = MyAPP.getInstance().getType();
//        if (type == 4) {
//            intentClass(DrMainActivity.class);
//        } else if (type == 1 || type == 2 || type == 3) {
//            intentClass(MarketMainActivity.class);
//        } else if (type == 5 || type == 6 || type == 7) {
//            intentClass(NewMediaMainActivity.class);
//        }
        intentClass(MainActivity.class);
    }

    /**
     * 验证码登录成功
     */
    @Override
    public void codeSuccess() {
        Toast("登录成功", 1);
        finish();
        isIntent();
    }

    /**
     * 验证码已发送
     */
    @Override
    public void getCodeSuccess() {
        DialogUtil.dismissProgress();
//        isGetCode = CountDownUtil.countDown(bt_code);
        Toast("验证码已发送", 3);
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        bt_code.setEnabled(true);
        bt_login.setEnabled(true);
    }
}
