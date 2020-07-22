package com.witkey.witkeyhelp.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.ImgUtil;


/**
 * Created by jie on 2020/4/1.
 */
public class MultiFunctionWindows extends PopupWindow {
    private Context context;
    private int number;


    public MultiFunctionWindows(Context context, int number) {
        super(context);
        this.context = context;
        this.number = number;
        initView();
    }

    private void initView() {


        View inflate = LayoutInflater.from(context).inflate(R.layout.multi_function_popub, null, false);


        inflate.findViewById(R.id.claner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(inflate);
        //设置宽度
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        //设置高度
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.anim_pop_bottombar);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        setBackgroundDrawable(dw);
        setFocusable(true);
    }



}
