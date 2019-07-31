package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author lingxu
 * @date 2019/7/26 11:52
 * @description 失物招领列表获取
 */
public class LostFoundResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("rows")
    private ArrayList<LostFoundBean> rows;

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

    public ArrayList<LostFoundBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<LostFoundBean> rows) {
        this.rows = rows;
    }

    public LostFoundResponse() {
    }

    public LostFoundResponse(int total, ArrayList<LostFoundBean> rows) {
        this.total = total;
        this.rows = rows;
    }
}
