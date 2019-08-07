package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.IMicroNotificationModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MicroNotificationModelImpl;
import com.witkey.witkeyhelp.view.IMicroNotifyGroupDetailView;

public class MicroNotifyGroupDetailPresenterImpl implements IMicroNotifyGroupDetailPresenter {
    private IMicroNotificationModel model;
    private IMicroNotifyGroupDetailView view;

    @Override
    public void getGroupMember(int catalog_id, int page) {
        model.getGroupMember(catalog_id, page, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showGroupMember((PagingResponse) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IMicroNotifyGroupDetailView view) {
        this.view = view;
        this.model = new MicroNotificationModelImpl();
    }
}
