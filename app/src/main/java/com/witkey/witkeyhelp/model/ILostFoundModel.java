package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.LostFoundBean;

public interface ILostFoundModel extends IModel{
    void getLostFoundList(LostFoundBean lostFoundBean, AsyncCallback callback);

    void getLostFoundDetail(int id, AsyncCallback callback);

    void addLostFound(LostFoundBean lostFoundBean, AsyncCallback callback);
}
