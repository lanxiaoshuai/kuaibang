package com.witkey.witkeyhelp.bean;

/**
 * @author lingxu
 * @date 2019/7/9 14:11
 * @description 悬赏大厅列表
 */
public class Mission {
    private String title;
    private String type;
    private String content;
    private String money;

    public Mission(String title, String type, String content, String money) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.money = money;
    }

    public Mission() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
