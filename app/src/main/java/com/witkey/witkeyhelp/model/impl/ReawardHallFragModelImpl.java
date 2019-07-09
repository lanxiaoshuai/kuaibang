package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.Mission;
import com.witkey.witkeyhelp.bean.MissionFilter;
import com.witkey.witkeyhelp.model.IReawardHallFragModel;

import java.util.ArrayList;
import java.util.List;

public class ReawardHallFragModelImpl implements IReawardHallFragModel {
    @Override
    public void getMissionList(String chooseClassify, String chooseOrder, MissionFilter filter, AsyncCallback callback) {
        List<Mission> missions=new ArrayList<>();
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        missions.add(new Mission("我是title","紧急求助","我是content","152"));
        callback.onSuccess(missions);
    }
}
