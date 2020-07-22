package com.witkey.witkeyhelp.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.tencent.connect.avatar.ImageActivity;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.event.LoginEvent;
import com.witkey.witkeyhelp.event.ToastEvent;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.WXPayUtils;
import com.witkey.witkeyhelp.view.impl.ActivityLostFound;
import com.witkey.witkeyhelp.view.impl.fragment.HomeFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jie on 2020/1/16.
 */

public class PopWinShare extends PopupWindow implements View.OnClickListener {


    private Tencent mTencent;
    private Activity activity;
    private int tag;
    private String messgae;
    private String imgurl;
    private String invitationCode;

    private String title;
    private int type;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PopWinShare(Activity paramActivity, String invitationCode) {
        super(paramActivity);
        //窗口布局
        tag = 2;
        activity = paramActivity;
        this.invitationCode = invitationCode;
        View share_popub = LayoutInflater.from(paramActivity).inflate(R.layout.share_popub, null);
        LinearLayout weChat = share_popub.findViewById(R.id.weChat);
        LinearLayout echat_Moments = share_popub.findViewById(R.id.echat_Moments);
        LinearLayout qq = share_popub.findViewById(R.id.qq);
        LinearLayout qq_zone = share_popub.findViewById(R.id.qq_zone);
        RelativeLayout cancl = share_popub.findViewById(R.id.cancl);
        weChat.setOnClickListener(this);
        echat_Moments.setOnClickListener(this);
        qq.setOnClickListener(this);
        qq_zone.setOnClickListener(this);
        cancl.setOnClickListener(this);
        setContentView(share_popub);
        //设置宽度
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        //设置高度
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.anim_pop_bottombar);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        setBackgroundDrawable(dw);
        setFocusable(true);
        mTencent = Tencent.createInstance("101844785", paramActivity);
    }

    private int popupWidth;
    private int popupHeight;
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public PopWinShare(Activity paramActivity, String messgae, View v) {
//        super(paramActivity);
//        //窗口布局
//
//        activity = paramActivity;
//        View share_popub = LayoutInflater.from(paramActivity).inflate(R.layout.dia_consult_hint, null,false);
//        //获取自身的长宽高
//        share_popub.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        popupHeight = share_popub.getMeasuredHeight();
//        popupWidth = share_popub.getMeasuredWidth();
//
//        setContentView(share_popub);
//        //设置宽度
//        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
//        //设置高度
//        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
//        //设置显示隐藏动画
//        setAnimationStyle(R.style.anim_pop_bottombar);
//        ColorDrawable dw = new ColorDrawable(0xb000000);
//        setBackgroundDrawable(dw);
//        setFocusable(true);
//        showUp(v);
//    }

    /**
     * 设置显示在v上方(以v的左边距为开始位置)
     *
     * @param v
     */
    public void showUp(View v) {
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //在控件上方显示
        showAtLocation(v, Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, location[1] - popupHeight);
    }

    public PopWinShare(int type, Activity paramActivity, String title, String messgae, String imgurl) {
        super(paramActivity);
        //窗口布局
        this.type = type;
        tag = 1;
        this.messgae = messgae;
        this.imgurl = imgurl;
        activity = paramActivity;
        this.imgurl = imgurl;
        this.title = title;
        View share_popub = LayoutInflater.from(paramActivity).inflate(R.layout.share_popub, null, false);
        LinearLayout weChat = share_popub.findViewById(R.id.weChat);
        LinearLayout echat_Moments = share_popub.findViewById(R.id.echat_Moments);
        LinearLayout qq = share_popub.findViewById(R.id.qq);
        LinearLayout qq_zone = share_popub.findViewById(R.id.qq_zone);
        RelativeLayout cancl = share_popub.findViewById(R.id.cancl);
        weChat.setOnClickListener(this);
        echat_Moments.setOnClickListener(this);
        qq.setOnClickListener(this);
        qq_zone.setOnClickListener(this);
        cancl.setOnClickListener(this);
        setContentView(share_popub);
        //设置宽度
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        //设置高度
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.anim_pop_bottombar);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        setBackgroundDrawable(dw);
        setFocusable(true);
        mTencent = Tencent.createInstance("101844785", paramActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weChat:
                EventBus.getDefault().post(new ToastEvent("分享成功"));
                if (tag == 1) {
                    WXPayUtils.load(activity, 0, messgae, imgurl, title);

                } else if (tag == 2) {


                    Bitmap bitmap = measureSize(activity, "");
                    WXPayUtils.WXsharePic(activity, "", true, bitmap, null);
                }
                dismiss();
                if (type == 0) {
                    MobclickAgent.onEvent(activity, "InvitationWeixin");
                } else {

                    MobclickAgent.onEvent(activity, "taskWeixin");
                }
                break;
            case R.id.echat_Moments:
                EventBus.getDefault().post(new ToastEvent("分享成功"));
                if (tag == 1) {
                    WXPayUtils.load(activity, 1, messgae, imgurl, title);
                } else {
                    Bitmap bitmap = measureSize(activity, "");

                    WXPayUtils.WXsharePic(activity, "", false, bitmap, null);
                }

                dismiss();
                if (type == 0) {
                    MobclickAgent.onEvent(activity, "InvitationFriends");
                } else {

                    MobclickAgent.onEvent(activity, "taskFriends");
                }
                break;
            case R.id.qq:
                EventBus.getDefault().post(new ToastEvent("分享成功"));
                if (tag == 1) {
                    qqShare();

                } else {


                    Bitmap bitmap1 = measureSize(activity, "");
                    onClickShare(saveBitmap(activity, bitmap1));

                }


                dismiss();
                if (type == 0) {
                    MobclickAgent.onEvent(activity, "InvitationQQ");
                } else {

                    MobclickAgent.onEvent(activity, "taskQQ");
                }
                break;
            case R.id.qq_zone:
                EventBus.getDefault().post(new ToastEvent("分享成功"));
                if (tag == 1) {
                    qqQzoneShare();
                } else {
                    Bitmap bitmap1 = measureSize(activity, "");
                    onClickShareKongJian(saveBitmap(activity, bitmap1));
                }
                dismiss();
                if (type == 0) {
                    MobclickAgent.onEvent(activity, "InvitationQQqSpace");
                } else {

                    MobclickAgent.onEvent(activity, "taskQSpace");
                }
                break;
            case R.id.cancl:
                dismiss();
                break;
        }
    }

    public void qqShare() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, messgae);//要分享的内容摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, imgurl);//内容地址
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, URL.getImgPath+"/logo.png");//分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "酷爱帮");//应用名称
        mTencent.shareToQQ(activity, params, new ShareUiListener());
    }

    /**
     * 分享到QQ空间
     *
     * @param
     */
    public void qqQzoneShare() {
        int QzoneType = QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE;
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneType);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//分享标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, messgae);//分享的内容摘要
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, imgurl);//分享的链接
        //分享的图片, 以ArrayList<String>的类型传入，以便支持多张图片（注：图片最多支持9张图片，多余的图片会被丢弃）
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add(URL.getImgPath+"/logo.png");//添加一个图片地址
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);//分享的图片URL
        mTencent.shareToQzone(activity, params, new ShareUiListener());
    }

    private void onClickShare(String path) {
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "酷爱帮");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(activity, params, new ShareUiListener());
    }

    private void onClickShareKongJian(String path) {
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "酷爱帮");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(activity, params, new ShareUiListener());
    }

    /**
     * 自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {


        }

        @Override
        public void onError(UiError uiError) {
            //分享失败
        }

        @Override
        public void onCancel() {
            //分享取消
        }
    }


    public Bitmap measureSize(Activity activity, String url) {
        //将布局转化成view对象
        View viewBitmap = LayoutInflater.from(activity).inflate(R.layout.fenxiangcht, null);
        View viewById = viewBitmap.findViewById(R.id.iv_long_img);
        TextView textView = viewBitmap.findViewById(R.id.invitationCode);
        textView.setText(invitationCode);
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
        return layoutView(viewById, width, height, url, activity);
    }

    public Bitmap layoutView(final View viewBitmap, int width, int height, String url, Activity activity) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        viewBitmap.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);

        viewBitmap.measure(measuredWidth, measuredHeight);
        viewBitmap.layout(0, 0, viewBitmap.getMeasuredWidth(), viewBitmap.getMeasuredHeight());
        Bitmap bitmap = viewSaveToImage(viewBitmap);
        return bitmap;
    }

    private Bitmap viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = viewConversionBitmap(view);

//        if (mBitmapDoneListener != null){
//            mBitmapDoneListener.bitmapDone(cachebmp);
//        }

        view.destroyDrawingCache();

        return cachebmp;
    }

    public Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

//        if (w <=0 || h<=0) {
//            Toast.makeText(this, "view's width or height are <= 0", Toast.LENGTH_SHORT).show();
//            return null;
//        }


        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

}
