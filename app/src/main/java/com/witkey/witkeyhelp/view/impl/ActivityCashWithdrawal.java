package com.witkey.witkeyhelp.view.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.BankListAdapter;
import com.witkey.witkeyhelp.adapter.UnpublishFeedbackAdapter;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.AllCardBean;
import com.witkey.witkeyhelp.bean.MyCardBean;
import com.witkey.witkeyhelp.bean.SystemMessageBean;
import com.witkey.witkeyhelp.bean.UnpublishFeedbackBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.TaskDetailsDialog;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.AmountMoneyPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.AmountMoneyPresenterImpl;
import com.witkey.witkeyhelp.util.DoubleUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.AmountMoneyView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;
import com.witkey.witkeyhelp.wxapi.WXEntryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jie on 2019/12/7.
 */

public class ActivityCashWithdrawal extends InitPresenterBaseActivity implements View.OnClickListener, AmountMoneyView {

    private AmountMoneyPresenter presenter;
    private Button nxt_step;
    private RelativeLayout bank_card_layout;
    private LinearLayout add_back_list;
    private ImageView colse_add;
    private String amountmoney;
    private ImageView card_img;
    private TextView card_name;
    private EditText amountMoney;
    private String bankId;
    private TextView tishi;
    private String lastNumber;
    private String serviceCharge;
    private TextView price_type;
    private TextView allwithdrawals;
    private TextView price_money;
    private String first;
    private String openId;
    private TextView payment_date;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        amountmoney = argIntent.getStringExtra("amountmoney");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withdraw:

                break;
            case R.id.bank_card_layout:
                if (aBoolean) {
                    if (PventQuickClick.isFastDoubleClick()) {
                        break;
                    }
                    //设置背景透明
                    addBackground();

                    //引入依附的布局
                    View parentView = LayoutInflater.from(this).inflate(R.layout.recharge_balance, null);
                    //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
                    popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                }


                break;
            case R.id.nxt_step:

                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                String price = amountMoney.getText().toString();
                String backname = card_name.getText().toString();
                if (!backname.equals("添加银行卡")) {
                    if (price.equals("") || price.equals("0")) {

                        ToastUtils.showTestShort(this, "请填写提现金额");
                    } else {
                        showCashWithdrawalAnimation(price);

                    }
                } else {
                    ToastUtils.showTestShort(this, "请添加银行卡");
                }
                break;
            case R.id.allwithdrawals:
                if (price_money.getText().toString().contains(".")) {
                    if (Double.parseDouble(price_money.getText().toString()) < 1.00) {
                        if (amountmoney.equals("余额")) {
                            ToastUtils.showTestShort(this, "您的余额为0或者余额小于1元不能提现");
                        } else if (amountmoney.equals("保证金")) {
                            ToastUtils.showTestShort(this, "您的保证金为0或者保证金小于1元不能提现");
                        }
                        return;
                    }
                } else {
                    if (Integer.parseInt(price_money.getText().toString()) < 1) {
                        if (amountmoney.equals("余额")) {
                            ToastUtils.showTestShort(this, "您的余额为0或者余额小于1元不能提现");
                        } else if (amountmoney.equals("保证金")) {
                            ToastUtils.showTestShort(this, "您的保证金为0或者保证金小于1元不能提现");
                        }
                        return;
                    }
                }
                if (price_money.getText().toString().contains(".")) {
                    int indexOf = price_money.getText().toString().indexOf(".");
                    amountMoney.setText(price_money.getText().toString().substring(0, indexOf));
                } else {
                    amountMoney.setText(price_money.getText().toString());
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void showBondDetails() {

    }

    @Override
    public void depositWithdrawalDetails() {

    }

    @Override
    public void bankCardInformation() {

    }


    @Override
    public void myBankcard(List<MyCardBean.ReturnObjectBean.RowsBean> data) {

        //  if (data == null || data.size() == 0) {
        Glide.with(this).load(URL.getImgPath + "weChat.png").into(card_img);
        if (openId == null || "".equals(openId)) {
            card_name.setText("微信(未绑定)");
        } else {
            card_name.setText("微信" + openId);
        }


//        } else {
//            card_img.setVisibility(View.VISIBLE);
//            if (data.size() > 0) {
//                Glide.with(this).load(URL.getImgPath + data.get(0).getBank().getMiniIcon()).into(card_img);
//                if (!TextUtils.isEmpty(data.get(0).getCardNo()) && data.get(0).getCardNo().length() >= 4) {
//
//                    lastNumber = data.get(0).getCardNo().substring(data.get(0).getCardNo().length() - 4, data.get(0).getCardNo().length()) + " 尾号" + data.get(0).getBank().getBankName().substring(2, data.get(0).getBank().getBankName().length());
//                    card_name.setText(data.get(0).getBank().getBankName().substring(2, data.get(0).getBank().getBankName().length()) + "(" + data.get(0).getCardNo().substring(data.get(0).getCardNo().length() - 4, data.get(0).getCardNo().length()) + ")");
//                }
//                bankId = data.get(0).getId();
//
//            }
//        }

        this.mDatas.clear();


        MyCardBean.ReturnObjectBean.RowsBean rowsBean = new MyCardBean.ReturnObjectBean.RowsBean();
        MyCardBean.ReturnObjectBean.RowsBean.BankBean bankBean = new MyCardBean.ReturnObjectBean.RowsBean.BankBean();


        //rowsBean.set
        bankBean.setBankName("微信");
        rowsBean.setCardNo("                       ");
        bankBean.setMiniIcon("weChat.png");
        rowsBean.setBank(bankBean);
        mDatas.add(rowsBean);
        this.mDatas.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addBankCard() {

    }

    @Override
    public void getBankCard(List<AllCardBean> mDatas) {
    }

    @Override
    public void cancelBankCard() {
        ToastUtils.showTestShort(this, "解绑成功");
        presenter.myBankcard(user.getUserId() + "");
    }

    @Override
    public void cashWithdrawal(String data) {

        Intent intent = new Intent(this, ActivityResultsWithdrawals.class);
        intent.putExtra("price", amountMoney.getText().toString());
        intent.putExtra("lastNumber", lastNumber);
        intent.putExtra("serviceCharge", serviceCharge);
        intent.putExtra("amountmoney", amountmoney);

        startActivity(intent);
        finish();


    }

    @Override
    protected void initWidget() {
        nxt_step = findViewById(R.id.nxt_step);
        bank_card_layout = findViewById(R.id.bank_card_layout);
        card_img = findViewById(R.id.card_img);
        card_name = findViewById(R.id.card_name);
        amountMoney = findViewById(R.id.amountMoney);

        amountMoney.post(new Runnable() {
            @Override
            public void run() {
                amountMoney.requestFocus();
            }
        });

        price_type = findViewById(R.id.price_type);
        allwithdrawals = findViewById(R.id.allwithdrawals);
        price_money = findViewById(R.id.price_money);
        payment_date = findViewById(R.id.payment_date);
        tishi = findViewById(R.id.tishi);

        User user = SpUtils.getObject(ActivityCashWithdrawal.this, "LOGIN");
        openId = user.getOpenId();
        showAnimation();
        amountMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0 && !card_name.getText().toString().equals("")) {
                    nxt_step.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                } else {
                    nxt_step.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.recharge_balance;
    }

    @Override
    protected void initEvent() {
        if (amountmoney.equals("余额")) {
            setIncludeTitle("余额提现");
            tishi.setVisibility(View.GONE);
            price_type.setText("");
        } else if (amountmoney.equals("保证金")) {
            setIncludeTitle("保证金提现");
            tishi.setVisibility(View.GONE);
        }

        nxt_step.setOnClickListener(this);
        bank_card_layout.setOnClickListener(this);
        allwithdrawals.setOnClickListener(this);
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new AmountMoneyPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.myBankcard(user.getUserId() + "");
        MyAPP.getInstance().getApi().getAcount(user.getUserId() + "").enqueue(new Callback(IModel.callback, "获取余额失败") {
            @Override
            public void getSuc(String body) {

                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject returnObject = jsonObject.getJSONObject("returnObject");

                    double balance = returnObject.getDouble("balance");

                    double deposit = returnObject.getDouble("deposit");
                    if (amountmoney.equals("余额")) {

                        price_type.setText("余额￥");
                        price_money.setText(balance + "");
                    } else if (amountmoney.equals("保证金")) {
                        price_type.setText("保证金￥");
                        price_money.setText(deposit + "");
                    }
                    JSONObject params = returnObject.getJSONObject("params");
                    first = params.getString("first");

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    private PopupWindow popupWindow;

    private void addBackground() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }


    private void showAnimation() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.dialog_bankcard, null, false);//引入弹窗布局
        if (popupWindow == null) {
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }


        //设置进出动画
        popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        dialogshowInfo(vPopupWindow);
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showCashWithdrawalAnimation(final String price) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.cash_withdrawal_popubwindow, null, false);//引入弹窗布局
        final PopupWindow cashPopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, false);
        ImageView coles_dialog = vPopupWindow.findViewById(R.id.coles_dialog);
        TextView cash_amount = vPopupWindow.findViewById(R.id.cash_amount);
        TextView service_charge = vPopupWindow.findViewById(R.id.service_charge);
        TextView rate = vPopupWindow.findViewById(R.id.rate);
        cash_amount.setText("￥" + price);
        if (amountmoney.equals("余额")) {
            if ("0".equals(first)) {
                service_charge.setText("首次提现免费");
                rate.setText("无");
                service_charge.setTextColor(getResources().getColor(R.color.color_baozhenjin));
                rate.setTextColor(getResources().getColor(R.color.color_baozhenjin));
                serviceCharge = "首次提现免费";
            } else {
                if (Integer.parseInt(price) <= 100) {
                    service_charge.setText("￥1.00");
                    serviceCharge = "￥1.00";
                } else {
                    service_charge.setText("￥" + DoubleUtil.mul(Double.parseDouble(price), 0.01) + "");
                    serviceCharge = (Double.parseDouble(price) * 0.01) + "";
                }
            }
        } else {
            service_charge.setText("￥0.00");
            serviceCharge = "￥0.00";
            rate.setText("无");
            service_charge.setTextColor(getResources().getColor(R.color.color_baozhenjin));
            rate.setTextColor(getResources().getColor(R.color.color_baozhenjin));

        }

        RelativeLayout confirmationWithdrawals = vPopupWindow.findViewById(R.id.confirmationWithdrawals);
        coles_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cashPopupWindow.dismiss();
            }
        });

        confirmationWithdrawals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }

                if (null == openId || "".equals(openId)) {
                    if (isWeixinAvilible(ActivityCashWithdrawal.this)) {
                        WXLogin();
                    } else {
                        ToastUtils.showTestShort(ActivityCashWithdrawal.this, "您还没有安装微信,请您安装微信之后在进行提现");
                    }
                    return;
                }
                DialogUtil.showProgress(ActivityCashWithdrawal.this);
                if (card_name.getText().toString().equals("微信")) {
                    lastNumber = "微信";
                    if (amountmoney.equals("余额")) {
                        presenter.cashWithdrawal(user.getUserId(), Integer.parseInt(price), bankId, 1, openId);
                    } else if (amountmoney.equals("保证金")) {
                        presenter.cashWithdrawal(user.getUserId(), Integer.parseInt(price), bankId, 0, openId);
                    }

                } else {
                    if (amountmoney.equals("余额")) {
                        presenter.cashWithdrawal(user.getUserId(), Integer.parseInt(price), bankId, 1, null);
                    } else if (amountmoney.equals("保证金")) {
                        presenter.cashWithdrawal(user.getUserId(), Integer.parseInt(price), bankId, 0, null);
                    }
                }
            }
        });
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        cashPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
                aBoolean = true;
            }
        });

        cashPopupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        //引入依附的布局
        View parentView = LayoutInflater.from(this).inflate(R.layout.recharge_balance, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        cashPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        aBoolean = false;
    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        IWXAPI wx77ff4c8528dac183 = WXAPIFactory.createWXAPI(this, "wx77ff4c8528dac183", false);
        wx77ff4c8528dac183.registerApp("wx77ff4c8528dac183");
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        wx77ff4c8528dac183.sendReq(req);
    }

    private boolean aBoolean = true;
    private ListView mListView;
    private BankListAdapter mAdapter;
    private List<MyCardBean.ReturnObjectBean.RowsBean> mDatas;

    private void dialogshowInfo(View vPopupWindow) {
        if (mAdapter == null) {
            mListView = (ListView) vPopupWindow.findViewById(R.id.bank_list);
            add_back_list = (LinearLayout) vPopupWindow.findViewById(R.id.add_back_list);
            colse_add = (ImageView) vPopupWindow.findViewById(R.id.colse_add);

            mDatas = new ArrayList<MyCardBean.ReturnObjectBean.RowsBean>();


        }
        mAdapter = new BankListAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Glide.with(ActivityCashWithdrawal.this).load(URL.getImgPath + mDatas.get(position).getBank().getMiniIcon()).into(card_img);

                if (position == 0) {
                    card_name.setText(mDatas.get(position).getBank().getBankName());
                } else {
                    if (!TextUtils.isEmpty(mDatas.get(position).getCardNo()) && mDatas.get(position).getCardNo().length() >= 4) {
                        lastNumber = mDatas.get(position).getCardNo().substring(mDatas.get(position).getCardNo().length() - 4, mDatas.get(position).getCardNo().length()) + " 尾号" + mDatas.get(position).getBank().getBankName().substring(2, mDatas.get(0).getBank().getBankName().length());

                        card_name.setText(mDatas.get(position).getBank().getBankName().substring(2, mDatas.get(position).getBank().getBankName().length()) + "(" + mDatas.get(position).getCardNo().substring(mDatas.get(position).getCardNo().length() - 4, mDatas.get(position).getCardNo().length()) + ")");

                    }
                }
                if (card_name.getText().toString().equals("微信")) {
                    payment_date.setText("预计2小时内到账");
                } else {
                    payment_date.setText("预计24小时内到账");
                }
                bankId = mDatas.get(position).getId();
                popupWindow.dismiss();
            }
        });


        add_back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(ActivityCashWithdrawal.this, ActivityAddBankCard.class);
                startActivityForResult(intent, 1);
            }
        });
        colse_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onError(String error) {

        ToastUtils.showTestShort(this, error);


    }

}
