package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MicroNotificationResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("rows")
    private ArrayList<MicroNotifyManagerBean> rows;

    @Override
    public String toString() {
        return "MicroNotificationResponse{" +
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

    public ArrayList<MicroNotifyManagerBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<MicroNotifyManagerBean> rows) {
        this.rows = rows;
    }

    public MicroNotificationResponse(int total, ArrayList<MicroNotifyManagerBean> rows) {
        this.total = total;
        this.rows = rows;
    }
}
