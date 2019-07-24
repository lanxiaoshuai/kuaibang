package com.witkey.witkeyhelp.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.witkey.witkeyhelp.Contacts.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限工具类
 */

public class PermissionUtil {
    public static final int PERMISSON_REQUESTCODE = 0;

    /**
     * 检查权限
     *
     * @param permissions
     */
    public static boolean checkPermissions(Context context, String... permissions) {

            //获取权限列表
            List<String> needRequestPermissonList = findDeniedPermissions(context, permissions);
            if (null != needRequestPermissonList
                    && needRequestPermissonList.size() > 0) {
                //list.toarray将集合转化为数组
                ActivityCompat.requestPermissions((Activity) context,
                        needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                        PERMISSON_REQUESTCODE);
                return true;
                //需要获取权限
            }
            return false;
            //已获取需要的权限

    }


    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @since 2.5.0
     */
    private static List<String> findDeniedPermissions(Context context, String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(context,
                    perm) != PackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.shouldShowRequestPermissionRationale(
//                    (Activity) context, perm)
                    ) {
                Log.d(Contacts.Use_TAG, "findDeniedPermissions: "+perm);
                    needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    public static boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
