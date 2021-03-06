package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.IMicroNotificationModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MicroNotificationModelImpl;
import com.witkey.witkeyhelp.view.IMicroNotificationView;

public class MicroNotificationPresenterImpl implements IMicroNotificationPresenter {
    private IMicroNotificationView view;
    private IMicroNotificationModel model;

    @Override
    public void getMicroNotificationList(int createUserId) {
        model.getMicroNotificationList(createUserId,new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMicroNootificationList((PagingResponse) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IMicroNotificationView view) {
        this.view = view;
        this.model = new MicroNotificationModelImpl();
    }
}
