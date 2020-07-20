package com.witkey.witkeyhelp.util;

import android.util.Log;

import com.witkey.witkeyhelp.Contacts.Contacts;

/**
 * @author lingxu
 * @date 2019/7/12 13:57
 * @description Log工具类
 */
public class LogUtil {
    public static void  logModelOnError(String error) {
        logw("onError--" + error );
    }

    public static void logModelOnResponse(String methodName, String body) {
        logi(methodName + ": onResponse--" + body);
    }

    public static void logModelOnSuccess(String methodName, String body) {
        logi(methodName + ": onSuccess--" + body);
    }

    public static void logInfo(String contextName, String body) {
        logi(contextName + ": " + body);
    }

    /**
     * Verbose通常表达开发调试过程中的一些详细信息，用Log.v()输出，不过滤地输出所有调试信息
     */
    private static void logv(String str) {
        Log.v(Contacts.Use_TAG, str);
    }

    /**
     * Debug来表达调试信息。
     */
    private static void logd(String str) {
        Log.d(Contacts.Use_TAG, str);
    }

    /**
     * Info来表达一些信息。
     */
    private static void logi(String str) {
        Log.i(Contacts.Use_TAG, str);
    }

    /**
     * Warning表示警告，但不一定会马上出现错误，开发时有时用来表示特别注意的地方。
     */
    private static void logw(String str) {
        Log.w(Contacts.Use_TAG, str);
    }

    /**
     * Error表示出现错误，是最需要关注解决的
     */
    private static void loge(String str) {

    }


}
