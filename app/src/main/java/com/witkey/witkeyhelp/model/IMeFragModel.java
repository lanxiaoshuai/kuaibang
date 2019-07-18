package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.User;

public interface IMeFragModel extends IModel{
    void getUser(User user, AsyncCallback callback);
}
