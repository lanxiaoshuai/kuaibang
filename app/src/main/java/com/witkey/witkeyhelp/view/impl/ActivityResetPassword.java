package com.witkey.witkeyhelp.view.impl;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.FormatUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.util.wight.TextWatcherAdapter;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

/**
 * Created by jie on 2020/1/2.
 */

public class ActivityResetPassword extends BaseActivity {


    private String password;
    private Button determine;
    private IModel.AsyncCallback callback;
    private String phone;
    private EditText new_password;
    private EditText confirm_password;
    private String code;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.password_reset);
        setIncludeTitle("重置密码");
        new_password = findViewById(R.id.new_password);
        confirm_password = findViewById(R.id.confirm_password);
        determine = findViewById(R.id.determine);
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");
        new_password.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() <= 12) {
                    password = s.toString();
                }
               // super.onTextChanged(s, start, before, count);
            }
        });
        confirm_password.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.length() == s.length()) {
                    determine.setClickable(true);
                    determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                } else {
                    determine.setClickable(false);
                    determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                }
              //  super.onTextChanged(s, start, before, count);
            }
        });
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                if (new_password.getText().toString().equals(confirm_password.getText().toString())) {
                    if (FormatUtil.isPassword(password)) {

                    } else {
                        Toast("请输入6-12位的数字+字母的密码", 3);
                    }
                    callback = new IModel.AsyncCallback() {
                        @Override
                        public void onSuccess(Object data) {
                        }

                        @Override
                        public void onError(Object data) {

                        }
                    };
                    DialogUtil.showProgress(ActivityResetPassword.this);
                    MyAPP.getInstance().getApi().hangePassword(phone, new_password.getText().toString(),code).enqueue(new Callback(callback, "修改密码失败") {
                        @Override
                        public void getSuc(String body) {
                            DialogUtil.dismissProgress();
                            ToastUtils.showTestShort(ActivityResetPassword.this,"修改密码成功");
                            MyAPP.getInstance().finishSingleActivity(ActivityPassword.class);
                            MyAPP.getInstance().finishSingleActivity(ActivitySecurityCheck.class);
                            finish();
                        }
                    });
                    callback = new IModel.AsyncCallback() {
                        @Override
                        public void onSuccess(Object data) {
                            DialogUtil.dismissProgress();
                        }

                        @Override
                        public void onError(Object data) {
                            DialogUtil.dismissProgress();
                        }
                    };
                } else {
                    ToastUtils.showTestShort(ActivityResetPassword.this, "密码不一致");
                }


            }
        });
    }
}
