package com.witkey.witkeyhelp.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 验证码倒计时
 */
public class CountDownUtil {

    private static CountDownTimer timer;

    public static boolean countDown(final TextView tvGetCode) {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText((millisUntilFinished / 1000) + " 秒");
                tvGetCode.setEnabled(false);
            }
            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText("获取验证码");
            }
        };
        timer.start();
        return true;
    }

    public static boolean cancleCount(final TextView tvGetCode) {
        if (timer != null) {
            timer.cancel();
            tvGetCode.setEnabled(true);
            tvGetCode.setText("获取验证码");
            timer = null;
            return false;
        }
        return false;
    }
}
