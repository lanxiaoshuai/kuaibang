package com.witkey.witkeyhelp.model.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import com.tencent.bugly.crashreport.CrashReport;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.bean.DiamondBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.IModel.AsyncCallback;
import com.witkey.witkeyhelp.util.Error;
import com.witkey.witkeyhelp.util.ExceptionUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.LogUtil;
import com.witkey.witkeyhelp.util.SystemUtil;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;

/**
 * @author lingxu
 * @date 2019/7/16 8:56
 * @description retrofit callback
 */
public abstract class Callback implements retrofit2.Callback<String> {
    private final AsyncCallback callback;
    private String errorStr;
    private final String TAG = "llx";

    public Callback(AsyncCallback callback, String errorStr) {
        this.callback = callback;
        this.errorStr = errorStr;
    }

    @Override
    public void onResponse(Call<String> call, retrofit2.Response<String> response) {
        if (response == null) {

            // Error.handleError(callback, new Throwable("服务器出现异常,请等待一下"), errorStr);
            onFailure(call, new Throwable("网络出现波动，请稍后重试"));
            return;
        }


        if (response.isSuccessful()) {
            String body = response.body(); //code(状态码): response.code()    错误信息:response.errorBody().string()
            int code = JSONUtil.getValueToInt(body, "errorCode");
            LogUtil.logInfo("model-Response", "状态码为" + code);
            if (body != null) {
                if (code == 200) {
                    try {
                        getSuc(body);
                    } catch (Exception e) {
                        callback.onError("解析数据出了点问题...");
                        //errorToast("解析数据出了点问题...");
                        //ExceptionUtil.CatchException(e);
                        errorMessage(body);
                    }
                } else {
//                    code为其他错误码
                    Error.isNoToken(body, callback);

                    errorMessage(body);
                }
            } else {
                //获取到的数据为空

                callback.onError("获取数据出了点问题...");
                errorMessage(body);
            }
        } else {
            try {
                String errorResponse = response.errorBody().string();
                Error.isNoToken(errorResponse, callback);
                errorMessage(errorResponse);
            } catch (IOException e) {

                errorMessage(e.toString());
            }
        }
    }

    public abstract void getSuc(String body);


    @Override
    public void onFailure(Call<String> call, Throwable t) {
        //未上传成功
           if(callback!=null){
               Error.handleError(callback, t, errorStr);
           }else {

           }

    }

    IModel.AsyncCallback call = new IModel.AsyncCallback() {
        @Override
        public void onSuccess(Object data) {


        }

        @Override
        public void onError(Object data) {

        }
    };

    private void errorMessage(String errorStr) {
        try {

            CrashReport.postCatchedException(new Throwable(errorStr + "===========" + getVersionCode() + "============"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取版本号
     *
     * @param
     * @return 版本号
     */
    public int getVersionCode() {

        //获取包管理器
        PackageManager pm = MyAPP.getInstance().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(MyAPP.getInstance().getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }


}
