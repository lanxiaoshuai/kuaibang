package com.witkey.witkeyhelp.view.impl;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.callback.IAddCallback;
import com.witkey.witkeyhelp.util.callback.IImageViewCallback;
import com.witkey.witkeyhelp.util.callback.ITextViewCallback;


/**
 * Created by Administrator on 2018/2/2.
 */

public abstract class ToolbarBaseActivity extends InitPresenterBaseActivity{
    private RelativeLayout backButton;
    private IAddCallback addCallback;
    private IImageViewCallback delCallback;
    private ITextViewCallback confirmCallback;

    /**
     * 设置了确定
     */
    public void confirm(View v) {
        if (confirmCallback != null) {
            confirmCallback.onClick();
        }
    }

    /**
     * del按钮
     */
    public void del(View v) {
        if (delCallback != null) {
            delCallback.onClick();
        }
    }

    /**
     * add按钮
     */
    public void add(View v) {
        if (addCallback != null) {
            addCallback.onAdd();
        }
    }
    /**
     * 设置返回按钮下对齐一块显示的右面的view
     */
    protected void addRule(int id) {
        backButton = (RelativeLayout) findViewById(R.id.tvBack);
        if (backButton != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) backButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_BOTTOM, id);
            backButton.setLayoutParams(params);
        }
    }
    /**
     * 显示添加按钮
     *
     * @param addCallback 添加回调
     */
    public void setShowAdd(IAddCallback addCallback) {
        this.addCallback = addCallback;
        Log.d(TAG, "setShowAdd: " + addCallback);
//        RelativeLayout tvAdd = (RelativeLayout) findViewById(R.id.tvAdd);
//        tvAdd.setVisibility(View.VISIBLE);
//        addRule(R.id.tvAdd);
    }


    /**
     * 设置显示清空按钮
     */
    public void setShowDel(IImageViewCallback delCallback) {
        this.delCallback = delCallback;
//        LinearLayout tvDel = (LinearLayout) findViewById(R.id.tvDel);
//        tvDel.setVisibility(View.VISIBLE);
//        addRule(R.id.tvDel);
    }

    /**
     * 设置显示确定按钮
     */
    public Button setShowConfirm(String buttonStr, ITextViewCallback confirmCallback) {
        this.confirmCallback = confirmCallback;
        Button tv_confirm = (Button) findViewById(R.id.tv_confirm);
        tv_confirm.setText(buttonStr);
        tv_confirm.setVisibility(View.VISIBLE);
        return tv_confirm;
    }

    public void setHideConfirm() {
        Button tv_confirm = (Button) findViewById(R.id.tv_confirm);
        tv_confirm.setVisibility(View.GONE);
    }
}
