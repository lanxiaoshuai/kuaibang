package com.witkey.witkeyhelp.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.witkey.witkeyhelp.util.CountDownUtil;
import com.witkey.witkeyhelp.util.FormatUtil;
import com.witkey.witkeyhelp.util.SharedPreferencesUtil;
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

    private boolean isPass = true; //是否为密码登录
    private boolean isSave = true; //默认记住密码

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
        //切换显示
        tv_change_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPass == true) {
                    bt_code.setVisibility(View.VISIBLE);
                    etPass.setHint("请输入验证码");
                    isPass = false;
                    tv_change_state.setText("密码登录");
                    cb_rm_pass.setChecked(false);
                    cb_rm_pass.setEnabled(false);
                } else {
                    bt_code.setVisibility(View.GONE);
                    isPass = true;
                    etPass.setHint("请输入密码");
                    tv_change_state.setText("验证码登录");
                    cb_rm_pass.setEnabled(true);
                    cb_rm_pass.setChecked(true);
                }
                etPass.setText("");
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
            }
        });
        //记住密码操作
        cb_rm_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                登录成功是否保存密码
                isSave = isChecked;
            }
        });
        tv_forget_pass.setOnClickListener(this);
        iv_login_qq.setOnClickListener(this);
        iv_login_wechat.setOnClickListener(this);
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
        //显示已保存的用户名跟密码
        etName.setText((String) SharedPreferencesUtil.getNamePass(getApplicationContext()).get("name"));
        etPass.setText((String) SharedPreferencesUtil.getNamePass(getApplicationContext()).get("pass"));
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
                    if (FormatUtil.isPassword(pass)) {
                        startGetData(false);
                        presenter.Login(new LoginRequest(phone, pass));
                    } else {
                        Toast("请输入6位以上的数字+字母", 3);
                    }
                } else {
                    //验证码登录
                    if (FormatUtil.isMobileNO(phone)) {
                        if (FormatUtil.isFourNumber(pass)) {
                            startGetData(false);
                            presenter.Login(new LoginRequest(phone, pass));
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
                    presenter.GetCode(new LoginRequest());
                } else {
                    Toast("请输入正确的手机号", 3);
                }

                break;
            case R.id.tv_forget_pass:
                // TODO: 2019/7/17 忘记密码
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
        }
    }


    /**
     * 开始获取数据时,不可以操作按钮
     *
     * @param isGetCode 是否为获取code时
     */
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
        SharedPreferencesUtil.saveNamePass(getApplicationContext(), etName.getText().toString().trim(), etPass.getText().toString().trim());
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
        CountDownUtil.countDown(bt_code);
        Toast("验证码已发送", 3);
    }

    @Override
    public void onError(String error) {
        super.onError(error);
        bt_code.setEnabled(true);
        bt_login.setEnabled(true);
    }
}
