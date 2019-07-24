package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.util.FileUtil;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.PermissionUtil;

import java.io.File;

/**
 * Created by Administrator on 2017/7/17.
 */

public abstract class PermissionActivity extends ToolbarBaseActivity {
    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    private boolean isStartSetting = false;
    protected String[] locationPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            //新增客户时，需要location以及camera权限
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE};
    protected String[] picPermissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    protected final int TYPE_LOCATION = 1;
    protected final int TYPE_PIC = 2; //checkPermissions() 还是有必要添加的
    private AlertDialog.Builder builder;
    protected boolean isStartSettingGPS;


    //    权限
    @Override
    final public void onRequestPermissionsResult(int requestCode,
                                                 String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PermissionUtil.PERMISSON_REQUESTCODE) {
            if (!PermissionUtil.verifyPermissions(paramArrayOfInt)) {      //没有授权
                showMissingPermissionDialog();              //显示提示信息
                isNeedCheck = true;
            } else {
                isNeedCheck = false;
                startLoad();
            }
        }
    }

    protected void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (isNeedCheck) {
                if (!PermissionUtil.checkPermissions(this, getNeedPermissions())) {
                    isNeedCheck = false;
                    startLoad();
                }
            } else {
                startLoad();
            }
        } else {
            //小于23可直接进行操作,不需要判断
            startLoad();
        }
    }
    protected void GPSIsOpen() {
        if (GPSIsOpenUtil.isOPen(this)) {
            checkPermissions();
        } else {
            if (builder != null) {
                builder = null;
            }
            builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS提示");
            builder.setMessage("请确定已开启定位(位置信息).");
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setPositiveButton("开启定位", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    isStartSettingGPS = true;
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    protected void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
        isStartSetting = true;
    }

    /**
     * 获取到权限进行的操作
     */
    protected void startLoad() {
    }


    protected abstract int getPermissionType();

    /**
     * 未获取到权限时显示的dialog
     */
    private void showMissingPermissionDialog() {
        if (getPermissionType() == TYPE_LOCATION) {
            showLocationMissingPermissionDialog();
        } else if (getPermissionType() == TYPE_PIC) {
            showPicMissingPermissionDialog();
        }
    }

    private void showPicMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("存储权限未开启,是否开启");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setPositiveButton("去开启",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    private void showLocationMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS提示");
        builder.setMessage("需要开启定位权限,是否开启?");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("去开启",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });
        builder.setCancelable(false);
        builder.show();
    }

    private String[] getNeedPermissions() {
        if (getPermissionType() == TYPE_LOCATION) {
            return locationPermissions;
        } else if (getPermissionType() == TYPE_PIC) {
            return picPermissions;
        }
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isStartSetting) {
            isNeedCheck = PermissionUtil.checkPermissions(this, getNeedPermissions());
            isStartSetting = false;
            if (!isNeedCheck) {
                startLoad();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (getPermissionType() == TYPE_PIC) {
            //删除缓存
            FileUtil.deleteAllFiles(new File(Contacts.path));
        }
        super.onDestroy();
    }
}