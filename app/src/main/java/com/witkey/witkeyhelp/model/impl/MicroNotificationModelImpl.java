package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.Message;
import com.witkey.witkeyhelp.bean.MessageResponse;
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

    @Override
    public void addMicroNotification(MicroNotificationBean microNotificationBean, AsyncCallback asyncCallback) {
        asyncCallback.onSuccess("");
    }

    @Override
    public void getMicroNotificationDetail(boolean isCheck, AsyncCallback callback) {
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        messages.add(new Message("我是title", "我是content我是content我是content我是content我是content", "2019-05-28 15:15:15"));
        callback.onSuccess(new MessageResponse(5, messages));

    }
}
