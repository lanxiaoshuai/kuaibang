package com.witkey.witkeyhelp.bean;

public class MissionFilter {
    /**
     * 任务分类
     */
    private String missionFilter;
    /**
     * 任务类型
     */
    private String missionType;
    /**
     * 是否需要保证金
     */
    private boolean isNeedBond;

    public String getMissionFilter() {
        return missionFilter;
    }

    public void setMissionFilter(String missionFilter) {
        this.missionFilter = missionFilter;
    }

    public String getMissionType() {
        return missionType;
    }

    public void setMissionType(String missionType) {
        this.missionType = missionType;
    }

    public boolean isNeedBond() {
        return isNeedBond;
    }

    public void setNeedBond(boolean needBond) {
        isNeedBond = needBond;
    }

    public MissionFilter(String missionFilter, String missionType, boolean isNeedBond) {
        this.missionFilter = missionFilter;
        this.missionType = missionType;
        this.isNeedBond = isNeedBond;
    }

    public MissionFilter() {
    }
}
