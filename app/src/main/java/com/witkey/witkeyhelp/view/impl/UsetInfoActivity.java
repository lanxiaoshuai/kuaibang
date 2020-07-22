package com.witkey.witkeyhelp.view.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.BitmapUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.RealPathFromUriUtils;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by jie on 2020/5/15.
 */

public class UsetInfoActivity extends BaseActivity {

    private RadioButton rb_male;
    private RadioButton rb_female;
    private RoundImageView user_photo;
    private EditText username;
    private EditText introduction;
    private AlbumPoPubWindows popWinShare;

    private String imagePath = "";
    private RadioGroup rg_gender;
    private String gender = "0";

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.personalhome);
        setIncludeTitle("编辑资料");
        rg_gender = findViewById(R.id.rg_gender);
        rg_gender.check(R.id.rb_Male);
        rb_male = findViewById(R.id.rb_Male);
        rb_female = findViewById(R.id.rb_Female);

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_Male:
                        gender = "0";
                        Drawable a = getResources().getDrawable(R.mipmap.gender_check);
                        rb_male.setCompoundDrawablesWithIntrinsicBounds(null, null, a, null);

                        Drawable b = getResources().getDrawable(R.mipmap.gender_unchecked);

                        rb_female.setCompoundDrawablesWithIntrinsicBounds(null, null, b, null);

                        break;
                    case R.id.rb_Female:
                        Drawable c = getResources().getDrawable(R.mipmap.xuanzhongnv);
                        rb_female.setCompoundDrawablesWithIntrinsicBounds(null, null, c, null);
                        Drawable d = getResources().getDrawable(R.mipmap.nan_uncheck);
                        rb_male.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                        gender = "1";
                        break;
                }
            }
        });


        user_photo = findViewById(R.id.user_photo);

        username = findViewById(R.id.username);

        introduction = findViewById(R.id.introduction);

        TextView userInformation = findViewById(R.id.userInformation);

        userInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    ToastUtils.showTestShort(UsetInfoActivity.this, "请填写昵称");
                }
                if (introduction.getText().toString().equals("")) {
                    ToastUtils.showTestShort(UsetInfoActivity.this, "请填写个性签名");
                }
                DialogUtil.showProgress(UsetInfoActivity.this);
                if (imagePath == null || imagePath.equals("")) {
                    MyAPP.getInstance().getApi().updateUserInfo(user.getUserId(),username.getText().toString(), user.getHeadUrl(), gender + "", introduction.getText().toString()).enqueue(new Callback(IModel.callback, "修改失败") {
                        @Override
                        public void getSuc(String body) {
                            user.setSex(gender+"");
                            user.setRealName(username.getText().toString());
                            user.setpSignature(introduction.getText().toString());
                            ToastUtils.showTestShort(UsetInfoActivity.this, "昵称修改成功~");
                            SpUtils.putObject(UsetInfoActivity.this, "LOGIN", user);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        UserInfo mMyInfo = JMessageClient.getMyInfo();
                                        mMyInfo.setNickname(user.getRealName());
                                        JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                                            @Override
                                            public void gotResult(int responseCode, String responseMessage) {

                                            }
                                        });

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            DialogUtil.dismissProgress();
                            finish();
                        }
                    });

                } else {
                    File sd = Environment.getExternalStorageDirectory();
                    String path = sd.getPath() + "/notes";
                    File file = new File(path);
                    if (!file.exists())
                        file.mkdir();
                    Luban.with(UsetInfoActivity.this)
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
                                    imgurl(imagePath);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();

                }
            }
        });

        Glide.with(this).load(URL.getImgPath + user.getHeadUrl()).into(user_photo);

        username.setText(user.getRealName());
        username.setSelection(username.getText().length());
        load();


        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                extnStep();
            }
        });
        if (user.getpSignature() == null || user.getpSignature().equals("")) {


            MyAPP.getInstance().getApi().login(user.getUserName(), user.getPassword()).enqueue(new Callback(IModel.callback, "登录失败") {
                @Override
                public void getSuc(String body) {

                    int code = JSONUtil.getValueToInt(body, "errorCode");
                    if (code == 200) {
                        //登录成功
                        User user = IModel.gson.fromJson(JSONUtil.getValueToString(body, "returnObject"), User.class);
                        MyAPP.getInstance().setUser(user);
                        SpUtils.putObject(UsetInfoActivity.this, "LOGIN", user);
                        introduction.setText(user.getpSignature());
                    }
                }
                //   }
            });
        } else {
            introduction.setText(user.getpSignature());
        }
        if (user.getSex().equals("0")) {
            rg_gender.check(R.id.rb_Male);
        } else if (user.getSex().equals("1")) {
            rg_gender.check(R.id.rb_Female);
        }

    }

    //输入框初始值
    private int num = 0;
    //输入框最大值
    public int mMaxNum = 10;

    private void load() {

//mPublishEdDesc是EditText
        username.addTextChangedListener(new TextWatcher() {
            //记录输入的字数
            private CharSequence wordNum;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时记录输入的字数
                wordNum = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num + s.length();
                //TextView显示剩余字数

                selectionStart = username.getSelectionStart();
                selectionEnd = username.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    username.setText(s);
                    username.setSelection(username.getText().toString().length());//设置光标在最后
                    //吐司最多输入300字

                }
            }
        });

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

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "requestCode" + requestCode + "resultCode" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                imagePath = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());

                Glide.with(this).load(imagePath).into(user_photo);


            } else if (requestCode == 2) {
//                imagePath = BitmapUtil.compressImage(imagePath);
//                imagePath = Contacts.imgPath + imgName;


            } else if (requestCode == 273) {

                imagePath = ImgUtil.fileUri.getAbsolutePath();
                Glide.with(this).load(imagePath).into(user_photo);


            } else if (requestCode == 10000) {
                ;

                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
//
                imagePath = images.get(0).getPath();
                Glide.with(this).load(imagePath).into(user_photo);


            }
        } else {
            //data.getStringExtra()
            imagePath = "";
        }
    }

    private void imgurl(final String businessImgUrl) {

        List<File> files = new ArrayList<>();

        List<MultipartBody.Part> parts = new ArrayList<>();

        files.add(new File(imagePath));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(0));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", files.get(0).getName(), requestBody);
        parts.add(part);


        MyAPP.getInstance().getApi().upLoadImg(parts).enqueue(new Callback(IModel.callback, "上传失败") {
            @Override
            public void getSuc(String body) {

                MyAPP.getInstance().getApi().updateUserInfo(user.getUserId(), username.getText().toString(), businessImgUrl, gender + "", introduction.getText().toString()).enqueue(new Callback(IModel.callback, "修改失败") {
                    @Override
                    public void getSuc(String body) {
                        if(!imagePath.equals("")){
                            user.setHeadUrl(businessImgUrl);
                            user.setSex(gender+"");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    File mFile = null;
                                    try {
                                        mFile = Glide.with(UsetInfoActivity.this).asFile().load(URL.getImgPath + user.getHeadUrl()).submit().get();
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
                        }
                        user.setRealName(username.getText().toString());
                        user.setpSignature(introduction.getText().toString());
                        ToastUtils.showTestShort(UsetInfoActivity.this, "昵称修改成功~");
                        SpUtils.putObject(UsetInfoActivity.this, "LOGIN", user);
                        DialogUtil.dismissProgress();

                        user.setpSignature(introduction.getText().toString());
                        ToastUtils.showTestShort(UsetInfoActivity.this, "昵称修改成功~");
                        SpUtils.putObject(UsetInfoActivity.this, "LOGIN", user);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    UserInfo mMyInfo = JMessageClient.getMyInfo();
                                    mMyInfo.setNickname(user.getRealName());
                                    JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                                        @Override
                                        public void gotResult(int responseCode, String responseMessage) {

                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        finish();
                    }
                });
            }
        });

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
