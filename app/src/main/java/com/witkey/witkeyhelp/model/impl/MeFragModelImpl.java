package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    public void getAcount(int userId, final AsyncCallback callback) {
        Log.d(TAG, "MeFragModelImpl-getAcount:userId= " + userId);
        api.getAcount(userId + "").enqueue(new Callback(callback, "获取账户失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MeFragModelImpl-getAcount-getSuc: " + body);
                Acount acount = gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), Acount.class);
                Log.d(TAG, "MeFragModelImpl-getAcount-getSuc: " + acount);
                callback.onSuccess(acount);
            }
        });
    }

    @Override
    public void getDeductionData(int userId, int deduction, final AsyncCallback callback) {
        api.getDiamondDeduction(userId, deduction).enqueue(new Callback(callback, "获取账户失败") {
            @Override
            public void getSuc(String body) {
                try {
                    JSONObject jsonObject=new JSONObject(body);
                    String errorMessage = jsonObject.getString("errorMessage");

                    callback.onSuccess(errorMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void updateUserInfo(int userId, String realName, String headUrl, String sex, final AsyncCallback callback) {
        api.updateUserInfo(userId,realName,headUrl,sex,"您还没有个性签名").enqueue(new Callback(callback, "修改个人信息失败") {
            @Override
            public void getSuc(String body) {
                callback.onSuccess(body);
            }
        });
    }
}
