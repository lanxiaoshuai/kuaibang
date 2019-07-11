package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.Message;

import java.util.ArrayList;

public class MessageFragModelImpl implements com.witkey.witkeyhelp.model.IMessageFragModel {
    @Override
    public void getMessageList(AsyncCallback callback) {
        ArrayList<Message> messages=new ArrayList<>();
        messages.add(new Message("我是title","我是content我是content我是content我是content我是content","2019-05-28 15:15:15"));
        messages.add(new Message("我是title","我是content我是content我是content我是content我是content","2019-05-28 15:15:15"));
        messages.add(new Message("我是title","我是content我是content我是content我是content我是content","2019-05-28 15:15:15"));
        messages.add(new Message("我是title","我是content我是content我是content我是content我是content","2019-05-28 15:15:15"));
        messages.add(new Message("我是title","我是content我是content我是content我是content我是content","2019-05-28 15:15:15"));
        callback.onSuccess(messages);
    }
}
