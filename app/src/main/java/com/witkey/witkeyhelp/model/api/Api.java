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


    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @POST("apiuser/login")
    @FormUrlEncoded
    Call<String> login(
            @Field("userName") String username,
            @Field("password") String password
    );

    /**
     * 注册
     *
     * @param username
     * @param password userType 未用 用户类型 1悬赏主 2威客
     * @return
     */
    @POST("apiuser/add")
    @FormUrlEncoded
    Call<String> register(
            @Field("userName") String username,
            @Field("password") String password
    );

    /**
     * 获取用户信息
     *
     * @return
     */
    @POST("apiuser/find/{userId}")
    @FormUrlEncoded
    Call<String> getUser(@Path("userId") int userId);


    /**
     * 发布信息咨询
     *
     * @return
     */
    @POST("apiorder/add")
    @FormUrlEncoded
    Call<String> addOrder(
            @Field("businessId") String businessId,
            @Field("password") String password
    );

    /**
     * 信息咨询
     * 暂时保存
     *
     * @return businessId
     */
    @POST("apibusiness/add")
    @FormUrlEncoded
    Call<String> addBusiness(
            @Field("title") String title,
            @Field("describes") String describes,
            @Field("businessImgUrl") String businessImgUrl,
            @Field("price") String price,
            @Field("contactsPhone") String contactsPhone,
            @Field("createUserId") String createUserId,
            @Field("businessType") String businessType,
            @Field("productType") String productType,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("businessNum") String businessNum,
            @Field("paymentType") String paymentType,
            @Field("endDate") String endDate,
            @Field("bargainingType") String bargainingType,
            @Field("biddingType") String biddingType,
            @Field("bondType") String bondType
//            title	否	string	标题
//            describes	是	string	描述
//            businessImgUrl	是	string	图片地址
//            price	是	BigDecimal	昵称
//            contactsPhone	是	string	联系电话
//            createUserId	是	int	用户ID
//            businessType	是	string	任务类型 1 信息咨询 2悬赏帮助 3紧急求助 4失物招领
//            productType	是	string	产品类型 1普通 2竞标
//            longitude	否	string	坐标（经度）
//            latitude	否	string	坐标（纬度）
//            businessNum	是	int	任务数量
//            paymentType	是	string	付款方式 1人民币 2钻石
//            endDate	否	date	结束日期
//            bargainingType	否	string	是否可以议价 0否 1是
//            biddingType	否	string	是否需要竞标 0否 1是
//            bondType	是	string	是否需要保证金 0否 1是
    );


    /**
     * 获取广告位地址
     *
     * @return
     */
    @POST("apibanner/list")
    @FormUrlEncoded
    Call<String> getBanner();


    //模板

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