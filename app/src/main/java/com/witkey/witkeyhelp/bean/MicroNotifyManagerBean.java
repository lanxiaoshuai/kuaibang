package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MicroNotifyManagerBean implements Serializable {
    @SerializedName("catalogId")
    private int catalogId;   //主键id
    @SerializedName("name")
    private String name;    //群名称
    @SerializedName("parentId")
    private int parentId;     //上级id
    @SerializedName("createUserId")
    private String createUserId;    //创建user_id
    @SerializedName("createDate")
    private String createDate;    //创建时间
    @SerializedName("groupRemark")
    private String groupRemark;   //群简介
    @SerializedName("groupImage")
    private String groupImage;   //群头像

    @Override
    public String toString() {
        return "MicroNotifyManagerBean{" +
                "catalogId=" + catalogId +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", createUserId='" + createUserId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", groupRemark='" + groupRemark + '\'' +
                ", groupImage='" + groupImage + '\'' +
                '}';
    }

    public MicroNotifyManagerBean(int catalogId, String name, int parentId, String createUserId, String createDate, String groupRemark, String groupImage) {
        this.catalogId = catalogId;
        this.name = name;
        this.parentId = parentId;
        this.createUserId = createUserId;
        this.createDate = createDate;
        this.groupRemark = groupRemark;
        this.groupImage = groupImage;
    }

    public MicroNotifyManagerBean(String name, String describe, String todayString) {
        this.name = name;
        this.createDate = todayString;
        this.groupRemark = describe;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getGroupRemark() {
        return groupRemark;
    }

    public void setGroupRemark(String groupRemark) {
        this.groupRemark = groupRemark;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }
}
