package com.witkey.witkeyhelp.bean;

public class LoginRequest {
    private String userName;
    private String password;
    private String userType; //暂未使用
    private boolean isPass;
    private String isInvitationCode;
    private String verifyCode;

    private String locationName;


    private String niquelydentifiesuI;

    public String getNiquelydentifiesuI() {
        return niquelydentifiesuI;
    }

    public void setNiquelydentifiesuI(String niquelydentifiesuI) {
        this.niquelydentifiesuI = niquelydentifiesuI;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationlName) {
        this.locationName = locationlName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsInvitationCode() {
        return isInvitationCode;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setIsInvitationCode(String isInvitationCode) {
        this.isInvitationCode = isInvitationCode;

    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public LoginRequest(String userName,String password, boolean isPass,String isInvitationCode,String  verifyCode,String locationName,String niquelydentifiesuI) {
        this.userName = userName;
        this.password = password;
        this.isPass = isPass;
        this.isInvitationCode=isInvitationCode;
        this.verifyCode=verifyCode;
        this.locationName=locationName;
        this.niquelydentifiesuI=niquelydentifiesuI;
    }
    public LoginRequest(String userName,String password,boolean isPass) {
        this.userName = userName;
        this.password = password;
        this.isPass = isPass;

    }
    @Override
    public String toString() {
        return "LoginRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isPass=" + isPass +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String userName) {
        this.userName = userName;

    }

    public LoginRequest() {
    }
}
