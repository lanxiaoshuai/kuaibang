package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.view.IMicroNotificationView;

public interface IMicroNotificationPresenter extends IPresenter<IMicroNotificationView> {
    void getMicroNotificationList(int createUserId);
}
