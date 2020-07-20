package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MissionBean;

public interface IMissionModel extends IModel{
    void getMissionList(MissionBean missionBean, String circleId, AsyncCallback callback);

    void getMissionDetail(String businessId,int userId, AsyncCallback callback);

    void receipt(int orderId, int userId, AsyncCallback callback);

    void getReleaseMissionList(MissionBean missionBean, AsyncCallback callback);

    void getReceiveMissionList(MissionBean missionBean, AsyncCallback callback);

    void confirmOrder(int orderId, int userId, AsyncCallback callback);

    void ollection(int orderId, int userId, AsyncCallback callback);


    void cancelCollection(int orderId, int userId, AsyncCallback callback);

    void taskRelievingCollection(String receiver, String orderId, String businessId, String remark,int start,AsyncCallback callback);

    void  confirmsReceiveOrder(String businessId, int taskdetails,AsyncCallback callback);

    void getDeductionData(int userId,int deduction , AsyncCallback callback);


    void getAcount(int userId, AsyncCallback callback);

    /**
     * 上传反馈内容
     */
    void UploadFeedback(String content, int userId ,String imgUrl,int type,Integer  businessId  , int orderId,AsyncCallback callback);

}
