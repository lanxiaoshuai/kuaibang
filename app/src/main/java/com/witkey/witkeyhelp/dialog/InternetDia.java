package com.witkey.witkeyhelp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;

/**
 * @author lingxu
 * @date 2019/7/12 14:03
 * @description 未联网弹出的dialog
 */
public class InternetDia extends Dialog {
    private Context context;
    private TextView tvOpen;

    public InternetDia(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        View dialogview = View.inflate(context, R.layout.dia_internet, null);
        setContentView(dialogview);
//        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
        setViews(dialogview);
        setListener();

        // 设置触摸对话框意外的地方取消对话框
        setCanceledOnTouchOutside(true);
    }


    private void setListener() {
        tvOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    // 3,打开系统自带的网络管理activity
                    // 得到android操作系统的版本号
                    // 4.2 17
                    // 5.0 22
                    // 6.0 23
                    int osVersion = Build.VERSION.SDK_INT;
                    // 得手机厂商，得手机号，得串号
                    // 不同android版本不同代码
                    if (osVersion > 10) {
                        // 网络管理是在设置
                        Intent intent = new Intent(
                                Settings.ACTION_WIRELESS_SETTINGS);
                        context.startActivity(intent);
                    }
                    InternetDia.this.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
