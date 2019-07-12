package com.witkey.witkeyhelp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * @author lingxu
 * @date 2019/7/12 10:31
 * @description 跳转界面工具类
 */
public class IntentUtil {

    /**
     * 带参数跳转界面
     *
     * @param context 可以是activity,也可以是fragment 默认请求编码1
     * @param intent  已携带好数据的intent
     */
    public static void startActivityForResult(Activity context, Intent intent) {
        context.startActivityForResult(intent, 1);
    }

    public static void startActivityForResult(Fragment context, Intent intent) {
        context.startActivityForResult(intent, 1);
    }

    /**
     * 带参数跳转界面
     *
     * @param context 可以是activity,也可以是fragment
     * @param intent  已携带好数据的intent
     * @param code    设置请求编码
     */
    public static void startActivityForResult(Activity context, Intent intent, int code) {
        context.startActivityForResult(intent, code);
    }

    public static void startActivityForResult(Fragment context, Intent intent, int code) {
        context.startActivityForResult(intent, code);
    }

    /**
     * 不带请求编码的跳转界面
     *
     * @param context
     * @param c
     */
    public static void startActivity(Context context, Class<?> c) {
        Intent i = new Intent(context, c);
        context.startActivity(i);
    }


}
