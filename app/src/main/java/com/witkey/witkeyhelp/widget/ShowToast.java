package com.witkey.witkeyhelp.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.witkey.witkeyhelp.R;

/**
 * @author lingxu
 * @date 2019/7/12 13:23
 * @description 自定义Toast
 */
public class ShowToast extends Toast {
    private View v;
    private TextView tv;
    private ImageView iv_hint;
    private Context context;

//    /**
//     * 0 不显示 1 success 2 fail 3 alert
//     */
//    private int state;

    public ShowToast(Context context, CharSequence message, int state) {
        super(context);
        this.context = context;
        setToast(context, message, state);
        this.setDuration(Toast.LENGTH_SHORT);
    }

    private void setToast(Context context, CharSequence message, int state) {
        v = View.inflate(context, R.layout.dia_toast, null);
        this.setView(v);

        tv = (TextView) v.findViewById(R.id.tvInfo);
        iv_hint = (ImageView) v.findViewById(R.id.iv_hint);
        setState(state);
        tv.setText(message);
    }

    public void setState(int state) {
        if(context instanceof Application){
            state=0;
        }
        if (state != 0) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv.getLayoutParams();
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) iv_hint.getLayoutParams();
            if (state == 3) {
                DisplayMetrics dm = new DisplayMetrics();
                //取得窗口属性
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
                //窗口高度
                double screenHeight = dm.heightPixels;
                double showHeight = screenHeight - (screenHeight * 0.3);
                this.setGravity(Gravity.TOP, 0, (int) showHeight);
                params1.removeRule(RelativeLayout.CENTER_HORIZONTAL);

                params.removeRule(RelativeLayout.BELOW);
                params.addRule(RelativeLayout.RIGHT_OF, R.id.iv_hint);
                iv_hint.setImageResource(R.drawable.ic_alert);
                iv_hint.setPadding(0,0,0,0);
            } else {
                iv_hint.setPadding(0,16,0,16);
                this.setGravity(Gravity.CENTER, 0, 0);
                params.addRule(RelativeLayout.BELOW, R.id.iv_hint);
                params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                if (state == 1) {
                    iv_hint.setImageResource(R.drawable.ic_success);
                } else if (state == 2) {
                    iv_hint.setImageResource(R.drawable.ic_fail);
                }
            }
            tv.setLayoutParams(params);
            iv_hint.setLayoutParams(params1);
        }
    }

    public ShowToast(Context context, CharSequence message, int duration, int state) {
        super(context);
        this.context = context;
        setToast(context, message, state);
        this.setDuration(duration);
    }

    @Override
    public void setText(CharSequence s) {
        tv.setText(s);
    }


}
