package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author lingxu
 * @date 2019/7/26 11:52
 * @description 悬赏大厅列表获取
 */
public class MissionRequest {
    @SerializedName("total")
    private int total;
    @SerializedName("rows")
    private ArrayList<MissionBean> rows;

    @Override
    public String toString() {
        return "MissionRequest{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<MissionBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<MissionBean> rows) {
        this.rows = rows;
    }

    public MissionRequest() {
    }

    public MissionRequest(int total, ArrayList<MissionBean> rows) {
        this.total = total;
        this.rows = rows;
    }
}
