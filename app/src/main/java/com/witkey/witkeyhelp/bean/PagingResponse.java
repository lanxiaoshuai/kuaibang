package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author lingxu
 * @date 2019/8/7 18:41
 * @description 分页处理
 */
public class PagingResponse<T> {
    @SerializedName("total")
    private int total;
    @SerializedName("rows")
    private ArrayList<T> rows;

    @Override
    public String toString() {
        return "PagingResponse{" +
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

    public ArrayList<T> getRows() {
        return rows;
    }

    public void setRows(ArrayList<T> rows) {
        this.rows = rows;
    }

    public PagingResponse() {
    }

    public PagingResponse(int total, ArrayList<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
