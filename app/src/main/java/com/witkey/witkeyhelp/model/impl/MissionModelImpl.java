package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

import retrofit2.Call;

public class MissionModelImpl implements IMissionModel {
    @Override
    public void getMissionList(MissionBean missionBean, String searchKeyWord, final AsyncCallback callback) {
        api.getTaskList(
                missionBean.getPageNum() + "",
                missionBean.getPageSize() + "",
                missionBean.getBusinessType(),
                missionBean.getProductType(),
                missionBean.getLongitude(),
                missionBean.getLatitude(),
                missionBean.getPaymentType(),
                missionBean.getBondType()
        ).enqueue(new Callback(callback, "获取列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-getMissionList: " + body);
                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson,JSONUtil.getValueToString(body, "returnObject"),MissionBean.class);
                Log.d(TAG, "MissionModelImpl-getMissionList: " + missionResponse);
                callback.onSuccess(missionResponse);
            }
        });
    }

    @Override
    public void getMissionDetail(String businessId, final AsyncCallback callback) {
        api.getMissionDetail(businessId).enqueue(new Callback(callback, "获取任务详情失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-getMissionDetail: " + body);
                MissionBean missionBean = gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);
                Log.d(TAG, "MissionModelImpl-getMissionDetail: " + missionBean);
                callback.onSuccess(missionBean);
            }
        });
    }

    @Override
    public void receipt(int orderId, int userId, final AsyncCallback callback) {
        Log.d(TAG, "MissionModelImpl-receipt: orderId=" + orderId + ",userId=" + userId);
        api.receipt(orderId + "", userId + "").enqueue(new Callback(callback, "接单失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-receipt: " + body);
                callback.onSuccess("");
            }
        });
    }

    /**
     * 我的
     * 发布的任务
     *
     * @param missionBean
     * @param callback
     */
    @Override
    public void getReleaseMissionList(MissionBean missionBean, final AsyncCallback callback) {
        Log.d(TAG, "MissionModelImpl-getReleaseMissionList: " + missionBean.toString());
        Call<String> stringCall;
        if (missionBean.getOrderState() == 4) {
            stringCall = api.getBusinessList(
                    missionBean.getCreateUserId() + "",
                    missionBean.getPageNum() + "",
                    missionBean.getPageSize() + "",
                    3 + "");
        } else {
            int orderState = 1;
            switch (missionBean.getOrderState()) {
                case 1:
                    orderState = 1;
                    break;
                case 2:
                    orderState = 4;
                    break;
                case 3:
                    orderState = 3;
                    break;
            }
            stringCall = api.getReleaseList(
                    missionBean.getPageNum() + "",
                    missionBean.getPageSize() + "",
                    missionBean.getCreateUserId() + "",
                    orderState + ""
            );
        }
        stringCall.enqueue(new Callback(callback, "获取任务列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-getReleaseMissionList: " + body);
                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson,JSONUtil.getValueToString(body, "returnObject"),MissionBean.class);
                Log.d(TAG, "MissionModelImpl-getReleaseMissionList: " + missionResponse);
                callback.onSuccess(missionResponse);
            }
        });
    }

    @Override
    public void getReceiveMissionList(MissionBean missionBean, final AsyncCallback callback) {
        Log.d(TAG, "MissionModelImpl-getReceiveMissionList: " + missionBean.toString());
        int orderState = 1;
        switch (missionBean.getOrderState()) {
            case 1:
                orderState = 1;
                break;
            case 2:
                orderState = 4;
                break;
            case 3:
                orderState = 3;
                break;
        }
        api.getReceiveList(
                missionBean.getCreateUserId() + "",
                missionBean.getPageNum() + "",
                missionBean.getPageSize() + "",
                orderState + ""
        ).enqueue(new Callback(callback, "获取任务列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-getReceiveMissionList: " + body);
                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson,JSONUtil.getValueToString(body, "returnObject"),MissionBean.class);
                Log.d(TAG, "MissionModelImpl-getReceiveMissionList: " + missionResponse);
                callback.onSuccess(missionResponse);
            }
        });
    }

    @Override
    public void confirmOrder(int orderId, int userId, final AsyncCallback callback) {
        Log.d(TAG, "MissionModelImpl-confirmOrder: orderId=" + orderId + ",userId=" + userId);
        api.confirm(
                orderId + "",
                userId + "").enqueue(new Callback(callback, "确认订单失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-confirm: " + body);
                callback.onSuccess("");
            }
        });
    }


}
