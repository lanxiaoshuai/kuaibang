package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.view.IMicroNotifyGroupDetailView;

public interface IMicroNotifyGroupDetailPresenter extends IPresenter<IMicroNotifyGroupDetailView> {

    void getGroupMember(int catalog_id, int page);
}
