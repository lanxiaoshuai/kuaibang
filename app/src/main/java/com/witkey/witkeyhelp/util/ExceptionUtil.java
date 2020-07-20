package com.witkey.witkeyhelp.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.model.IModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author lingxu
 * @date 2019/7/12 10:08
 * @description 提交异常的工具类
 */
public class ExceptionUtil {

    public static void CatchException(Exception e) {
        String info = null;
//        if (MyAPP.ISRELEASE) {
        // 收集异常信息,联网发送给服务器,开发人员通过服务器随时查看异常信息
        StringWriter wr = new StringWriter();
        final PrintWriter err = new PrintWriter(wr);
        e.printStackTrace(err);
        // 详细异常信息打印到printWriter
        // printWriter有把信息达到StringWirter
        // 通过StringWriter转成String
        info = wr.toString();
        // sendInfo(info);
     //   errorMessage(info);
    }


    public static void CatchException(Throwable e) {
        String info = null;
//        if (MyAPP.ISRELEASE) {
        // 收集异常信息,联网发送给服务器,开发人员通过服务器随时查看异常信息
        StringWriter wr = new StringWriter();
        final PrintWriter err = new PrintWriter(wr);
        e.printStackTrace(err);
        // 详细异常信息打印到printWriter
        // printWriter有把信息达到StringWirter
        // 通过StringWriter转成String
        info = wr.toString();
        //errorMessage(info);
    }

//
//    private static void sendInfo(String info) {
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        Request.Builder requestBuilder = new Request.Builder().url(URL.appLog + "?content=" + info + "&type=" + 1);
//        //可以省略，默认是GET请求
//        requestBuilder.method("GET", null);
//        Request request = requestBuilder.build();
//        Call mcall = mOkHttpClient.newCall(request);
//        mcall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String str = response.body().string();
//                    Log.i("llx", response.message() + " , body " + str);
//                } else {
//                    String str = response.body().string();
//                    Log.i("llx", response.message() + " error : body " + str);
//                }
//            }
//        });
//        Log.i("llx", info);
//    }


    public static void errorMessage(String errorStr) {
        CrashReport.postCatchedException(new Throwable(errorStr+ "==========="+getVersionCode()+"============"));
        MyAPP.getInstance().getApi().apiPhoneLog(errorStr, getVersionCode() + "", SystemUtil.getSystemModel()).enqueue(new com.witkey.witkeyhelp.model.util.Callback(IModel.callback, "上传错误信息") {
            @Override
            public void getSuc(String body) {


            }

        });
    }

    /**
     * 获取版本号
     *
     * @param
     * @return 版本号
     */
    public static int getVersionCode() {

        //获取包管理器
        PackageManager pm = MyAPP.getInstance().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(MyAPP.getInstance().getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }
}
