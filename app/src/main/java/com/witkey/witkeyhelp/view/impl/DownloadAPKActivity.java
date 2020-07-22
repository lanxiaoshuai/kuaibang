package com.witkey.witkeyhelp.view.impl;


import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;

import android.os.IBinder;

import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;


import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.services.DownloadServise;



/**
 * Created by jie on 2020/5/25.
 */

public class DownloadAPKActivity extends AppCompatActivity {


;
    //  请求链接
    private String url = "http://39.100.86.8/kuaiBang/download/app-yingyongbao-release.apk";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          setContentView(R.layout.activity_login);
       // showDownloadDialog();
  //  new DownloadUtils(this, url, "kuaibang.apk");
       downLoadCs();

    }
    private void downLoadCs() {  //下载调用

        Intent intent = new Intent(this, DownloadServise.class);
        intent.putExtra(DownloadServise.BUNDLE_KEY_DOWNLOAD_URL, url);
        isBindService = bindService(intent, conn, BIND_AUTO_CREATE); //绑定服务即开始下载 调用onBind()
    }


    private boolean isBindService;
    private ServiceConnection conn = new ServiceConnection() { //通过ServiceConnection间接可以拿到某项服务对象

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadServise.DownloadBinder binder = (DownloadServise.DownloadBinder) service;
          final   DownloadServise downloadServise = binder.getService();

            //接口回调，下载进度
            downloadServise.setOnProgressListener(new DownloadServise.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {
                    if((int)(fraction * 100)>=100){
                        Log.e("tag",100+"qqqqqqqq");
                    }else {
                        Log.e("tag",(int)(fraction * 100)+"asdsadas");
                    }



                    //判断是否真的下载完成进行安装了，以及是否注册绑定过服务
                    if (fraction == downloadServise.UNBIND_SERVICE && isBindService) {

                        unbindService(conn);
                        isBindService = false;
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}




