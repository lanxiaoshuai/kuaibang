package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.view.IMyMissionView;

public interface IMyMissionPresenter extends IPresenter<IMyMissionView>{
    void getReleaseMissionList(MissionBean missionBean);
    void getReceiveMissionList(MissionBean missionBean);
}
