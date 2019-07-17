package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.LoginRequest;

/**
 * 相关登录操作
 */

public interface ILoginModel extends IModel {
    /**
     * 登录
     */
    void Login(LoginRequest loginRequest, AsyncCallback callback);

    /**
     * 登录
     */
    void register(LoginRequest loginRequest, AsyncCallback callback);

    /**
     * 发送验证码
     */
    void GetCode(LoginRequest loginRequest, AsyncCallback callback);

    /**
     * 提交验证码,新密码
     */
    void commitPass(LoginRequest loginRequest, AsyncCallback callback);
    /**
     * `登出
     */
    void logOut(LoginRequest requestBean, AsyncCallback callback);
}
