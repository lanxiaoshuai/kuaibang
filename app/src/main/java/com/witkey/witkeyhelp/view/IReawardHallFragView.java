package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.Mission;

import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/9 16:10
 * @description
 */
public interface IReawardHallFragView extends IView{
    /**
     * 获取任务列表
     */
    void showMissionList(List<Mission> missions);
}
