package com.witkey.witkeyhelp.event;

/**
 * Created by jie on 2020/4/8.
 */

public class AdvertisementEvent {

    private String result;

    public String getLogin() {
        return result;
    }

    public void setLogin(String result) {
        this.result = result;
    }

    public AdvertisementEvent(String result) {
        this.result = result;
    }
}
