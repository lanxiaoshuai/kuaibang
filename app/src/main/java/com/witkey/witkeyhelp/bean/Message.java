package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author lingxu
 * @date 2019/7/11 14:34
 * @description 消息bean
 */
public class Message {
    private String title;
    private String date;
    private String avatar;

    @SerializedName("realName")
    private String realName;
    @SerializedName("content")
    private String content;
    @SerializedName("createTime")
    private String createTime;
    /*
     "searchValue": null,
     "createBy": null,
     "createTime": "2019-06-05 16:27:57",
     "updateBy": null,
     "updateTime": null,
     "remark": null,
     "params": {},
     "id": 112,
     "title": null,
     "content": "有新的服务发布,请注意查收4",
     "createUserName": null,
     "createUserId": null,
     "remarks": null,
     "delFlag": null,
     "msgId": null,
     "receiver": null,
     "mesType": null,
     "catalogId": null,
     "realName": "测试用户",
     "headUrl": null
     */

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", avatar='" + avatar + '\'' +
                ", realName='" + realName + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
