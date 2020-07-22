package com.witkey.witkeyhelp.bean;

/**
 * Created by jie on 2020/5/15.
 */

public class LabelBean {
    private int  positions;
    private boolean isCheck;
    private String lable;

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public int getPositions() {
        return positions;
    }

    public void setPositions(int positions) {
        this.positions = positions;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    public LabelBean(int positions, boolean isCheck, String lable) {
        this.positions = positions;
        this.isCheck = isCheck;
        this.lable = lable;
    }
}
