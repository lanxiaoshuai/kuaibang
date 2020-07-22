package com.witkey.witkeyhelp.model;

/**
 * Created by jie on 2019/12/6.
 */

public interface SystemMessageModel extends IModel {


    void showSystemMessages(int pageNum,int paegsize ,int userId,AsyncCallback asyncCallback );
}
