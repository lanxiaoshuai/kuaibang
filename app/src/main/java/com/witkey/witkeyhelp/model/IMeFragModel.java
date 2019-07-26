package com.witkey.witkeyhelp.model;

public interface IMeFragModel extends IModel{
    void getUser(int id, AsyncCallback callback);

    void getAcount(int userId, AsyncCallback callback);
}
