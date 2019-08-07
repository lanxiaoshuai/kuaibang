package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.witkey.witkeyhelp.bean.Message;
import com.witkey.witkeyhelp.bean.MessageResponse;
import com.witkey.witkeyhelp.bean.MicroNotificationResponse;
import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MicroNotificationModelImpl implements com.witkey.witkeyhelp.model.IMicroNotificationModel {
    @Override
    public void getMicroNotificationList(int createUserId, final AsyncCallback callback) {
        Log.d(TAG, "MicroNotificationModelImpl-getMicroNotificationList:createUserId=" + createUserId);
        api.getMicroNotificationList(createUserId).enqueue(new Callback(callback, "获取微通知列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MicroNotificationModelImpl-getMicroNotificationList:" + body);
                MicroNotificationResponse microNotificationResponse=gson.fromJson(JSONUtil.getValueToString(body, "returnObject"),MicroNotificationResponse.class);
                callback.onSuccess(microNotificationResponse);
            }
        });
    }

    @Override
    public void addMicroNotification(MicroNotifyManagerBean microNotificationBean, AsyncCallback asyncCallback) {
        asyncCallback.onSuccess("");
    }

    @Override
    public void getMicroNotificationDetail(int createUserId, boolean isCheck, final AsyncCallback callback) {
        Log.d(TAG, "MicroNotificationModelImpl-getMicroNotificationDetail: createUserId=" + createUserId + ",isCheck=" + isCheck);
        Call<String> stringCall;
        if (isCheck) {
            stringCall = api.getMicroNotifyMessageCheckList(createUserId);
        } else {
            stringCall = api.getMicroNotifyMessageUnCheckList(createUserId);
        }
        stringCall.enqueue(new Callback(callback, "获取消息列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MicroNotificationModelImpl-getMicroNotificationDetail: " + body);
//                MessageResponse messageResponse=gson.fromJson(JSONUtil.getValueToString(body, "returnObject"),MessageResponse.class);
//                callback.onSuccess(messageResponse);
            }
        });
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        callback.onSuccess(new MessageResponse(5, messages));
    }

    @Override
    public void getMicroNotifyManagerList(int createUserId, int parentId, final AsyncCallback callback) {
        Log.d(TAG, "MicroNotificationModelImpl-getMicroNotifyManagerList: createUserId=" + createUserId + ",parentId=" + parentId);
        Call<String> microNotifyManagerList;
        if(parentId==0) {
            microNotifyManagerList = api.getMicroNotifyManagerList(createUserId, parentId);
       }else{
         microNotifyManagerList = api.getMicroNotifyManagerListOther(createUserId, parentId);
       }
        microNotifyManagerList.enqueue(new Callback(callback, "获取列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MicroNotificationModelImpl-getMicroNotifyManagerList: " + body);
                ArrayList<MicroNotifyManagerBean> microNotifyManagerBeans =
                        gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), new TypeToken<List<MicroNotifyManagerBean>>() {
                        }.getType());
                callback.onSuccess(microNotifyManagerBeans);
            }
        });

    }
}
