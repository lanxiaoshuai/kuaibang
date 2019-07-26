package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MissionBean;

public interface IReawardHallFragModel extends IModel{
    void getMissionList(MissionBean missionBean, String searchKeyWord, AsyncCallback callback);
}
