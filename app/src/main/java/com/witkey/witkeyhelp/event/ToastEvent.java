package com.witkey.witkeyhelp.event;

/**
 * Created by jie on 2020/5/12.
 */

public class ToastEvent {

    private String toast;

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

    public ToastEvent(String toast) {
        this.toast = toast;
    }
}
