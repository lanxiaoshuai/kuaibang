package com.witkey.witkeyhelp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.view.impl.HomeActivity;


import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author lingxu
 * @date 2019/7/12 10:08
 * @description 抓取异常
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private MyAPP app;

    public CrashHandler(MyAPP myAPP) {
        app = myAPP;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//		if (!app.ISRELEASE) {
//			ExceptionUtil.CatchException((Exception) ex);
//		} else {
        ExceptionUtil.CatchException((Exception) ex);
        // toast告诉用户重启
        // 不起线程,不会显示,它在崩溃的边缘
        new Thread() {
            public void run() {
                Looper.prepare();// 轮循队列
                UIUtils.showToastSafe("啊哦,我出了点故障~");
                Looper.loop();
            }
        }.start();

        Log.i("llx", "执行成功");
        // 显示toast 使其休息两秒
        try {
            Thread.currentThread().sleep(2000);
        } catch (Exception e) {
        }
        // 实现重启功能

        // 程序已经崩溃,起一个新栈 Intent.FLAG_ACTIVITY_NEW_TASK
        Intent intent = new Intent(app, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(app, 100,// 请求码
                intent,// 会放到新栈中
                Intent.FLAG_ACTIVITY_NEW_TASK);

        // 定时器 使其多久之后才操作 获取系统服务
        AlarmManager alarmManager = (AlarmManager) app
                .getSystemService(Context.ALARM_SERVICE);
        // RTC 如果手机处于休眠状态,就不执行
        alarmManager.set(AlarmManager.RTC,
                System.currentTimeMillis() + 2000, pendingIntent);

        app.finishActivity();
    }

}
