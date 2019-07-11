package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.Message;

import java.util.List;

public interface IMessageFragView extends IView {
    /**
     * 获取消息
     * @param messageList 消息列表
     */
    void showMessageList(List<Message> messageList);
}
