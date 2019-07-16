package com.witkey.witkeyhelp.presenter;


import com.witkey.witkeyhelp.bean.LoginRequest;
import com.witkey.witkeyhelp.view.ILoginView;

public interface ILoginPresenter extends IPresenter<ILoginView> {
    /**
     * 密码登录后
     */
    void Login(LoginRequest loginRequest);

    /**
     * 发送验证码成功
     */
    void GetCode(LoginRequest loginRequest);
}
