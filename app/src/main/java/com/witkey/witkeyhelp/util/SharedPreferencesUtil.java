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

    public static boolean isFirst(Context context) {
        sp = context.getSharedPreferences("First", 0);
        Log.d(Contacts.Use_TAG, "isFirst: "+sp.getBoolean("isFirst", true));
        if (sp.getBoolean("isFirst", true)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public static Map getLocal(Context context) {
        sp = context.getSharedPreferences("wxhm_sp", 0);
        Map map = new HashMap<String, String>();
        map.put("token", sp.getString("token", ""));
        map.put("type", sp.getString("type", ""));
        return map;
    }
}
