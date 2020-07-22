package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2020/2/24.
 */

public class ReasonListBean {


    /**
     * searchValue : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * remark : null
     * params : {}
     * id : 5
     * reason : 没有看清悬赏内容
     * type : 1
     */

    private Object searchValue;
    private Object createBy;
    private Object createTime;
    private Object updateBy;
    private Object updateTime;
    private Object remark;
    private ParamsBean params;
    private int id;
    @SerializedName("reason")
    private String reason;
    private int type;

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public Object getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Object createBy) {
        this.createBy = createBy;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class ParamsBean {
    }
}
