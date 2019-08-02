package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.MicroNotificationBean;
import com.witkey.witkeyhelp.bean.MicroNotificationResponse;

import java.util.ArrayList;

public class MicroNotificationModelImpl implements com.witkey.witkeyhelp.model.IMicroNotificationModel {
    @Override
    public void getMicroNotificationList(AsyncCallback callback) {
        ArrayList<MicroNotificationBean> rows = new ArrayList<>();
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new MicroNotificationBean("title", "content", "2019-07-09 15:54:15"));
        MicroNotificationResponse microNotificationResponse = new MicroNotificationResponse(9, rows);
        callback.onSuccess(microNotificationResponse);
    }
}
