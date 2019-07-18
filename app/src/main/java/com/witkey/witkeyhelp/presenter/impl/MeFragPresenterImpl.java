package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.IMeFragModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MeFragModelImpl;
import com.witkey.witkeyhelp.view.IMeFragView;

public class MeFragPresenterImpl implements com.witkey.witkeyhelp.presenter.IMeFragPresenter {
    private IMeFragView view;
    private IMeFragModel model;
    @Override
    public void getUser(User user) {
        model.getUser(user, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onError(Object data) {

            }
        });
    }

    @Override
    public void init(IMeFragView view) {
        this.view=view;
        this.model=new MeFragModelImpl();
    }
}
