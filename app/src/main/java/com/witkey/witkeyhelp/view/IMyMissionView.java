package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.PagingResponse;

public interface IMyMissionView extends IView{
    void showMissionList(PagingResponse missionResponse);

    void onError(String  msg);
}
