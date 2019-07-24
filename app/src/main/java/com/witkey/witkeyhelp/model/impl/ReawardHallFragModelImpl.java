package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.Mission;
import com.witkey.witkeyhelp.bean.MissionFilter;
import com.witkey.witkeyhelp.model.IReawardHallFragModel;
import com.witkey.witkeyhelp.model.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ReawardHallFragModelImpl implements IReawardHallFragModel {
    @Override
    public void getMissionList(String chooseClassify, String chooseOrder, MissionFilter filter,String searchKeyWord, AsyncCallback callback) {
        Mission mission = new Mission();
        api.getTaskList(
                mission.getPageNum()+"",
                mission.getPageSize()+"",
                mission.getBusinessType()+"",
                mission.getPaymentType()+"",
                mission.getLongitude()+"",
                mission.getLatitude()+"",
                mission.getPaymentType()+"",
                mission.getBiddingType()+"",
                mission.getBondType()+""
                ).enqueue(new Callback(callback,"获取列表失败") {
            @Override
            public void getSuc(String body) {
                Log.d(TAG, "ReawardHallFragModelImpl-getMissionList: "+body);

            }
        });
        List<Mission> missions=new ArrayList<>();
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        callback.onSuccess(missions);
    }
}
