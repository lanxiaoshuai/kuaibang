package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IMicroNotificationDetailView;

public interface IMicroNotificationDetailPresenter extends IPresenter<IMicroNotificationDetailView>{

    void getMicroNotificationDetail(int createUserId,boolean isCheck);

}
