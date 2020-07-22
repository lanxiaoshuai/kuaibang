package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.ReleasePhotoAdapter;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IMeFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MeFragPresenterImpl;
import com.witkey.witkeyhelp.util.BitmapUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.RealPathFromUriUtils;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMeFragView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by jie on 2019/12/16.
 */

public class UserInfoActivity extends InitPresenterBaseActivity implements View.OnClickListener, IMeFragView {

    private IMeFragPresenter presenter;
    private RelativeLayout relativeLayout;
    private RelativeLayout relay_user_name;
    private String imgName = "consult";//图片名字

    private String imagePath = "";


    private IModel.AsyncCallback callback;
    private String businessImgUrl;
    private TextView username;
    private AlbumPoPubWindows popWinShare;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relay_user_name:

                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                Intent intent = new Intent(this, UserInfoNameActivity.class);
                startActivity(intent);
                break;
            case R.id.user_img:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                extnStep();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "requestCode" + requestCode + "resultCode" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File sd = Environment.getExternalStorageDirectory();
                String path = sd.getPath() + "/notes";
                File file = new File(path);
                if (!file.exists())
                    file.mkdir();
                imagePath = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                Luban.with(this)
                        .load(imagePath)
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
                                imagePath = file.getPath();
                                imgurl();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();

            } else if (requestCode == 2) {
                imagePath = BitmapUtil.compressImage(imagePath);
                imagePath = Contacts.imgPath + imgName;


            } else if (requestCode == 273) {
                File sd = Environment.getExternalStorageDirectory();
                String path = sd.getPath() + "/notes";
                File file = new File(path);
                if (!file.exists())
                    file.mkdir();
                imagePath = ImgUtil.fileUri.getAbsolutePath();
                Luban.with(this)
                        .load(imagePath)
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
                                imagePath = file.getPath();
                                imgurl();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();

            } else if (requestCode == 10000) {
                File sd = Environment.getExternalStorageDirectory();
                String path = sd.getPath() + "/notes";
                File file = new File(path);
                if (!file.exists())
                    file.mkdir();

                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
//
                imagePath = images.get(0).getPath();

                Luban.with(this)
                        .load(imagePath)
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
                                imagePath = file.getPath();
                                imgurl();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();


            }
        } else {
            //data.getStringExtra()
            imagePath = "";

        }

    }

    private void imgurl() {
        MobclickAgent.onEvent(this, "changeAvatar");
        List<File> files = new ArrayList<>();

        List<MultipartBody.Part> parts = new ArrayList<>();

        files.add(new File(imagePath));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(0));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(0).getName(), requestBody);
        parts.add(part);


        callback = new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {

                presenter.updateUserInfo(user.getUserId(), user.getRealName(), businessImgUrl, 0 + "");
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
            }
        };
        MyAPP.getInstance().getApi().upLoadImg(parts).enqueue(new Callback(callback, "上传失败") {
            @Override
            public void getSuc(String body) {
                businessImgUrl = JSONUtil.getValueToString(body, "returnObject");
                callback.onSuccess(businessImgUrl);
            }
        });

    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    public void showUser(User user) {

    }

    @Override
    public void showAcount(Acount data) {

    }

    @Override
    public void showDeductionData(String data) {

    }

    @Override
    public void updateUserInfo(String data) {

        user.setHeadUrl(businessImgUrl);
        ToastUtils.showTestShort(this, "修改成功");
        SpUtils.putObject(this, "LOGIN", user);

        new Thread(new Runnable() {
            @Override
            public void run() {
                File mFile = null;
                try {
                    mFile = Glide.with(UserInfoActivity.this).asFile().load(URL.getImgPath + user.getHeadUrl()).submit().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                JMessageClient.updateUserAvatar(mFile, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {

                        if (responseCode == 0) {
                            Log.e("tag", "修改成功");
                        } else {
                            Log.e("tag", "修改失败");
                        }
                    }
                });
            }
        }).start();


        finish();
    }

    @Override
    protected void initWidget() {
        relativeLayout = findViewById(R.id.user_img);

        relay_user_name = findViewById(R.id.relay_user_name);
        username = findViewById(R.id.username);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = SpUtils.getObject(this, "LOGIN");
        username.setText(user.getRealName());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initEvent() {
        relativeLayout.setOnClickListener(this);
        relay_user_name.setOnClickListener(this);
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MeFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        setIncludeTitle("个人信息");
    }

    /**
     * 读写权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREAD = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

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


    private void extnStep() {

        popWinShare = new AlbumPoPubWindows(this, 1);


        //引入依附的布局
        View parentView = LayoutInflater.from(this).inflate(R.layout.activity_consult, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        final Activity context = this;

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        context.getWindow().setAttributes(lp);
        //dismiss时恢复原样
        popWinShare.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().setAttributes(lp);
            }
        });
        popWinShare.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean uthority = false;
        if (requestCode == 0) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {

                    // 权限被拒绝，弹出dialog 提示去开启权限
                    //         showPermissions();
                    uthority = true;
                    break;
                }

            }
            if (!uthority) {
                ImgUtil.autoObtainCameraPermission(this);
            }
        } else if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {

                    // 权限被拒绝，弹出dialog 提示去开启权限
                    //         showPermissions();
                    uthority = true;
                    break;
                }

            }
            if (uthority == false) {
                showAlbum(1);
            }

        }

    }


    private void showAlbum(int number) {
        Log.e("tag", "aaaaaa" + number);
        //参数很多，根据需要添加
        PictureSelector.create(this)
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

}
