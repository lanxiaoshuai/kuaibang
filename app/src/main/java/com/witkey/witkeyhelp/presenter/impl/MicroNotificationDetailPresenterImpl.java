package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.MessageResponse;
import com.witkey.witkeyhelp.model.IMicroNotificationModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MicroNotificationModelImpl;
import com.witkey.witkeyhelp.view.IMicroNotificationDetailView;

public class MicroNotificationDetailPresenterImpl implements com.witkey.witkeyhelp.presenter.IMicroNotificationDetailPresenter {
    private IMicroNotificationDetailView view;
    private IMicroNotificationModel model;

    @Override
    public void getMicroNotificationDetail(int createUserId,boolean isCheck) {
        model.getMicroNotificationDetail(createUserId,isCheck ,new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMicroNotificationDetail((MessageResponse) data);
            }
            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IMicroNotificationDetailView view) {
        this.view = view;
        this.model = new MicroNotificationModelImpl();
    }
}
