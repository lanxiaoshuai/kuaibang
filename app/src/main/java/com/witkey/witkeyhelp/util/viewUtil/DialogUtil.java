package com.witkey.witkeyhelp.util.viewUtil;

import android.content.Context;

import com.witkey.witkeyhelp.dialog.ProgressDia;

/**
 * @author lingxu
 * @date 2019/7/12 13:55
 * @description 对话框工具类
 */
public class DialogUtil {

    static ProgressDia progressDialog;

    /**
     * 显示进度条对话框
     */
    static public void showProgress(Context context, String msg) {
        dismissProgress();
        progressDialog = new ProgressDia(context, msg);
        progressDialog.show();

    }

    /**
     * 显示没有text对话框
     */
    static public void showProgress(Context context) {
        dismissProgress();
        progressDialog = new ProgressDia(context);
        progressDialog.setHide();
        progressDialog.show();

    }

    /**
     * 关闭进度条对话框
     */
    static public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
