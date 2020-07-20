package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.view.impl.LoginActivity;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

/**
 * @author lingxu
 * @date 2019/7/12 15:22
 * @description 处理错误的工具类
 */
public class Error implements IModel {
    /**
     * 显示到前台 获取到服务器给到的错误信息
     *
     * @param s
     * @param callback
     */
    public static void isNoToken(String s, AsyncCallback callback) {
        // Log.e(TAG, "isNoToken: " + s);
        if (callback == null) {
            return;
        }
        int statsCode = JSONUtil.getValueToInt(s, "errorCode");
        String meesage = JSONUtil.getValueToString(s, "errorMessage");

        if (statsCode != 0) {
            //后台报的错误
            if (statsCode == 201) {
                LogUtil.logModelOnError("余额不足");
                callback.onError(meesage);
            } else if (statsCode == 203) {
                LogUtil.logModelOnError("用户名已经存在");
                //  callback.onError("用户名已经存在");
                callback.onError(meesage);
            } else if (statsCode == 301) {
                LogUtil.logModelOnError("接受的参数是null");
                //   callback.onError("参数为空");
                callback.onError(meesage);
            } else if (statsCode == 305) {
                LogUtil.logModelOnError("用户为null");
                //    callback.onError("请登录");
                callback.onError("该用户未注册");
            } else if (statsCode == 302) {
                LogUtil.logModelOnError("接受的参数不合法");
                //  callback.onError("参数不合法");
                callback.onError(meesage);
            } else if (statsCode == 304) {
                LogUtil.logModelOnError(meesage);
                callback.onError(meesage);
            } else if (statsCode == 303) {
                LogUtil.logModelOnError(meesage);
                callback.onError(meesage);
            } else if (statsCode == 500) {
                LogUtil.logModelOnError("网络出现波动，请稍后重试");
                callback.onError("网络出现波动，请稍后重试");

            } else {

                String message = JSONUtil.getValueToString(s, "errorMessage");
                LogUtil.logModelOnError("服务器--" + message);
                callback.onError("网络出现波动，请稍后重试");
            }
        } else {
            //获取未成功
            statsCode = JSONUtil.getValueToInt(s, "status_code");
            if (statsCode == 500) {
                LogUtil.logModelOnError("服务器错误");
                callback.onError("网络出现波动，请稍后重试");
            }
        }
    }

    public static void handleError(AsyncCallback callback, Throwable t, String errorStr) {
        if (t.getCause() != null) {
            String s;
            if ("Network is unreachable".equals(t.getCause().getMessage())) {
                s = "请确认网络已连接~~";
                callback.onError(s);
            } else if ("timeout".equals(t.getCause().getMessage())) {
                s = "请求超时啦,稍后重试哦~~";
                callback.onError(s);
            } else {
                if (TextUtils.isEmpty(errorStr)) {
                    callback.onError(errorStr);
                } else {
                    if (BaseActivity.NETWORKBOOLEAN) {

                        s = "网络异常,请确认网络是否连接~";
                    } else {
                        s = "请确认网络已连接~~";
                    }


                    callback.onError(s);
                }
                ExceptionUtil.CatchException(t);
            }
            LogUtil.logModelOnError("本地--" + errorStr);
        } else {
            callback.onError("网络出现波动，请稍后重试");
        }
    }

    public static void showError(String error, Context context) {
//        if ("登陆超时".equals(error)) {
//         //   Intent i = new Intent(context, LoginActivity.class);
////            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//           // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//          //  context.startActivity(i);
//        } else {
        //  ToastUtils.showShort(context, error, 2);
        //}
    }
}
