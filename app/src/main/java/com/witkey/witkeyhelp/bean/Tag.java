package com.witkey.witkeyhelp.bean;

import java.io.Serializable;

/**
 * @author lingxu
 * @date 2019/7/12 9:36
 * @description Tag
 */
public class Tag implements Serializable {
    private int id;
    private String name;
    private String title;
    private int picId;//图片id

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(int id, String name, int picId) {
        this.id = id;
        this.name = name;
        this.picId = picId;
    }

    public int getPicId() {
        return picId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", picId=" + picId +
                '}';
    }
}
