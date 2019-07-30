package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.model.ILostFoundModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.LostFoundModelImpl;
import com.witkey.witkeyhelp.presenter.ILostFoundDetailPresenter;
import com.witkey.witkeyhelp.view.ILostFoundDetailView;

public class LostFoundDetailPresenterImpl implements ILostFoundDetailPresenter {
    private ILostFoundModel model;
    private ILostFoundDetailView view;

    @Override
    public void getLostFoundDetail(int id) {
        model.getLostFoundDetail(id, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showLostFoundDetail((LostFoundBean) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(ILostFoundDetailView view) {
        this.view = view;
        this.model = new LostFoundModelImpl();
    }
}
