package com.witkey.witkeyhelp.model.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/3/26.
 */

public interface Api {
    String USER = "user";
    String TAGS = "tags";
    String USER_ROLE = "user.role";
    String ROLE = "role";
    String SUBORDINATE = "subordinate";
    String CHANNEL = "channel";
    String BELOW = "below";

    //公用

    //用户

    /**
     * 获取用户列表
     *
     * @param id
     * @param addSelf
     */
    @GET("users")
    Call<String> users(@Query("user_id") String id, @Query("self") int addSelf);


    /**
     * 登录
     *
     * @param username
     * @param password
     * @param type              登录类型in:verification_code,password
     * @param verification_code
     * @param verification_key
     * @return
     */
    @POST("authorizations")
    @FormUrlEncoded
    Call<String> authorizations(@Field("username") String username,
                                @Field("password") String password,
                                @Field("type") String type,
                                @Field("verification_code") String verification_code,
                                @Field("verification_key") String verification_key
    );

    /**
     * 登出
     */
    @DELETE("authorizations/current")
    Call<String> logoutCurrent();

    /**
     * 渠道联系人修改
     *
     * @param channelId
     * @param id
     * @param phone
     * @param wechat
     * @return
     */
    @PATCH("channels/{channelId}/channelContacts/{id}")
    Call<String> changeChannelDetail(@Path("channelId") int channelId, @Path("id") int id, @Query("phone") String phone, @Query("wechat") String wechat);


    /**
     * 创建渠道. [POST /channels]
     *
     * @param requestBody 必填：
     *                    name 渠道名称
     *                    address 渠道地址
     *                    contact_name 渠道联系人
     *                    选填：
     *                    longitude 经度
     *                    latitude 纬度
     *                    province 省
     *                    city 市
     *                    area 区
     *                    type 类型
     *                    fee 合作费用
     *                    picture 门头照
     *                    license 资质
     *                    contact_phone 联系人电话
     *                    contact_wechat 联系人微信
     *                    contact_title 联系人职位
     * @return isSuccess
     */
    @POST("channels")
    Call<String> addChannel(@Body RequestBody requestBody);

    @POST("approvals/{id}/finish")
    Call<String> editLeave(@Path("id") int id, @Body RequestBody requestBody);
}