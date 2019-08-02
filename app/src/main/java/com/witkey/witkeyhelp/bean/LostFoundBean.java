package com.witkey.witkeyhelp.bean;

import java.util.HashMap;

public class LostFoundBean {
    private String imgUrl;
    private String title;
    private String content;
    private String date;
    private HashMap<String, String> photoMap;

    public LostFoundBean(String title, String content, String date, HashMap<String, String> photoMap, String contact) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.photoMap = photoMap;
        this.contact = contact;
    }

    public HashMap<String, String> getPhotoMap() {
        return photoMap;
    }

    public void setPhotoMap(HashMap<String, String> photoMap) {
        this.photoMap = photoMap;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private String contact;
    private int id;

    @Override
    public String toString() {
        return "LostFoundBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public LostFoundBean() {
    }

    public LostFoundBean(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
