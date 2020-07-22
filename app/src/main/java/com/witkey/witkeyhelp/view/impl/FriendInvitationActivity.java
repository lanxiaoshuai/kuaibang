package com.witkey.witkeyhelp.view.impl;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.DiamondAdapter;
import com.witkey.witkeyhelp.event.ResultEvent;
import com.witkey.witkeyhelp.event.ToastEvent;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.ShadowDrawable;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.PopWinShare;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by jie on 2020/1/17.
 */

public class FriendInvitationActivity extends BaseActivity implements View.OnClickListener {
    private PopWinShare popWinShare;
    private View parentView;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.friendinvitation_layout);
        setIncludeTitle("好友分享");
        LinearLayout bbbb = findViewById(R.id.bbbb);


        TextView yaoqingma = findViewById(R.id.yaoqingma);
        yaoqingma.setText(user.getInvitationCode());
        bbbb.setOnClickListener(this);
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bbbb:
                if (popWinShare == null) {
                    parentView = LayoutInflater.from(FriendInvitationActivity.this).inflate(R.layout.friendinvitation_layout, null);
                    //       popWinShare = new PopWinShare(this, user.getInvitationCode());
                    popWinShare = new PopWinShare(0, this, "信息咨询，悬赏帮忙，接单赚钱，尽在酷爱帮app，快来下载吧！", "这款app可以咨询信息、悬赏找人帮忙、也可以帮别人忙来获取酬劳", "http://a.app.qq.com/o/simple.jsp?pkgname=com.witkey.witkeyhelp");

                    popWinShare.setOnDismissListener(new PopupWindow.OnDismissListener() {

                        @Override
                        public void onDismiss() {
                            WindowManager.LayoutParams lp = getWindow().getAttributes();
                            lp.alpha = 1f;
                            getWindow().setAttributes(lp);
                        }
                    });
                }
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;//调节透明度
                getWindow().setAttributes(lp);
                popWinShare.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private boolean shareIt;

    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(ToastEvent event) {
        // 响应事件
        shareIt = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shareIt) {
            ToastUtils.showTestShort(this, "分享成功");
        }

    }
}
