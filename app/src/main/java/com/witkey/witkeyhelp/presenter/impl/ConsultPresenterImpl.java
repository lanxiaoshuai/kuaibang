package com.witkey.witkeyhelp.presenter.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PayInfoBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.model.IConsultModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.ConsultModelImpl;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IConsultView;

import java.util.List;

public class ConsultPresenterImpl implements com.witkey.witkeyhelp.presenter.IConsultPresenter {
    private IConsultView view;
    private IConsultModel model;

    @Override
    public void saveConsult(MissionBean missionBean) {
        model.saveConsult(missionBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.saveSuc((String) data);
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError((String) data);

            }
        });
    }


    @Override
    public void saveImagurlConsult(List<ReleasePhotoBean> missionBean) {
        model.puhotoConsult(missionBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.saveImgSuc((String) data);
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError((String) data);

            }
        });
    }

    @Override
    public void receipt(int orderId, int userId) {
        model.receipt(orderId, userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.receiptSuc();
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void wxAppletPay(String body, String price, String ip,String businessId) {
        model.wxAppletPay(body, price, ip, businessId,new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();

                view.wxAppletPay((PayInfoBean) data);
            }

            @Override
            public void onError(Object data) {

                DialogUtil.dismissProgress();
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void apiwxpayResult(String outTradeNo) {
        model.apiwxpayResult(outTradeNo, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.wxAppletPayResult("");
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.wxAppletPayError(data.toString());
            }
        });
    }


    @Override
    public void publishConsult(String businessId) {
        model.publishConsult(businessId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.publishSuc();
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IConsultView view) {
        this.view = view;
        this.model = new ConsultModelImpl();
    }
}
