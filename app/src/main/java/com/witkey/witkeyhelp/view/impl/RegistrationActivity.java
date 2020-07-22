package com.witkey.witkeyhelp.view.impl;

import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import me.jingbin.progress.WebProgress;

/**
 * Created by asus on 2020/2/27.
 */

public class RegistrationActivity extends BaseActivity {

    private WebView webView;
    private WebProgress mProgress;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.registration_agreement);
        setIncludeTitle("隐私政策和用户协议");
        webView = findViewById(R.id.registrationAgreement);
        mProgress = findViewById(R.id.progress);
        webView.loadUrl(getIntent().getStringExtra("weburl"));
//系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgress.setWebProgress(newProgress);
            }


        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgress.hide();
            }


            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mProgress.hide();
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (webView.canGoBack()) {
                webView.goBack();// 返回上一页面
                return true;
            } else {
                finish();
                // System.exit(0);// 退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
