package com.witkey.witkeyhelp.view;


/**
 * Created by Administrator on 2017/3/10.
 */

public interface ILoginView extends IView {
    /**
     * 密码登录后
     */
    void passSuccess();
    /**
     * 验证码登录后
     */
    void codeSuccess();
    /**
     * 发送验证码成功
     */
    void getCodeSuccess();
}
