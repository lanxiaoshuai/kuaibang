package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.bean.net.RequestBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.view.impl.LoginActivity;
import com.googlecode.jsonplugin.JSONException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/12 15:22
 * @description 处理错误的工具类
 */
public class Error implements IModel {
    /**
     * 获取到服务器给到的错误信息
     *
     * @param s
     * @param callback
     */
    public static void isNoToken(String s, AsyncCallback callback, RequestBean handleErrorBean) {
        Log.d(TAG, "isNoToken: " + s);
        int statsCode = JSONUtil.getValueToInt(s, "status_code");
        if (statsCode == 500) {
            LogUtil.logModelOnError("服务器错误", handleErrorBean);
            callback.onError("服务器错误");
        } else if (statsCode == 403) {
            String message = JSONUtil.getValueToString(s, "message");
            LogUtil.logModelOnError("服务器--" + message, handleErrorBean);
            callback.onError(message);
        } else if (statsCode == 401) {
            LogUtil.logModelOnError("服务器登陆超时", handleErrorBean);
            callback.onError("登陆超时");
//            Log.d(Use_TAG, "isNoToken: " + MyAPP.getInstance().getToken() + "----" + SharedPreferencesUtil.getLocal(MyAPP.getInstance()));
        } else if (statsCode == 422) {
            if (s.contains("errors")) {
                String error = JSONUtil.getValueToString(s, "errors");
                Object obj = null;
                try {
                    obj = com.googlecode.jsonplugin.JSONUtil.deserialize(error);
                    HashMap<String, List<String>> objMap = (HashMap) obj;
                    Collection<List<String>> values = objMap.values();
                    if (values.size() != 0) {
                        for (List<String> str : values) {
                            String s1 = str.get(0);
                            LogUtil.logModelOnError("服务器--" + s1, handleErrorBean);
                            callback.onError(s1);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String message = JSONUtil.getValueToString(s, "message");
            LogUtil.logModelOnError("服务器--" + message, handleErrorBean);
            callback.onError(message);
        }
    }

    @Deprecated
    public static void handleError(AsyncCallback callback, Throwable t, String errorStr) {
        if (t.getCause() != null) {
            if ("Network is unreachable".equals(t.getCause().getMessage())) {
                callback.onError("请确认网络已连接~~");
            } else {
                if (errorStr != null) {
                    callback.onError(errorStr);
                } else {
                    callback.onError("后台出错了~");
                }
                ExceptionUtil.CatchException(t);
            }
        }
    }

    public static void handleError(AsyncCallback callback, Throwable t, RequestBean handleErrorBean) {
        if (t.getCause() != null) {
            String s;
            if ("Network is unreachable".equals(t.getCause().getMessage())) {
                s = "请确认网络已连接~~";
                callback.onError(s);
            } else if ("timeout".equals(t.getCause().getMessage())) {
                s = "请求超时啦,稍后重试哦~~";
                callback.onError(s);
            } else {
                s = handleErrorBean.getErrorStr();
                if (TextUtils.isEmpty(s)) {
                    callback.onError(s);
                } else {
                    s = "出了一点问题,请重试哦~";
                    callback.onError(s);
                }
                ExceptionUtil.CatchException(t, handleErrorBean);
            }
            LogUtil.logModelOnError("本地--" + s, handleErrorBean);
        }
    }


    public static void showError(String error, Context context) {
        if ("登陆超时".equals(error)) {
            Intent i = new Intent(context, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            ToastUtils.showShort(context, error, 2);
        }
    }

    @Deprecated
    public static void isNoToken(String s, AsyncCallback callback) {
        Log.d(TAG, "isNoToken: " + s);
        int statsCode = JSONUtil.getValueToInt(s, "status_code");
        if (statsCode == 200) {
//         callback.onError("服务器错误");
        } else if (statsCode == 500) {
            Log.d(TAG, "服务器错误");
            callback.onError("服务器错误");
        } else if (statsCode == 403) {
            String message = JSONUtil.getValueToString(s, "message");
            callback.onError(message);
        } else if (statsCode == 401) {
            Log.d(TAG, "登陆超时");
            callback.onError("登陆超时");
            Log.d(TAG, "isNoToken: " + MyAPP.getInstance().getToken() + "----" + SharedPreferencesUtil.getLocal(MyAPP.getInstance()));
        } else if (statsCode == 422) {
            if (s.contains("errors")) {
                String error = JSONUtil.getValueToString(s, "errors");
                Object obj = null;
                try {
                    obj = com.googlecode.jsonplugin.JSONUtil.deserialize(error);
                    HashMap<String, List<String>> objMap = (HashMap) obj;
                    Collection<List<String>> values = objMap.values();
                    if (values.size() != 0) {
                        for (List<String> str : values) {
                            callback.onError(str.get(0));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            callback.onError(JSONUtil.getValueToString(s, "message"));
        }
    }
}
