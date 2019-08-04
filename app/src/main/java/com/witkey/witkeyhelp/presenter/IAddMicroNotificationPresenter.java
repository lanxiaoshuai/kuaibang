package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.MicroNotificationBean;
import com.witkey.witkeyhelp.view.IAddMicroNotificationView;

public interface IAddMicroNotificationPresenter extends IPresenter<IAddMicroNotificationView> {
    void addMicroNotification(MicroNotificationBean microNotificationBean);
}
