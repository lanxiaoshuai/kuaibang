package com.witkey.witkeyhelp.view.impl;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.ConsultBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.ConsultHintDialog;
import com.witkey.witkeyhelp.presenter.IConsultPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.ConsultPresenterImpl;
import com.witkey.witkeyhelp.util.CalendarUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.view.IConsultView;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author lingxu
 * @date 2019/7/12 14:18
 * @description 信息咨询
 */
public class ConsultActivity extends PermissionActivity implements View.OnClickListener, IConsultView {
    private TextView tv_title_content; //咨询内容
    private EditText et_content;//咨询内容
    private ImageView iv_pic; //显示添加的图片
    private TextView btn_add_pic; //添加图片

    private TextView tv_title_date; //任务截止时间
    private TextView tv_date; //选择结束时间spinner

    private TextView tv_title_money; //悬赏金额
    private EditText et_money; //输入您的任务金额
    private RadioGroup rg_money_type; //金额类型
    private RadioButton rb_RMB; //人民币
    private RadioButton rb_diamonds; //钻石

    private TextView btn_open_more; //展开更多高级设置

    private LinearLayout ll_more;//更多的布局

    private TextView tv_title_title; //任务标题
    private EditText et_title; //输入您的任务关键词

    private TextView tv_title_isBargaining; //是否可以议价
    private RadioGroup rg_isBargaining;
    private RadioButton rb_isBargaining_true;
    private RadioButton rb_isBargaining_false;

    private TextView tv_title_contact; //联系方式
    private EditText et_contact; //输入您的联系方式

    private TextView tv_title_mission_num; //任务数量
    private NiceSpinner spin_mission_num;

    private TextView tv_title_isNeedBidding; //是否需要竞标
    private RadioGroup rg_isNeedBidding;
    private RadioButton rb_isNeedBidding_true;
    private RadioButton rb_isNeedBidding_false;

    private TextView tv_title_isNeedBond; //是否需要保证金,点击操作
    private RadioGroup rg_isNeedBond;
    private RadioButton rb_isNeedBond_true;
    private RadioButton rb_isNeedBond_false;

    private Button btn_save;//暂时保存
    private Button btn_publish;//立即发布

    private String chooseTime;//选中的时间

    private IConsultPresenter presenter;
    private String imgPath1; //图片地址
    private String imgName = "consult";//图片名字
    private HashMap<String, String> photoMap; //保存图片

    private boolean isOpenMore = false; //是否已经打开更多
    private boolean isHavePic; //是否已添加图片
    private boolean isMoneyTypeRmb = true; //悬赏金额类型是否为rmb
    private boolean isBargaining = false; //是否可以议价
    private boolean isNeedBidding = false; //是否需要竞标
    private boolean isNeedBond = false; //是否需要保证金
    private boolean isPublish;//是否为发布

    private String[] mission_nums = {"1","2","3","4","5"};
    private int mission_num = 1;

    private String businessType; //任务类型

    private User user;

    @Override
    protected void initWidget() {
        setIncludeTitle("信息咨询");
        tv_title_content = findViewById(R.id.tv_title_content);
        et_content = findViewById(R.id.et_content);
        iv_pic = findViewById(R.id.iv_pic);
        btn_add_pic = findViewById(R.id.btn_add_pic);
        tv_title_date = findViewById(R.id.tv_title_date);
        tv_date = findViewById(R.id.tv_date);
        tv_title_money = findViewById(R.id.tv_title_money);
        et_money = findViewById(R.id.et_money);
        rg_money_type = findViewById(R.id.rg_money_type);
        rb_RMB = findViewById(R.id.rb_RMB);
        ll_more = findViewById(R.id.ll_more);
        rb_diamonds = findViewById(R.id.rb_diamonds);
        btn_open_more = findViewById(R.id.btn_open_more);
        tv_title_title = findViewById(R.id.tv_title_title);
        et_title = findViewById(R.id.et_title);
        tv_title_isBargaining = findViewById(R.id.tv_title_isBargaining);
        rg_isBargaining = findViewById(R.id.rg_isBargaining);
        rb_isBargaining_true = findViewById(R.id.rb_isBargaining_true);
        rb_isBargaining_false = findViewById(R.id.rb_isBargaining_false);
        tv_title_contact = findViewById(R.id.tv_title_contact);
        et_contact = findViewById(R.id.et_contact);
        tv_title_mission_num = findViewById(R.id.tv_title_mission_num);
        spin_mission_num = findViewById(R.id.spin_mission_num);
        tv_title_isNeedBidding = findViewById(R.id.tv_title_isNeedBidding);
        rg_isNeedBidding = findViewById(R.id.rg_isNeedBidding);
        rb_isNeedBidding_true = findViewById(R.id.rb_isNeedBidding_true);
        rb_isNeedBidding_false = findViewById(R.id.rb_isNeedBidding_false);
        tv_title_isNeedBond = findViewById(R.id.tv_title_isNeedBond);
        rg_isNeedBond = findViewById(R.id.rg_isNeedBond);
        rb_isNeedBond_true = findViewById(R.id.rb_isNeedBond_true);
        rb_isNeedBond_false = findViewById(R.id.rb_isNeedBond_false);
        btn_save = findViewById(R.id.btn_save);
        btn_publish = findViewById(R.id.btn_publish);
        checkPermissions();
    }

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        businessType = argIntent.getStringExtra("EXTRA_PAGE_TYPE");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_consult;
    }
    @Override
    protected void initEvent() {
        btn_add_pic.setOnClickListener(this);
        btn_open_more.setOnClickListener(this);
        tv_title_isNeedBond.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        spin_mission_num.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                mission_num = Integer.parseInt(mission_nums[position]);
            }
        });
        //悬赏金额类型
        rg_money_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_RMB:
                        isMoneyTypeRmb = true;
                        break;
                    case R.id.rb_diamonds:
                        isMoneyTypeRmb = false;
                        break;
                }
            }
        });
        //是否可以议价
        rg_isBargaining.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_isBargaining_true:
                        isBargaining = true;
                        break;
                    case R.id.rb_isBargaining_false:
                        isBargaining = false;
                        break;
                }
            }
        });
        //是否需要竞标
        rg_isNeedBidding.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_isNeedBidding_true:
                        isNeedBidding = true;
                        break;
                    case R.id.rb_isNeedBidding_false:
                        isNeedBidding = false;
                        break;
                }
            }
        });
        //是否需要保证金,点击操作
        rg_isNeedBond.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_isNeedBond_true:
                        isNeedBond = true;
                        break;
                    case R.id.rb_isNeedBond_false:
                        isNeedBond = false;
                        break;
                }
            }
        });
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new ConsultPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        //设置不显示更多
        isOpenMore = false;
        ll_more.setVisibility(View.GONE);
        btn_open_more.setVisibility(View.VISIBLE);
        //任务数量spin显示
        spin_mission_num.attachDataSource(Arrays.asList(mission_nums));
        //准备user数据
        user = MyAPP.getInstance().getUser();
        //rb设置
        rg_money_type.check(R.id.rb_RMB);
        rg_isBargaining.check(R.id.rb_isBargaining_false);
        rg_isNeedBidding.check(R.id.rb_isNeedBidding_false);
        rg_isNeedBond.check(R.id.rb_isNeedBond_false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_pic:
                ImgUtil.addPic(mActivity, 1, new ImgUtil.ChoosePic() {
                    @Override
                    public void pick(String imgPath) {
                        imgPath1 = imgPath;
                    }
                }, imgName);
                break;
            case R.id.btn_open_more:
                if (!isOpenMore) {
                    isOpenMore = true;
                    ll_more.setVisibility(View.VISIBLE);
                    btn_open_more.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_title_isNeedBond:
                ConsultHintDialog consultHintDialog = new ConsultHintDialog(mActivity, spin_mission_num);
                consultHintDialog.show();
                break;
            case R.id.btn_save:
                //保存按钮
                isPublish=false;
                saveConsult();
                break;
            case R.id.btn_publish:
                //发布按钮
                isPublish=true;
                saveConsult();
                break;
            case R.id.tv_date:
                DatePickerDialog startdialog = new DatePickerDialog(mActivity, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        chooseTime = setTimeShow(year, monthOfYear, dayOfMonth);
                        tv_date.setText(chooseTime);
                    }
                }, CalendarUtil.getTime()[0], CalendarUtil.getTime()[1], CalendarUtil.getTime()[2]);
                DatePicker datePicker = startdialog.getDatePicker();
                datePicker.setMinDate(System.currentTimeMillis());
                startdialog.show();
                break;
        }
    }

    private void saveConsult() {
        ConsultBean consultBean = new ConsultBean();
        String describes = et_content.getText().toString();
        if (describes.isEmpty()) {
            Toast("请填写问题详情~", 3);
            return;
        }
        consultBean.setDescribes(describes);
        // 不弄String businessImgUrl;
        if (!isHavePic) {
            Toast("请添加图片~", 3);
            return;
        }
        consultBean.setBusinessImgUrl("123");
        consultBean.setPhotoMap(photoMap);
        if (chooseTime.isEmpty()) {
            Toast("请选择结束日期~", 3);
            return;
        }
        consultBean.setEndDate(chooseTime);
        String money = et_money.getText().toString();
        if (money.isEmpty()) {
            Toast("请输入悬赏金额~", 3);
            return;
        }
        consultBean.setPrice(money);
        consultBean.setPaymentType(isMoneyTypeRmb ? "1" : "2");
        String title = et_title.getText().toString();
        consultBean.setTitle(title);
        consultBean.setBargainingType(isBargaining ? "1" : "0");
        String contact = et_contact.getText().toString();
        if (contact.isEmpty()) {
            Toast("请输入联系方式~", 3);
            return;
        }
        consultBean.setContactsPhone(contact);
        consultBean.setBusinessNum(mission_num);
        consultBean.setBiddingType(isNeedBidding ? "1" : "0");
        consultBean.setProductType(isNeedBidding ? "1" : "0"); //于上方重复
        consultBean.setBondType(isNeedBond ? "1" : "0");
        //非填写
        consultBean.setCreateUserId(user.getUserId());
        consultBean.setBusinessType(businessType);//信息咨询
        //未必须
        consultBean.setLatitude("");
        consultBean.setLongitude("");
        presenter.saveConsult(consultBean);
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
        iv_pic.setImageBitmap(bitmap);
        if (photoMap == null) {
            photoMap = new HashMap<>();
        }
        photoMap.put(imgName, imgName);
        isHavePic = true;
    }

    @Override
    public void saveSuc(String businessId) {
        if(isPublish){
            presenter.publishConsult(businessId);
        }else{
            finish();
        }
    }

    @Override
    public void publishSuc() {
        Toast("发布成功",1);
        finish();
    }

    @Override
    protected int getPermissionType() {
        return TYPE_PIC;
    }
}
