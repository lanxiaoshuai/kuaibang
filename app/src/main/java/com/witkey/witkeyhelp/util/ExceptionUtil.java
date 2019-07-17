package com.witkey.witkeyhelp.util;

import android.util.Log;

import com.witkey.witkeyhelp.URL;

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
        sendInfo(info);
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
        sendInfo(info);
    }


    private static void sendInfo(String info) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(URL.appLog + "?content=" + info + "&type=" + 1);
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i("llx", response.message() + " , body " + str);
                } else {
                    String str = response.body().string();
                    Log.i("llx", response.message() + " error : body " + str);
                }
            }
        });
        Log.i("llx", info);
    }

}
