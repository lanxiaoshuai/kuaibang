package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;

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

    public static boolean countDownMoneyReward( final String  username, final TextView tvGetCode, final TextView countdown, final int useriId, final int id, final LinearLayout linearLayout) {
        timer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText((millisUntilFinished / 1000) + "s");

                tvGetCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                //  tvGetCode.setText("赏金已到账");
                countdown.setVisibility(View.VISIBLE);
                tvGetCode.setVisibility(View.GONE);
                MyAPP.getInstance().getApi().advertisingGetReward(id, useriId).enqueue(new Callback(IModel.callback, "领取赏金失败") {
                    @Override
                    public void getSuc(String body) {

                        tvGetCode.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        SpUtils.putBoolean(MyAPP.getInstance(), username + "Advertisement", true);
                    }
                });
            }
        };
        timer.start();

        return true;
    }

    public static CountDownTimer getTimer() {
        return timer;
    }

}
