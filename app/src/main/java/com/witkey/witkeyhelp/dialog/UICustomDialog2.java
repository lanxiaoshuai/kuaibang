package com.witkey.witkeyhelp.dialog;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;


/**
 * 没有title的dialog Title: UICustomDialog2.java
 *
 * @author Angus 2016年9月28日
 * @version 2.0
 */
public class UICustomDialog2 {

    private Dialog dialog;
    private String content;
    private LinearLayout popView;
    private TextView textViewCancel;
    private Activity cls;

    /*
     * int 传参
     */
    public UICustomDialog2(Activity mActivity, String content, String type) {
        this.cls = mActivity;

            dialog = new Dialog(mActivity, R.style.CustomDialog);



        popView = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.ui_custom_dialog2, null);

        // 内容
        TextView contents = (TextView) popView
                .findViewById(R.id.dialog2_content);
        contents.setText(content);
        // 1 禁用dialog外点击不消失，2外不消失并且禁用返回键，3不处理
        if ("2".equals(type)) {
            dialog.setCancelable(false); // false 禁止返回键 和下边的touch必须同时使用
            dialog.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失
        } else if ("1".equals(type)) {
            dialog.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失
        }
        // 添加动态内容
        dialog.setContentView(popView, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);


    }
    public boolean isshowing(){

      return   dialog.isShowing();
    }
    /*
     * String
     */
    public UICustomDialog2(Activity mActivity, String content) {
        this.cls = mActivity;
        dialog = new Dialog(mActivity, R.style.mask_dialog);
        // dialog = new Dialog(context, theme)
        popView = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.ui_custom_dialog2, null);

        // 内容
        TextView contents = (TextView) popView
                .findViewById(R.id.dialog2_content);
        contents.setText(content);
        // 添加动态内容
        dialog.setContentView(popView, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
    }

    /**
     * 关于我们版本更新
     *
     * @param mActivity
     * @param fromHtml
     */
    public UICustomDialog2(Activity mActivity, Spanned fromHtml) {
        this.cls = mActivity;
        dialog = new Dialog(mActivity, R.style.mask_dialog);
        // dialog = new Dialog(context, theme)
        popView = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.ui_custom_dialog2, null);

        // 内容
        TextView contents = (TextView) popView
                .findViewById(R.id.dialog2_content);
        contents.setText(fromHtml);
        // 添加动态内容
        dialog.setContentView(popView, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        // dialog.setCancelable(false); //false 禁止返回键 和下边的touch必须同时使用
        dialog.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失
        dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
    }

    /**
     * 启动版本更新
     *
     * @param mActivity 1不强制，2强制
     * @param fromHtml
     * @param type
     */
    public UICustomDialog2(Activity mActivity, Spanned fromHtml, String type) {
        this.cls = mActivity;
        dialog = new Dialog(mActivity, R.style.mask_dialog);
        // dialog = new Dialog(context, theme)
        popView = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.ui_custom_dialog2, null);

        // 内容
        TextView contents = (TextView) popView
                .findViewById(R.id.dialog2_content);
        contents.setText(fromHtml);
        // 添加动态内容
        dialog.setContentView(popView, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if ("2".equals(type)) {
            dialog.setCancelable(false); // false 禁止返回键 和下边的touch必须同时使用
            dialog.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失
        } else {
            dialog.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失
        }
        dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
    }

    /**
     * 添加取消按钮按钮
     *
     * @param @param  btn
     * @param @return 按钮对象
     * @return TextView 返回类型
     * @Title: setCancelButton
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public TextView setCancelButton(int dialogCancel) {
        textViewCancel = (TextView) popView.findViewById(R.id.dialog2_cancel);
        textViewCancel.setText(dialogCancel);

        textViewCancel.setBackgroundResource(R.drawable.custom_dialog_alone_cancel_selector);
        return textViewCancel;
    }

    /**
     * 添加确定按钮按钮
     *
     * @param @param  btn
     * @param @return 按钮对象
     * @return TextView 返回类型
     * @Title: steOkButton
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public TextView setOkButton(int dialogCommit) {
        TextView textViewOk = (TextView) popView.findViewById(R.id.dialog2_ok);
        textViewOk.setText(dialogCommit);
        textViewOk.setVisibility(View.VISIBLE);
        textViewCancel.setBackgroundResource(R.drawable.custom_dialog_cancel_selector);
        textViewOk.setBackgroundResource(R.drawable.custom_dialog_ok_selector);
        TextView dividingLine = (TextView) popView
                .findViewById(R.id.dialog2_dividing_line);
        dividingLine.setVisibility(View.VISIBLE);
        return textViewOk;
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.dismiss();
    }

}
