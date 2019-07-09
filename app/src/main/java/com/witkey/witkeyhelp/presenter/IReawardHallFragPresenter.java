package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.MissionFilter;
import com.witkey.witkeyhelp.view.IReawardHallFragView;

public interface IReawardHallFragPresenter extends IPresenter<IReawardHallFragView>{
    void getMissionList(String chooseClassify, String chooseOrder, MissionFilter filter);
}
