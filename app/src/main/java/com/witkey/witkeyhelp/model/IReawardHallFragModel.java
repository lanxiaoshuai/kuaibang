package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MissionFilter;

public interface IReawardHallFragModel extends IModel{
    void getMissionList(String chooseClassify, String chooseOrder, MissionFilter filter,String searchKeyWord,AsyncCallback callback);
}
