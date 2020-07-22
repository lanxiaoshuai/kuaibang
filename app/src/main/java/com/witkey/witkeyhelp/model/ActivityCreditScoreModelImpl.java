package com.witkey.witkeyhelp.model;

import android.util.Log;

import com.witkey.witkeyhelp.model.util.Callback;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityCreditScoreModelImpl implements ActivityCreditScoreModel {
    @Override
    public void showBillDData(int pageNum, int  pageSize, int userId, final AsyncCallback callback) {
        Log.e("user",userId+"");
      api.reputNumlist(pageNum,pageSize,userId).enqueue(new Callback(callback, "获取账单信息失败") {
          @Override
          public void getSuc(String body) {
             // Log.e("tag",body);
              callback.onSuccess(body);
          }
      });
    }
}
