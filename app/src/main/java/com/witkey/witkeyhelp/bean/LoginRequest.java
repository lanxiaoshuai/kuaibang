package com.witkey.witkeyhelp.bean;

public class LoginRequest {
    private String userName;
    private String password;
    private String userType; //暂未使用
    private boolean isPass;

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public LoginRequest(String userName, String password, boolean isPass) {
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

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginRequest() {
    }
}
