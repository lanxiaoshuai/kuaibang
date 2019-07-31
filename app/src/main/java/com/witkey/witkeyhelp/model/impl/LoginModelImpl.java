package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.bean.LoginRequest;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.ILoginModel;
import com.witkey.witkeyhelp.util.Error;
import com.witkey.witkeyhelp.util.ExceptionUtil;
import com.witkey.witkeyhelp.util.JSONUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginModelImpl implements ILoginModel {
    @Override
    public void Login(final LoginRequest loginRequest, final AsyncCallback callback) {
        Log.d(TAG, "LoginModelImpl-Login: " + loginRequest.toString());
        if (loginRequest.isPass()) {
            api.login(loginRequest.getUserName(), loginRequest.getPassword()).enqueue(
                    new retrofit2.Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                String body = response.body(); //code(状态码): response.code()    错误信息:response.errorBody().string()
                                int code = JSONUtil.getValueToInt(body, "errorCode");
                                if (code == 200) {
                                    //登录成功
                                    User user = gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), User.class);
                                    MyAPP.getInstance().setUser(user);
                                    Log.d(TAG, "onResponse: " + user.toString());
                                    callback.onSuccess("登录成功");
                                } else if (code == 305) {
                                    //用户为null,未注册
                                    LoginModelImpl.this.register(loginRequest, callback);
                                }
                            } else {
                                try {
                                    String errorResponse = response.errorBody().string();
                                    Error.isNoToken(errorResponse, callback);
                                } catch (IOException e) {
                                    ExceptionUtil.CatchException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            //未上传成功
                            Error.handleError(callback, t, "网络错误");
                        }
                    });
        } else {
            LoginModelImpl.this.register(loginRequest, callback);
        }
    }

    @Override
    public void register(final LoginRequest loginRequest, final AsyncCallback callback) {
        Log.d(TAG, "LoginModelImpl-register: " + loginRequest.toString());
        api.register(loginRequest.getUserName(), loginRequest.getPassword()).enqueue(new com.witkey.witkeyhelp.model.util.Callback(callback, "注册失败,请重试") {
            @Override
            public void getSuc(String body) {
                //注册成功
                Log.d(TAG, "LoginModelImpl-register: " + body);
                LoginModelImpl.this.Login(loginRequest, callback);
            }
        });
    }

    @Override
    public void GetCode(LoginRequest loginRequest, final AsyncCallback callback) {
        Log.d(TAG, "LoginModelImpl-GetCode: " + loginRequest.toString());
        api.sendMsg(loginRequest.getUserName(), loginRequest.getPassword()).enqueue(new com.witkey.witkeyhelp.model.util.Callback(callback, "获取验证码失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "LoginModelImpl-GetCode: " + body);
                callback.onSuccess("");
            }
        });
    }

    @Override
    public void commitPass(LoginRequest loginRequest, AsyncCallback callback) {

    }

    @Override
    public void logOut(LoginRequest requestBean, AsyncCallback callback) {

    }
}
