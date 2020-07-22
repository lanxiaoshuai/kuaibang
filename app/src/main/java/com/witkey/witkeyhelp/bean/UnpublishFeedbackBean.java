package com.witkey.witkeyhelp.bean;

/**
 * Created by jie on 2019/11/29.
 */

public class UnpublishFeedbackBean {


    private boolean isChecked;
    private String msg="卧槽选中了";




    public UnpublishFeedbackBean(boolean isCheched,String msg) {
        this.isChecked = isCheched;
        this.msg=msg;

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
