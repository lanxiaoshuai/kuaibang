package com.witkey.witkeyhelp.util.viewUtil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

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
    static public void showProgress(final Context context, String msg) {
        dismissProgress();
        progressDialog = new ProgressDia(context, msg);

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Activity context1 = (Activity) context;
                context1.finish();
            }
        });
        progressDialog.show();

    }

    /**
     * 显示没有text对话框
     */
    static public void showProgress(final Context context) {
        dismissProgress();
        progressDialog = new ProgressDia(context);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Activity context1 = (Activity) context;
                context1.finish();
            }
        });

        progressDialog.setHide();
        progressDialog.show();

    }

    /**
     * 关闭进度条对话框
     */
    static public void dismissProgress() {
        try {
            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {
            progressDialog = null;
        }
    }
}
