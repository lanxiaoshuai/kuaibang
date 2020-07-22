package com.witkey.witkeyhelp.view.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.icu.math.BigDecimal;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PayInfoBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.dialog.ReceiptSucDia;
import com.witkey.witkeyhelp.dialog.TaskDetailsDialog;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;

import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IConsultPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.ConsultPresenterImpl;
import com.witkey.witkeyhelp.util.DoubleUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.WXPayUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IConsultView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.jpush.im.android.api.model.UserInfo.Field.region;

/**
 * Created by jie on 2019/12/13.
 */

public class Zhifu extends PermissionActivity implements View.OnClickListener, IConsultView {

    private IConsultPresenter presenter;
    private CheckBox cb_is;
    private CheckBox cb_is_weixin;
    private boolean aBoolean;
    private RelativeLayout balancePayment;
    private RelativeLayout weChatPayment;
    private MissionBean missionBean = new MissionBean();
    private TextView help_name;
    private TextView amountMoney;
    private boolean payment;
    private Button btn_payment;
    private WXPayUtils wxPayUtils;
    private boolean typeBoolean;
    private String out_trade_no;
    private List<ReleasePhotoBean> photoList;
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
    private PopupWindow cashPopupWindow;
    private List mlist;
    private UICustomDialog2 dialog2;

    @Override
    protected void initWidget() {

        balancePayment = findViewById(R.id.balancePayment);
        cb_is = findViewById(R.id.cb_is);
        cb_is_weixin = findViewById(R.id.cb_is_weixin);
        weChatPayment = findViewById(R.id.weChatPayment);
        help_name = findViewById(R.id.help_name);
        amountMoney = findViewById(R.id.amountMoney);
        btn_payment = findViewById(R.id.btn_payment);
        findViewById(R.id.tvBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            payment = getIntent().getBooleanExtra("payment", false);

            missionBean = (MissionBean) getIntent().getSerializableExtra("release");
            photoList = (List<ReleasePhotoBean>) getIntent().getSerializableExtra("imgurl");
        } else if (type == 1) {
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


        }


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_zhifu;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (typeBoolean) {
            presenter.apiwxpayResult(out_trade_no);
        }
    }

    @Override
    protected void initEvent() {
        balancePayment.setOnClickListener(this);
        weChatPayment.setOnClickListener(this);
        btn_payment.setOnClickListener(this);
        initData();
    }


    private void initData() {

        amountMoney.setText(missionBean.getPrice() + "");


        String businessType = missionBean.getBusinessType();
        if ("1".equals(businessType)) {
            help_name.setText("咨询");
        } else if ("2".equals(businessType)) {
            help_name.setText("帮忙");

        }
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
        setIncludeTitle("支付");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weChatPayment:
                aBoolean = true;
                cb_is.setChecked(false);
                cb_is_weixin.setChecked(true);
                break;
            case R.id.balancePayment:
                aBoolean = false;
                cb_is.setChecked(true);
                cb_is_weixin.setChecked(false);
                break;
            case R.id.btn_payment:
                dialog2 = new UICustomDialog2(this, "确认支付", "3");
                dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.hide();
                    }
                });
                dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (aBoolean) {

                            if (isWeixinAvilible(Zhifu.this)) {

                                wxPayUtils = new WXPayUtils(Zhifu.this);
                                missionBean.setPayType(1);
                                if (photoList.size() == 0) {
                                    DialogUtil.showProgress(Zhifu.this);


                                    presenter.saveConsult(missionBean);

                                } else {

                                    for (int i = 0; i < photoList.size(); i++) {
                                        if (!photoList.get(i).isaBoolean()) {
                                            photoList.remove(i);
                                        }
                                    }


                                    DialogUtil.showProgress(Zhifu.this);
                                    presenter.saveImagurlConsult(photoList);


                                }
                            } else {
                                ToastUtils.showTestShort(Zhifu.this, "您还没有安装微信,请您安装微信之后在进行支付");
                            }
                        } else {
                            missionBean.setPayType(0);
                            if (photoList.size() == 0) {
                                DialogUtil.showProgress(Zhifu.this);
                                presenter.saveConsult(missionBean);
                            } else {

                                for (int i = 0; i < photoList.size(); i++) {
                                    if (!photoList.get(i).isaBoolean()) {
                                        photoList.remove(i);
                                    }
                                }

                                DialogUtil.showProgress(Zhifu.this);

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
                                Luban.with(Zhifu.this)
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
                                                Log.e("qqqeee", file.getPath());

                                                photoList.add(new ReleasePhotoBean(file.getPath(), true));
                                                if (photoList.size() == mlist.size()) {
                                                    presenter.saveImagurlConsult(photoList);
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                // TODO 当压缩过程出现问题时调用
                                                Log.e("qqq", e.getMessage() + "    aaaaaaa");
                                            }
                                        }).launch();

                            }

                        }
                        dialog2.hide();

                    }
                });
                dialog2.show();

                break;
            default:
                break;
        }
    }

    @Override
    public void saveSuc(String businessId) {
        if (aBoolean) {
            String price = missionBean.getPrice();
            DialogUtil.showProgress(this);
            Map<String, String> map = new HashMap<>();
            map.put("businessId", businessId);
            presenter.wxAppletPay("酷爱帮", Integer.parseInt(price) * 100 + "", "==", map.toString());

        } else {
            Toast("发布成功", 1);

            setResult(100);
            finish();
        }

    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    public void publishSuc() {

    }

    @Override
    public void saveImgSuc(String imgurl) {

        DialogUtil.showProgress(mActivity);

        missionBean.setBusinessImgUrl(imgurl);
        presenter.saveConsult(missionBean);


    }

    private ReceiptSucDia receiptSucDia;

    @Override
    public void receiptSuc() {
        receiptSucDia = new ReceiptSucDia(mActivity);
        addTooBackground();
        receiptSucDia.show();
    }

    @Override
    public void wxAppletPay(PayInfoBean payInfoBean) {
        out_trade_no = payInfoBean.getReturnObject().getOut_trade_no();
        wxPayUtils.WxPay(payInfoBean, this);
        typeBoolean = true;
    }

    @Override
    public void wxAppletPayResult(String result) {
        //    Log.e("tag", "支付成功");
        ToastUtils.showTestShort(this, "支付成功");

        setResult(100);
        finish();
    }

    @Override
    public void wxAppletPayError(String errorresult) {
        typeBoolean = false;

        ToastUtils.showTestShort(this, "支付失败");


    }

    private void addTooBackground() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        receiptSucDia.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    protected int getPermissionType() {
        return TYPE_PIC;
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

    @Override
    public void onError(String error) {


        if (error.contains("保证金不足")) {
            TaskDetailsDialog detailsDialog = new TaskDetailsDialog(this, 1, 0);
            addThereBackground(detailsDialog);
            detailsDialog.show();
        } else if (error.contains("余额不足")) {

            //   Log.e("tag", "余额不足，请用微信支付");
            ToastUtils.showTestShort(this, "余额不足，请用微信支付");
        } else {
            ToastUtils.showTestShort(this, error);
        }

    }

    private void addThereBackground(TaskDetailsDialog taskDetailsDialog) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样
        taskDetailsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.dismissProgress();
    }


}
