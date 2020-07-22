package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.SystemMessageBean;
import com.witkey.witkeyhelp.model.SystemMessageModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;

/**
 * Created by jie on 2019/12/6.
 */

public class SystemMessageModelImpl implements SystemMessageModel {


    @Override
    public void showSystemMessages(int pageNum, int paegsize,int userId,final AsyncCallback asyncCallback) {
          api.SystemsMessage(pageNum,paegsize,userId).enqueue(new Callback(asyncCallback, "获取系统消息失败") {
              @Override
              public void getSuc(String body) {
                  SystemMessageBean beanFromJson = JsonUtils.getBeanFromJson(body, SystemMessageBean.class);
                  asyncCallback.onSuccess(beanFromJson);
              }
          });
    }
}
