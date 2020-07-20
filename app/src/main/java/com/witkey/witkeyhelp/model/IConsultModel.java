package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;

import java.util.List;

public interface IConsultModel extends IModel {
    void saveConsult(MissionBean missionBean, AsyncCallback callback);

    void publishConsult(String businessId, AsyncCallback callback);


    void puhotoConsult(List<ReleasePhotoBean> missionBean, AsyncCallback callback);

    void receipt(int orderId, int userId, AsyncCallback callback);

    void wxAppletPay(String  body, String price,String ip,String businessId, AsyncCallback callback);

    void apiwxpayResult(String  outTradeNo, AsyncCallback callback);
}
