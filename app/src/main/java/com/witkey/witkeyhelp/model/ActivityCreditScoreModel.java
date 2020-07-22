package com.witkey.witkeyhelp.model;

/**
 * Created by jie on 2019/12/6.
 */

public interface ActivityCreditScoreModel extends IModel {

    void showBillDData(int pageNum, int pageSize, int userId, AsyncCallback callback);
}
