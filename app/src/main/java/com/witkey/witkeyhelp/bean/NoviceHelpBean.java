package com.witkey.witkeyhelp.bean;

/**
 * Created by jie on 2020/4/13.
 */

public class NoviceHelpBean {
    private String titleHome;
    private String title;
    private String guizhe;
    private String guizheTwo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGuizhe() {
        return guizhe;
    }

    public void setGuizhe(String guizhe) {
        this.guizhe = guizhe;
    }

    public String getGuizheTwo() {
        return guizheTwo;
    }

    public void setGuizheTwo(String guizheTwo) {
        this.guizheTwo = guizheTwo;
    }

    public NoviceHelpBean(String titleHome, String title, String guizhe, String guizheTwo) {
        this.titleHome = titleHome;
        this.title = title;
        this.guizhe = guizhe;
        this.guizheTwo = guizheTwo;
    }

    public String getTitleHome() {
        return titleHome;
    }

    public void setTitleHome(String titleHome) {
        this.titleHome = titleHome;
    }

    @Override
    public String toString() {
        return "NoviceHelpBean{" +
                "titleHome='" + titleHome + '\'' +
                ", title='" + title + '\'' +
                ", guizhe='" + guizhe + '\'' +
                ", guizheTwo='" + guizheTwo + '\'' +
                '}';
    }
}
