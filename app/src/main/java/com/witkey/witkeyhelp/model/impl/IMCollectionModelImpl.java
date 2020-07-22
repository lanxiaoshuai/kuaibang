package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.IMCollectionModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;

/**
 * Created by jie on 2019/11/28.
 */

public class IMCollectionModelImpl implements IMCollectionModel{
    @Override
    public void showCollectionDetail(int pageNum, int pageSize, int userId, final AsyncCallback callback) {
        api.collectionList(pageNum,pageSize,userId).enqueue(new Callback(callback, "获取收藏列表失败") {
            @Override
            public void getSuc(String body) {
                Log.e(TAG,"获取收藏列表成功"+body);
                PagingResponse microNotificationResponse = JSONUtil.fromJsonObjectList(gson, JSONUtil.getValueToString(body, "returnObject"), MissionBean.class);
                callback.onSuccess(microNotificationResponse);
            }
        });
    }



}
