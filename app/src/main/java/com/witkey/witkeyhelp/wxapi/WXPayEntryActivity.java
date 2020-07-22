package com.witkey.witkeyhelp.wxapi;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

/**
 * Created by jie on 2019/12/2.
 */

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    private String app_id = "wx77ff4c8528dac183";//微信开发后台申请的app_id


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (mWxPayinterace != null) {
            mWxPayinterace.Wxpay(resp.errCode);
        }


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            switch (resp.errCode) {
                case 0://支付成功

                    Log.e(TAG, "onResp: resp.errCode = 0   支付成功");
                    finish();
                    break;
                case -1://错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                    finish();
                    Log.e(TAG, "onResp: resp.errCode = -1  支付错误");
                    break;
                case -2://用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。
                    Log.e(TAG, "onResp: resp.errCode = -2  用户取消");
                    finish();
                    break;

            }


        }
    }

    @Override
    protected void onCreateActivity() {

        api = WXAPIFactory.createWXAPI(this, app_id);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    public interface WxPayinterace {
        void Wxpay(int code);
    }

    private static WxPayinterace mWxPayinterace;

    public static void setWxPayinterace(WxPayinterace wxPayinterace) {

        mWxPayinterace = wxPayinterace;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.dismissProgress();
    }
}
