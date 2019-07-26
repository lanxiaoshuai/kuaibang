package com.witkey.witkeyhelp.bean;

public class LostFoundBean {
    private String imgUrl;
    private String title;
    private String content;
    private String date;

    @Override
    public String toString() {
        return "LostFoundBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
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

    public LostFoundBean( String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
