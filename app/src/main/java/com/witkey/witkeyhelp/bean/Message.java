package com.witkey.witkeyhelp.bean;

/**
 * @author lingxu
 * @date 2019/7/11 14:34
 * @description 消息bean
 */
public class Message {
    private String title;
    private String content;
    private String date;
    private String avatar;

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Message(String title, String content, String date, String avatar) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.avatar = avatar;
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

    public Message(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Message() {
    }
}
