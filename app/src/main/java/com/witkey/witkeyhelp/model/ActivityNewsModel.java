package com.witkey.witkeyhelp.model;

/**
 * Created by jie on 2019/12/6.
 */

public interface ActivityNewsModel  extends IModel {

    void showBillDData(int pageNum,int  pageSize,int userId,int amountType,AsyncCallback callback);
}
