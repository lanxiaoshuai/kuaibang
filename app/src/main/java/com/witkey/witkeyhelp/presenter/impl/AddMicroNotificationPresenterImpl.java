package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;
import com.witkey.witkeyhelp.model.IMicroNotificationModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MicroNotificationModelImpl;
import com.witkey.witkeyhelp.view.IAddMicroNotificationView;

public class AddMicroNotificationPresenterImpl implements com.witkey.witkeyhelp.presenter.IAddMicroNotificationPresenter {
    private IAddMicroNotificationView view;
    private IMicroNotificationModel model;

    @Override
    public void addMicroNotification(MicroNotifyManagerBean microNotificationBean) {
        model.addMicroNotification(microNotificationBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.addSuc();
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IAddMicroNotificationView view) {
        this.view = view;
        this.model = new MicroNotificationModelImpl();
    }
}
