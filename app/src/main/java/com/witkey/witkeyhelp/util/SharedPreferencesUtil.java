package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.util.Log;

import com.witkey.witkeyhelp.Contacts.Contacts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences("First", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }
    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences("First", Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }

    }
    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("First", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences("First", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("First", Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences("First", Context.MODE_PRIVATE);
        return sp.getAll();
    }



}
