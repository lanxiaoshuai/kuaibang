package com.witkey.witkeyhelp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.witkey.witkeyhelp.R;


/**
 * @author lingxu
 * @date 2019/7/24 15:46
 * @description 咨询保证金提示
 */
public class ConsultHintDialog extends Dialog {
    private String TAG = "llx";
    private Context context; //tagflowlayout需要context  getcontext不可以

    private View upView;


    public ConsultHintDialog(Context context, View upView) {
        super(context, R.style.ShareDialog);
        this.context = context;
        this.upView = upView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //点击空余位置不可关闭
        this.setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.dia_consult_hint);
        //弹窗点击周围空白处弹出层自动消失弹窗消失(false时为点击周围空白处弹出层不自动消失)
        this.setCanceledOnTouchOutside(true);

//        开始:显示到某个控件下方
        //获取当前Activity所在的窗体
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        //获取通知栏高度  重要的在这，获取到通知栏高度
        int notificationBar = Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
//获取控件 textview 的绝对坐标,( y 轴坐标是控件上部到屏幕最顶部（不包括控件本身）)
        //location [0] 为x绝对坐标;location [1] 为y绝对坐标
        int[] location = new int[2];
        upView.getLocationInWindow(location); //获取在当前窗体内的绝对坐标
        upView.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        wlp.x = 0;//对 dialog 设置 x 轴坐标
        wlp.y = location[1] + upView.getHeight() - notificationBar; //对dialog设置y轴坐标
        wlp.gravity = Gravity.TOP;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
//      结束:显示到某个控件下方
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
