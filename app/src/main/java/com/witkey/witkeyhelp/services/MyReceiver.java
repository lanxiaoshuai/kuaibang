package com.witkey.witkeyhelp.services;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;

import com.witkey.witkeyhelp.event.AdvertisementEvent;

import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.SystemMessageActivity;
import com.witkey.witkeyhelp.view.impl.TaskAccomplished;
import com.witkey.witkeyhelp.view.impl.HomeActivity;



import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.helper.Logger;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";
    private Intent startInten;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();

            Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + AA(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

                try {

                    JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    String type = jsonObject.getString("type");
                    if ("10000".equals(type)) {

                    } else {
                        startInten = new Intent(context, TaskAccomplished.class);
                        startInten.putExtra("content", bundle.getString(JPushInterface.EXTRA_MESSAGE));
                        startInten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyAPP.getInstance().startActivity(startInten);
                    }


                } catch (Exception e) {
                 //   ToastUtils.showTestShort(context, e.getMessage());
                }


                //  AppCompatActivity baseActivity = (AppCompatActivity) context;
                //     baseActivity.overridePendingTransition(R.anim.intent_bottom_in,R.anim.bottom_silent);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

                Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户点击打开了通知");


                if (JPushInterface.EXTRA_ALERT_TYPE.equals(intent.getAction())) {

                }


//                JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_MESSAGE));
//                String type = jsonObject.getString("type");
                JSONObject aa = AA(bundle);
                String string = aa.getString("cn.jpush.android.EXTRA");


                if ("10000".equals(string)) {
                    if (getTopActivity(context).contains("SystemMessageActivity")) {
                        // Log.e("tag", getTopActivity(context) + "=======" + MissionDetailActivity.taskdetails);

                        EventBus.getDefault().post(new AdvertisementEvent("到账"));
                    } else {
                        startInten = new Intent(context, SystemMessageActivity.class);
                        startInten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MyAPP.getInstance().startActivity(startInten);
                    }
                }



            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }


    // 打印所有的 intent extra 数据
    private static JSONObject AA(Bundle bundle) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            for (String key : bundle.keySet()) {
                if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                    jsonObject.put(key, bundle.getInt(key));
                } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                    jsonObject.put(key, bundle.getBoolean(key));
                } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                    if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                        Logger.i(TAG, "This message has no Extra data");
                        continue;
                    }

                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it = json.keys();

                        while (it.hasNext()) {
                            String myKey = it.next();

                            jsonObject.put(key, json.optString(myKey));
                        }
                    } catch (JSONException e) {
                        Logger.e(TAG, "Get message extra JSON error!");
                    }

                } else {
                    jsonObject.put(key, bundle.get(key));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        //   if (HomeActivity.isForeground) {
        Intent i = new Intent(context, HomeActivity.class);
        i.putExtra("type", "push");
        i.putExtras(bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);

        //  }
    }

    //判断当前界面显示的是哪个Activity
    public static String getTopActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        //  Log.d("Chunna.zheng", "pkg:"+cn.getPackageName());//包名
        //    Log.e("Chunna.zheng", "cls:" + cn.getClassName());//包名加类名
        return cn.getClassName();
    }

}
