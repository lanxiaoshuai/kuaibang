package com.witkey.witkeyhelp.event;

/**
 * Created by jie on 2020/1/20.
 */

public class ResultEvent {

    private String result;

    public String getLogin() {
        return result;
    }

    public void setLogin(String result) {
        this.result = result;
    }

    public ResultEvent(String result) {
        this.result = result;
    }
}
