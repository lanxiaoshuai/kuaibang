package com.witkey.witkeyhelp.view.impl;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.StatusbarColorUtils;
import com.witkey.witkeyhelp.util.SystemBarTintManager;
import com.witkey.witkeyhelp.view.IView;

public class BaseActivity extends AppCompatActivity implements IView {

    private Bundle savedInstanceState;
    private BaseActivity mActivity;
    private ViewGroup fwRootLayout;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // fragment:getActivity()空指针解决方法
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");
            //注意：基类是Activity时参数为android:fragments， 一定要在super.onCreate函数前执行！！！
        }
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        mActivity = BaseActivity.this;

        //配置状态栏
        initSystemBar();
    }
//    配置状态栏
    private void initSystemBar() {
        //界面中设置FitsSystemWindows会有灰条
        fwRootLayout = (ViewGroup) getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //FLAG_TRANSLUCENT_STATUS 华为手机不显示
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置不起作用
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        tintManager = new SystemBarTintManager(this);// 创建状态栏的管理实例
        tintManager.setStatusBarTintEnabled(true);// 激活状态栏设置
        tintManager.setNavigationBarTintEnabled(true);// 激活导航栏设置
        if (isLight()) {
            tintManager.setStatusBarDarkMode(false, this);//false 状态栏字体颜色是白色 MIUI
            fwRootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE); //HUAWEI
            StatusbarColorUtils.setStatusBarDarkIcon(this, false);  //参数 false 白色 true 黑色 MEIZU
        } else {
            tintManager.setStatusBarDarkMode(true, this);//false 状态栏字体 true 颜色是黑色
            fwRootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusbarColorUtils.setStatusBarDarkIcon(this, true);  //参数 false 白色 true 黑色
        }
    }

    private boolean isLight() {
        return false;
    }

    /**
     * 设置标题
     */
    public void setIncludeTitle(String title) {
        TextView tv = (TextView) findViewById(R.id.include_title);
        if (tv != null)
            tv.setText(title);
    }

    /**
     * 错误时操作
     * @param error 错误信息
     */
    @Override
    public void onError(String error) {

    }
}
