package com.witkey.witkeyhelp.bean;

import java.io.Serializable;

/**
 * Created by jie on 2020/4/1.
 */

public class ReleasePhotoBean implements Serializable {
    private String url;
    private boolean aBoolean;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    @Override
    public String toString() {
        return "ReleasePhotoBean{" +
                "url='" + url + '\'' +
                ", aBoolean=" + aBoolean +
                '}';
    }

    public ReleasePhotoBean(String url, boolean aBoolean) {
        this.url = url;
        this.aBoolean = aBoolean;
    }
}
