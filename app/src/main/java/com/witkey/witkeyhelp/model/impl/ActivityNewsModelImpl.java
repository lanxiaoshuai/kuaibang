package com.witkey.witkeyhelp.model.impl;

import android.util.Log;

import com.witkey.witkeyhelp.model.ActivityNewsModel;
import com.witkey.witkeyhelp.model.SystemMessageModel;
import com.witkey.witkeyhelp.model.util.Callback;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityNewsModelImpl implements ActivityNewsModel {
    @Override
    public void showBillDData(int pageNum, int  pageSize, int userId, int amountType, final AsyncCallback callback) {
      api.accountFlow(pageNum,pageSize,userId,amountType).enqueue(new Callback(callback, "获取账单信息失败") {
          @Override
          public void getSuc(String body) {
             // Log.e("tag",body);
              callback.onSuccess(body);
          }
      });
    }
}
