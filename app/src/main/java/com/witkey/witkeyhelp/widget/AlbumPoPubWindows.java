package com.witkey.witkeyhelp.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;


import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.util.ImgUtil;


/**
 * Created by jie on 2020/4/1.
 */
public class AlbumPoPubWindows extends PopupWindow {
    private Context context;
    private int number;


    public AlbumPoPubWindows(Context context, int number) {
        super(context);
        this.context = context;
        this.number = number;
        initView();
    }

    private void initView() {


        View inflate = LayoutInflater.from(context).inflate(R.layout.mission_accomplished, null, false);
        inflate.findViewById(R.id.finish_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lacksPermissions(context, permissionsREAD)) {//读写权限没开启
                    ActivityCompat.requestPermissions((Activity) context, permissionsREAD, 0);
                } else {
                    ImgUtil.autoObtainCameraPermission((AppCompatActivity) context);

                }
                dismiss();
            }
        });
        inflate.findViewById(R.id.mobile_Album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lacksPermissions(context, permissionsREAD)) {//读写权限没开启

                    ActivityCompat.requestPermissions((Activity) context, permissionsREAD, 1);
                } else {

                    showAlbum(number);

                }
                dismiss();
//             ImageSelector.builder()
//                        .useCamera(true) // 设置是否使用拍照
//                        .setSingle(false)  //设置是否单选
//                        .setMaxSelectCount(number) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .canPreview(true) //是否可以预览图片，默认为true
//                        .start((Activity) context, 10000); // 打开相册


            }

        });
        inflate.findViewById(R.id.claner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(inflate);
        //设置宽度
        setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        //设置高度
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(R.style.anim_pop_bottombar);
        ColorDrawable dw = new ColorDrawable(0xb000000);
        setBackgroundDrawable(dw);
        setFocusable(true);
    }

    private void showAlbum(int number) {
        //参数很多，根据需要添加
        PictureSelector.create((Activity) context)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(number)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选PictureConfig.MULTIPLE : PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.selectionMedia(selectList)// 是否传入已选图片
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
                //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .rotateEnabled(false) // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(10000);//结果回调onActivityResult code
    }


    /**
     * 照相权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREAD = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };



    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public static boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {

        for (String permission : permissionsREAD) {
            if (lacksPermission(mContexts, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

}
