package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.bean.PositionvBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2019/5/28.
 */

public class ListDataSave {


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public  void setDataList(String tag, List<com.witkey.witkeyhelp.bean.PositionvBean> datalist) {
        if (null == datalist )
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public List<com.witkey.witkeyhelp.bean.PositionvBean> getDataList(String tag) {
        List<com.witkey.witkeyhelp.bean.PositionvBean> datalist=new ArrayList<com.witkey.witkeyhelp.bean.PositionvBean>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<com.witkey.witkeyhelp.bean.PositionvBean>>() {
        }.getType());
        return datalist;

    }
    /**
     * 获取List
     * @param tag
     * @return
     */
    public List<CicleBean.ReturnObjectBean.RowsBean> getCicleList(String tag) {
        List<CicleBean.ReturnObjectBean.RowsBean> datalist=new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<CicleBean.ReturnObjectBean.RowsBean>>() {
        }.getType());
        return datalist;

    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public  void setCicleList(String tag, List<CicleBean.ReturnObjectBean.RowsBean> datalist) {
        if (null == datalist )
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public List<String> getSearchList(String tag) {
        List<String> datalist=new ArrayList<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        return datalist;

    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public  void setSearchList(String tag, List<String> datalist) {
        if (null == datalist )
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }
}
