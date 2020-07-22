package com.witkey.witkeyhelp.widget.pickerimage.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.witkey.witkeyhelp.util.StatusbarColorUtils;
import com.witkey.witkeyhelp.util.SystemBarTintManager;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.pickerimage.fragment.BaseFragment;
import com.witkey.witkeyhelp.widget.pickerimage.utils.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;



public abstract class UIView extends BaseActivity {

    private boolean destroyed = false;

    private static Handler handler;

    private Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBar();
    }

    @Override
    public void onBackPressed() {
        invokeFragmentManagerNoteStateNotSaved();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        destroyed = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onNavigateUpClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setToolBar(int toolBarId, ToolBarOptions options) {
        toolbar = (Toolbar) findViewById(toolBarId);
        toolbar.setTitleTextColor(Color.WHITE);
        if (options.titleId != 0) {
            toolbar.setTitle(options.titleId);
        }
        if (!TextUtils.isEmpty(options.titleString)) {
            toolbar.setTitle(options.titleString);
        }

        setSupportActionBar(toolbar);

        if (options.isNeedNavigate) {
            toolbar.setNavigationIcon(options.navigateId);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigateUpClicked();
                }
            });
        }
    }

    public void setToolBar(int toolbarId, int titleId, int logoId) {
        toolbar = (Toolbar) findViewById(toolbarId);
        toolbar.setTitle(titleId);
        toolbar.setLogo(logoId);
        setSupportActionBar(toolbar);
    }

    public Toolbar getToolBar() {
        return toolbar;
    }

    public int getToolBarHeight() {
        if (toolbar != null) {
            return toolbar.getHeight();
        }

        return 0;
    }

    public void onNavigateUpClicked() {
        onBackPressed();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 延时弹出键盘
     *
     * @param focus 键盘的焦点项
     */
    protected void showKeyboardDelayed(View focus) {
        final View viewToFocus = focus;
        if (focus != null) {
            focus.requestFocus();
        }

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewToFocus == null || viewToFocus.isFocused()) {
                    showKeyboard(true);
                }
            }
        }, 200);
    }


    public boolean isDestroyedCompatible() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyedCompatible17();
        } else {
            return destroyed || super.isFinishing();
        }
    }

    @TargetApi(17)
    private boolean isDestroyedCompatible17() {
        return super.isDestroyed();
    }

    /**
     * fragment management
     */
    public BaseFragment addFragment(BaseFragment fragment) {
        List<BaseFragment> fragments = new ArrayList<BaseFragment>(1);
        fragments.add(fragment);

        List<BaseFragment> fragments2 = addFragments(fragments);
        return fragments2.get(0);
    }

    public List<BaseFragment> addFragments(List<BaseFragment> fragments) {
        List<BaseFragment> fragments2 = new ArrayList<BaseFragment>(fragments.size());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        boolean commit = false;
        for (int i = 0; i < fragments.size(); i++) {
            // install
            BaseFragment fragment = fragments.get(i);
            int id = fragment.getContainerId();

            // exists
            BaseFragment fragment2 = (BaseFragment) fm.findFragmentById(id);

            if (fragment2 == null) {
                fragment2 = fragment;
                transaction.add(id, fragment);
                commit = true;
            }

            fragments2.add(i, fragment2);
        }

        if (commit) {
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {

            }
        }

        return fragments2;
    }

    public BaseFragment switchContent(BaseFragment fragment) {
        return switchContent(fragment, false);
    }

    protected BaseFragment switchContent(BaseFragment fragment, boolean needAddToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fragment.getContainerId(), fragment);
        if (needAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        try {
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {

        }

        return fragment;
    }

    protected boolean displayHomeAsUpEnabled() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                return onMenuKeyDown();

            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    protected boolean onMenuKeyDown() {
        return false;
    }

    private void invokeFragmentManagerNoteStateNotSaved() {
        FragmentManager fm = getSupportFragmentManager();
        ReflectionUtil.invokeMethod(fm, "noteStateNotSaved", null);
    }

    protected void switchFragmentContent(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(fragment.getContainerId(), fragment);
        try {
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isCompatible(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }
    /**
     * 判断状态栏的字体要亮色还是暗色
     *
     * @return true亮色, false暗色
     */
    protected boolean isLight() {
        return false;
    }
    private ViewGroup fwRootLayout;

    /**
     * 配置状态栏
     */
    private SystemBarTintManager tintManager;
    /**
     * 配置状态栏
     */
    public void initSystemBar() {
        //界面中设置FitsSystemWindows会有灰条
        fwRootLayout = (ViewGroup) getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //FLAG_TRANSLUCENT_STATUS 华为手机不显示
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            fwRootLayout.setSystemUiVisibility(option);
            //设置不起作用
//            fwRootLayout.setFitsSystemWindows(true);//需要把根布局设置为这个属性 子布局则不会占用状态栏位置
//            fwRootLayout.setClipToPadding(true);//需要把根布局设置为这个属性 子布局则不会占用状态栏位置
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        tintManager = new SystemBarTintManager(this);// 创建状态栏的管理实例
        tintManager.setStatusBarTintEnabled(true);// 激活状态栏设置
        tintManager.setNavigationBarTintEnabled(true);// 激活导航栏设置
//        tintManager.setStatusBarTintColor(0xff111111);//设置状态栏颜色
//        tintManager.setStatusBarTintResource(Color.RED);//设置状态栏颜色
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
}
