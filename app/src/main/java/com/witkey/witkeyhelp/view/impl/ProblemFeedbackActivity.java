package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.ReleasePhotoAdapter;
import com.witkey.witkeyhelp.bean.FullImageInfo;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.ILoginPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.LoginPresenterImpl;
import com.witkey.witkeyhelp.presenter.ProblemFeedbackPresenter;
import com.witkey.witkeyhelp.presenter.impl.ProblemFeedbackPresenterImpl;
import com.witkey.witkeyhelp.util.BitmapUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.RealPathFromUriUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.ProblemFeedbackView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;
import com.witkey.witkeyhelp.widget.RecycleGridDivider;

import com.witkey.witkeyhelp.widget.imagepicker.SelectDialog;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by jie on 2019/12/6.
 */

public class ProblemFeedbackActivity extends InitPresenterBaseActivity implements ProblemFeedbackView, View.OnClickListener {


    private ProblemFeedbackPresenter presenter;
    private EditText et_content;
    private ImageView feedbackimg;

    private Button btn_publish;
    private String imagePath = "";
    private String imgName = "consult";//图片名字
    private String imgPath1 = ""; //图片地址

    private int type;
    public static final int REQUEST_CODE_SELECT = 100;
    private int businessId;
    private int orderId;
    private int id;
    private ImageView expression;
    private RecyclerView photolist;
    private List<ReleasePhotoBean> photoList;
    private ReleasePhotoAdapter photoAdapter;
    private List mlist;
    private AlbumPoPubWindows popWinShare;

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);

        type = getIntent().getIntExtra("type", 0);
        businessId = getIntent().getIntExtra("businessId", 0);
        orderId = getIntent().getIntExtra("orderId", 0);
        id = getIntent().getIntExtra("id", 0);
    }

    @Override
    public void UploadFeedback(String data) {
        //   ToastUtils.showTestShort(this, data);
        if (type == 0) {
            ToastUtils.showTestShort(this, "反馈成功");
        } else {
            ToastUtils.showTestShort(this, "举报成功");
        }
        finish();
    }

    private List<ReleasePhotoBean> photoListTwo = new ArrayList<>();

    @Override
    public void saveImgSuc(String imgurl) {
        DialogUtil.showProgress(this);

        if (type == 0) {
            presenter.UploadFeedback(et_content.getText().toString(), user.getUserId(), imgurl, type, businessId, orderId);

        } else if (type == 1) {
            presenter.UploadFeedback(et_content.getText().toString(), user.getUserId(), imgurl, type, businessId, orderId);
        } else if (type == 2) {
            MyAPP.getInstance().getApi().reportOrder(et_content.getText().toString(), user.getUserId(), orderId, imgurl, 1).enqueue(new Callback(IModel.callback, "任务举报失败") {
                @Override
                public void getSuc(String body) {
                    DialogUtil.dismissProgress();
                    popubWiodowTask(ProblemFeedbackActivity.this);

                }
            });
        } else if (type == 3) {
            MyAPP.getInstance().getApi().lostAricleReport(id, et_content.getText().toString(), imgurl).enqueue(new Callback(IModel.callback, "任务举报失败") {
                @Override
                public void getSuc(String body) {
                    DialogUtil.dismissProgress();
                    popubWiodowTask(ProblemFeedbackActivity.this);
                }
            });
        }
    }

    private void popubWiodowTask(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.feedback_task, null, false);//引入弹窗布局

        TextView feedback_task = vPopupWindow.findViewById(R.id.feedback_task);
        ImageView coles_dialog = vPopupWindow.findViewById(R.id.coles_dialog);

        feedback_task.setText("举报成功！\n我们会尽快做出处理后回复！");

        LinearLayout cancel = vPopupWindow.findViewById(R.id.cancel);
        final PopupWindow relievepopupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                relievepopupWindow.dismiss();
                MyAPP.getInstance().finishSingleActivity(MissionDetailActivity.class);
                finish();
            }
        });
        coles_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                relievepopupWindow.dismiss();
                MyAPP.getInstance().finishSingleActivity(MissionDetailActivity.class);
                finish();
            }
        });


        //  popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);
        View parentView = LayoutInflater.from(context).inflate(R.layout.task_view, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        relievepopupWindow.setFocusable(false);
        try {
            addBackgroundPopub(relievepopupWindow);
            relievepopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void addBackgroundPopub(PopupWindow popup) {


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    protected void initWidget() {

        et_content = findViewById(R.id.et_content);
        et_content.post(new Runnable() {
            @Override
            public void run() {
                et_content.requestFocus();
            }
        });

        feedbackimg = findViewById(R.id.iv_pic);


        btn_publish = findViewById(R.id.btn_publish);
        expression = findViewById(R.id.expression);
        photolist = findViewById(R.id.photolist);
        photolist.setLayoutManager(new GridLayoutManager(this, 3));
        int spanCount = 3; // 3 columns
        int spacing = 36; // 50px
        boolean includeEdge = false;
        //  photolist.addItemDecoration(new RecycleGridDivider(spanCount, spacing, includeEdge));
        photoList = new ArrayList<ReleasePhotoBean>();
        photoList.add(new ReleasePhotoBean(imageTranslateUri(R.mipmap.picture_adding), false));
        photoAdapter = new ReleasePhotoAdapter(this, photoList);
        photolist.setAdapter(photoAdapter);
        photoAdapter.setOnItemClickListener(new ReleasePhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                boolean isAddPic = false;
                photoList.remove(position);
                for (int i = 0; i < photoList.size(); i++) {
                    if (photoList.get(i).isaBoolean() == false) {
                        isAddPic = true;
                        break;
                    }
                }
                if (isAddPic) {

                } else {
                    photoList.add(new ReleasePhotoBean(imageTranslateUri(R.mipmap.picture_adding), false));
                }
                photoAdapter.notifyDataSetChanged();
            }
        });

        photoAdapter.setOnItemPhotoClickListener(new ReleasePhotoAdapter.OnItemPhotoClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                extnStep(position);
            }
        });
    }

    private String imageTranslateUri(int resId) {

        Resources r = getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri.toString();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.problemfeedback;
    }

    @Override
    protected void initEvent() {
        feedbackimg.setOnClickListener(this);

        btn_publish.setOnClickListener(this);
        expression.setOnClickListener(this);
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new ProblemFeedbackPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        if (type == 0) {
            setIncludeTitle("意见反馈");
            btn_publish.setText("立即反馈");
        } else {
            setIncludeTitle("举报");
            btn_publish.setText("立即举报");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_pic:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
//                if (photoList.size()==0) {
//                    if (lacksPermissions(this, permissionsREAD)) {//读写权限没开启
//                        ActivityCompat.requestPermissions(this, permissionsREAD, 0);
//
//                    } else {
//                        //读写权限已开启
//                        ImgUtil.addPic(mActivity, 1, new ImgUtil.ChoosePic() {
//                            @Override
//                            public void pick(String imgPath) {
//                                imgPath1 = imgPath;
//                            }
//                        }, imgName);
//                    }
//
//                } else {
//                    onclilk(v);
//                }

                break;
            case R.id.btn_publish:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                photoListTwo.clear();
                for (int i = 0; i < photoList.size(); i++) {
                    if (photoList.get(i).isaBoolean()) {
                        photoListTwo.add(photoList.get(i));
                    }
                }
                String content = et_content.getText().toString();
                if (!content.equals("")) {
                    DialogUtil.showProgress(this);
                    if (photoListTwo.size() == 0) {

                        if (type == 0) {
                            presenter.UploadFeedback(content, user.getUserId(), imagePath, type, businessId, orderId);
                        } else if (type == 1) {
                            presenter.UploadFeedback(content, user.getUserId(), imagePath, type, businessId, orderId);
                        } else if (type == 2) {

                            MyAPP.getInstance().getApi().reportOrder(content, user.getUserId(), orderId, imagePath, 1).enqueue(new Callback(IModel.callback, "任务解除失败") {
                                @Override
                                public void getSuc(String body) {
                                    DialogUtil.dismissProgress();
                                    popubWiodowTask(ProblemFeedbackActivity.this);

                                }
                            });
                        } else if (type == 3) {
                            MyAPP.getInstance().getApi().lostAricleReport(id, et_content.getText().toString(), imagePath).enqueue(new Callback(IModel.callback, "任务举报失败") {
                                @Override
                                public void getSuc(String body) {
                                    DialogUtil.dismissProgress();
                                    popubWiodowTask(ProblemFeedbackActivity.this);

                                }
                            });
                        }

                    } else {
                        File sd = Environment.getExternalStorageDirectory();
                        String path = sd.getPath() + "/notes";
                        File file = new File(path);
                        if (!file.exists())
                            file.mkdir();

                        mlist = new ArrayList<>();
                        mlist.clear();
                        for (int i = 0; i < photoListTwo.size(); i++) {
                            mlist.add(photoListTwo.get(i).getUrl());
                        }
                        photoListTwo.clear();
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


                                        photoListTwo.add(new ReleasePhotoBean(file.getPath(), true));
                                        if (photoListTwo.size() == mlist.size()) {

                                            presenter.saveImgSuc(photoListTwo);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // TODO 当压缩过程出现问题时调用

                                    }
                                }).launch();


                    }
                } else {
                    ToastUtils.showTestShort(this, "请填写内容");
                }


                break;
            case R.id.expression:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                feedbackimg.setImageResource(R.mipmap.picture_adding);
                imagePath = "";
                expression.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imagePath = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                photoAdapter.notifyDataSetChanged();
                expression.setVisibility(View.VISIBLE);
            } else if (requestCode == 273) {


                imagePath = ImgUtil.fileUri.getAbsolutePath();
                MediaScannerConnection
                        .scanFile(this, new String[]{ImgUtil.fileUri.getAbsolutePath()}, null, null);
                imagePath = ImgUtil.fileUri.getAbsolutePath();
                if (photoList.size() == 1) {
                    photoList.add(0, new ReleasePhotoBean(imagePath, true));
                } else {
                    photoList.add(photoList.size() - 1, new ReleasePhotoBean(imagePath, true));
                }
                if (photoList.size() == 4) {
                    photoList.remove(3);
                }
                photoAdapter.notifyDataSetChanged();
            } else if (requestCode == 10000) {
                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
//            ArrayList<String> images = data.getStringArrayListExtra(
//                    ImageSelector.SELECT_RESULT);
                for (int i = 0; i < images.size(); i++) {
                    if (photoList.size() == 1) {
                        photoList.add(0, new ReleasePhotoBean(images.get(i).getPath(), true));
                    } else {
                        photoList.add(photoList.size() - 1, new ReleasePhotoBean(images.get(i).getPath(), true));
                    }
                }
                if (photoList.size() == 4) {
                    photoList.remove(3);
                }

                photoAdapter.notifyDataSetChanged();
//                for (int i = 0; i <photoList.size()  ; i++) {
//                    Luban.with(this)
//                            .load(photoList.get(i).getUrl())
//                            .ignoreBy(100)
//                            .setTargetDir(photoList.get(i).getUrl())
//                            .filter(new CompressionPredicate() {
//                                @Override
//                                public boolean apply(String path) {
//                                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                                }
//                            })
//                            .setCompressListener(new OnCompressListener() {
//                                @Override
//                                public void onStart() {
//                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                }
//                                @Override
//                                public void onSuccess(final File file) {
//                                    // TODO 压缩成功后调用，返回压缩后的图片文件
//                                    // imagePath= file.getPath();
//                                }
//                                @Override
//                                public void onError(Throwable e) {
//                                    // TODO 当压缩过程出现问题时调用
//                                }
//                            }).launch();
//                }

            }
        } else {
            imagePath = "";
        }
    }


    private void extnStep(int position) {
        //读写权限已开启
        if (photoList.get(position).isaBoolean()) {
            List<ReleasePhotoBean> mlist = new ArrayList<>();
            for (int i = 0; i < photoList.size(); i++) {
                if (photoList.get(i).isaBoolean()) {
                    mlist.add(photoList.get(i));
                }
            }
            onclilk(mlist, position);
        } else {


            popWinShare = new AlbumPoPubWindows(this, position + 1);

        }
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


    private void onclilk(List<ReleasePhotoBean> mlist, int position) {


        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("photo", (Serializable) mlist);
        intent.putExtra("position", position);
        startActivity(intent);
        //Activity context = (Activity) this.context;
        //  context.overridePendingTransition(0, 0);
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
                showAlbum(3);
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
