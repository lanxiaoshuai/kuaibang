package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MicroNotificationResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("rows")
    private ArrayList<MicroNotificationBean> rows;

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

    public ArrayList<MicroNotificationBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<MicroNotificationBean> rows) {
        this.rows = rows;
    }

    public MicroNotificationResponse(int total, ArrayList<MicroNotificationBean> rows) {
        this.total = total;
        this.rows = rows;
    }
}
