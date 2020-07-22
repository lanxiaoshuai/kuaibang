package com.witkey.witkeyhelp.view.impl;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.TimeUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by asus on 2020/3/4.
 */

public class ActivityResultsWithdrawals extends BaseActivity {
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_results_withdrawals);

        Intent intent = getIntent();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间


        String price = intent.getStringExtra("price");
        String lastNumber = intent.getStringExtra("lastNumber");
        String serviceCharge1 = intent.getStringExtra("serviceCharge");
        String amountmoney = intent.getStringExtra("amountmoney");

      ImageView gray_line= findViewById(R.id.gray_line);
     ImageView an_yuan= findViewById(R.id.an_yuan);
    TextView  processing_text= findViewById(R.id.processing_text);
        TextView  successfully= findViewById(R.id.successfully);
        if (lastNumber.equals("微信")) {
            cal.add(Calendar.HOUR, 2);//增加2个小时 
            gray_line.setImageResource(R.mipmap.green_line);
            an_yuan.setImageResource(R.mipmap.green_image);
            processing_text.setTextSize(sp2px(this,5));
            processing_text.setTextColor(getResources().getColor(R.color.color_grey_999999));

            successfully.setTextSize(sp2px(this,6));

            successfully.setTextColor(getResources().getColor(R.color.cp_color_gray_dark));
        } else {
            cal.add(Calendar.DATE, 1);//增加一天  
//            gray_line.setImageResource(R.mipmap.gray_line);
//            an_yuan.setImageResource(R.mipmap.ash_img);
//            processing_text.setTextColor(getResources().getColor(R.color.cp_color_gray_dark));
//            successfully.setTextSize(sp2px(this,16));
//
//            successfully.setTextColor(getResources().getColor(R.color.cp_color_gray_dark));
        }


        TextView paymentdate = findViewById(R.id.paymentdate);
        TextView cashWithdrawalAmount = findViewById(R.id.cashWithdrawalAmount);
        TextView paidbankcard = findViewById(R.id.paidbankcard);
        TextView serviceCharge = findViewById(R.id.serviceCharge);
        RelativeLayout confirmationWithdrawals = findViewById(R.id.confirmationWithdrawals);

        paymentdate.setText("预计" + TimeUtils.getDateStr(cal.getTime(), null) + "前到账");
        cashWithdrawalAmount.setText("￥" + price + ".00");
        paidbankcard.setText(lastNumber);
        if (amountmoney.equals("余额")) {
            serviceCharge.setText(serviceCharge1 + "");
            setIncludeTitle("余额提现");
        } else if (amountmoney.equals("保证金")) {
            serviceCharge.setText("￥0.00");
            setIncludeTitle("保证金提现");
        }

        confirmationWithdrawals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    public static int sp2px(Context context, float spValue) {
               final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
               return (int) (spValue * fontScale + 0.5f);
           }
}
