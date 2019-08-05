package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MicroNotificationBean;

public interface IMicroNotificationModel extends IModel{
    void getMicroNotificationList(AsyncCallback callback);

    void addMicroNotification(MicroNotificationBean microNotificationBean, AsyncCallback asyncCallback);

    void getMicroNotificationDetail(boolean isCheck, AsyncCallback callback);
}
