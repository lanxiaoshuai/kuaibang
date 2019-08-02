package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.MicroNotificationResponse;

public interface IMicroNotificationView extends IView {
    void showMicroNootificationList(MicroNotificationResponse microNotificationResponse);
}
