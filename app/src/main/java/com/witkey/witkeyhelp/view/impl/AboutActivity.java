package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.services.DownloadServise;

import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jie on 2019/12/17.
 */

public class AboutActivity extends BaseActivity {

    private ProgressBar pb;
    private Dialog relievepopupWindow;
    private boolean download;
    private String versionCode;
    private TextView msg_count;
    private JSONObject jsonObject;
    private LinearLayout update_layout;

    @Override
    protected void onCreateActivity() {

        setContentView(R.layout.activity_about_wo);
        setIncludeTitle("关于我们");
        TextView version_number = findViewById(R.id.version_number);

        RelativeLayout commonProblem = findViewById(R.id.commonProblem);
        findViewById(R.id.website).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                //  startActivity(new Intent(AboutActivity.this, RegistrationActivity.class).putExtra("weburl", "http://www.kabnice.com/"));
            }
        });
        findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, RegistrationActivity.class).putExtra("weburl", URL.versionUrl + "api/share/terms"));
            }
        });
        version_number.setText("版本号：" + getVerName(this));
        commonProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                Intent intent = new Intent(AboutActivity.this, NoviceHelpActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout checkupdates = findViewById(R.id.checkupdates);

        TextView updates = findViewById(R.id.updates);

        msg_count = findViewById(R.id.msg_count);

        updates.setText("检查更新: " + getVerName(this));
        update();
        checkupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jsonObject != null) {

                    if (Integer.parseInt(versionCode) > getVersionCode()) {


                        try {
                            task_submission_fail(AboutActivity.this, jsonObject.getString("versionName"), jsonObject.getString("apkSize"), jsonObject.getString("modifyContent"), jsonObject.getString("uploadTime"), jsonObject.getString("downloadUrl"));
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    } else {
                        ToastUtils.showTestShort(AboutActivity.this, "已是最新版本");
                    }
                } else {
                    ToastUtils.showTestShort(AboutActivity.this, "已是最新版本");
                }

            }
        });
    }


    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


    /**
     * @return 获取版本号
     */
    private int getVersionCode() {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;

    }

    private void update() {
        MyAPP.getInstance().getApi().versionUpdate().enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    jsonObject = new JSONObject(response.body().toString());

                    versionCode = jsonObject.getString("versionCode");
                    //下载地址
                    jsonObject.getString("downloadUrl");

                    if (Integer.parseInt(versionCode) > getVersionCode()) {
                        msg_count.setVisibility(View.VISIBLE);

                    } else {
                        msg_count.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }


        });
    }

    private void downLoadCs(String downloadUrl) {  //下载调用

        Intent intent = new Intent(this, DownloadServise.class);
        intent.putExtra(DownloadServise.BUNDLE_KEY_DOWNLOAD_URL, downloadUrl);
        isBindService = bindService(intent, conn, BIND_AUTO_CREATE); //绑定服务即开始下载 调用onBind()
    }


    private boolean isBindService;
    private ServiceConnection conn = new ServiceConnection() { //通过ServiceConnection间接可以拿到某项服务对象

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadServise.DownloadBinder binder = (DownloadServise.DownloadBinder) service;
            final DownloadServise downloadServise = binder.getService();

            //接口回调，下载进度
            downloadServise.setOnProgressListener(new DownloadServise.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {
                    if ((int) (fraction * 100) >= 100) {
                        //  Log.e("tag", 100 + "qqqqqqqq");
                        pb.setProgress(100);
                    } else {
                        //   Log.e("tag", (int) (fraction * 100) + "qqqqqqqq");
                        pb.setProgress((int) (fraction * 100));
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

    private void task_submission_fail(Context context, String versioncontent, String siezecontent, String updatelogu, String timeupdate, final String downloadUrl) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popub_upgrade, null, false);//引入弹窗布局

        ImageView expression = vPopupWindow.findViewById(R.id.expression);

        TextView version_name = vPopupWindow.findViewById(R.id.version_name);
        TextView packagesize = vPopupWindow.findViewById(R.id.packagesize);

        TextView update_content = vPopupWindow.findViewById(R.id.update_content);

        TextView not_new = vPopupWindow.findViewById(R.id.not_new);
        pb = vPopupWindow.findViewById(R.id.pb);

        final TextView update_text = vPopupWindow.findViewById(R.id.update_text);

        TextView updatetime = vPopupWindow.findViewById(R.id.updatetime);

        update_layout = vPopupWindow.findViewById(R.id.update_layout);
        updatetime.setText("更新时间 :   " + timeupdate);
        version_name.setText("版本 :   " + versioncontent);
        packagesize.setText("包大小 :   " + siezecontent + "MB");
        update_content.setText(updatelogu);
        relievepopupWindow = new Dialog(this, R.style.CustomDialog);
        update_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lacksPermissions(AboutActivity.this, permissionsREADWrite)) {//地理位置权限
                    ActivityCompat.requestPermissions(AboutActivity.this, permissionsREADWrite, 1);
                } else {
                    downLoadCs(downloadUrl);
                    update_layout.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                }

            }
        });

        not_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relievepopupWindow.dismiss();
            }
        });
        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relievepopupWindow.dismiss();
            }
        });
        //   }


        // 添加动态内容
        relievepopupWindow.setContentView(vPopupWindow, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relievepopupWindow.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            try {

                downLoadCs(jsonObject.getString("downloadUrl"));
                update_layout.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public static boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {

        for (String permission : permissionsREAD) {
            if (lacksPermission(mContexts, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 照相权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREADWrite = {

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

}
