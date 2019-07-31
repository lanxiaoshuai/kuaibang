package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.bean.LostFoundResponse;
import com.witkey.witkeyhelp.model.ILostFoundModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.LostFoundModelImpl;
import com.witkey.witkeyhelp.view.ILostFoundView;

public class LostFoundPresenterImpl implements com.witkey.witkeyhelp.presenter.ILostFoundPresenter {
    private ILostFoundView view;
    private ILostFoundModel model;

    @Override
    public void getLostFoundList(LostFoundBean lostFoundBean) {
        model.getLostFoundList(lostFoundBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showLostFoundList((LostFoundResponse) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(ILostFoundView view) {
        this.view = view;
        this.model = new LostFoundModelImpl();
    }
}
