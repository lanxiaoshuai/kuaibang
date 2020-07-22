package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;


import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.ReleasePhotoAdapter;
import com.witkey.witkeyhelp.bean.FullImageInfo;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.BitmapUtil;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.RealPathFromUriUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;
import com.witkey.witkeyhelp.widget.RecycleGridDivider;

import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by asus on 2020/3/20.
 */

public class AcitivityReleaseFound extends BaseActivity {

    private EditText et_content;
    private Button button;
    private EditText et_contact;

    private String imagePath = "";
    private String imgName = "consult";//图片名字
    private double latitude;
    private double longitude;
    private TextView position;
    private String businessImgUrl;
    private String city = "";
    private String province = "";
    private String imgPath1 = ""; //图片地址
    private ImageView expression;
    private RecyclerView photolist;
    private List<ReleasePhotoBean> photoList;
    private ReleasePhotoAdapter photoAdapter;
    private List<ReleasePhotoBean> photoListTwo = new ArrayList<>();
    private UICustomDialog2 dialog2;
    private List mlist;
    private boolean isAddPic;
    private AlbumPoPubWindows popWinShare;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.myrelease_task);
        setIncludeTitle("发布失物招领");

        boolean miui = isMIUI();
        if (miui) {
            boolean oPen = GPSIsOpenUtil.isOPen(this);
            if (oPen) {
                getLocationClient();
            } else {
                showLocationMissingPermissionDialog();
            }
        } else {
            getLocationClient();
        }
        et_content = findViewById(R.id.et_content);



        et_content.post(new Runnable() {
            @Override
            public void run() {
                et_content.requestFocus();
            }
        });
        button = findViewById(R.id.btn_publish);
        et_contact = findViewById(R.id.et_contact);

        position = findViewById(R.id.position);
        position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcitivityReleaseFound.this, ActivityShare.class);
                intent.putExtra("city", city);
                intent.putExtra("province", province);
                startActivityForResult(intent, 100);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = et_content.getText().toString();
                if (content.equals("")) {
                    ToastUtils.showTestShort(AcitivityReleaseFound.this, "请输入失物招领内容");
                } else {
                    String contact = et_contact.getText().toString();
                    if (contact.length() == 0 || contact.length() == 11) {

                    } else {
                        ToastUtils.showTestShort(AcitivityReleaseFound.this, "请填写正确电话");
                        return;
                    }
                    for (int i = 0; i < photoList.size(); i++) {
                        if (photoList.get(i).isaBoolean()) {
                            photoListTwo.add(photoList.get(i));
                        }
                    }

                    DialogUtil.showProgress(AcitivityReleaseFound.this);
                    if (photoListTwo.size() == 0) {
                        loadFound();
                    } else {
                        imgurl();

                    }
                }
            }
        });

        InputFilter inputFilter = new InputFilter() {
            String string = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
            Pattern pattern = Pattern.compile(string);

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    // MyToast.showText("只能输入汉字,英文，数字");
                    return "";
                }

            }
        };
        et_content.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(10000)});
        expression = findViewById(R.id.expression);
        expression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imagePath = "";
                expression.setVisibility(View.GONE);
            }
        });

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
    private void extnStep(int position ) {
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


            popWinShare = new AlbumPoPubWindows(this, position+1);

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


    private String imageTranslateUri(int resId) {

        Resources r = getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri.toString();
    }

    private void showLocationMissingPermissionDialog() {
        dialog2 = new UICustomDialog2(this, "GPS未打开,去开启", "3");
        dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.hide();
            }
        });
        dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.hide();
                startAppSettings();
            }
        });
        dialog2.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private int GPS_REQUEST_CODE = 10;

    protected void startAppSettings() {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, GPS_REQUEST_CODE);

    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        Log.e("tag",manufacturer);
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("huawei".equalsIgnoreCase(manufacturer)||"Xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    private void loadFound() {
        DialogUtil.showProgress(AcitivityReleaseFound.this);
        MyAPP.getInstance().getApi().lostAricleAdd(user.getUserId(), et_content.getText().toString(), et_contact.getText().toString(), businessImgUrl, longitude + "", latitude + "", position.getText().toString()).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {

                DialogUtil.dismissProgress();
                ToastUtils.showTestShort(AcitivityReleaseFound.this, "发布成功");

                setResult(10000);
                finish();
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "requestCode" + requestCode + "resultCode" + resultCode);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            imagePath = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());

            expression.setVisibility(View.VISIBLE);
            photoAdapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_OK && requestCode == 273) {

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
        } else if (resultCode == RESULT_OK && requestCode == 10000) {

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


        } else if (requestCode == 10) {
            dialog2.hide();
            getLocationClient();
        } else if (requestCode == 100) {
            if (data != null) {
                if (data.getStringExtra("name") != null) {
                    position.setText(data.getStringExtra("name"));
                    city = data.getStringExtra("city");
                    latitude = data.getDoubleExtra("latitude", 0);
                    longitude = data.getDoubleExtra("longitude", 0);

                }
            }
        }


    }

    private void imgurl() {


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
                        Log.e("qqqeee", file.getPath());

                        photoListTwo.add(new ReleasePhotoBean(file.getPath(), true));
                        if (photoListTwo.size() == mlist.size()) {
                            List<File> files = new ArrayList<>();

                            List<MultipartBody.Part> parts = new ArrayList<>();
                            for (int i = 0; i < photoListTwo.size(); i++) {
                                files.add(new File(photoListTwo.get(i).getUrl()));
                                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
                                MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(i).getName(), requestBody);
                                parts.add(part);
                            }
                            MyAPP.getInstance().getApi().upLoadImg(parts).enqueue(new Callback(IModel.callback, "上传失败") {


                                @Override
                                public void getSuc(String body) {
                                    List<File> files = new ArrayList<>();

                                    List<MultipartBody.Part> parts = new ArrayList<>();
                                    for (int i = 0; i < photoListTwo.size(); i++) {
                                        files.add(new File(photoListTwo.get(i).getUrl()));
                                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
                                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(i).getName(), requestBody);
                                        parts.add(part);
                                    }
                                    MyAPP.getInstance().getApi().upLoadImg(parts).enqueue(new Callback(IModel.callback, "上传失败") {
                                        @Override
                                        public void getSuc(String body) {
                                            DialogUtil.dismissProgress();
                                            DialogUtil.showProgress(AcitivityReleaseFound.this);
                                            businessImgUrl = JSONUtil.getValueToString(body, "returnObject");
                                            loadFound();
                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        Log.e("qqq", e.getMessage() + "    aaaaaaa");
                    }
                }).launch();


    }

    @Override
    protected boolean isGetUser() {
        return true;
    }






    public AMapLocation aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void getLocationClient() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation.getErrorCode() == 0) {

                    city = aMapLocation.getCity();
                    province = aMapLocation.getProvince();
                    aMapLocation.getAddress();
                    String aoiName = aMapLocation.getAoiName();

                    if (aoiName == null || aoiName.equals("")) {
                        position.setText(aMapLocation.getPoiName() + "");
                    } else {
                        position.setText(aoiName + "");
                    }
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();

                } else {

                }


            }
        };
//初始化定位
        mLocationClient = new AMapLocationClient(this);
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();


        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();


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
        }else if(requestCode==1){
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {

                    // 权限被拒绝，弹出dialog 提示去开启权限
                    //         showPermissions();
                    uthority = true;
                    break;
                }

            }
            if (uthority==false) {
                showAlbum(3);
            }

        }

    }


    private void showAlbum(int number) {
        Log.e("tag","aaaaaa"+number);
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
