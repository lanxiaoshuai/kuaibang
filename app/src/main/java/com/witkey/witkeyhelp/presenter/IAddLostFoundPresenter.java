package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.view.IAddLostFoundView;

public interface IAddLostFoundPresenter extends IPresenter<IAddLostFoundView> {
    void addLostFound(LostFoundBean lostFoundBean);
}
