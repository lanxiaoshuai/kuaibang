package com.witkey.witkeyhelp.view.impl;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.content.res.Resources;
import android.graphics.Bitmap;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;

import android.os.Environment;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.donkingliang.labels.LabelsView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.Interfacecallback.Modifystate;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MyCiclesAdapter;
import com.witkey.witkeyhelp.adapter.ReleasePhotoAdapter;

import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PayInfoBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;

import com.witkey.witkeyhelp.dialog.TaskDetailsDialog;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IConsultPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.ConsultPresenterImpl;

import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PermissionUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.RealPathFromUriUtils;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.util.wight.TextWatcherAdapter;
import com.witkey.witkeyhelp.view.IConsultView;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;


import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;


import java.io.File;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * @author lingxu
 * @date 2019/7/12 14:18
 * @description 信息咨询
 */
public class ConsultActivity extends InitPresenterBaseActivity implements View.OnClickListener, IConsultView {
    private TextView tv_title_content; //咨询内容
    private EditText et_content;//咨询内容


    private TextView tv_title_date; //任务截止时间
    private TextView tv_date; //选择结束时间spinner

    private TextView tv_title_money; //悬赏金额
    private EditText et_money; //输入您的任务金额
    private RadioGroup rg_money_type; //金额类型
    private RadioButton rb_RMB; //人民币
    private RadioButton rb_diamonds; //钻石
    private RelativeLayout btn_open_more; //展开更多高级设置
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


    private Button btn_publish;//立即发布
    private String chooseTime = "";//选中的时间
    private IConsultPresenter presenter;
    private String imgPath1 = ""; //图片地址
    private String imgName = "consult";//图片名字
    private HashMap<String, String> photoMap = new HashMap<>(); //保存图片

    private boolean isOpenMore = false; //是否已经打开更多
    private boolean isHavePic; //是否已添加图片
    private boolean isMoneyTypeRmb = true; //悬赏金额类型是否为rmb
    private boolean isBargaining = false; //是否可以议价
    private boolean isNeedBidding = false; //是否需要竞标
    private boolean isPosition = true; //是否需要保证金
    private boolean isPublish;//是否为发布
    private boolean isNeedBond = false; //是否需要保证金
    private String[] mission_nums = {"1", "2", "3", "4", "5"};
    private int mission_num = 1;

    private String businessType; //任务类型
    private String imgurl = "";
    private TextView position;
    private String imagePath = "";
    private MissionBean missionBean;

    private RadioGroup rg_isPosition;
    private MissionBean missionBean1;
    private RelativeLayout bondrelativeLayout;
    private EditText bond_price;
    private String city = "";
    private String province = "";
    private int viewwidth;
    private int viewheight;
    private View text_view_xian;
    private ImageView expression;
    private RecyclerView photolist;
    private List<ReleasePhotoBean> photoList;
    private ReleasePhotoAdapter photoAdapter;

    private boolean isAddPic;
    private AlbumPoPubWindows popWinShare;
    private UICustomDialog2 dialog2;
    private List<String> mlist;
    private int businessTypeNum;
    private int indexposition = 3;
    private PopupWindow ymCircles;
    private RelativeLayout include_title_by;
    private MyCiclesAdapter myCiclesAdapter;

    private LabelsView cloud_tag;
    private List<CicleBean.ReturnObjectBean.RowsBean> labList;
    private List<CicleBean.ReturnObjectBean.RowsBean> attentionList;
    private RelativeLayout addcircle;
    private View consult_line;
    private LinearLayout dynamic_publishing;
    private LinearLayout rewardtohelp;
    private LinearLayout dynamic_layout;
    private View line_dynamic;


    @Override
    protected void initWidget() {


        user = SpUtils.getObject(this, "LOGIN");
        if (user == null) {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("home", true);

            i.putExtra("EXTRA_PAGE_TYPE", businessType);
            startActivity(i);
            finish();
            // Toast.makeText(mActivity, "未登录", Toast.LENGTH_SHORT).show();

        }

        tv_title_content = findViewById(R.id.tv_title_content);
        line_dynamic = findViewById(R.id.line_dynamic);

        et_content = findViewById(R.id.et_content);
        et_content.post(new Runnable() {
            @Override
            public void run() {
                et_content.requestFocus();
            }
        });

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
        consult_line = findViewById(R.id.consult_line);

        dynamic_publishing = findViewById(R.id.dynamic_publishing);
        rewardtohelp = findViewById(R.id.rewardtohelp);
        dynamic_layout = findViewById(R.id.dynamic_layout);

        tv_title_contact = findViewById(R.id.tv_title_contact);
        et_contact = findViewById(R.id.et_contact);
        tv_title_mission_num = findViewById(R.id.tv_title_mission_num);
        spin_mission_num = findViewById(R.id.spin_mission_num);
        tv_title_isNeedBidding = findViewById(R.id.tv_title_isNeedBidding);
        rg_isNeedBidding = findViewById(R.id.rg_isNeedBidding);
        rb_isNeedBidding_true = findViewById(R.id.rb_isNeedBidding_true);
        rb_isNeedBidding_false = findViewById(R.id.rb_isNeedBidding_false);

        rg_isNeedBond = findViewById(R.id.rg_isNeedBond);
        rb_isNeedBond_true = findViewById(R.id.rb_isNeedBond_true);
        rb_isNeedBond_false = findViewById(R.id.rb_isNeedBond_false);

        btn_publish = findViewById(R.id.btn_publish);
        position = findViewById(R.id.position);

        text_view_xian = findViewById(R.id.text_view_xian);
        rg_isPosition = findViewById(R.id.rg_isPosition);
        expression = findViewById(R.id.expression);

        bond_price = findViewById(R.id.bond_price);


        photolist = findViewById(R.id.photolist);
        addcircle = findViewById(R.id.addcircle);


        cloud_tag = findViewById(R.id.cloud_tag);
        labList = new ArrayList<>();
        CicleBean.ReturnObjectBean.RowsBean rowsBean = new CicleBean.ReturnObjectBean.RowsBean();
        rowsBean.setAbbreviation("全部");
        CicleBean.ReturnObjectBean.RowsBean addTo = new CicleBean.ReturnObjectBean.RowsBean();
        addTo.setAbbreviation(" ＋ ");
        labList.add(rowsBean);
        labList.add(addTo);

        cloud_tag.setLabels(labList, new LabelsView.LabelTextProvider<CicleBean.ReturnObjectBean.RowsBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, CicleBean.ReturnObjectBean.RowsBean data) {
                if (position == labList.size() - 1) {
                    label.setBackground(getResources().getDrawable(R.drawable.shape_gray_home));
                }
                return data.getAbbreviation();
            }
        });


        photolist.setLayoutManager(new GridLayoutManager(this, 3));


        photoList = new ArrayList<>();
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

        cloud_tag.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                if (label.getText().toString().equals("全部")) {

                } else {
                    display();
                }
            }
        });
        addcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });
        getReadydialog();
        attenTion();
        photoAdapter.setOnItemPhotoClickListener(new ReleasePhotoAdapter.OnItemPhotoClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                indexposition = position;
                extnStep();
            }
        });


        businessTypeNum = Integer.parseInt(businessType);
        if (businessTypeNum == 1) {
            setIncludeTitle("咨询");
            tv_title_content.setText("咨询内容");
            et_content.setHint("在此输入咨询内容");
            btn_open_more.setVisibility(View.GONE);
            ll_more.setVisibility(View.GONE);
            consult_line.setVisibility(View.GONE);
        } else if (businessTypeNum == 2) {
            setIncludeTitle("帮忙");
            tv_title_content.setText("帮助内容");
            et_content.setHint("在此输入帮助内容");
            rewardtohelp.setVisibility(View.VISIBLE);
            dynamic_publishing.setVisibility(View.VISIBLE);
            btn_open_more.setVisibility(View.VISIBLE);
            dynamic_layout.setVisibility(View.GONE);
        } else if (businessTypeNum == 5) {
            setIncludeTitle("动态");
            tv_title_content.setText("在此输入动态内容");
            dynamic_publishing.setVisibility(View.GONE);
            rewardtohelp.setVisibility(View.GONE);
            btn_open_more.setVisibility(View.GONE);
            dynamic_layout.setVisibility(View.GONE);
            ll_more.setVisibility(View.GONE);
            isMoneyTypeRmb=false;
            line_dynamic.setVisibility(View.GONE);
        }
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

        missionBean = new MissionBean();
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


    private void extnStep() {
        //读写权限已开启
        if (photoList.get(indexposition).isaBoolean()) {
            List<ReleasePhotoBean> mlist = new ArrayList<>();
            for (int i = 0; i < photoList.size(); i++) {
                if (photoList.get(i).isaBoolean()) {
                    mlist.add(photoList.get(i));
                }
            }
            onclilk(mlist, indexposition);
        } else {
            switch (indexposition) {
                case 0:
                    popWinShare = new AlbumPoPubWindows(this, 3);
                    break;
                case 1:
                    popWinShare = new AlbumPoPubWindows(this, 2);
                    break;
                case 2:
                    popWinShare = new AlbumPoPubWindows(this, 1);
                    break;
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


    }

    private void onclilk(List<ReleasePhotoBean> mlist, int position) {


        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("photo", (Serializable) mlist);
        intent.putExtra("position", position);
        startActivity(intent);
        //Activity context = (Activity) this.context;
        //  context.overridePendingTransition(0, 0);
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
        Log.e("tag", manufacturer);
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("huawei".equalsIgnoreCase(manufacturer) || "Xiaomi".equalsIgnoreCase(manufacturer)) {
            return true;
        }
        return false;
    }


    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        businessType = argIntent.getStringExtra("EXTRA_PAGE_TYPE");

//        missionBean1 = (MissionBean) argIntent.getSerializableExtra("body");
//        if (missionBean1 != null) {
//            businessType = missionBean1.getBusinessType();
//        }

    }

    @Override
    protected void startLoad() {
        Log.e("tag", "没有拍照权限");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_consult;
    }

    @Override
    protected void initEvent() {
        //   btn_add_pic.setOnClickListener(this);
        btn_open_more.setOnClickListener(this);


        btn_publish.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        position.setOnClickListener(this);

        expression.setOnClickListener(this);
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
                        bond_price.setVisibility(View.VISIBLE);
                        text_view_xian.setVisibility(View.VISIBLE);
                        bond_price.setFocusable(true);
                        bond_price.setFocusableInTouchMode(true);
                        bond_price.requestFocus();

                        break;
                    case R.id.rb_isNeedBond_false:
                        isNeedBond = false;
                        bond_price.setVisibility(View.GONE);
                        text_view_xian.setVisibility(View.GONE);
                        break;
                }
            }
        });

        rg_isPosition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_isPosition_true:

                        isPosition = true;
                        break;
                    case R.id.rb_isPosition_false:
                        isPosition = false;
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

        //任务数量spin显示
        spin_mission_num.attachDataSource(Arrays.asList(mission_nums));
        //rb设置
        rg_money_type.check(R.id.rb_RMB);

        rg_isNeedBidding.check(R.id.rb_isNeedBidding_false);
        rg_isNeedBond.check(R.id.rb_isNeedBond_false);
        rg_isPosition.check(R.id.rb_isPosition_true);
        if (missionBean1 != null) {
            // showBean();
        }

    }


    /**
     * 设置显示在v上方(以v的左边距为开始位置)
     *
     * @param v
     */


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_add_pic:
//                if (lacksPermissions(this, permissionsREAD)) {//读写权限没开启
//
//                    ActivityCompat.requestPermissions(this, permissionsREAD, 0);
//
//                } else {
//
//
//                    //读写权限已开启
//
//                    ImgUtil.addPic(mActivity, 1, new ImgUtil.ChoosePic() {
//                        @Override
//                        public void pick(String imgPath) {
//                            imgPath1 = imgPath;
//                        }
//                    }, imgName);
//                }
//
//                break;
            case R.id.btn_open_more:
                if (!isOpenMore) {
                    isOpenMore = true;
                    ll_more.setVisibility(View.VISIBLE);

                    ll_more.setFocusable(true);
                    ll_more.setFocusableInTouchMode(true);
                    ll_more.requestFocus();
                    // btn_open_more.setVisibility(View.GONE);
                    statistics("More");
                } else {
                    isOpenMore = false;
                    ll_more.setVisibility(View.GONE);

                }
                break;

//            case R.id.btn_save:
//                //保存按钮
//                isPublish = false;
//                saveConsult();
//                break;
            case R.id.btn_publish:
                //发布按钮
                // isPublish = true;
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }

                saveConsult();
                break;
            case R.id.tv_date:

                try {
                    showTimeDialog();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.position:
                Intent intent = new Intent(this, ActivityShare.class);

                intent.putExtra("city", city);
                intent.putExtra("province", province);
                startActivityForResult(intent, 100);

                statistics("position");

                break;


        }
    }

    private void statistics(String eventId) {
        if (businessTypeNum == 1) {
            MobclickAgent.onEvent(this, "information" + eventId);
        } else if (businessTypeNum == 2) {
            MobclickAgent.onEvent(this, "rewardhelp" + eventId);
        }

    }

    private void showTimeDialog() throws ParseException {


        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //  Toast.makeText(this, getTime(date), Toast.LENGTH_SHORT).show();

                tv_date.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示

                .setTitleSize(20)//标题文字大小
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setLabel("年", "月", "日", "时", "分", "")//默认设置为年月日时分秒
                .build();

        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    private List<ReleasePhotoBean> photoListTwo = new ArrayList<>();

    private void saveConsult() {

        String describes = et_content.getText().toString();
        if (describes.isEmpty()) {
            Toast("请填写问题详情~", 3);
            return;
        }
        missionBean.setDescribes(describes);
        // 不弄String businessImgUrl;
//
//        if (!isHavePic) {
//            Toast("请添加图片~", 3);
//            return;
//        }

        missionBean.setBusinessImgUrl(imagePath);


        missionBean.setPhotoMap(photoMap);

        missionBean.setEndDate(chooseTime);
        if (businessTypeNum != 5) {
            String money = et_money.getText().toString();
            if (money.isEmpty() || Integer.parseInt(money) <= 0) {
                Toast("请输入悬赏金额~", 3);
                return;
            }
            missionBean.setPrice(money);
        } else {
            missionBean.setPrice( "1");
        }



        missionBean.setPaymentType(isMoneyTypeRmb ? "1" : "2");
        String title = et_title.getText().toString();
        missionBean.setTitle(title);
        missionBean.setBargainingType(isBargaining ? "1" : "0");
        missionBean.setPlaceName(position.getText().toString());

        String contact = et_contact.getText().toString();
        if (contact.equals("")) {

        } else {
            if (contact.length() != 11) {
                Toast("请输入正确联系方式~", 3);
                return;
            }
        }
        if (!isPosition) {
            statistics("territory");
        }
        missionBean.setContactsPhone(contact);
        missionBean.setBusinessNum(mission_num);
        missionBean.setBiddingType(isNeedBidding ? "1" : "0");
        missionBean.setUnareaType(isPosition ? "1" : "0");
        missionBean.setProductType(isNeedBidding ? "1" : "0"); //于上方重复
        missionBean.setBondType(isNeedBond ? "1" : "0");

        if (isNeedBond) {
            String baozenprice = bond_price.getText().toString();

            if (baozenprice == null || "".equals(baozenprice) || baozenprice.equals("0")) {
                Toast("请输入保证金~", 3);
                return;
            } else {
                missionBean.setDeposit(Integer.parseInt(baozenprice));
            }
            statistics("bond");
        } else {

        }
        //非填写
        missionBean.setCreateUserId(user.getUserId());
        missionBean.setBusinessType(businessType);//信息咨询
        //未必须
        missionBean.setLatitude(latitude + "");
        missionBean.setLongitude(longitude + "");
        List<String> circleId = new ArrayList<>();
        if (labList.size() < 4) {
            labList.remove(labList.size() - 1);
        }
        for (int i = 0; i < labList.size(); i++) {
            if (labList.get(i).isSelected()) {
                circleId.add(labList.get(i).getCircleId());
            }
        }
        missionBean.setCircleIds(circleId);
        photoListTwo.clear();
        if (isMoneyTypeRmb) {
            for (int i = 0; i < photoList.size(); i++) {
                if (!photoList.get(i).isaBoolean()) {

                } else {
                    photoListTwo.add(photoList.get(i));
                }
            }
            Intent intent = new Intent(this, Zhifu.class);
            intent.putExtra("payment", true);
            intent.putExtra("release", missionBean);
            intent.putExtra("imgurl", (Serializable) photoListTwo);
            startActivityForResult(intent, 100);
        } else {


            dialog2 = new UICustomDialog2(this, "确认支付", "3");
            dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog2.hide();
                }
            });
            dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    missionBean.setPayType(3);
                    DialogUtil.showProgress(ConsultActivity.this);
                    for (int i = 0; i < photoList.size(); i++) {
                        if (!photoList.get(i).isaBoolean()) {

                        } else {

                            photoListTwo.add(photoList.get(i));
                        }
                    }
                    if (photoListTwo.size() == 0) {

                        presenter.saveConsult(missionBean);
                    } else {
                        photoListTwo.clear();
                        for (int i = 0; i < photoList.size(); i++) {
                            if (photoList.get(i).isaBoolean()) {
                                photoListTwo.add(photoList.get(i));
                            }
                        }
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
                        Luban.with(ConsultActivity.this)
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
                                            presenter.saveImagurlConsult(photoListTwo);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // TODO 当压缩过程出现问题时调用
                                        Log.e("qqq", e.getMessage() + "    aaaaaaa");
                                    }
                                }).launch();


                    }

                    dialog2.hide();

                }
            });
            dialog2.show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "requestCode" + requestCode + "resultCode" + resultCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imagePath = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                Luban.with(this)
                        .load(imagePath)
                        .ignoreBy(100)
                        .setTargetDir(imagePath)
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

                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();
                photoList.add(new ReleasePhotoBean(imagePath, true));
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


            }
        } else if (requestCode == 10) {
            dialog2.hide();
            getLocationClient();
        } else if (requestCode == 100) {
            if (data != null) {
                if (data.getStringExtra("name") != null) {
                    city = data.getStringExtra("city");
                    latitude = data.getDoubleExtra("latitude", 0);
                    longitude = data.getDoubleExtra("longitude", 0);
                    Log.e("tag", latitude + "sadasdasdsa" + longitude);
                    missionBean.setLatitude(latitude + "");
                    missionBean.setLongitude(longitude + "");
                    position.setText(data.getStringExtra("name"));
                }
            }
        } else if (resultCode == 100) {
            setResult(100);
            finish();
        } else {
            imagePath = "";
            expression.setVisibility(View.GONE);
        }

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
    public void saveSuc(String businessId) {

        //  presenter.wxAppletPay("描述", "1", "==",businessId,user.getUserId()+"");

        Toast("发布成功", 1);
        setResult(100);
        finish();


    }

    @Override
    public void publishSuc() {
        Toast("发布成功", 1);
        setResult(100);
        finish();
    }

    @Override
    public void saveImgSuc(String imgurl) {

        missionBean.setBusinessImgUrl(imgurl);
        presenter.saveConsult(missionBean);

    }

    @Override
    public void receiptSuc() {

    }

    @Override
    public void wxAppletPay(PayInfoBean payInfoBean) {

    }

    @Override
    public void wxAppletPayResult(String result) {

    }

    @Override
    public void wxAppletPayError(String errorresult) {

    }

    @Override
    public void onError(String error) {
        if (error.contains("钻石余额不足")) {
            TaskDetailsDialog detailsDialog = new TaskDetailsDialog(this, 10, 0);
            addThereBackground(detailsDialog);
            detailsDialog.show();
        } else {
            ToastUtils.showTestShort(this, error);
        }


    }

    private void addThereBackground(TaskDetailsDialog taskDetailsDialog) {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样
        taskDetailsDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);

            }
        });
    }

    public AMapLocation aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    private double latitude;
    private double longitude;

    private void getLocationClient() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.e("llx", aMapLocation.toStr());

                if (aMapLocation.getErrorCode() == 0) {
                    if (aMapLocation.getAoiName() == null || aMapLocation.getAoiName().equals("")) {
                        position.setText(aMapLocation.getPoiName());
                    } else {
                        position.setText(aMapLocation.getAoiName());
                    }

                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    city = aMapLocation.getCity();
                    province = aMapLocation.getProvince();


                } else {
                    position.setText("定位失败");
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
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();


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

    private void getReadydialog() {


        View vPopupWindow = getLayoutInflater().inflate(R.layout.my_circles, null, false);//引入弹窗布局
        if (ymCircles == null) {
            ymCircles = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }
        //设置进出动画
        ymCircles.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        include_title_by = findViewById(R.id.include_title_by);
        if (attentionList == null) {
            attentionList = new ArrayList<>();
        }
        if (myCiclesAdapter == null) {
            myCiclesAdapter = new MyCiclesAdapter(this, attentionList);
        }
        GridView colose_screen = vPopupWindow.findViewById(R.id.screening_criteria);

        colose_screen.setAdapter(myCiclesAdapter);

        colose_screen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CicleBean.ReturnObjectBean.RowsBean rowsBean = attentionList.get(position);
                if (rowsBean.isSelected()) {
                    rowsBean.setSelected(false);
                } else {
                    rowsBean.setSelected(true);
                }
                myCiclesAdapter.notifyDataSetChanged();
            }
        });
        ymCircles.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                labList.clear();
                for (int i = 0; i < attentionList.size(); i++) {
                    boolean selected = attentionList.get(i).isSelected();
                    if (selected) {
                        labList.add(attentionList.get(i));
                    } else {
                    }
                }

                if (labList.size() == 0) {
                    CicleBean.ReturnObjectBean.RowsBean rowsBean = new CicleBean.ReturnObjectBean.RowsBean();
                    rowsBean.setAbbreviation("全部");
                    CicleBean.ReturnObjectBean.RowsBean addTo = new CicleBean.ReturnObjectBean.RowsBean();
                    addTo.setAbbreviation(" ＋ ");
                    labList.add(rowsBean);
                    labList.add(addTo);
                    cloud_tag.setLabels(labList, new LabelsView.LabelTextProvider<CicleBean.ReturnObjectBean.RowsBean>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, CicleBean.ReturnObjectBean.RowsBean data) {
                            if (position == labList.size() - 1) {
                                label.setBackground(getResources().getDrawable(R.drawable.shape_gray_home));
                            }
                            return data.getAbbreviation();
                        }
                    });
                } else if (labList.size() < 4) {

                    CicleBean.ReturnObjectBean.RowsBean addTo = new CicleBean.ReturnObjectBean.RowsBean();
                    addTo.setAbbreviation(" ＋ ");
                    labList.add(addTo);
                    cloud_tag.setLabels(labList, new LabelsView.LabelTextProvider<CicleBean.ReturnObjectBean.RowsBean>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, CicleBean.ReturnObjectBean.RowsBean data) {
                            if (position == labList.size() - 1) {
                                label.setBackground(getResources().getDrawable(R.drawable.shape_gray_home));
                            }
                            return data.getAbbreviation();
                        }
                    });
                } else {
                    cloud_tag.setLabels(labList, new LabelsView.LabelTextProvider<CicleBean.ReturnObjectBean.RowsBean>() {
                        @Override
                        public CharSequence getLabelText(TextView label, int position, CicleBean.ReturnObjectBean.RowsBean data) {
                            return data.getAbbreviation();
                        }
                    });
                }
            }
        });
    }

    private void attenTion() {
        MyAPP.getInstance().getApi().circleMyList(user.getUserId()).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {


                CicleBean beanFromJson = JsonUtils.getBeanFromJson(body, CicleBean.class);

                CicleBean.ReturnObjectBean returnObject = beanFromJson.getReturnObject();

                List<CicleBean.ReturnObjectBean.RowsBean> rows = returnObject.getRows();
                if (attentionList != null) {
                    attentionList.clear();
                    attentionList.addAll(rows);
                    myCiclesAdapter.notifyDataSetChanged();
                }

            }


        });

    }

    private void display() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] mLocation = new int[2];
            include_title_by.getLocationInWindow(mLocation);
            ymCircles.showAtLocation(include_title_by, Gravity.NO_GRAVITY, 0, mLocation[1] + include_title_by.getHeight());
        } else {
            ymCircles.showAsDropDown(include_title_by, 0, 0);
        }

    }
}
