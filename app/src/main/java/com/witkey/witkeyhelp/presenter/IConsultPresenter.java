package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.view.IConsultView;

import java.util.List;

public interface IConsultPresenter extends IPresenter<IConsultView> {
    void saveConsult(MissionBean missionBean);

    void publishConsult(String businessId);

    void saveImagurlConsult(List<ReleasePhotoBean> missionBean);

    void receipt(int orderId, int userId);

    void wxAppletPay(String  body, String price,String ip,String businessId);

    void apiwxpayResult(String  outTradeNo);
}
