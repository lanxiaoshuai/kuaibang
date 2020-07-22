package com.witkey.witkeyhelp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.PayInfoBean;
import com.witkey.witkeyhelp.constant.Constants;
import com.witkey.witkeyhelp.view.impl.fragment.HomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.witkey.witkeyhelp.util.WeixinShareManager.bmpToByteArray;


public class WXPayUtils {
    public static Context context;

    public WXPayUtils(Context context) {
        WXPayUtils.context = context;
    }

    public void WxPay(PayInfoBean list, Context context) {
        //保存交易ID
        PayInfoBean.ReturnObjectBean returnObject = list.getReturnObject();

        //   returnObject.get

        //  SpUtils.putString(context, Constants.Wx_out_trade_no, list.get(0).getOut_trade_no());

//			2、注册APPID
//			商户APP工程中引入微信JAR包，调用API前，需要先向微信注册您的APPID，代码如下：
        final IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
//			// 将该app注册到微信
        api.registerApp(Constants.APP_ID);
//			3、调起支付
//			商户服务器生成支付订单，先调用统一下单API(详见第7节)生成预付单，获取到prepay_id后将参数再次签名传输给APP发起支付。以下是调起微信支付的关键代码：
        PayReq request = new PayReq();
        request.appId = returnObject.getAppid();
        request.partnerId = returnObject.getPartnerid();
        request.prepayId = returnObject.getPrepayid();
        request.packageValue = returnObject.getPackageX();
        request.nonceStr = returnObject.getNoncestr();
        request.timeStamp = returnObject.getTimestamp();
        request.sign = returnObject.getSign();
        api.sendReq(request);

    }

    public static IWXAPI api;

    public static void load(Activity activity, int type,String message,String url,String title) {

        final IWXAPI api = WXAPIFactory.createWXAPI(activity, Constants.APP_ID, false);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = message;
        Bitmap thumb = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.applicationicon);
        if (thumb != null) {
            Bitmap mBp = Bitmap.createScaledBitmap(thumb, 120, 120, true);
            mBp.recycle();
            msg.thumbData = bmpToByteArray(thumb, true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();    //创建一个请求对象
        req.message = msg;

        if (type == 0) {
            req.scene = SendMessageToWX.Req.WXSceneSession;   //设置发送给朋友
        } else if (type == 1) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;    //设置发送到朋友圈
        }

        req.transaction = "设置一个tag";  //用于在回调中区分是哪个分享请求
         boolean successed = api.sendReq(req);   //如果调用成功微信,会返回true

    }

    /**
     * 微信文字分享
     *
     * @param text    分享内容
     * @param context
     */
    public static void WXTextShare(String text, Context context) {

        //初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = text;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        //分享到朋友圈：req.scene = SendMessageToWX.Req.WXSceneTimeline;
        //分享到好友会话：req.scene = SendMessageToWX.Req.WXSceneSession;
        //添加到微信收藏：req.scene = SendMessageToWX.Req.WXSceneFavorite;

        //调用api接口发送数据到微信

        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
        }
        api.sendReq(req);
    }


    public static void WXsharePic(Activity activity,String transaction, final boolean isSession, Bitmap bitmap, String path) {
        //初始化WXImageObject和WXMediaMessage对象
        final IWXAPI api = WXAPIFactory.createWXAPI(activity, Constants.APP_ID, false);
        WXImageObject imageObject;
        if (!StringUtil.isEmpty(path)) {
            imageObject = new WXImageObject();
            imageObject.setImagePath(path);
        } else {
            imageObject = new WXImageObject(bitmap);
        }
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;//设置缩略图
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        bitmap.recycle();
        msg.thumbData = getBitmapByte(scaledBitmap);
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction + Long.toString(System.currentTimeMillis());
        req.message = msg;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = isSession ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        //调用api接口发送数据到微信
        api.sendReq(req);
    }

    public static byte[] getBitmapByte(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

}