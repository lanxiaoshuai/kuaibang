package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.bean.LostFoundResponse;

import java.util.ArrayList;

public class LostFoundModelImpl implements com.witkey.witkeyhelp.model.ILostFoundModel {
    @Override
    public void getLostFoundList(LostFoundBean lostFoundBean, AsyncCallback callback) {
        ArrayList<LostFoundBean> rows = new ArrayList<>();
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        rows.add(new LostFoundBean("title", "content", "2019-07-09 15:54:15"));
        LostFoundResponse lostFoundRequest = new LostFoundResponse(9, rows);
        callback.onSuccess(lostFoundRequest);
    }

    @Override
    public void getLostFoundDetail(int id, AsyncCallback callback) {
        LostFoundBean lostFoundBean = new LostFoundBean("title", "content", "2019-07-09 15:54:15");
        callback.onSuccess(lostFoundBean);
    }

    @Override
    public void addLostFound(LostFoundBean lostFoundBean, AsyncCallback callback) {
        callback.onSuccess("");
    }
}
