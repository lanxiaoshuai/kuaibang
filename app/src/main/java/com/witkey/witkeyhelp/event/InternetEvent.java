package com.witkey.witkeyhelp.event;

/**
 * Created by jie on 2020/5/16.
 */

public class InternetEvent {

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public InternetEvent(String login) {
        this.login = login;
    }
}
