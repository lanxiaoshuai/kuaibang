package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.impl.MissionModelImpl;
import com.witkey.witkeyhelp.view.IMissionDetailView;

public class MissionDetailPresenterImpl implements com.witkey.witkeyhelp.presenter.IMissionDetailPresenter {
    private IMissionModel model;
    private IMissionDetailView view;

    @Override
    public void getMissionDetail(String businessId) {
        model.getMissionDetail(businessId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMission((MissionBean) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void receipt(int orderId, int userId) {
        model.receipt(orderId, userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.receiptSuc();
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IMissionDetailView view) {
        this.view = view;
        this.model = new MissionModelImpl();
    }
}
