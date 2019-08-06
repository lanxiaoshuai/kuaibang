package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;
import com.witkey.witkeyhelp.model.IMicroNotificationModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MicroNotificationModelImpl;
import com.witkey.witkeyhelp.view.IMicroNotifyManagerView;

import java.util.ArrayList;

public class MicroNotifyManagerPresenterImpl implements com.witkey.witkeyhelp.presenter.IMicroNotifyManagerPresenter {
    private IMicroNotificationModel model;
    private IMicroNotifyManagerView view;

    @Override
    public void init(IMicroNotifyManagerView view) {
        this.view = view;
        this.model = new MicroNotificationModelImpl();
    }

    @Override
    public void getMicroNotifyManagerList(int createUserId, int parentId) {
        model.getMicroNotifyManagerList(createUserId, parentId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMicroNotifyManager((ArrayList<MicroNotifyManagerBean>) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }
}
