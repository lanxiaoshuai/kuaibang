package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MissionBean;

public interface IMissionModel extends IModel{
    void getMissionList(MissionBean missionBean, String searchKeyWord, AsyncCallback callback);

    void getMissionDetail(String businessId, AsyncCallback callback);

    void receipt(int orderId, int userId, AsyncCallback callback);

    void getReleaseMissionList(MissionBean missionBean, AsyncCallback callback);

    void getReceiveMissionList(MissionBean missionBean, AsyncCallback callback);

    void confirmOrder(int orderId, int userId, AsyncCallback callback);
}
