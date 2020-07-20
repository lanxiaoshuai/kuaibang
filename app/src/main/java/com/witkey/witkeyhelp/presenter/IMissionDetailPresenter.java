package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IMissionDetailView;

public interface IMissionDetailPresenter extends IPresenter<IMissionDetailView> {
    void getMissionDetail(String businessId, int userId);

    void receipt(int orderId, int userId);

    void collection(int orderId, int userId);

    void cancelCollection(int orderId, int userId);


    void taskRelievingCollection(String receiver, String orderId, String businessId, String remark,int start);


    void  confirmsReceiveOrder(String businessId,int taskdetails);



    void getDeductionData(int userId,int deduction );

    void getAcount(int userId);

    /**
     * 上传反馈内容
     */
    void UploadFeedback(String content, int userId ,String imgUrl,int type,Integer  businessId,int orderId);
}
