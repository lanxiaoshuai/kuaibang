package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.view.ILostFoundView;

public interface ILostFoundPresenter extends IPresenter<ILostFoundView>{
    void getLostFoundList(LostFoundBean missionBean);
}
