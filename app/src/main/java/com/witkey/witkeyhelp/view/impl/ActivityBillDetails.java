package com.witkey.witkeyhelp.view.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.NewsDetailsAdapter;
import com.witkey.witkeyhelp.bean.BillFlowBean;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityBillDetails extends BaseActivity {

    private BillFlowBean.ReturnObjectBean.RowsBean rowsBean;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.bill_details);
        setIncludeTitle("账单详情");
        rowsBean = (BillFlowBean.ReturnObjectBean.RowsBean) getIntent().getSerializableExtra("BILLDETAILS");
        ImageView bill_details_img = findViewById(R.id.bill_details_img);
        TextView bill_details_title = findViewById(R.id.bill_details_title);
        TextView bill_details_price_type = findViewById(R.id.bill_details_price_type);
        TextView bill_details_time = findViewById(R.id.bill_details_time);
        TextView bill_details_content = findViewById(R.id.bill_details_content);
        TextView bill_details_price = findViewById(R.id.bill_details_price);

        LinearLayout eduction = findViewById(R.id.eduction);

        TextView back_name = findViewById(R.id.back_name);
        TextView shenqinshijian = findViewById(R.id.shenqinshijian);
        TextView daozhangshijian = findViewById(R.id.daozhangshijian);
        TextView daozhangshijian_time = findViewById(R.id.daozhangshijian_time);
        LinearLayout daozanshijian = findViewById(R.id.daozanshijian);
        LinearLayout linearLayout = findViewById(R.id.zhangdan_contnt);
        bill_details_title.setText(rowsBean.getTitle());
        bill_details_time.setText(rowsBean.getCreateTime());
        bill_details_content.setText(rowsBean.getDescription());

            if(rowsBean!=null){
                switch (rowsBean.getTitle()) {
                    case "信息咨询":
                        bill_details_img.setImageResource(R.mipmap.news_img);

                        shenqinshijian.setText("收款时间");
                        daozanshijian.setVisibility(View.GONE);

                        if ("sub".equals(rowsBean.getType())) {
                            back_name.setText("支付方式");
                            shenqinshijian.setText("消费时间");
                            bill_details_price.setText("-" + rowsBean.getAmount() + "");
                            switch (rowsBean.getAmountType()) {
                                case 0:
                                    bill_details_price_type.setText("钻石");
                                    break;
                                case 1:
                                    bill_details_price_type.setText("余额");
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    bill_details_price_type.setText("微信支付");
                                    break;
                            }
                        } else if ("add".equals(rowsBean.getType())) {
                            back_name.setText("当前状态");
                            bill_details_price.setText("+" + rowsBean.getAmount() + "");
                            shenqinshijian.setText("收款时间");
                            switch (rowsBean.getAmountType()) {
                                case 0:
                                    bill_details_price_type.setText("已存入钻石");
                                    break;
                                case 1:
                                    bill_details_price_type.setText("已存入余额");
                                    break;

                            }
                        }
                        break;
                    case "悬赏帮助":

                        bill_details_img.setImageResource(R.mipmap.offerreward);
                        daozanshijian.setVisibility(View.GONE);

                        if ("sub".equals(rowsBean.getType())) {
                            bill_details_price.setText("-" + rowsBean.getAmount() + "");
                            back_name.setText("支付方式");
                            shenqinshijian.setText("消费时间");
                            switch (rowsBean.getAmountType()) {
                                case 0:
                                    bill_details_price_type.setText("钻石");
                                    break;
                                case 1:
                                    bill_details_price_type.setText("余额");
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    bill_details_price_type.setText("微信支付");
                                    break;
                            }
                        } else if ("add".equals(rowsBean.getType())) {
                            //  bill_details_price.setTextColor(getResources().getColor(R.color.item_mission_red));
                            bill_details_price.setText("+" + rowsBean.getAmount() + "");
                            back_name.setText("当前状态");

                            shenqinshijian.setText("收款时间");
                            switch (rowsBean.getAmountType()) {
                                case 0:
                                    bill_details_price_type.setText("已存入钻石");
                                    break;
                                case 1:
                                    bill_details_price_type.setText("已存入余额");
                                    break;

                            }
                        }
                        //  Glide.with(this).load(R.mipmap.offerreward).into(bill_details_img);
                        break;
                    case "余额提现":
                        bill_details_img.setImageResource(R.mipmap.yuetixian);
                        back_name.setText("提现账户");
                        bill_details_price_type.setText(rowsBean.getParams().getBankName());
                        shenqinshijian.setText("申请时间");
                        daozhangshijian.setText("到账时间");
                        bill_details_time.setText(rowsBean.getParams().getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price.setText("-"+rowsBean.getAmount());
                        break;
                    case "保证金提现":
                        bill_details_img.setImageResource(R.mipmap.yuetixian);
                        back_name.setText("提现账户");
                        bill_details_price_type.setText(rowsBean.getParams().getBankName());
                        shenqinshijian.setText("申请时间");
                        daozhangshijian.setText("到账时间");
                        bill_details_time.setText(rowsBean.getParams().getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price.setText("-"+rowsBean.getAmount());
                        break;
                    case "保证金冻结":
                        bill_details_img.setImageResource(R.mipmap.marginrecharge_detils);
                        back_name.setText("当前状态");
                        bill_details_price_type.setText("冻结");
                        shenqinshijian.setText("冻结时间");
                        //    daozhangshijian.setText("到账时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price.setText("-"+rowsBean.getAmount() + "");
                        break;
                    case "解冻保证金":
                        bill_details_img.setImageResource(R.mipmap.marginrecharge_detils);
                        back_name.setText("当前状态");
                        bill_details_price_type.setText("解冻");
                        shenqinshijian.setText("解冻时间");
                        daozhangshijian.setText("到账时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price.setText("-"+rowsBean.getAmount() + "");
                        break;
                    case "保证金充值":
                        bill_details_img.setImageResource(R.mipmap.marginrecharge_detils);
                        back_name.setText("当前状态");
                        shenqinshijian.setText("收款时间");
                        daozhangshijian.setText("到账时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        break;
                    case "保证金退回":
                        bill_details_img.setImageResource(R.mipmap.marginrecharge_detils);
                        back_name.setText("当前状态");
                        shenqinshijian.setText("收款时间");
                        daozhangshijian.setText("到账时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        break;
                    case "退回资金":
                        back_name.setText("当前状态");
                        bill_details_img.setImageResource(R.mipmap.returnfunds);
                        bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("消费时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        switch (rowsBean.getAmountType()) {
                            case 0:
                                bill_details_price_type.setText("已存入钻石");
                                break;
                            case 1:
                                bill_details_price_type.setText("已存入余额");
                                break;
                        }
                        break;
                    case "投放广告":
                        back_name.setText("支付方式");
                        bill_details_img.setImageResource(R.mipmap.diamondnoticon);
                        bill_details_price.setText("-" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("消费时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        switch (rowsBean.getAmountType()) {
                            case 0:
                                bill_details_price_type.setText("钻石支付");
                                break;
                            case 1:
                                bill_details_price_type.setText("余额支付");
                                break;
                        }
                        break;
                    case "浏览广告":
                        back_name.setText("当前状态");
                        bill_details_img.setImageResource(R.mipmap.diamondnoticon);
                        bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("收款时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        switch (rowsBean.getAmountType()) {
                            case 0:
                                bill_details_price_type.setText("已存入钻石");
                                break;
                            case 1:
                                bill_details_price_type.setText("已存入余额");
                                break;
                        }
                        break;
                    case "提现手续费":
                        back_name.setText("支付方式");
                        bill_details_img.setImageResource(R.mipmap.returnfunds);
                        bill_details_price.setText("-" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("消费时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        bill_details_price_type.setText("余额支付");

                        break;
                    case "系统赠送":
                        bill_details_img.setImageResource(R.mipmap.system_presentation_dtelis);
                        back_name.setText("当前状态");
                        bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("收款时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price_type.setText("已存入钻石");
                        break;
                    case "抵扣平台费":
                        bill_details_img.setImageResource(R.mipmap.returnfunds);
                        bill_details_price.setText(rowsBean.getAmount() + "");
                        back_name.setText("支付方式");
                        // bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("收款时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getEndTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price_type.setText("钻石");
                        eduction.setVisibility(View.GONE);
                        break;
                    case "新用户注册赠送":
                        back_name.setText("当前状态");
                        bill_details_img.setImageResource(R.mipmap.system_presentation_dtelis);
                        bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("收款时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getCreateTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price_type.setText("已存入钻石");
                        break;
                    case "用户推广赠送":
                        back_name.setText("当前状态");
                        bill_details_img.setImageResource(R.mipmap.system_presentation_dtelis);
                        bill_details_price.setText("+" + rowsBean.getAmount() + "");
                        shenqinshijian.setText("收款时间");
                        daozhangshijian.setText("收款时间");
                        bill_details_time.setText(rowsBean.getCreateTime() + "");
                        daozhangshijian_time.setText(rowsBean.getParams().getCreateTime() + "");
                        linearLayout.setVisibility(View.GONE);
                        daozanshijian.setVisibility(View.GONE);
                        bill_details_price_type.setText("已存入钻石");
                        break;
                }
            }else {
                ToastUtils.showTestShort(this,"数据出现问题,请稍后再试");
            }

    }
}