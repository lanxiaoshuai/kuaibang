package com.witkey.witkeyhelp.view.impl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.donkingliang.labels.LabelsView;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.HistoryAdapter;
import com.witkey.witkeyhelp.adapter.NearbyAdapter;
import com.witkey.witkeyhelp.adapter.SearchAdapter;

import com.witkey.witkeyhelp.bean.PositionvBean;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;

import com.witkey.witkeyhelp.util.ListDataSave;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jie on 2020/6/1.
 */

public class SurroundingActivity extends AppCompatActivity implements View.OnClickListener, Inputtips.InputtipsListener, AdapterView.OnItemClickListener {


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


    private double latitude;
    private double longitude;
    private List<String> mNearblist;
    private NearbyAdapter nearbyAdapter;
    private GridView popular_cities;

    private boolean selectCity;
    private TextView earbynpopular;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surrounding);


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

                    Inputtips inputtips = new Inputtips(SurroundingActivity.this, inputtipsQuery);//定义一个输入提示对象，传入当前上下文和搜索对象
                    inputtips.setInputtipsListener(SurroundingActivity.this);//设InputtipsQuery置输入提示查询的监听，实现输入提示的监听方法onGetInputtips()
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
        popular_cities = findViewById(R.id.popular_cities);
        earbynpopular = findViewById(R.id.earbynpopular);

        text_city.setOnClickListener(this);
        relocation.setOnClickListener(this);
        ctual_location.setOnClickListener(this);

        getLocationClient();

        loadFujin();


        history = new ArrayList<>();
        mNearblist = new ArrayList<>();
        mNearblist.add("上海");
        mNearblist.add("烟台");
        mNearblist.add("北京");
        mNearblist.add("杭州");
        mNearblist.add("云南");
        mNearblist.add("昆山");
        mNearblist.add("深圳");
        mNearblist.add("石家庄");
        nearbyAdapter = new NearbyAdapter(this, mNearblist);

        popular_cities.setAdapter(nearbyAdapter);

        popular_cities.setVisibility(View.GONE);
        search_history.setVisibility(View.VISIBLE);


        popular_cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                city = mNearblist.get(position);
                text_city.setText(city);
                popular_cities.setVisibility(View.GONE);
                search_history.setVisibility(View.VISIBLE);
                selectCity = false;
                earbynpopular.setText("附近地址");
                history.clear();
                historicalPositionAdapter.notifyDataSetChanged();
            }
        });

        historicalPositionAdapter = new HistoryAdapter(this, history);
        search_history.setAdapter(historicalPositionAdapter);
        search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                popupWindow.dismiss();


                finish();
            }
        });


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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_edit_delete:
                inputText.setText("");

                break;
            case R.id.text_city:
                inputText.setText("");
                popular_cities.setVisibility(View.VISIBLE);
                search_history.setVisibility(View.GONE);
                selectCity = true;
                earbynpopular.setText("热门地址");
                break;
            case R.id.relocation:


                getLocationClient();


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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (selectCity) {
                popular_cities.setVisibility(View.GONE);
                search_history.setVisibility(View.VISIBLE);
                selectCity = false;
                earbynpopular.setText("附近地址");
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        popupWindow.dismiss();

        finish();
    }

    /**
     * 周边搜索
     */
    private void nearby() {
        PoiSearch.Query query = new PoiSearch.Query("", "", city);
        query.setPageSize(10);

        PoiSearch poiSearch = new PoiSearch(this, query);

        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {

                ArrayList<PoiItem> pois = poiResult.getPois();
                for (int j = 0; j < pois.size(); j++) {
                    LatLonPoint latLonPoint = pois.get(j).getLatLonPoint();

                    history.add(new PositionvBean(pois.get(j).toString(), "", latLonPoint.getLatitude(), latLonPoint.getLongitude()));
                }
                historicalPositionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {


            }
        });
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,
                longitude), 1000));//设置周边搜索的中心点以及半径
        poiSearch.searchPOIAsyn();
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
                    city = aMapLocation.getCity();

                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    text_city.setText(city);
                    nearby();
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
