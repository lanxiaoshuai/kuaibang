package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.google.gson.Gson;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MissionModelImpl implements IMissionModel {
    @Override
    public void getMissionList(MissionBean missionBean, String circleId, final AsyncCallback callback) {
        api.getTaskList(
                missionBean.getPageNum() + "",
                "10",
                missionBean.getBusinessType(),
                missionBean.getLongitude(),
                missionBean.getLatitude(),
                missionBean.getPaymentType(),
                missionBean.getBondType(),
                missionBean.getMflag(),
                circleId,
                missionBean.getCreateUserId()
        ).enqueue(new Callback(callback, "获取列表失败") {
            @Override
            public void getSuc(String body) {
                //     Log.e(TAG, "MissionModelImpl-getMissionList: " + body);
                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson, JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);

                Log.e(TAG, missionResponse.toString());
                callback.onSuccess(missionResponse);
            }
        });
    }
    @Override
    public void getMissionDetail(String businessId, int userId, final AsyncCallback callback) {
        api.getMissionOrder(businessId, userId).enqueue(new Callback(callback, "获取任务详情失败") {
            @Override
            public void getSuc(String body) {
                Log.e(TAG, "MissionModelImpl-getMissionDetail: " + body);
                MissionBean missionBean = gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);
                Log.e(TAG, "MissionModelImpl-getMissionDetail: " + missionBean);
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
        if (missionBean.getOrderState().equals("4")) {
            stringCall = api.getBusinessList(
                    missionBean.getCreateUserId() + "",
                    missionBean.getPageNum() + "",
                    missionBean.getPageSize() + "",
                    1 + "");
        } else {
            String orderState = "1";
            switch (missionBean.getOrderState()) {
                case "1":
                    orderState = "1";
                    break;
                case "2":
                    orderState = "4";
                    break;
                case "3":
                    orderState = "3";
                    break;
            }
            stringCall = api.getReleaseList(
                    missionBean.getPageNum() + "",
                    10 + "",
                    missionBean.getCreateUserId() + "",
                    orderState + ""
            );
        }
        stringCall.enqueue(new Callback(callback, "获取任务列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-getReleaseMissionList: " + body);
                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson, JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);
                Log.d(TAG, "MissionModelImpl-getReleaseMissionList: " + missionResponse);
                callback.onSuccess(missionResponse);
            }
        });
    }

    @Override
    public void getReceiveMissionList(MissionBean missionBean, final AsyncCallback callback) {
        Log.d(TAG, "MissionModelImpl-getReceiveMissionList: " + missionBean.toString());
        String orderState = "1";
        switch (missionBean.getOrderState()) {
            case "1":
                orderState = "1";
                break;
            case "2":
                orderState = "6";
                break;
            case "3":
                orderState = "4";
                break;
        }
        api.getReceiveList(
                missionBean.getCreateUserId() + "",
                missionBean.getPageNum() + "",
                10 + "",
                orderState + ""
        ).enqueue(new Callback(callback, "获取任务列表失败") {
            @Override
            public void getSuc(String body) {

                //   Log.d(TAG, "MissionModelImpl-getReceiveMissionList: " + body);
                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson, JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);
                //  Log.d(TAG, "MissionModelImpl-getReceiveMissionList: " + missionResponse);
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

    @Override
    public void ollection(int orderId, int userId, final AsyncCallback callback) {
        Log.d(TAG, "MissionModelImpl-confirmOrder: orderId=" + orderId + ",userId=" + userId);
        api.olleEction(orderId + "", userId + "").enqueue(new Callback(callback, "确认订单失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-confirm: " + body);
                callback.onSuccess(body);
            }
        });
    }

    @Override
    public void cancelCollection(int orderId, int userId, final AsyncCallback callback) {
        Log.d(TAG, "MissionModelImpl-confirmOrder: orderId=" + orderId + ",userId=" + userId);
        api.cancelColleEction(orderId + "", userId + "").enqueue(new Callback(callback, "取消收藏失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "MissionModelImpl-confirm: " + body);
                callback.onSuccess(body);
            }
        });
    }

    @Override
    public void taskRelievingCollection(String receiver, String orderId, String businessId, String remark, int start, final AsyncCallback callback) {


        switch (start) {

            case 2:
                //悬赏者取消任务
                api.rewardGiverwithdrawsTask(receiver, businessId, remark, 0, orderId).enqueue(new Callback(callback, "悬赏者取消任务") {
                    @Override
                    public void getSuc(String body) {
                        Log.d(TAG, "MissionModelImpl-confirm: " + body);
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            String errorMessage = jsonObject.getString("errorMessage");
                            callback.onSuccess(errorMessage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 5:
                //接单者取消任务
                api.taskRelievingCollection(receiver, orderId, remark).enqueue(new Callback(callback, "接单人取消任务失败") {
                    @Override
                    public void getSuc(String body) {
                        Log.e(TAG, "MissionModelImpl-confirm: " + body);
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            String errorMessage = jsonObject.getString("returnObject");

                            callback.onSuccess(errorMessage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case 4:
                //悬赏者撤回任务
                api.rewardGiverwithdrawsTask(receiver, businessId, remark, 1, orderId).enqueue(new Callback(callback, "悬赏者取消任务") {
                    @Override
                    public void getSuc(String body) {
                        Log.d(TAG, "MissionModelImpl-confirm: " + body);
                        try {
                            JSONObject jsonObject = new JSONObject(body);
                            String errorMessage = jsonObject.getString("errorMessage");
                            callback.onSuccess(errorMessage);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

        }

    }

    @Override
    public void confirmsReceiveOrder(String orderId, int taskdetails, final AsyncCallback callback) {
        //接单者确认接单
        if (taskdetails == 5) {
            api.confirmsReceiveOrder(orderId).enqueue(new Callback(callback, "接单者确认任务") {
                @Override
                public void getSuc(String body) {

                    Log.e(TAG, "MissionModelImpl-confirm: " + body);

                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String errorMessage = jsonObject.getString("errorMessage");

                        callback.onSuccess(errorMessage);
                    } catch (JSONException e) {
                        e.printStackTrace();


                    }

                }
            });
        } else if (taskdetails == 2) {
            api.rewardConfirmationTask(Integer.parseInt(orderId), 1).enqueue(new Callback(callback, "悬赏者确认任务") {
                @Override
                public void getSuc(String body) {
                    Log.d(TAG, "MissionModelImpl-confirm: " + body);
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String errorMessage = jsonObject.getString("errorMessage");
                        callback.onSuccess(errorMessage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public void getDeductionData(int userId, int deduction, final AsyncCallback callback) {
        api.getDiamondDeduction(userId, deduction).enqueue(new Callback(callback, "获取账户失败") {
            @Override
            public void getSuc(String body) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String errorMessage = jsonObject.getString("errorMessage");

                    callback.onSuccess(errorMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
    public void UploadFeedback(String content, int userId, String imgUrl, int type, Integer businessId, int orderId, final AsyncCallback callback) {
        api.feedback(content, userId, imgUrl, type, businessId, orderId).enqueue(new Callback(callback, "上传举报反馈失败") {
            @Override
            public void getSuc(String body) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String errorMessage = jsonObject.getString("errorMessage");

                    callback.onSuccess(errorMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
