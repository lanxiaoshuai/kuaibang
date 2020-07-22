package com.witkey.witkeyhelp.view.impl;

import android.view.View;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

public class SuccessCiclerActivity extends BaseActivity {
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.createdcicler);
        setIncludeTitle("提交审核");
        findViewById(R.id.create_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
