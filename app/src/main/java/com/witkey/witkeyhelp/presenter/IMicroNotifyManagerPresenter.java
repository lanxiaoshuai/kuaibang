package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IMicroNotifyManagerView;

public interface IMicroNotifyManagerPresenter extends IPresenter<IMicroNotifyManagerView> {
    void getMicroNotifyManagerList(int createUserId, int parentId);
}
