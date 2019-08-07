package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

public class MicroNotifyGroupMember {
    /**
     * type	string	身份1群主2管理员3普通成员
     * realName	string	昵称
     * headUrl	string	头像
     * userId	int	用户ID
     */
    @SerializedName("catalogId")
    private int catalogId;
    @SerializedName("userId")
    private int userId;
    @SerializedName("type")
    private String type;
    @SerializedName("realName")
    private String realName;
    @SerializedName("remark")
    private String remark;
    @SerializedName("headUrl")
    private String headUrl;

    public MicroNotifyGroupMember() {
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MicroNotifyGroupMember{" +
                "catalogId=" + catalogId +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", realName='" + realName + '\'' +
                ", remark='" + remark + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public MicroNotifyGroupMember(int catalogId, int userId, String type, String realName, String headUrl) {
        this.catalogId = catalogId;
        this.userId = userId;
        this.type = type;
        this.realName = realName;
        this.headUrl = headUrl;
    }
}
