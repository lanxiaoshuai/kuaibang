package com.witkey.witkeyhelp.event;

/**
 * Created by jie on 2020/4/2.
 */

public class UnreadBean {
    private String result;

    public String getLogin() {
        return result;
    }

    public void setLogin(String result) {
        this.result = result;
    }

    public UnreadBean(String result) {
        this.result = result;
    }
}
