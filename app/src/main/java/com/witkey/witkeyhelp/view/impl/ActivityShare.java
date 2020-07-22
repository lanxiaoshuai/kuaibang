package com.witkey.witkeyhelp.view.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.HistoryAdapter;
import com.witkey.witkeyhelp.adapter.SearchAdapter;
import com.witkey.witkeyhelp.bean.JsonBean;
import com.witkey.witkeyhelp.bean.PositionvBean;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.GetJsonDataUtil;
import com.witkey.witkeyhelp.util.ListDataSave;
import com.witkey.witkeyhelp.util.SharedPreferencesUtil;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.takevideo.utils.SPUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocatedCity;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

/**
 * Created by jie on 2020/1/20.
 */

public class ActivityShare extends BaseActivity implements View.OnClickListener, Inputtips.InputtipsListener, AdapterView.OnItemClickListener {

    private EditText inputText;
    private ImageView buttonDelete;
    private SearchAdapter searchAdapter;
    private ListView search_list;
    private List<PositionvBean> searchList;
    private PopupWindow popupWindow;
    private LinearLayout search_box;
    private TextView text_city;
    private String city;
    private String province;
    private TextView ctual_location;
    private TextView relocation;
    private ListView search_history;
    private HistoryAdapter historicalPositionAdapter;
    private List<PositionvBean> history;
    private ListDataSave listDataSave;
    private LinearLayout button_dibu;
    private LinearLayout no_address;
    private ImageView delete_list;
    private RelativeLayout lishibiaot;
    private double latitude;
    private double longitude;
    private String locationCity;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_search);
        setIncludeTitle("选择地理位置");


        initJsonData();
        inputText = findViewById(R.id.search_edit_text);
        ctual_location = findViewById(R.id.ctual_location);
        buttonDelete = findViewById(R.id.search_edit_delete);
        search_history = findViewById(R.id.search_history);
        relocation = findViewById(R.id.relocation);
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")) {
                    buttonDelete.setVisibility(View.VISIBLE);
                    String content = charSequence.toString().trim();//获取自动提示输入框的内容
                    InputtipsQuery inputtipsQuery = new InputtipsQuery(content, city);//初始化一个输入提示搜索对象，并传入参数
                    inputtipsQuery.setCityLimit(true);//将获取到的结果进行城市限制筛选

                    Inputtips inputtips = new Inputtips(ActivityShare.this, inputtipsQuery);//定义一个输入提示对象，传入当前上下文和搜索对象
                    inputtips.setInputtipsListener(ActivityShare.this);//设InputtipsQuery置输入提示查询的监听，实现输入提示的监听方法onGetInputtips()
                    inputtips.requestInputtipsAsyn();//输入查询提示的异步接口实现
                } else {
                    buttonDelete.setVisibility(View.GONE);
                    button_dibu.setVisibility(View.VISIBLE);
                    popupWindow.dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        buttonDelete.setOnClickListener(this);
        search_box = findViewById(R.id.search_box);
        text_city = findViewById(R.id.text_city);
        button_dibu = findViewById(R.id.button_dibu);
        delete_list = findViewById(R.id.delete_list);
        lishibiaot = findViewById(R.id.lishibiaot);
        delete_list.setOnClickListener(this);
        text_city.setOnClickListener(this);
        relocation.setOnClickListener(this);
        ctual_location.setOnClickListener(this);
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
        loadFujin();

        listDataSave = new ListDataSave(this, "kuaibang");
        history = listDataSave.getDataList("HISTORY");
        if (history == null) {
            history = new ArrayList<>();

        } else if (history.size() == 0) {
            lishibiaot.setVisibility(View.GONE);
        }
        Collections.reverse(history);//对histories 集合中的数据进行倒叙排序
        historicalPositionAdapter = new HistoryAdapter(this, history);
        search_history.setAdapter(historicalPositionAdapter);
        search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PositionvBean positionvBean = history.get(position);
                history.remove(positionvBean);
                Collections.reverse(history);
                history.add(positionvBean);
                popupWindow.dismiss();
                listDataSave.setDataList("HISTORY", history);

                String name = positionvBean.getName();
                Intent i = new Intent();
                i.putExtra("name", name);
                i.putExtra("city", text_city.getText().toString());
                i.putExtra("latitude", positionvBean.getLatitude());
                i.putExtra("longitude", positionvBean.getLongitude());
                setResult(100, i);
                finish();
            }
        });
        city = getIntent().getStringExtra("city");
        province = getIntent().getStringExtra("province");
        text_city.setText(city);
    }

    private void showLocationMissingPermissionDialog() {
        final UICustomDialog2 dialog2 = new UICustomDialog2(this, "GPS未打开,去开启", "3");
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


    @SuppressLint("WrongConstant")
    private void loadFujin() {
        View share_popub = LayoutInflater.from(this).inflate(R.layout.list_poi, null);
        search_list = share_popub.findViewById(R.id.search_list);
        no_address = share_popub.findViewById(R.id.no_address);
        search_list.setOnItemClickListener(this);
        if (popupWindow == null) {
            popupWindow = new PopupWindow(share_popub, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }

        popupWindow.setContentView(share_popub);
        //设置宽度

        //设置显示隐藏动画


        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //给PopupWindow的window窗口设置软键盘展示属性
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ColorDrawable dw = new ColorDrawable(0xb000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(false);
    }


    @Override
    /*
     输入提示的回调方法
     参数1：提示列表
     参数2：返回码
     */
    public void onGetInputtips(List<Tip> list, int returnCode) {
        if (returnCode == AMapException.CODE_AMAP_SUCCESS) {//如果输入提示搜索成功
            popupWindow.showAsDropDown(search_box);
            searchList = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {

                String district = list.get(i).getDistrict();

                if (list.get(i).getPoint() == null) {

                } else {
                    PositionvBean positionvBean = new PositionvBean();
                    positionvBean.setName(list.get(i).getName() + "");

                    if (list.get(i).getAddress().equals("")) {
                        positionvBean.setAddress(list.get(i).getDistrict() + "");
                    } else {
                        positionvBean.setAddress(district + list.get(i).getAddress() + "");
                    }

                    positionvBean.setLatitude(list.get(i).getPoint().getLatitude());
                    positionvBean.setLongitude(list.get(i).getPoint().getLongitude());
                    searchList.add(positionvBean);
                }


            }

            searchAdapter = new SearchAdapter(this, searchList);
            search_list.setAdapter(searchAdapter);//为listview适配
            //新建一个适配器
            button_dibu.setVisibility(View.GONE);

            if (searchList.size() == 0) {
                no_address.setVisibility(View.VISIBLE);

            } else {
                no_address.setVisibility(View.GONE);
            }
        }
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
        String JsonData = new GetJsonDataUtil().getJson("provincerelease.json", this);//获取assets目录下的json文件数据

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

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;

                city = opt2tx;

                text_city.setText(city);
                //  Toast.makeText(JsonDataActivity.this, tx, Toast.LENGTH_SHORT).show();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_edit_delete:
                inputText.setText("");

                break;
            case R.id.text_city:
                inputText.setText("");
//                if (popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                }
//                List<HotCity> hotCities = new ArrayList<>();
//                hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
//                hotCities.add(new HotCity("上海", "上海", "101020100"));
//                hotCities.add(new HotCity("广州", "广东", "101280101"));
//                hotCities.add(new HotCity("深圳", "广东", "101280601"));
//                hotCities.add(new HotCity("杭州", "浙江", "101210101"));
//                CityPicker.from(this) //activity或者fragment
//                        .enableAnimation(true)    //启用动画效果，默认无
//                        .setLocatedCity(new LocatedCity(locationCity, locationCity, "0")).setOnPickListener(new OnPickListener() {
//                    @Override
//                    public void onPick(int position, City data) {
//                        city = data.getName();
//                        //Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
//                        text_city.setText(data.getName());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        //  Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onLocate() {
//
////                        boolean miui = isMIUI();
////                        if (miui) {
////                            boolean oPen = GPSIsOpenUtil.isOPen(ActivityShare.this);
////                            if (oPen) {
////                                getLocationClient();
////                            } else {
////                                showLocationMissingPermissionDialog();
////                            }
////                        } else {
////
////                            getLocationClient();
////                        }
//                    }
//                }).show();
                initOptionPicker();
                break;
            case R.id.relocation:


                boolean miui = isMIUI();
                if (miui) {
                    boolean oPen = GPSIsOpenUtil.isOPen(ActivityShare.this);
                    if (oPen) {
                        ctual_location.setText("定位中·");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getLocationClient();

                            }
                        }, 1000);

                    } else {
                        showLocationMissingPermissionDialog();
                    }
                } else {
                    ctual_location.setText("定位中·");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getLocationClient();
                        }
                    }, 1000);
                }
                break;
            case R.id.delete_list:
                history.clear();
                listDataSave.setDataList("HISTORY", history);
                lishibiaot.setVisibility(View.GONE);
                break;
            case R.id.ctual_location:

                popupWindow.dismiss();
                String name = ctual_location.getText().toString();
                Intent i = new Intent();
                i.putExtra("name", name);
                i.putExtra("city", text_city.getText().toString());
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);
                setResult(100, i);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean boo = false;
        PositionvBean positionvBean = searchList.get(position);
        for (int i = 0; i < history.size(); i++) {
            if (positionvBean.getName().equals(history.get(i).getName())) {
                boo = true;
            }
        }
        if (boo) {
            history.remove(positionvBean);
        } else {
            Collections.reverse(history);//对histories 集合中的数据进行倒叙排序
            history.add(positionvBean);
        }

        listDataSave.setDataList("HISTORY", history);
        popupWindow.dismiss();
        String name = positionvBean.getName();
        Intent i = new Intent();
        i.putExtra("name", name);
        i.putExtra("city", text_city.getText().toString());
        i.putExtra("latitude", positionvBean.getLatitude());
        i.putExtra("longitude", positionvBean.getLongitude());
        setResult(100, i);
        finish();
    }


    public AMapLocation aMap;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void getLocationClient() {
        MobclickAgent.onEvent(this, "relocate");
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    if (aMapLocation.getAoiName() == null || "".equals(aMapLocation.getAoiName())) {
                        ctual_location.setText(aMapLocation.getPoiName() + "");
                    } else {
                        ctual_location.setText(aMapLocation.getAoiName() + "");
                    }
                    locationCity = aMapLocation.getCity();
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();

                } else {
                    ctual_location.setText("定位失败");
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                getLocationClient();
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
