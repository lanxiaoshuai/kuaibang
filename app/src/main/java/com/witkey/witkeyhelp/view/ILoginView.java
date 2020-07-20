package com.witkey.witkeyhelp.view;


import android.view.View;

import com.witkey.witkeyhelp.bean.User;

/**
 * Created by Administrator on 2017/3/10.
 */

public interface ILoginView extends IView {
    /**
     * 密码登录后
     */
    void passSuccess(User user);
    /**
     * 验证码登录后
     */
    void codeSuccess(User  user);
    /**
     * 发送验证码成功
     */
    void getCodeSuccess(String code);

    void passRegisterSuccess();
}
