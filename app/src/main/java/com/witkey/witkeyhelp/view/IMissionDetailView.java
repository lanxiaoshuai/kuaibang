package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.MissionBean;

public interface IMissionDetailView extends IView {
    void showMission(MissionBean missionBean);
    void receiptSuc();
}
