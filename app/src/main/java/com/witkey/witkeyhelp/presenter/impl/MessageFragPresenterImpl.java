package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.Message;
import com.witkey.witkeyhelp.model.IMessageFragModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MessageFragModelImpl;
import com.witkey.witkeyhelp.presenter.IMessageFragPresenter;
import com.witkey.witkeyhelp.view.IMessageFragView;

import java.util.List;

public class MessageFragPresenterImpl implements IMessageFragPresenter {
    private IMessageFragView view;
    private IMessageFragModel model;

    @Override
    public void getMessageList() {
        model.getMessageList(new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMessageList((List<Message>) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IMessageFragView view) {
        model = new MessageFragModelImpl();
        this.view = view;
    }
}
