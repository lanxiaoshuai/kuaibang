package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.LoginRequest;
import com.witkey.witkeyhelp.model.ILoginModel;
import com.witkey.witkeyhelp.model.util.Callback;

public class LoginModelImpl implements ILoginModel {
    @Override
    public void Login(LoginRequest loginRequest, AsyncCallback callback) {
        Log.d(TAG, "Login: "+loginRequest.toString());
        api.login(loginRequest.getUserName(),loginRequest.getPassword()).enqueue(new Callback(callback,"获取失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "getSuc: "+body);
            }
        });
    }

    @Override
    public void GetCode(LoginRequest loginRequest, AsyncCallback callback) {

    }

    @Override
    public void commitPass(LoginRequest loginRequest, AsyncCallback callback) {

    }

    @Override
    public void logOut(LoginRequest requestBean, AsyncCallback callback) {

    }
}
