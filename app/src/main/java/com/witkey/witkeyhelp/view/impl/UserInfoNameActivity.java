package com.witkey.witkeyhelp.view.impl;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.presenter.IMeFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MeFragPresenterImpl;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMeFragView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by jie on 2019/12/16.
 */

public class UserInfoNameActivity extends InitPresenterBaseActivity implements View.OnClickListener, IMeFragView {

    private IMeFragPresenter presenter;
    private EditText editText;
    private Button echarge;
    private ImageView search_edit_delete;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.echarge:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                String name = editText.getText().toString();
                if ("".equals(name)) {

                    ToastUtils.showTestShort(this, "请输入修改名称");
                } else {
                    DialogUtil.showProgress(this);
                    presenter.updateUserInfo(user.getUserId(), name, user.getHeadUrl(), 0 + "");
                }
                break;
        }
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    public void showUser(User user) {

    }

    @Override
    public void showAcount(Acount data) {

    }

    @Override
    public void showDeductionData(String data) {

    }

    @Override
    public void updateUserInfo(String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    UserInfo mMyInfo = JMessageClient.getMyInfo();
                        mMyInfo.setNickname(user.getRealName());
                        JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                            @Override
                            public void gotResult(int responseCode, String responseMessage) {

                            }
                        });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        MobclickAgent.onEvent(this,"nameChange");
        user.setRealName(editText.getText().toString());
        ToastUtils.showTestShort(this, "昵称修改成功~");
        SpUtils.putObject(this, "LOGIN", user);

        finish();
    }

    @Override
    protected void initWidget() {
        editText = findViewById(R.id.updateusername);


        editText.post(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
            }
        });
        echarge = findViewById(R.id.echarge);
        search_edit_delete = findViewById(R.id.search_edit_delete);
        search_edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                editText.setText("");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if(charSequence.toString().length()>0){
                  echarge.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                  search_edit_delete.setVisibility(View.VISIBLE);
              }else {
                  echarge.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                  search_edit_delete.setVisibility(View.GONE);
              }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        load();

    }
    //输入框初始值
    private int num = 0;
    //输入框最大值
    public int mMaxNum = 20;
    private void load() {

//mPublishEdDesc是EditText
        editText.addTextChangedListener(new TextWatcher() {
            //记录输入的字数
            private CharSequence wordNum;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时记录输入的字数
                wordNum = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num + s.length();
                //TextView显示剩余字数

                selectionStart = editText.getSelectionStart();
                selectionEnd = editText.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    editText.setText(s);
                    editText.setSelection(editText.getText().toString().length());//设置光标在最后
                    //吐司最多输入300字

                }
            }
        });

    }
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_username;
    }

    @Override
    protected void initEvent() {
        echarge.setOnClickListener(this);
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MeFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        setIncludeTitle("修改昵称");
    }
}
