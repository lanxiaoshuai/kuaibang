package com.witkey.witkeyhelp.util;

import android.net.Uri;

import com.google.gson.Gson;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.bean.PagingResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author lingxu
 * @date 2019/7/12 13:24
 * @description json工具类
 */
public class JSONUtil {
    /**
     * 获得值
     */
    public static String getValueToString(String jsonStr, String key) {
        if (jsonStr == null || jsonStr.trim().equals(""))
            return null;
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            return jsonObj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得值到整数
     */
    public static int getValueToInt(String jsonStr, String key) {

        if (jsonStr == null || jsonStr.trim().equals(""))
            return -1;

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            return jsonObj.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getEncode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "utf-8");
    }


    public static <T> PagingResponse<T> fromJsonObjectList(Gson gson, String reader, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(PagingResponse.class, new Class[]{clazz});
        return gson.fromJson(reader, type);
    }
    //update date: 2019/7/12 13:28
    //author:lingxu
    // 原分页
//    /**
//     * 泛型
//     * 解析data是list的情况
//     */
//    public static <T> PagingListResult1<T> fromJsonObject(Gson gson, String reader, Class<T> clazz) {
//        Type type = new ParameterizedTypeImpl(PagingListResult1.class, new Class[]{clazz});
//        return gson.fromJson(reader, type);
//    }
//
//    /**
//     * 泛型 另一种
//     * 解析data是list的情况
//     */
//    public static <T> PagingListResult<T> fromJsonObjectList(Gson gson, String reader, Class<T> clazz) {
//        Type type = new ParameterizedTypeImpl(PagingListResult.class, new Class[]{clazz});
//        return gson.fromJson(reader, type);
//    }
//
//    /**
//     * 泛型
//     * 解析data是array的情况
//     */
//    public static <T> PagingResultObject<List<T>> fromJsonArray(Gson gson, String reader, Class<T> clazz) {
//        // 生成List<T> 中的 List<T>
//        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
//        // 根据List<T>生成完整的Result<List<T>>
//        Type type = new ParameterizedTypeImpl(PagingResultObject.class, new Type[]{listType});
//        return gson.fromJson(reader, type);
//    }

    public static final String TOKEN = "token";

    public static String buildUrl(String url, Map<String, String> params) {
        Uri.Builder builder = Uri.parse(url).buildUpon().appendQueryParameter(TOKEN, MyAPP.getInstance().getToken());
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        Uri uri = builder.build();
        URL returnUrl = null;
        try {
            returnUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returnUrl.toString();
    }

    public static String buildUrl(String url) {
        Uri.Builder builder = Uri.parse(url).buildUpon().appendQueryParameter(TOKEN, MyAPP.getInstance().getToken());
        Uri uri = builder.build();
        URL returnUrl = null;
        try {
            returnUrl = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returnUrl.toString();
    }
}
