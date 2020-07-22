package com.witkey.witkeyhelp.view.impl;

import android.view.WindowManager;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.when_page.PageFrameLayout;

/**
 * Created by jie on 2019/12/30.
 */

public class ActivityBootPage extends BaseActivity {

    private PageFrameLayout contentFrameLayout;

    @Override
    protected void onCreateActivity() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_boot_page);
        contentFrameLayout = (PageFrameLayout) findViewById(R.id.contentFrameLayout);
        // 设置资源文件和选中圆点
        contentFrameLayout.setUpViews(new int[]{
                R.layout.page_tab1,
                R.layout.page_tab2,
                R.layout.page_tab3,
                R.layout.page_tab4,
                R.layout.page_tab5,
        }, R.mipmap.banner_on, R.mipmap.banner_off);


    }
}
