package com.witkey.witkeyhelp;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.witkey.witkeyhelp.Contacts.Contacts;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.api.Api;
import com.witkey.witkeyhelp.util.CrashHandler;
import com.witkey.witkeyhelp.util.Error;
import com.witkey.witkeyhelp.util.Exclude;
import com.witkey.witkeyhelp.util.SharedPreferencesUtil;
import com.witkey.witkeyhelp.util.retrofit.StringConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/3/9.
 */

public class MyAPP extends Application {
    /**
     * 是否在真机,是的话不谈log
     */
    public static final boolean ISRELEASE = false;
    public static boolean ISUSENEW = true;
    public ArrayList<Activity> activityList = new ArrayList<Activity>();
    private static MyAPP instance;
    //获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    //获取到主线程的looper
    private static Looper mMainThreadLooper = null;
    //获取到主线程
    private static Thread mMainThead = null;
    //获取到主线程的id
    private static int mMainTheadId;

    private String token;
    private String verification_key;
    private Gson gson;
    private User user;
    private boolean isNetContect;
    public Retrofit retrofit;
    private Api api;

    public Api getApi() {
        if (api == null) {
            api = getRetrofit().create(Api.class);
        }
        return api;
    }

    @Override
    public void onCreate() {
        CrashHandler crashHandler = new CrashHandler(this);
        // 设置之后,出了异常,执行框架中的catch
        // 不加,程序出现异常,会直接结束
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        super.onCreate();
        instance = this;
        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        this.mMainTheadId = Process.myTid();//主線程id
        Exclude ex = new Exclude();
        gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }

    private Retrofit initRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request oldRequest = chain.request();
                                // 添加新的参数
                                HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                                        .newBuilder()
                                        .scheme(oldRequest.url().scheme())
                                        .host(oldRequest.url().host())
                                        .addQueryParameter("token", MyAPP.getInstance().getToken());
                                Log.d(Contacts.Use_TAG, "retrofit-Token: " + MyAPP.getInstance().getToken());
                                // 新的请求
                                Request newRequest = oldRequest.newBuilder()
                                        .method(oldRequest.method(), oldRequest.body())
//                                        .url(authorizedUrlBuilder.build())
                                        .url(authorizedUrlBuilder.build())
                                        .header("Authorization", "Bearer " + MyAPP.getInstance().getToken())
                                        .build();

                                return chain.proceed(newRequest);
                            }
                        }
                ).retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder = builder.baseUrl(URL.versionUrl);
        Retrofit retrofit = builder
                .addConverterFactory(StringConverterFactory.create())
                .client(client)
                .build();
        return retrofit;

    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            Log.d("llx", "getRetrofit:retrofit空啦");
            retrofit = initRetrofit();
        }
        return retrofit;
    }

    public boolean isNetContect() {
        return isNetContect;
    }

    public void setNetContect(boolean netContect) {
        isNetContect = netContect;
    }

    public String getVerification_key() {
        return verification_key;
    }

    public void setVerification_key(String verification_key) {
        this.verification_key = verification_key;
    }

    public Gson getGson() {
        return gson;
    }

    public void saveLocal(String token, int type) {
        SharedPreferencesUtil.saveLocal(getApplicationContext(), token, type);
    }

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            setToken((String) SharedPreferencesUtil.getLocal(this).get("token"));
            String type = (String) SharedPreferencesUtil.getLocal(this).get("type");
        }
        return token;
    }

    public User getUser() {
        if(user==null){
            Error.showError("登陆超时", getInstance());
            return null;
        }else if(user.getUserId()==0){
            Error.showError("登陆超时", getInstance());
            return null;
        }
//        if (user == null) {
//            return new User(190, "15333003834", "测试用户");
//        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
        //token修改后retrofit没反应过来
        if (retrofit != null) {
            retrofit = null;
        }
//        retrofit = initRetrofit();
    }


    public static MyAPP getInstance() {
        return instance;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void finishActivity() {
        for (Activity activity : activityList) {
            try {
                activity.finish();
            } catch (Exception e) {
            }
        }

        // 杀死进程,不然不会再次执行oncreate()
        Process.killProcess(Process.myPid());
    }
}
