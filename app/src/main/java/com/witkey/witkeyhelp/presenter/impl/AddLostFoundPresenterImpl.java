package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.model.ILostFoundModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.LostFoundModelImpl;
import com.witkey.witkeyhelp.presenter.IAddLostFoundPresenter;
import com.witkey.witkeyhelp.view.IAddLostFoundView;

public class AddLostFoundPresenterImpl implements IAddLostFoundPresenter {
    private ILostFoundModel model;
    private IAddLostFoundView view;

    @Override
    public void addLostFound(LostFoundBean lostFoundBean) {
        model.addLostFound(lostFoundBean, new IModel.AsyncCallback() {
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
    public void init(IAddLostFoundView view) {
        this.view = view;
        this.model = new LostFoundModelImpl();
    }
}
