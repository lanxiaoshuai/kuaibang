package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IMessageFragView;

public interface IMessageFragPresenter extends IPresenter<IMessageFragView> {
    void getMessageList();
}
