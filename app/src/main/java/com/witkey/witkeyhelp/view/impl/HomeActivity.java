package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.sh.sdk.shareinstall.ShareInstall;
import com.sh.sdk.shareinstall.listener.AppGetWakeUpListener;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;


import com.witkey.witkeyhelp.event.UnreadBean;

import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.fragment.ConversationListFragment;
import com.witkey.witkeyhelp.view.impl.fragment.HomeFragment;
import com.witkey.witkeyhelp.view.impl.fragment.MeFragment;
import com.witkey.witkeyhelp.view.impl.fragment.RewardHallFragment;
import com.witkey.witkeyhelp.view.impl.fragment.CircleFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;


/**
 * Created by jie on 2019/12/28.
 */

public class HomeActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {


    private ArrayList<Fragment> mFragments;
    private int lastIndex;


    private User user;

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    private boolean isStartSetting = false;
    protected String[] locationPermissions = {

            Manifest.permission.ACCESS_FINE_LOCATION
    };
    protected final int TYPE_L有必要添加的OCATION = 1;
    protected final int TYPE_PIC = 2; //checkPermissions() 还是
    private FragmentTransaction ft;
    private Fragment currentFragment;
    private TextView count;
    private CircleFragment cicleFragment;
    //  public static boolean isForeground = false;

    @Override
    protected void onCreateActivity() {

        ShareInstall.getInstance().getWakeUpParams(getIntent(), wakeUpListener);

        setContentView(R.layout.activity_main);
        initViews();
        setTitle(R.string.title_home);
        initBottomNavigation();
        initData();


        //registerMessageReceiver();  // used for receive msg


    }


    //    权限
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] paramArrayOfInt) {
//        if (requestCode == PermissionUtil.PERMISSON_REQUESTCODE) {
//            if (!PermissionUtil.verifyPermissions(paramArrayOfInt)) {      //没有授权
//                //   showMissingPermissionDialog();              //显示提示信息
//                isNeedCheck = true;
//            } else {
//                isNeedCheck = false;
//                startLoad();
//            }
//        }
//    }


    /**
     * 未获取到权限时显示的dialog
     */
    private void showMissingPermissionDialog() {

        showLocationMissingPermissionDialog();

    }

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
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, GPS_REQUEST_CODE);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //  isForeground = true;

    }

    private void initData() {


        mFragments = new ArrayList<>();
        if (cicleFragment == null) {
            cicleFragment = new CircleFragment();
        }
        mFragments.add(cicleFragment);
        //    mFragments.add(new RewardHallFragment());
        mFragments.add(new ConversationListFragment());
        mFragments.add(new MeFragment());
        // 初始化展示MessageFragment
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)) {
            setFragmentPosition(2);
            navigation.getMenu().getItem(2).setIcon(R.mipmap.ic_me_select);
        } else {
            setFragmentPosition(0);
            navigation.getMenu().getItem(0).setIcon(R.mipmap.ic_home_select);
        }


    }


    private void setFragmentPosition(int position) {
        ft = getSupportFragmentManager().beginTransaction();
        currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commitAllowingStateLoss();
            ft.add(R.id.content, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();


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
        if (JMessageClient.getAllUnReadMsgCount() > 0) {
            count.setVisibility(View.VISIBLE);
        } else {
            count.setVisibility(View.GONE);
        }
    }

    private BottomNavigationView navigation;
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
//                    return true;
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

    private void initBottomNavigation() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        closeAnimation(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);

        //  获取整个的NavigationView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);

//这里就是获取所添加的每一个Tab(或者叫menu)，
        View tab = menuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;

//加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false);

//添加到Tab上
        itemView.addView(badge);
        count = (TextView) badge.findViewById(R.id.tv_msg_count);
        //  count.setText(String.valueOf(1));

//如果没有消息，不需要显示的时候那只需要将它隐藏即可
        count.setVisibility(View.GONE);


    }

    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(UnreadBean event) {
        if (event.getLogin().equals("0")) {
            count.setVisibility(View.GONE);
        } else if (event.getLogin().equals("1")) {
            count.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 收到消息
     */

    public void onEvent(MessageEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                count.setVisibility(View.VISIBLE);
            }
        });

    }

    private void initViews() {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onDestroy() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();


    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

                    setFragmentPosition(3);
                    navigation.getMenu().getItem(3).setIcon(R.mipmap.ic_me_select);
                }
            } catch (Exception e) {

            }
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
            Log.e("ShareInstallAAAAAA", "info = " + info);
            try {
                JSONObject object = new JSONObject(info);
                // 通过该方法拿到设置的渠道值，剩余值为自定义的其他参数e
                String channel = object.optString("channel");
                Log.e("ShareInstall", "channel = " + channel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("RestrictedApi")
    public void closeAnimation(BottomNavigationView view) {
        BottomNavigationMenuView mMenuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < mMenuView.getChildCount(); i++) {
            BottomNavigationItemView button = (BottomNavigationItemView) mMenuView.getChildAt(i);
            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");
            float mSmallLabelSize = mSmallLabel.getTextSize();
            setField(button.getClass(), button, "shiftAmount", 0F);
            setField(button.getClass(), button, "scaleUpFactor", 1F);
            setField(button.getClass(), button, "scaleDownFactor", 1F);
            mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize);
        }
        mMenuView.updateMenuView();
    }


    private <T> T getField(Class targetClass, Object instance, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void setField(Class targetClass, Object instance, String fieldName, Object value) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
