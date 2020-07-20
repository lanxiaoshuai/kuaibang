package com.witkey.witkeyhelp.model;

public interface IMeFragModel extends IModel{
    void getUser(int id, AsyncCallback callback);

    void getAcount(int userId, AsyncCallback callback);

    void getDeductionData(int userId,int deduction , AsyncCallback callback);

    void updateUserInfo(int userId,String realName , String headUrl,String sex,AsyncCallback callback);
}
