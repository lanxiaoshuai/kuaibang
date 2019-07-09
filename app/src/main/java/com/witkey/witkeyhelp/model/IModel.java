package com.witkey.witkeyhelp.model;

public interface IModel {
    String TAG = "llx";
//    Gson gson = MyAPP.getInstance().getGson();
//    Api api = MyAPP.getInstance().getApi();
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
