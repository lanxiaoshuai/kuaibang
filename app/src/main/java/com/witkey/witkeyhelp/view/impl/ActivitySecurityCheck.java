package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.CountDownUtil;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.util.wight.TextWatcherAdapter;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jie on 2020/1/2.
 */

public class ActivitySecurityCheck extends BaseActivity {

    private TextView again_code;
    private IModel.AsyncCallback callback;
    private String phone;
    private String code;
    private Button determine;
    private EditText editText;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.security_check);
        setIncludeTitle("安全检查");
        editText = findViewById(R.id.verification_Code);

        //    LinearLayout count_down = findViewById(R.id.count_down);

        again_code = findViewById(R.id.again_code);

        determine = findViewById(R.id.determine);
        TextView user_phone_text = findViewById(R.id.user_phone_text);

        CountDownUtil.countDown(again_code);
        editText.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length() == 6) {
                    determine.setClickable(true);

                    determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                } else {
                    determine.setClickable(false);
                    determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                }
                super.onTextChanged(s, start, before, count);
            }
        });
        phone = getIntent().getStringExtra("phone");
        code = getIntent().getStringExtra("code");

        user_phone_text.setText(hidePhoneNum(phone));

        again_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownUtil.countDown(again_code);
                callback = new IModel.AsyncCallback() {
                    @Override
                    public void onSuccess(Object data) {


                    }

                    @Override
                    public void onError(Object data) {
                        DialogUtil.dismissProgress();
                    }
                };

                MyAPP.getInstance().getApi().sendMsg(phone).enqueue(new Callback(callback, "上传失败") {
                    @Override
                    public void getSuc(String body) {
                        try {
                            JSONObject jsonObject=new JSONObject(body);
                            code= jsonObject.getString("returnObject");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });


            }
        });
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.equals(editText.getText().toString())) {

                    Intent intent = new Intent(ActivitySecurityCheck.this, ActivityResetPassword.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("code", code);
                    startActivity(intent);
                } else {
                    ToastUtils.showTestShort(ActivitySecurityCheck.this, "请输入正确的验证码");
                }

            }
        });
    }

    /**
     * 隐藏部分手机号码
     *
     * @param phone
     * @return
     */
    public static String hidePhoneNum(String phone) {
        String result = "";
        if (phone != null || !"".equals(phone)) {

                result = phone.substring(0, 3) + "****" + phone.substring(7);

        }
        return result;
    }


}
