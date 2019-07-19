package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("userId")
    private int userId;
    @SerializedName("userName")
    private String userName;
    //    private String password;
    @SerializedName("userType")
    private String userType;//1悬赏主 2威客
    @SerializedName("headUrl")
    private String headUrl;//用户头像
    @SerializedName("realName")
    private String realName;//用户昵称

    @Override
    public String  toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userType='" + userType + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }

    private String searchValue;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    //        private String params" //未知
    private String salt;
    private String cardNo;
    private String sex;
    private String age;
    private String phone;
    private String email;
    private String cardPicUrl;
    private String cardJustUrl;
    private String cardBackUrl;
    private String address;
    private String available;
    private String lastLoginDate;
    private String lastLoginIP;
    private String clientId;
    private String checkState;
    private String checkUserId;
    private String checkUserName;
    private String checkDate;
    private String checkOpinion;
    private String createDate;
    private String remarks;
    private String delFlag;
    private String accessToken;
    private String errorTimes;
    private String ryToken;
    private String communityName;
    private String ryUserId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public User(int userId, String userName, String userType, String headUrl, String realName) {
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.headUrl = headUrl;
        this.realName = realName;
    }
}
