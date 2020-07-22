package com.witkey.witkeyhelp.model;

/**
 * Created by jie on 2019/11/28.
 */

public interface IMCollectionModel extends IModel {

    void  showCollectionDetail(int pageNum,int pageSize,int userId,AsyncCallback callback);
}
