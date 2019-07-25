package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MissionBean;

public interface IConsultModel extends IModel{
    void saveConsult(MissionBean missionBean, AsyncCallback callback);

    void publishConsult(String businessId, AsyncCallback callback);
}
