package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;

public interface IMicroNotificationModel extends IModel{
    void getMicroNotificationList(int createUserId,AsyncCallback callback);

    void addMicroNotification(MicroNotifyManagerBean microNotificationBean, AsyncCallback asyncCallback);

    void getMicroNotificationDetail(int createUserId,boolean isCheck, AsyncCallback callback);

    void getMicroNotifyManagerList(int createUserId, int parentId, AsyncCallback callback);
}
