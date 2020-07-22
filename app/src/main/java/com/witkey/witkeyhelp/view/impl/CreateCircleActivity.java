package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.DiamondAdapter;
import com.witkey.witkeyhelp.bean.DiamondBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

/**
 * Created by jie on 2020/6/17.
 */

public class CreateCircleActivity extends BaseActivity {

    private EditText circlename;
    private EditText circle_abbreviation;
    private EditText circle_definition;
    private Button btn_create_now;
    private boolean checkRegister = true;

    private boolean aBoolean;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.create_circle_layout);
        setIncludeTitle("创建圈子");
        circlename = findViewById(R.id.circlename);

        circle_abbreviation = findViewById(R.id.circle_abbreviation);

        circle_definition = findViewById(R.id.circle_definition);

        CheckBox cb_is_cicle = findViewById(R.id.cb_is_cicle);

        TextView connection_is_cicle = findViewById(R.id.connection_is_cicle);
        connection_is_cicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CreateCircleActivity.this, RegistrationActivity.class).putExtra("weburl", URL.versionUrl + "api/share/terms"));
            }
        });

        btn_create_now = findViewById(R.id.btn_create_now);
        cb_is_cicle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkRegister = isChecked;

                } else {
                    checkRegister = isChecked;

                }
            }
        });


        circlename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 && checkRegister) {
                    btn_create_now.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                    aBoolean = true;
                } else {
                    btn_create_now.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                    aBoolean = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        circle_abbreviation.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;
            private int selectionStart;
            private int selectionEnd;
            //输入框初始值
            private int num = 0;
            //输入框最大值
            public int mMaxNum = 6;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;
//                if (s.toString().length() > 0 && !circlename.getText().toString().equals("") && !circle_definition.getText().toString().equals("")) {
//                    btn_create_now.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
//                    aBoolean = true;
//                } else {
//                    btn_create_now.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
//                    aBoolean = false;
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num + s.length();
                //TextView显示剩余字数

                selectionStart = circle_abbreviation.getSelectionStart();
                selectionEnd = circle_abbreviation.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    circle_abbreviation.setText(s);
                    circle_abbreviation.setSelection(circle_abbreviation.getText().toString().length());//设置光标在最后
                    //吐司最多输入300字

                }
            }

        });
        circle_definition.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;
            private int selectionStart;
            private int selectionEnd;
            //输入框初始值
            private int num = 0;
            //输入框最大值
            public int mMaxNum = 200;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;
//                if (s.toString().length() > 0 && !circle_abbreviation.getText().toString().equals("") && !circle_definition.getText().toString().equals("")) {
//                    btn_create_now.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
//                    aBoolean = true;
//                } else {
//                    btn_create_now.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
//                    aBoolean = false;
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num + s.length();
                //TextView显示剩余字数

                selectionStart = circle_definition.getSelectionStart();
                selectionEnd = circle_definition.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    circle_definition.setText(s);
                    circle_definition.setSelection(circle_definition.getText().toString().length());//设置光标在最后
                    //吐司最多输入300字

                }
            }
        });

        btn_create_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (aBoolean) {
                    if (!checkRegister) {
                        ToastUtils.showTestShort(CreateCircleActivity.this, "请您同意创建圈子协议");
                        return;
                    }
                    createCircle();
                }

            }
        });

    }

    /**
     * 创建圈子
     */
    private void createCircle() {
        DialogUtil.showProgress(this);
        MyAPP.getInstance().getApi().apiCircleAdd(circlename.getText().toString(), circle_abbreviation.getText().toString(), circle_definition.getText().toString(), user.getUserId()).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {
                DialogUtil.dismissProgress();
                Intent intent = new Intent(CreateCircleActivity.this, SuccessCiclerActivity.class);
                startActivity(intent);
                finish();
            }


        });
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }
}
