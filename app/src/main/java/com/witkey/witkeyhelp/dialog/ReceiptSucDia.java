package com.witkey.witkeyhelp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.view.impl.MyMissionActivity;


/**
 * @author lingxu
 * @date 2019/7/26 14:51
 * @description 接单成功dia
 */
public class ReceiptSucDia extends Dialog {
    private Context context;
    private TextView tvOpen;

    public ReceiptSucDia(Context context) {
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
                Intent i = new Intent(context, MyMissionActivity.class);
                i.putExtra("EXTRA_IS_RELEASE", false);
                context.startActivity(i);
            }
        });
    }

    private void setViews(View v) {
        tvOpen = (TextView) v.findViewById(R.id.tvOpen);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
