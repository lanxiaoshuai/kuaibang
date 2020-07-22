package com.witkey.witkeyhelp.view.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

/**
 * Created by jie on 2020/4/13.
 */

public class NoviceHelpDetailsActivity extends BaseActivity {

    private ImageView rule_image;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_novice_details);
        setIncludeTitle("新手帮助");


        TextView novice_title = findViewById(R.id.novice_title);
        novice_title.setText(getIntent().getStringExtra("title"));
        TextView rule_one = findViewById(R.id.rule_one);
        rule_one.setText(getIntent().getStringExtra("rule_one"));
        TextView rule_two = findViewById(R.id.rule_two);
        String rule_two1 = getIntent().getStringExtra("rule_two");

        rule_image = findViewById(R.id.rule_image);
        if ("".equals(rule_two1) || null == rule_two1) {
            rule_image.setVisibility(View.GONE);
        } else {
            rule_image.setVisibility(View.VISIBLE); rule_two.setText(rule_two1);

            rule_two.setVisibility(View.VISIBLE);

        }


    }
}
