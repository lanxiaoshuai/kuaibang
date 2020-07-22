package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by asus on 2020/3/6.
 */

public class GetJsonDataUtil  {

    /**
     * 从asset路径下读取对应文件转String输出
     * @param
     * @return
     */
//    public static String getJson(Context mContext, String fileName) {
//        // TODO Auto-generated method stub
//        StringBuilder sb = new StringBuilder();
//        AssetManager am = mContext.getAssets();
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    am.open(fileName)));
//            String next = "";
//            while (null != (next = br.readLine())) {
//                sb.append(next);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block

//            e.printStackTrace();
//            sb.delete(0, sb.length());
//        }

//        return sb.toString().trim();
//    }
    public static String getJson(String fileName,Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
