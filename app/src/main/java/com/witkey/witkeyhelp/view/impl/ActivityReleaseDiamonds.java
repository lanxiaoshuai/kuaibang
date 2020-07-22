package com.witkey.witkeyhelp.view.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.ReleasePhotoAdapter;
import com.witkey.witkeyhelp.adapter.UnpublishFeedbackAdapter;
import com.witkey.witkeyhelp.bean.AdDetailsBean;
import com.witkey.witkeyhelp.bean.JsonBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.bean.UnpublishFeedbackBean;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.CountDownUtil;
import com.witkey.witkeyhelp.util.DoubleUtil;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.GetJsonDataUtil;
import com.witkey.witkeyhelp.util.ImgUtil;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.RealPathFromUriUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;
import com.witkey.witkeyhelp.widget.NoScrollListview;

import org.json.JSONArray;

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
 * Created by jie on 2020/4/3.
 */

public class ActivityReleaseDiamonds extends BaseActivity implements View.OnClickListener, View.OnTouchListener {

    private RecyclerView photolist;
    private List<ReleasePhotoBean> photoList;
    private ReleasePhotoAdapter photoAdapter;
    private boolean isAddPic;
    private AlbumPoPubWindows popWinShare;

    private RadioGroup rg_money_type; //金额类型
    private boolean isMoneyTypeRmb = true; //悬赏金额类型是否为rmb
    private EditText number;
    private EditText issue_title;
    private EditText issue_content;
    private RelativeLayout whole_country;
    private RelativeLayout provinces;
    private RelativeLayout mylocation;
    private Button btn_publish;

    private int regiontypr = 1;
    private CheckBox cb_is;
    private CheckBox cb_is_two;
    private CheckBox cb_is_there;
    private EditText distance;
    private String imagePath;
    private TextView position;
    private String city;
    private TextView text_provinces;
    private String imaurl;
    private PopupWindow cashPopupWindow;
    private Intent intent;
    private List<ReleasePhotoBean> photoListTwo = new ArrayList<>();
    private UICustomDialog2 dialog2;
    private ImageView map_icon;
    private MapView mMapView;
    private AMap aMap;
    private int kilometersk = 0;
    private Circle circle;
    private MyLocationStyle myLocationStyle;
    private ScrollView scrollView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_releasediamonds);
        setIncludeTitle("发布");
        photolist = findViewById(R.id.photolist);
        number = findViewById(R.id.number);
        issue_title = findViewById(R.id.issue_title);

        issue_title.post(new Runnable() {
            @Override
            public void run() {
                issue_title.requestFocus();
            }
        });
        issue_content = findViewById(R.id.issue_content);
        distance = findViewById(R.id.distance);


        whole_country = findViewById(R.id.whole_country);
        provinces = findViewById(R.id.provinces);
        mylocation = findViewById(R.id.mylocation);
        btn_publish = findViewById(R.id.btn_publish);

        cb_is = findViewById(R.id.cb_is);
        cb_is_two = findViewById(R.id.cb_is_two);
        cb_is_there = findViewById(R.id.cb_is_there);
        position = findViewById(R.id.position);
        text_provinces = findViewById(R.id.text_provinces);

        scrollView = findViewById(R.id.scrollView);


        whole_country.setOnClickListener(this);
        provinces.setOnClickListener(this);
        mylocation.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
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


        map_icon = findViewById(R.id.map_icon);
        mMapView = findViewById(R.id.map);
        mMapView.onCreate(getSavedInstanceState());
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色

        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色

        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


        distance.setOnTouchListener(this);
        issue_content.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(10000)});

        issue_title.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(10000)});

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

        photoAdapter.setOnItemPhotoClickListener(new ReleasePhotoAdapter.OnItemPhotoClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                extnStep(position);
            }
        });
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

        initJsonData();
        loadData();
        load();
    }

    @SuppressLint("Range")
    private void editLoaction(double latitude, double longitude, int kilometers) {
        LatLng latLng = new LatLng(latitude, longitude);

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        if (circle != null) {
            circle.remove();
        }
        circle = aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(kilometers * 1000).
                fillColor(Color.argb(50, 1, 1, 1)).
                strokeColor(Color.argb(50, 1, 1, 1)).
                strokeWidth(15));

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    private String imageTranslateUri(int resId) {

        Resources r = getResources();
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + r.getResourcePackageName(resId) + "/"
                + r.getResourceTypeName(resId) + "/"
                + r.getResourceEntryName(resId));

        return uri.toString();
    }

    private void loadData() {
        rg_money_type = findViewById(R.id.rg_money_type);
        rg_money_type.check(R.id.rb_RMB);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.whole_country:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                regiontypr = 1;
                cb_is.setChecked(true);
                cb_is_two.setChecked(false);
                cb_is_there.setChecked(false);
                distance.clearFocus();//失去焦点
                hideInput();
                position.setOnClickListener(null);
                position.setClickable(false);
                distance.setText("");
                mMapView.setVisibility(View.GONE);
                map_icon.setVisibility(View.VISIBLE);
                break;
            case R.id.provinces:

                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                initOptionPicker();
                break;
            case R.id.mylocation:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                position.setOnClickListener(this);
                position.setClickable(true);
                distance.requestFocus();//获取焦点
                distance.setFocusable(true);
                showInput(distance);
                regiontypr = 3;
                cb_is.setChecked(false);
                cb_is_two.setChecked(false);
                cb_is_there.setChecked(true);
                mMapView.setVisibility(View.VISIBLE);
                map_icon.setVisibility(View.GONE);
//                scrollView.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                    }
//                });
                break;
            case R.id.btn_publish:
                photoListTwo.clear();
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (issue_title.getText().toString().equals("")) {
                    ToastUtils.showTestShort(this, "请输入标题");
                    return;
                }
                if (issue_content.getText().toString().equals("")) {
                    ToastUtils.showTestShort(this, "请输入内容");
                    return;
                }
                if (number.getText().toString().equals("")) {
                    ToastUtils.showTestShort(this, "请选择人数");
                    return;
                }
                if (Integer.parseInt(number.getText().toString()) <= 0) {
                    ToastUtils.showTestShort(this, "请选择人数");
                    return;
                }
                if (regiontypr == 3) {

                    if (distance.getText().toString().equals("")) {
                        ToastUtils.showTestShort(this, "请输入公里数");
                        return;
                    }
                    if (Integer.parseInt(distance.getText().toString()) <= 0) {
                        ToastUtils.showTestShort(this, "请输入公里数");
                        return;
                    }
                }
                if (regiontypr == 1) {
                    region = "全国";
                } else if (regiontypr == 2) {
                    region = text_provinces.getText().toString();
                }
                for (int i = 0; i < photoList.size(); i++) {
                    if (!photoList.get(i).isaBoolean()) {

                    } else {
                        photoListTwo.add(photoList.get(i));
                    }
                }

                intent = new Intent(this, ActivityPayment.class);
                intent.putExtra("content", issue_content.getText().toString());
                intent.putExtra("title", issue_title.getText().toString());
                intent.putExtra("imgurl", (Serializable) photoListTwo);
                intent.putExtra("putNum", number.getText().toString());
                intent.putExtra("putArea", region);
                intent.putExtra("placeName", Ownlocation);
                intent.putExtra("putScope", distance.getText().toString());
                intent.putExtra("longitude", longitude + "");
                intent.putExtra("latitude", latitude + "");
                intent.putExtra("putLocation", position.getText().toString());
                intent.putExtra("regiontypr", regiontypr);
                intent.putExtra("type", 1);
                intent.putExtra("payment", isMoneyTypeRmb);
                startActivityForResult(intent, 1314);

//                } else {
//                    showCashWithdrawalAnimation();
//                }

                break;
            case R.id.position:
                intent = new Intent(this, ActivityShare.class);
                intent.putExtra("city", city);
                intent.putExtra("province", province);
                startActivityForResult(intent, 100);
                distance.requestFocus();//获取焦点
                break;
        }
    }

    //输入框初始值
    private int num = 0;
    //输入框最大值
    public int mMaxNum = 20;
    private String region;

    private void loadDataFabu() {
        if (regiontypr == 1) {
            region = "全国";
        } else if (regiontypr == 2) {
            region = text_provinces.getText().toString();
        }
        MyAPP.getInstance().getApi().advertisingAdd(user.getUserId(), "0", issue_content.getText().toString(), issue_title.getText().toString(), imaurl, number.getText().toString(), region, Ownlocation, distance.getText().toString(), Integer.parseInt(number.getText().toString()) + Integer.parseInt(number.getText().toString()) * 0.1, longitude + "", latitude + "", position.getText().toString(), regiontypr).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {
                DialogUtil.dismissProgress();
                Log.e("tag", body);
                cashPopupWindow.dismiss();
                setResult(1314);
                finish();
            }


        });
    }

    private void load() {

//mPublishEdDesc是EditText
        issue_title.addTextChangedListener(new TextWatcher() {
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

                selectionStart = issue_title.getSelectionStart();
                selectionEnd = issue_title.getSelectionEnd();
                //判断大于最大值
                if (wordNum.length() > mMaxNum) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    issue_title.setText(s);
                    issue_title.setSelection(issue_title.getText().toString().length());//设置光标在最后
                    //吐司最多输入300字

                }
            }
        });
        distance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    kilometersk = 0;
                } else {
                    kilometersk = Integer.parseInt(s.toString());
                }

                editLoaction(latitude, longitude, kilometersk);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    //移动到指定经纬度
    private void initAMap() {
        LatLng latLng = new LatLng(latitude, longitude);
        CameraPosition cameraPosition = new CameraPosition(latLng, 15, 0, 30);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        aMap.moveCamera(cameraUpdate);
        drawMarkers(latLng );
    }
    //画定位标记图
    public void drawMarkers(    LatLng latLng ) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .draggable(true);
        Marker marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imagePath = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());

                photoList.add(new ReleasePhotoBean(imagePath, true));

                photoAdapter.notifyDataSetChanged();

            } else if (requestCode == 273) {

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
                    position.setText(data.getStringExtra("name"));
                    editLoaction(latitude, longitude, kilometersk);
                    initAMap();
                }
            }
        } else if (requestCode == 1314) {
            setResult(1314);
            finish();
        }
    }


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    private double latitude;
    private double longitude;
    private String province;
    private String Ownlocation;

    private void getLocationClient() {
        mLocationListener = new AMapLocationListener() {


            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.e("llx", aMapLocation.toStr());

                if (aMapLocation.getErrorCode() == 0) {
                    if (aMapLocation.getAoiName() == null || aMapLocation.getAoiName().equals("")) {
                        position.setText(aMapLocation.getPoiName());
                        Ownlocation = aMapLocation.getPoiName();
                    } else {
                        position.setText(aMapLocation.getAoiName());
                        Ownlocation = aMapLocation.getAoiName();
                    }

                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    city = aMapLocation.getCity();
                    province = aMapLocation.getProvince();

                    editLoaction(aMapLocation.getLatitude(), aMapLocation.getLongitude(), kilometersk);

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


    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson("province.json", this);//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *parseData
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return detail;
    }


    private void initOptionPicker() {//条件选择器初始化


        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

//                String opt3tx = options2Items.size() > 0
//                        && options3Items.get(options1).size() > 0
//                        && options3Items.get(options1).get(options2).size() > 0 ?
//                        options3Items.get(options1).get(options2).get(options3) : "";

                // String tx = opt1tx + opt2tx + opt3tx;

                if (opt2tx.equals("全省")) {

                    text_provinces.setText(opt1tx);
                } else {
                    text_provinces.setText(opt2tx);
                }
                distance.setText("");
                regiontypr = 2;
                cb_is.setChecked(false);
                cb_is_two.setChecked(true);
                cb_is_there.setChecked(false);
                distance.clearFocus();//失去焦点
                position.setOnClickListener(null);
                position.setClickable(false);
                hideInput();

                mMapView.setVisibility(View.GONE);
                map_icon.setVisibility(View.VISIBLE);

            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        //pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        //   pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:   //按下
                position.setOnClickListener(this);
                position.setClickable(true);
                distance.requestFocus();//获取焦点
                distance.setFocusable(true);
                // showInput(distance);
                regiontypr = 3;
                cb_is.setChecked(false);
                cb_is_two.setChecked(false);
                cb_is_there.setChecked(true);
                mMapView.setVisibility(View.VISIBLE);
                map_icon.setVisibility(View.GONE);
//                scrollView.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                    }
//                });
                break;
            case MotionEvent.ACTION_MOVE:   //移动
                break;
            case MotionEvent.ACTION_UP:     //抬起
                break;
            default:
                break;
        }
        return false;
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
