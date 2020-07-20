package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.witkey.witkeyhelp.widget.ShowToast;

/**
 * @author lingxu
 * @date 2019/7/12 13:23
 * @description Toast统一管理类
 */
public class ToastUtils {


    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Toast mToast;
    public static boolean isShow = true;

    public static Toast getmToast() {
        return mToast;
    }

    public static void setmToast(Toast mToast) {

        ToastUtils.mToast = mToast;
    }

    /**
     * 短时间显示Toast
     * <p>
     * 0 不显示 1 success 2 fail 3 alert
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message, int state) {
        if (isShow) {
            if (mToast == null) {
                mToast = new ShowToast(context, message, state);
            } else {
                ((ShowToast) mToast).setState(state);
                mToast.setText(message);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }

    }

    /**
     * 短时间显示测试Toast
     *
     * @param context
     * @param message
     */
    public static void showTestShort(Context context, CharSequence message) {
        if (isShow) {
           // if (mToast == null) {
                mToast = new ShowToast(context, message);
//            } else {
//                mToast.setText(message);
//                mToast.setDuration(Toast.LENGTH_SHORT);
//            }

            mToast.show();
        }else {

        }

    }

    public static void cancel() {
        mToast.cancel();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message, int state) {
        if (isShow) {
            if (mToast == null) {
                mToast = new ShowToast(context, message, Toast.LENGTH_LONG, state);
            } else {
                mToast.setText(message);

                mToast.setDuration(Toast.LENGTH_LONG);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration, int state) {
        if (isShow) {
            if (mToast == null) {
                mToast = new ShowToast(context, message, duration, state);
            } else {
                mToast.setText(message);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }


}
