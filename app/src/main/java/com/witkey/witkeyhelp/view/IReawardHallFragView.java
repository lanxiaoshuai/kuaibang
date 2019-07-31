package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.MissionResponse;

/**
 * @author lingxu
 * @date 2019/7/9 16:10
 * @description
 */
public interface IReawardHallFragView extends IView{
    /**
     * 获取任务列表
     */
    void showMissionList(MissionResponse missionResponse);
}
