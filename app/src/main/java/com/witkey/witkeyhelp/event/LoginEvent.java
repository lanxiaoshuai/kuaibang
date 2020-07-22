package com.witkey.witkeyhelp.event;

/**
 * Created by jie on 2019/12/28.
 */

public class LoginEvent  {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LoginEvent(String login) {
        this.login = login;
    }
}
