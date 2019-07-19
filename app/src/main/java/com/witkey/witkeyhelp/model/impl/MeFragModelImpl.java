package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

public class MeFragModelImpl implements com.witkey.witkeyhelp.model.IMeFragModel {
    @Override
    public void getUser(int id, final AsyncCallback callback) {
        api.getUser(id).enqueue(new Callback(callback, "获取用户信息失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MeFragModelImpl-getUser: " + body);
                User user = gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), User.class);
                MyAPP.getInstance().setUser(user);
                callback.onSuccess(user);
            }
        });
    }
}
