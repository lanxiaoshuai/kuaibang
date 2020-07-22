package com.witkey.witkeyhelp.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.view.impl.ActivityBalance;
import com.witkey.witkeyhelp.view.impl.ActivityBond;
import com.witkey.witkeyhelp.view.impl.ActivityCashWithdrawal;
import com.witkey.witkeyhelp.view.impl.ActivityMarginRecharge;
import com.witkey.witkeyhelp.view.impl.ReceiveMissionActivity;
import com.witkey.witkeyhelp.view.impl.ReleaseMissionActivity;

/**
 * Created by jie on 2019/11/30.
 */

public class TaskDetailsDialog extends Dialog implements View.OnClickListener {

    private Activity context;
    private TextView tvOpen;
    private int type;
    private LinearLayout task_withdrawal;
    private LinearLayout bond;
    private LinearLayout cancel_task_completion;
    private LinearLayout withdraw;
    private LinearLayout cancel;
    private LinearLayout to_recharge;
    private LinearLayout confirm;

    private int businessId;
    private LinearLayout verify_clear;
    private LinearLayout verify;
    private TextView tips;
    private TextView nsufficient_amount;
    private String data;
    private ImageView cancel_les;

    public TaskDetailsDialog(@NonNull Activity context, int type, int businessId, String data) {
        super(context, R.style.ShareDialog);
        this.context = context;
        this.type = type;
        this.businessId = businessId;
        this.data = data;

    }

    public TaskDetailsDialog(@NonNull Activity context, int type, int businessId) {
        super(context, R.style.ShareDialog);
        this.context = context;
        this.type = type;
        this.businessId = businessId;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将对话框的大小按屏幕大小的百分比设置


        View dialogview = View.inflate(context, R.layout.task_withdrawal, null);


        setContentView(dialogview);
        setViews(dialogview);

        setListener();

        // 设置触摸对话框意外的地方取消对话框
        setCanceledOnTouchOutside(true);
        inittype();
    }

    private void inittype() {

        switch (type) {
            case 0:
                task_withdrawal.setVisibility(View.VISIBLE);
                break;
            case 1:
                bond.setVisibility(View.VISIBLE);
                cancel_les.setVisibility(View.GONE);
                break;
            case 2:
                cancel_task_completion.setVisibility(View.VISIBLE);
                break;
            case 3:
                verify.setVisibility(View.VISIBLE);
                cancel_les.setVisibility(View.GONE);
                break;
            case 4:
                bond.setVisibility(View.VISIBLE);
                break;
            case 5:
                verify.setVisibility(View.VISIBLE);
                break;
            case 6:
                verify.setVisibility(View.VISIBLE);
                break;
            case 7:
                bond.setVisibility(View.VISIBLE);

                break;
            case 8:
                verify.setVisibility(View.VISIBLE);
                break;
            case 9:
                verify.setVisibility(View.VISIBLE);
                break;
            case 10:
                verify.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void setListener() {


        withdraw.setOnClickListener(this);
        cancel.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
        verify_clear.setOnClickListener(this);
        to_recharge.setOnClickListener(this);
        cancel_les.setOnClickListener(this);
    }

    private void setViews(View v) {


        task_withdrawal = v.findViewById(R.id.task_withdrawal);
        bond = v.findViewById(R.id.bond);
        cancel_task_completion = v.findViewById(R.id.cancel_task_completion);
        verify = v.findViewById(R.id.verify);


        verify_clear = v.findViewById(R.id.verify_clear);
        withdraw = (LinearLayout) v.findViewById(R.id.withdraw);
        cancel = (LinearLayout) v.findViewById(R.id.cancel);
        to_recharge = (LinearLayout) v.findViewById(R.id.to_recharge);
        confirm = (LinearLayout) v.findViewById(R.id.confirm);
        tips = findViewById(R.id.tips);
        nsufficient_amount = findViewById(R.id.nsufficient_amount);
        cancel_les = findViewById(R.id.cancel_les);
        TextView tiptwo = findViewById(R.id.tiptwo);
        if (type == 3) {
            tips.setText(data);
            tiptwo.setVisibility(View.GONE);
        } else if (type == 5) {
            tips.setText("任务解除成功");
            tiptwo.setVisibility(View.GONE);
        } else if (type == 6) {
            tips.setText("任务解除成功");
            tiptwo.setVisibility(View.VISIBLE);
        } else if (type == 7) {
            nsufficient_amount.setText("余额不足");
        } else if (type == 1) {
            nsufficient_amount.setText("保证金不足");
        } else if (type == 8) {
            tips.setText("任务提交成功");
            tiptwo.setVisibility(View.VISIBLE);
            tiptwo.setText("等待悬赏者确认");
        } else if (type == 9) {
            tips.setText("任务已完成");
            tiptwo.setText("请前往个人中心查看");
            tiptwo.setVisibility(View.VISIBLE);
        } else if (type == 10) {
            tips.setText("钻石不足");
            tiptwo.setVisibility(View.GONE);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withdraw:

                break;
            case R.id.cancel:

                cancel();

                break;
            case R.id.to_recharge:
                if (type == 1) {
                    cancel();
                    Intent intent = new Intent(context, ActivityMarginRecharge.class);
                    intent.putExtra("amountmoney", "保证金");
                    context.startActivity(intent);
                } else if (type == 7) {
                    cancel();
                    Intent intent = new Intent(context, ActivityBalance.class);
                    intent.putExtra("amountmoney", "余额");
                    context.startActivity(intent);
                }
                break;
            case R.id.confirm:

                cancel();

                break;
            case R.id.verify_clear:

                cancel();


                break;
            case R.id.cancel_les:
                cancel();
                break;
            default:
                break;

        }
    }


}
