package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.util.wight.TextWatcherAdapter;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jie on 2020/1/2.
 */

public class ActivityPassword extends BaseActivity {

    private EditText user_phone;
    private Button button;
    private String verifyCode = "";
    private IModel.AsyncCallback callback;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.retrieve_password);
        setIncludeTitle("找回密码");
        user_phone = findViewById(R.id.user_phone);

        button = findViewById(R.id.determine);
        user_phone.addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 11) {
                    button.setClickable(true);

                    button.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                } else {
                    button.setClickable(false);
                    button.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                }
                super.onTextChanged(s, start, before, count);
            }


        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMobileNum(user_phone.getText().toString())) {

                    callback = new IModel.AsyncCallback() {
                        @Override
                        public void onSuccess(Object data) {
                        }

                        @Override
                        public void onError(Object data) {
                            DialogUtil.dismissProgress();
                        }
                    };

                    MyAPP.getInstance().getApi().sendMsg(user_phone.getText().toString()).enqueue(new Callback(callback, "上传失败") {
                        @Override
                        public void getSuc(String body) {
                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                verifyCode = jsonObject.getString("returnObject");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(ActivityPassword.this, ActivitySecurityCheck.class);
                            intent.putExtra("phone", user_phone.getText().toString());
                            intent.putExtra("code", verifyCode);
                            startActivity(intent);
                        }
                    });
                    callback = new IModel.AsyncCallback() {
                        @Override
                        public void onSuccess(Object data) {

                        }

                        @Override
                        public void onError(Object data) {
                            DialogUtil.dismissProgress();
                            ToastUtils.showTestShort(ActivityPassword.this, data.toString());
                        }
                    };
                } else {
                    ToastUtils.showTestShort(ActivityPassword.this, "请输入正确的手机号");
                }


            }
        });
    }

    /**
     * 检查是否是电话号码
     *
     * @return
     */
    public static boolean isMobileNum(String mobiles) {

        String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";

        Pattern p = Pattern
                .compile(REGEX_MOBILE);
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
}
