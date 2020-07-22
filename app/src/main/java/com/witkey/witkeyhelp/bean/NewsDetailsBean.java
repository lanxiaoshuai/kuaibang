package com.witkey.witkeyhelp.bean;

/**
 * Created by jie on 2019/12/6.
 */

public class NewsDetailsBean {

    private int  type;
    private String title;
    private String content;
    private String price;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public NewsDetailsBean(int type, String title, String content, String price) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
