package com.witkey.witkeyhelp.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.UIUtils;


/**
 * 进度对话框
 */
public class ProgressDia extends ProgressDialog {
    private TextView tvInfo;
    private String info;

    public ProgressDia(Context context, int theme, String msg) {
        super(context, R.style.ShareDialog);
        this.info = msg;

    }

    public ProgressDia(Context context, String msg) {
        super(context);
        this.info = msg;

    }

    public ProgressDia(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
        Window window = getWindow();
        //背景不变暗的操作
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);
        //////////////
        window.setBackgroundDrawableResource(R.color.transparent); // 设置对话框背景为透明
        this.setContentView(R.layout.dia_progress);//位置大,setview位置小
        tvInfo = (TextView) findViewById(R.id.tvInfo);

    }

    public void setHide() {
        if (tvInfo != null) {
            tvInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        this.getWindow().setLayout(UIUtils.dip2px(100), UIUtils.dip2px(100));
        if (tvInfo != null) {
            if (tvInfo.getVisibility() == View.VISIBLE) {
                tvInfo.setText(info);
            }
        }
    }
}
