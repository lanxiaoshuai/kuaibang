package com.witkey.witkeyhelp.bean;

import java.io.Serializable;

public class MicroNotificationBean implements Serializable {
    private String title;
    private String content;
    private String date;
    private String avatar;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MicroNotificationBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MicroNotificationBean(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
