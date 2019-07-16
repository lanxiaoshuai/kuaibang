package com.witkey.witkeyhelp.model.util;

import android.util.Log;

import com.witkey.witkeyhelp.bean.net.RequestBean;
import com.witkey.witkeyhelp.model.IModel.AsyncCallback;
import com.witkey.witkeyhelp.util.ExceptionUtil;
import com.witkey.witkeyhelp.util.LogUtil;
import com.witkey.witkeyhelp.util.Error;

import java.io.IOException;

import retrofit2.Call;

public abstract class Callback implements retrofit2.Callback<String> {
    private AsyncCallback callback;
    private String errorStr;

    public Callback(AsyncCallback callback, RequestBean bean) {
        this.callback = callback;
    }

    public Callback(AsyncCallback callback, String errorStr) {
        this.callback = callback;
        this.errorStr = errorStr;
    }



    @Override
    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
        Log.d("llx", "onResponse: "+response);
        if (response.isSuccessful()) {
            String body = response.body();
            int code = response.code();
            LogUtil.logInfo("model-Response", "状态码为" + code);
            if (code == 204) {
                getSuc("");
            } else {
                if (body != null) {
                    try {
                        getSuc(body);
                    } catch (Exception e) {
                        callback.onError("解析数据出了点问题...");
                        ExceptionUtil.CatchException(e);
                    }
                } else {
                    //获取到的数据为空
                    callback.onError("获取数据出了点问题...");
                }
            }
        } else {
            try {
                String errorResponse = response.errorBody().string();
                    Error.isNoToken(errorResponse, callback);
            } catch (IOException e) {
                ExceptionUtil.CatchException(e);
            }
        }
    }

    public abstract void getSuc(String body);


    @Override
    public void onFailure(Call<String> call, Throwable t) {
        //未上传成功
            Error.handleError(callback, t, errorStr);
    }
}
