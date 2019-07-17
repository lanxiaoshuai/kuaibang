package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.witkey.witkeyhelp.Contacts.Contacts;

import java.util.HashMap;
import java.util.Map;

/**
 * 偏好设置工具类
 */

public class SharedPreferencesUtil {
    static SharedPreferences sp;

    public static void saveLocal(Context context, String token, int type) {
        sp = context.getSharedPreferences("wxhm_sp", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.putString("type", type + "");
        editor.commit();
    }

    public static Map getLocal(Context context) {
        sp = context.getSharedPreferences("wxhm_sp", 0);
        Map map = new HashMap<String, String>();
        map.put("token", sp.getString("token", ""));
        map.put("type", sp.getString("type", ""));
        return map;
    }

    // TODO: 2019/7/17 加密开源库 compile 'online.devliving:securedpreferencestore:latest_version'
    public static void saveNamePass(Context context, String name, String pass) {
        sp = context.getSharedPreferences("witkey_user", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.putString("pass", pass + "");
        editor.commit();
    }

    public static Map getNamePass(Context context) {
        sp = context.getSharedPreferences("witkey_user", 0);
        Map map = new HashMap<String, String>();
        map.put("name", sp.getString("name", ""));
        map.put("pass", sp.getString("pass", ""));
        return map;
    }

    public static boolean isFirst(Context context) {
        sp = context.getSharedPreferences("First", 0);
        Log.d(Contacts.Use_TAG, "isFirst: " + sp.getBoolean("isFirst", true));
        if (sp.getBoolean("isFirst", true)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
            return true;
        } else {
            return false;
        }
    }


}
