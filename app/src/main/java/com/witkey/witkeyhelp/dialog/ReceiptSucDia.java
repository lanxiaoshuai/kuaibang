package com.witkey.witkeyhelp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;


import com.witkey.witkeyhelp.view.impl.MyHelpActivity;
import com.witkey.witkeyhelp.view.impl.ReleaseMissionActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * @author lingxu
 * @date 2019/7/26 14:51
 * @description 接单成功dia
 */
public class ReceiptSucDia extends Dialog {
    private Activity context;
    private TextView tvOpen;
    private ImageView cancel_les;

    public ReceiptSucDia(Activity context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        View dialogview = View.inflate(context, R.layout.dia_receipt_suc, null);
        setContentView(dialogview);
        setViews(dialogview);
        setListener();
        // 设置触摸对话框意外的地方取消对话框
        setCanceledOnTouchOutside(true);
    }

    private void setListener() {
        tvOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MyHelpActivity.class);
//                i.putExtra("EXTRA_IS_RELEASE", false);
                context.startActivity(i);
                dismiss();
            }
        });
        cancel_les.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

            }
        });
    }

    private void setViews(View v) {
        tvOpen = (TextView) v.findViewById(R.id.tvOpen);
        cancel_les = findViewById(R.id.cancel_les);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
