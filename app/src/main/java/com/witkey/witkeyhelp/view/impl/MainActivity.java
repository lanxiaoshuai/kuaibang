package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.sh.sdk.shareinstall.ShareInstall;
import com.sh.sdk.shareinstall.listener.AppGetWakeUpListener;
import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.util.FileUtil;
import com.witkey.witkeyhelp.util.PermissionUtil;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.fragment.HomeFragment;
import com.witkey.witkeyhelp.view.impl.fragment.MeFragment;
import com.witkey.witkeyhelp.view.impl.fragment.NotLoggedInFragment;
import com.witkey.witkeyhelp.view.impl.fragment.RewardHallFragment;
import com.witkey.witkeyhelp.view.impl.fragment.CircleFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;


/**
 * @author lingxu
 * @date 2019/7/12 14:19
 * @description 主页面
 */
public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {
    private ArrayList<Fragment> mFragments;
    private int lastIndex;
    private User user;
    private BottomNavigationView navigation;
    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    private boolean isStartSetting = false;
    protected String[] locationPermissions = {
//            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
   //         Manifest.permission.ACCESS_FINE_LOCATION,
         //   Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            //新增客户时，需要location以及camera权限
//            Manifest.permission.CAMERA,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.MODIFY_AUDIO_SETTINGS

    };
    protected final int TYPE_L有必要添加的OCATION = 1;
    protected final int TYPE_PIC = 2; //checkPermissions() 还是

    private CircleFragment cicleFragment;
    private Fragment currentFragment;

    @Override
    protected void onCreateActivity() {

        ShareInstall.getInstance().getWakeUpParams(getIntent(),wakeUpListener);

        setContentView(R.layout.activity_main);
        initViews();
        setTitle(R.string.title_home);
        initBottomNavigation();
        initData();

        //checkPermissions();
        user = SpUtils.getObject(this, "LOGIN");
//        if (isStartSetting) {
//            isNeedCheck = PermissionUtil.checkPermissions(this, getNeedPermissions());
//            isStartSetting = false;
//            if (!isNeedCheck) {
//                startLoad();
//            }
//        }

    }

    //    权限
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] paramArrayOfInt) {
//        if (requestCode == PermissionUtil.PERMISSON_REQUESTCODE) {
//            if (!PermissionUtil.verifyPermissions(paramArrayOfInt)) {      //没有授权
//                //showMissingPermissionDialog();              //显示提示信息
//                isNeedCheck = true;
//            } else {
//                isNeedCheck = false;
//                startLoad();
//            }
//        }
//    }



    private String[] getNeedPermissions() {

        return locationPermissions;

    }

    private void showLocationMissingPermissionDialog() {


        final UICustomDialog2 dialog2 = new UICustomDialog2(this, "GPS提示", "3");
        dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.hide();
            }
        });
        dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.hide();
                startAppSettings();
            }
        });
        dialog2.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private int GPS_REQUEST_CODE = 10;

    protected void startAppSettings() {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.parse("package:" + getPackageName()));

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, GPS_REQUEST_CODE);

        // startActivity(intent);
        isStartSetting = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        resetToDefaultIcon();//重置到默认不选中图片
        String name = currentFragment.getClass().getSimpleName();
        switch (name) {
            case "CircleFragment":
                setFragmentPosition(0);
                navigation.getMenu().getItem(0).setIcon(R.mipmap.ic_home_select);
                break;
//            case "RewardHallFragment":
//                setFragmentPosition(1);
//                navigation.getMenu().getItem(1).setIcon(R.mipmap.ic_rewardhall_select);
//                break;
            case "ConversationListFragment":
                setFragmentPosition(1);
                navigation.getMenu().getItem(1).setIcon(R.mipmap.ic_message_select);
                break;
            case "MeFragment":
                setFragmentPosition(2);
                navigation.getMenu().getItem(2).setIcon(R.mipmap.ic_me_select);
                navigation.getMenu().getItem(2).setChecked(true);
                break;
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

    private void initData() {
        mFragments = new ArrayList<>();
        if(     cicleFragment ==null){
            cicleFragment = new CircleFragment();
        }
        mFragments.add(cicleFragment);
     //   mFragments.add(new RewardHallFragment());
        mFragments.add(new NotLoggedInFragment());
        mFragments.add(new MeFragment());
        // 初始化展示MessageFragment
        String type = getIntent().getStringExtra("type");

        if ("0".equals(type)) {
            setFragmentPosition(3);
            navigation.getMenu().getItem(2).setIcon(R.mipmap.ic_me_select);
        } else {
            setFragmentPosition(0);
            navigation.getMenu().getItem(0).setIcon(R.mipmap.ic_home_select);
        }

    }


    private void setFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.content, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();


    }

    private void initBottomNavigation() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
    }

    private void initViews() {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            resetToDefaultIcon();//重置到默认不选中图片
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //在这里替换图标
                    setFragmentPosition(0);
                    item.setIcon(R.mipmap.ic_home_select);
                    return true;
//                case R.id.navigation_rewardhall:
//                    //在这里替换图标
//                    setFragmentPosition(1);
//                    item.setIcon(R.mipmap.ic_rewardhall_select);
                 //   return true;
                case R.id.navigation_message:
                    //在这里替换图标
                    setFragmentPosition(1);
                    item.setIcon(R.mipmap.ic_message_select);
                    return true;
                case R.id.navigation_me:
                    //在这里替换图标
                    setFragmentPosition(2);
                    item.setIcon(R.mipmap.ic_me_select);
                    return true;

            }
            return false;
        }

    };

    private void resetToDefaultIcon() {
        MenuItem home = navigation.getMenu().findItem(R.id.navigation_home);
        home.setIcon(R.mipmap.ic_home_black_24dp);
//        MenuItem navigation_rewardhall = navigation.getMenu().findItem(R.id.navigation_rewardhall);
//        navigation_rewardhall.setIcon(R.mipmap.ic_rewardhall_black_24dp);
        MenuItem navigation_message = navigation.getMenu().findItem(R.id.navigation_message);
        navigation_message.setIcon(R.mipmap.ic_message_black_24dp);
        MenuItem navigation_me = navigation.getMenu().findItem(R.id.navigation_me);
        navigation_me.setIcon(R.mipmap.ic_me_black_24dp);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (1 == TYPE_PIC) {
            //删除缓存
            FileUtil.deleteAllFiles(new File(Contacts.path));
        }

    }
    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则app在后台运行时，会无法截获
        ShareInstall.getInstance().getWakeUpParams(intent, wakeUpListener);
    }

    // 注意：SDK调用getWakeUpParams方法获取参数是异步操作，请确保在onGetWakeUpFinish回调中拿到参数后才去处理自己的业务逻辑
    private AppGetWakeUpListener wakeUpListener = new AppGetWakeUpListener() {
        @Override
        public void onGetWakeUpFinish(String info) {
            // 客户端获取到的参数是json字符串格式
            Log.d("ShareInstallAAAAAA", "info = " + info);
            try {
                JSONObject object = new JSONObject(info);
                // 通过该方法拿到设置的渠道值，剩余值为自定义的其他参数
                String channel = object.optString("channel");
                Log.e("ShareInstall", "channel = " + channel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
