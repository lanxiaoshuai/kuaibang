package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.view.IReawardHallFragView;

public interface IReawardHallFragPresenter extends IPresenter<IReawardHallFragView>{
    void getMissionList(MissionBean bean, String searchKeyWord);
}
