package com.witkey.witkeyhelp.view.impl;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.PayInfoBean;
import com.witkey.witkeyhelp.presenter.IConsultPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.ConsultPresenterImpl;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.WXPayUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IConsultView;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.wxapi.WXPayEntryActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jie on 2019/12/7.
 */

public class ActivityMarginRecharge extends PermissionActivity implements View.OnClickListener, IConsultView {


    private IConsultPresenter presenter;
    private Button echarge;
    private EditText rechargeJine;

    private WXPayUtils wxPayUtils;
    private boolean typeBoolean;

    private String out_trade_no;
    private WXPayEntryActivity.WxPayinterace WxPayinterace = new WXPayEntryActivity.WxPayinterace() {
        @Override
        public void Wxpay(int code) {
            Log.e("tag", code + "" + "走了回调");
        }
    };

    @Override
    protected void initWidget() {

        echarge = findViewById(R.id.echarge);
        rechargeJine = findViewById(R.id.rechargeJine);

        rechargeJine.post(new Runnable() {
            @Override
            public void run() {
                rechargeJine.requestFocus();
            }
        });
        rechargeJine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    echarge.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                } else {
                    echarge.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fillet;
    }

    @Override
    protected void initEvent() {
        echarge.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.echarge:
                String price = rechargeJine.getText().toString();

                if (price == null || price.equals("") || price.equals("0")) {
                    ToastUtils.showTestShort(this, "请输入充值金额");
                } else {
                    wxPayUtils = new WXPayUtils(this);
                    WXPayEntryActivity.setWxPayinterace(WxPayinterace);
                    DialogUtil.showProgress(this);
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", user.getUserId() + "");
                    presenter.wxAppletPay("酷爱帮", Integer.parseInt(price) * 100 + "", "==", map.toString());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (typeBoolean) {
            presenter.apiwxpayResult(out_trade_no);


        }
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    public void saveSuc(String businessId) {

    }

    @Override
    public void publishSuc() {

    }

    @Override
    public void saveImgSuc(String imgurl) {

    }

    @Override
    public void receiptSuc() {

    }

    @Override
    public void wxAppletPay(PayInfoBean payInfoBean) {

        out_trade_no = payInfoBean.getReturnObject().getOut_trade_no();
        wxPayUtils.WxPay(payInfoBean, this);
        typeBoolean = true;
    }

    @Override
    public void wxAppletPayResult(String result) {
        //    Log.e("tag", "");
        ToastUtils.showTestShort(this, "充值成功");
        MyAPP.getInstance().finishSingleActivity(ConsultActivity.class);
        finish();
    }

    @Override
    public void wxAppletPayError(String errorresult) {
        typeBoolean = false;

        ToastUtils.showTestShort(this, "充值失败");
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new ConsultPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        setIncludeTitle("保证金充值");
    }

    @Override
    protected int getPermissionType() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
