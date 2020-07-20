package com.witkey.witkeyhelp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.model.api.Api;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;

public interface IModel {
    String TAG = "llx";
    Gson gson = MyAPP.getInstance().getGson();
    Api api = MyAPP.getInstance().getApi();

    IModel.AsyncCallback callback = new IModel.AsyncCallback() {
        @Override
        public void onSuccess(Object data) {


        }

        @Override
        public void onError(Object data) {

            ToastUtils.showTestShort(MyAPP.getInstance(), data.toString());
            DialogUtil.dismissProgress();
        }
    };

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface AsyncCallback {
        /**
         * 成功时
         */
        void onSuccess(Object data);

        /**
         * 失败时
         */
        void onError(Object data);
    }
}
