package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.LostFoundBean;
import com.witkey.witkeyhelp.bean.MicroNotificationBean;
import com.witkey.witkeyhelp.presenter.IAddLostFoundPresenter;
import com.witkey.witkeyhelp.presenter.IAddMicroNotificationPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.AddLostFoundPresenterImpl;
import com.witkey.witkeyhelp.presenter.impl.AddMicroNotificationPresenterImpl;
import com.witkey.witkeyhelp.util.CalendarUtil;
import com.witkey.witkeyhelp.util.FormatUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.view.IAddLostFoundView;
import com.witkey.witkeyhelp.view.IAddMicroNotificationView;

import java.util.HashMap;

/**
 * @author lingxu
 * @date 2019/8/1 14:38
 * @description 添加微通知群
 */
public class AddMicroNotificationActivity extends PermissionActivity implements IAddMicroNotificationView, View.OnClickListener {
    private RelativeLayout rl_pic_defalut;
    private TextView tv_add_pic; //添加图片
    private ImageView iv_show_add_pic;
    private EditText et_name;
    private EditText et_describe;
    private TextView tv_add_lost;

    private boolean isShowPic = false;

    private IAddMicroNotificationPresenter presenter;

    private String imgPath1; //图片地址
    private String imgName = "microNotification";//图片名字
    private HashMap<String, String> photoMap; //保存图片

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new AddMicroNotificationPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        setIncludeTitle("微通知账号");
    }

    @Override
    protected void initWidget() {
        rl_pic_defalut = findViewById(R.id.rl_pic_defalut);
        tv_add_pic = findViewById(R.id.tv_add_pic);
        iv_show_add_pic = findViewById(R.id.iv_show_add_pic);
        et_name = findViewById(R.id.et_describe);
        et_describe = findViewById(R.id.et_hint);
        tv_add_lost = findViewById(R.id.tv_add_lost);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_micro_notification;
    }

    @Override
    protected void initEvent() {
        tv_add_pic.setOnClickListener(this);
        tv_add_lost.setOnClickListener(this);
    }

    @Override
    public void addSuc() {
        Toast("添加成功", 1);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_pic:
                ImgUtil.addPic(mActivity, 1, new ImgUtil.ChoosePic() {
                    @Override
                    public void pick(String imgPath) {
                        imgPath1 = imgPath;
                    }
                }, imgName);
                break;
            case R.id.tv_add_lost:
                //提交
                String name = et_name.getText().toString().trim();
                String describe = et_describe.getText().toString().trim();
                if (!name.isEmpty()) {
                    if (!describe.isEmpty()) {
                        presenter.addMicroNotification(new MicroNotificationBean(name, describe, CalendarUtil.getTodayString()));
                    } else {
                        Toast("描述不可为空", 2);
                    }
                } else {
                    Toast("名称不可为空", 2);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // 裁剪照片
                if (imgPath1 != null) {
                    ImgUtil.compress(imgPath1, 1080, 1080);
                    img1Suc(BitmapFactory.decodeFile(imgPath1));
                } else {
                    ImgUtil.startPhotoZoom(imgName, mActivity, data.getData(), 480, 480, 2);
                }
            } else if (requestCode == 2) {
                // 显示照片
                ImgUtil.compress(Contacts.imgPath + imgName, 1080, 1080);
                img1Suc(BitmapFactory.decodeFile(Contacts.imgPath + imgName));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void img1Suc(Bitmap bitmap) {
        iv_show_add_pic.setImageBitmap(bitmap);
        if (photoMap == null) {
            photoMap = new HashMap<>();
        }
        photoMap.put(imgName, imgName);
        isShowPic = true;
        setShowPic(isShowPic);
    }

    private void setShowPic(boolean isShowPic) {
        if (isShowPic) {
            rl_pic_defalut.setVisibility(View.GONE);
            iv_show_add_pic.setVisibility(View.VISIBLE);
        } else {
            rl_pic_defalut.setVisibility(View.VISIBLE);
            iv_show_add_pic.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getPermissionType() {
        return TYPE_PIC;
    }
}
