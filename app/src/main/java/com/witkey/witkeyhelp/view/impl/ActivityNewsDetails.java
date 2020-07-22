package com.witkey.witkeyhelp.view.impl;

import android.view.View;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityNewsDetails extends BaseActivity {
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.news_details);
        setIncludeTitle("信息咨询");
        TextView content = findViewById(R.id.content);
        TextView sendtime = findViewById(R.id.sendtime);

        content.setText(getIntent().getStringExtra("content"));
        sendtime.setText(getIntent().getStringExtra("time"));
    }
}
