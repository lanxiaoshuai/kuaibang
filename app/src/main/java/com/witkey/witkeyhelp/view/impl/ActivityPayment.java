package com.witkey.witkeyhelp.view.impl;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.PayInfoBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.DoubleUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.WXPayUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by jie on 2020/4/13.
 */

public class ActivityPayment extends BaseActivity implements View.OnClickListener {


    private String content;
    private String title;
    private String number;
    private String putArea;
    private String ownlocation;
    private String putScope;
    private String longitude;
    private String latitude;
    private String putLocation;
    private int regiontypr;
    private int type;
    private List<ReleasePhotoBean> photoList;
    private boolean isMoneyTypeRmb;
    private TextView amountMoney;
    private ImageView payment_icon;
    private TextView numbertext;
    private TextView single_reward_cs;
    private TextView platform_fee;
    private ImageView moneyreward_icon;
    private ImageView platform_fee_icon;
    private RelativeLayout balancePayment;
    private RelativeLayout weChatPayment;
    private Button btn_payment;
    private CheckBox cb_is;
    private CheckBox cb_is_weixin;
    private boolean aBoolean;
    private LinearLayout linearLayout;
    private String out_trade_no;
    private WXPayUtils wxPayUtils;

    private boolean typeBoolean;
    private String imaurl;
    private UICustomDialog2 dialog2;
    private List mlist;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_advertisement);
        setIncludeTitle("支付订单");
        initView();
        initOnclick();
        content = getIntent().getStringExtra("content");

        title = getIntent().getStringExtra("title");

        photoList = (List<ReleasePhotoBean>) getIntent().getSerializableExtra("imgurl");
        number = getIntent().getStringExtra("putNum");

        putArea = getIntent().getStringExtra("putArea");

        ownlocation = getIntent().getStringExtra("Ownlocation");


        putScope = getIntent().getStringExtra("putScope");

        longitude = getIntent().getStringExtra("longitude");

        latitude = getIntent().getStringExtra("latitude");

        putLocation = getIntent().getStringExtra("putLocation");

        regiontypr = getIntent().getIntExtra("regiontypr", 0);
        isMoneyTypeRmb = getIntent().getBooleanExtra("payment", true);
        numbertext.setText(number);

        if (isMoneyTypeRmb) {
            amountMoney.setText("￥" + DoubleUtil.add(DoubleUtil.mul(Integer.parseInt(number), 0.10), DoubleUtil.mul(DoubleUtil.mul(Integer.parseInt(number), 0.10), 0.10)));
            payment_icon.setVisibility(View.GONE);
            moneyreward_icon.setVisibility(View.GONE);
            platform_fee_icon.setVisibility(View.GONE);
            single_reward_cs.setText("￥0.10");
            platform_fee.setText(DoubleUtil.mul(DoubleUtil.mul(Integer.parseInt(number), 0.10), 0.10) + "");
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            amountMoney.setText((DoubleUtil.add(Integer.parseInt(number), DoubleUtil.mul(Integer.parseInt(number), 0.1))) + "");
            payment_icon.setVisibility(View.VISIBLE);
            moneyreward_icon.setVisibility(View.VISIBLE);
            platform_fee_icon.setVisibility(View.VISIBLE);
            single_reward_cs.setText("￥1.00");
            platform_fee.setText("￥" + DoubleUtil.mul(Integer.parseInt(number), 0.1) + "");
            linearLayout.setVisibility(View.GONE);

        }
        ImageView ivBarLeft = findViewById(R.id.ivBarLeft);
        ivBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(-1);
                finish();
            }
        });
    }

    private void initOnclick() {
        balancePayment.setOnClickListener(this);
        weChatPayment.setOnClickListener(this);
        btn_payment.setOnClickListener(this);
    }

    private void initView() {
        amountMoney = findViewById(R.id.amountMoney);
        numbertext = findViewById(R.id.number);
        single_reward_cs = findViewById(R.id.single_reward_cs);
        platform_fee = findViewById(R.id.platform_fee);
        payment_icon = findViewById(R.id.payment_icon);
        moneyreward_icon = findViewById(R.id.moneyreward_icon);
        platform_fee_icon = findViewById(R.id.platform_fee_icon);

        balancePayment = findViewById(R.id.balancePayment);
        weChatPayment = findViewById(R.id.weChatPayment);

        btn_payment = findViewById(R.id.btn_payment);
        linearLayout = findViewById(R.id.rmb);
        cb_is = findViewById(R.id.cb_is);
        cb_is_weixin = findViewById(R.id.cb_is_weixin);
        wxPayUtils = new WXPayUtils(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weChatPayment:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                aBoolean = true;
                cb_is.setChecked(false);
                cb_is_weixin.setChecked(true);
                break;
            case R.id.balancePayment:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                aBoolean = false;
                cb_is.setChecked(true);
                cb_is_weixin.setChecked(false);
                break;
            case R.id.btn_payment:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                dialog2 = new UICustomDialog2(ActivityPayment.this, "确认支付", "3");
                dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.hide();
                    }
                });
                dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (photoList.size() == 0) {

                            if (isMoneyTypeRmb) {
                                if (aBoolean) {
                                    loadDataFabu("2", "");
                                } else {
                                    loadDataFabu("1", "");
                                }

                            } else {
                                loadDataFabu("0", "");
                            }
                        } else {
                            imgurl();
                        }
                        dialog2.hide();

                    }
                });
                dialog2.show();


                break;
        }
    }

    private void imgurl() {
        DialogUtil.showProgress(this);

        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath() + "/notes";
        File file = new File(path);
        if (!file.exists())
            file.mkdir();

        mlist = new ArrayList<>();
        mlist.clear();
        for (int i = 0; i < photoList.size(); i++) {
            mlist.add(photoList.get(i).getUrl());
        }
        photoList.clear();
        Luban.with(this)
                .load(mlist)
                .ignoreBy(100)
                .setTargetDir(file.getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(final File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        // imagePath= file.getPath();


                        photoList.add(new ReleasePhotoBean(file.getPath(), true));
                        if (photoList.size() == mlist.size()) {
                            List<File> files = new ArrayList<>();
                            DialogUtil.showProgress(ActivityPayment.this);
                            List<MultipartBody.Part> parts = new ArrayList<>();
                            for (int i = 0; i < photoList.size(); i++) {
                                files.add(new File(photoList.get(i).getUrl()));
                                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
                                MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(i).getName(), requestBody);
                                parts.add(part);
                            }
                            MyAPP.getInstance().getApi().upLoadImg(parts).enqueue(new Callback(IModel.callback, "上传失败") {


                                @Override
                                public void getSuc(String body) {
                                    imaurl = JSONUtil.getValueToString(body, "returnObject");

                                    if (isMoneyTypeRmb) {
                                        if (aBoolean) {
                                            loadDataFabu("2", imaurl);
                                        } else {
                                            loadDataFabu("1", imaurl);
                                        }

                                    } else {
                                        loadDataFabu("0", imaurl);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用

                    }
                }).launch();


    }

    private void loadDataFabu(String amountType, String imaurl) {
        DialogUtil.showProgress(this);
        double dou = 0.00;
        if (isMoneyTypeRmb) {
            dou = DoubleUtil.add(DoubleUtil.mul(Integer.parseInt(number), 0.10), DoubleUtil.mul(Integer.parseInt(number) * 0.10, 0.10));
        } else {
            dou = DoubleUtil.add(Integer.parseInt(number), DoubleUtil.mul(Integer.parseInt(number), 0.1));
        }
        MyAPP.getInstance().getApi().advertisingAdd(user.getUserId(), amountType, content, title.toString(), imaurl, number, putArea, ownlocation, putScope, dou, longitude + "", latitude + "", putLocation, regiontypr).enqueue(new Callback(IModel.callback, "发布广告失败") {
            @Override
            public void getSuc(String body) {

                if (aBoolean) {
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONObject returnObject = jsonObject.getJSONObject("returnObject");
                        WeChatPayment(returnObject.getInt("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showTestShort(ActivityPayment.this, "发布成功");
                    DialogUtil.dismissProgress();

                    setResult(1314);
                    finish();
                }

            }
        });
    }

    private void WeChatPayment(int id) {
        Map<String, String> map = new HashMap<>();
        map.put("adverId", id + "");
        double add = DoubleUtil.add(DoubleUtil.mul(Integer.parseInt(number), 0.10), DoubleUtil.mul(Integer.parseInt(number) * 0.10, 0.10));
        double mul = DoubleUtil.mul(add, 100);
        MyAPP.getInstance().getApi().wxAppletPay("酷爱帮", new Double(mul).intValue() + "" + "", "==", map.toString()).enqueue(new Callback(IModel.callback, "微信支付失败") {
            @Override
            public void getSuc(String body) {

                DialogUtil.dismissProgress();

                PayInfoBean payInfoBean = JsonUtils.getBeanFromJson(body, PayInfoBean.class);
                out_trade_no = payInfoBean.getReturnObject().getOut_trade_no();
                wxPayUtils.WxPay(payInfoBean, ActivityPayment.this);
                typeBoolean = true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (typeBoolean) {
            MyAPP.getInstance().getApi().apiwxpayresult(out_trade_no).enqueue(new Callback(IModel.callback, "微信支付查询结果失败") {
                @Override
                public void getSuc(String body) {
                    DialogUtil.dismissProgress();
                    ToastUtils.showTestShort(ActivityPayment.this, "发布成功");
                    setResult(1314);
                    finish();

                }
            });
        }
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //   Toast.makeText(this, "按下了back键   onKeyDown()", Toast.LENGTH_SHORT).show();
            setResult(-1);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
