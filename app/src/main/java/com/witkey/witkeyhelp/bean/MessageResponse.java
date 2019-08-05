package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author lingxu
 * @date 2019/7/26 11:52
 * @description 消息列表获取
 */
public class MessageResponse {
    @SerializedName("total")
    private int total;
    @SerializedName("rows")
    private ArrayList<Message> rows;

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

    public ArrayList<Message> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Message> rows) {
        this.rows = rows;
    }

    public MessageResponse() {
    }

    public MessageResponse(int total, ArrayList<Message> rows) {
        this.total = total;
        this.rows = rows;
    }
}
