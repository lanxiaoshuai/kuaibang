package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.PayInfoBean;

public interface IConsultView extends IView{
    void saveSuc(String businessId);
    void publishSuc();

    void saveImgSuc(String  imgurl);

    void receiptSuc();


    void wxAppletPay(PayInfoBean payInfoBean);

    void wxAppletPayResult(String result);


    void wxAppletPayError(String errorresult);
}
