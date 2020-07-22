package com.witkey.witkeyhelp.wxapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.AppUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.wight.ImageLoader;
import com.witkey.witkeyhelp.view.impl.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.witkey.witkeyhelp.util.WeixinShareManager.bmpToByteArray;

/**
 * Created by jie on 2020/1/16.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {


    // IWXAPI 是第三方app和微信通信的openapi接口
    private static IWXAPI api;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //   getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        api = WXAPIFactory.createWXAPI(this, "wx77ff4c8528dac183", false);
        api.handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq arg0) {

    }

    @Override
    public void onResp(BaseResp resp) {

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                finish();
                String code = ((SendAuth.Resp) resp).code;
                getAccessToken(code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消

                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //分享拒绝
                break;
        }
    }

    private void getAccessToken(String code) {
        createProgressDialog();
        //获取授权
        StringBuffer loginUrl = new StringBuffer();
        loginUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=")
                .append("wx77ff4c8528dac183")
                .append("&secret=")
                .append("1bb823c8b156fc827914053b58c8256c")
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        //  Log.d("urlurl", loginUrl.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(loginUrl.toString())
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("fan12", "onFailure: ");
                if (this != null && !WXEntryActivity.this.isFinishing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                Log.e("fan12", "onResponse: " + responseInfo);
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(responseInfo);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    User user = SpUtils.getObject(WXEntryActivity.this, "LOGIN");
                    user.setOpenId(openId);
                    SpUtils.putObject(WXEntryActivity.this, "LOGIN", user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //    getUserInfo(access, openId);
            }
        });
    }

    private void getUserInfo(String access, String openid) {
        String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openid;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getUserInfoUrl)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("fan12", "onFailure: ");
                if (this != null && !WXEntryActivity.this.isFinishing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseInfo = response.body().string();
                Log.e("fan123", "onResponse: " + responseInfo);
//                SharedPreferences.Editor editor = getSharedPreferences("userInfo", MODE_PRIVATE).edit();
//                editor.putString("responseInfo", responseInfo);
//                editor.commit();
                finish();

                if (this != null && !WXEntryActivity.this.isFinishing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    private void createProgressDialog() {
        Context mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//转盘
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("登录中，请稍后");
        if (this != null && !this.isFinishing()) {
            mProgressDialog.show();
        }


    }
}
