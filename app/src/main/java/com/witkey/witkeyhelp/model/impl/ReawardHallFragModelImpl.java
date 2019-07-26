package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.MissionRequest;
import com.witkey.witkeyhelp.model.IReawardHallFragModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

public class ReawardHallFragModelImpl implements IReawardHallFragModel {
    @Override
    public void getMissionList(MissionBean missionBean, String searchKeyWord, final AsyncCallback callback) {
        api.getTaskList(
                missionBean.getPageNum()+"",
                missionBean.getPageSize()+"",
                missionBean.getBusinessType(),
                missionBean.getPaymentType(),
                missionBean.getLongitude(),
                missionBean.getLatitude(),
                missionBean.getPaymentType(),
                missionBean.getBiddingType(),
                missionBean.getBondType()
                ).enqueue(new Callback(callback,"获取列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG,  "ReawardHallFragModelImpl-getMissionList: "+body);
                MissionRequest missionRequest= gson.fromJson(JSONUtil.getValueToString(body,"returnObject"),MissionRequest.class);
                Log.d(TAG,  "ReawardHallFragModelImpl-getMissionList: "+missionRequest);
                callback.onSuccess(missionRequest);
            }
        });
    }
}
