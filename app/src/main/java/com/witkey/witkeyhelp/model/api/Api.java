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
     * 发送验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return
     */
    @POST("apiuser/sendMsg")
    @FormUrlEncoded
    Call<String> sendMsg(
            @Field("phone") String phone,
            @Field("code") String code
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
    Call<String> addOrder(@Field("businessId") String businessId);

    /**
     * 信息咨询
     * 暂时保存
     *
     * @return businessId
     */
    @POST("apibusiness/add")
    Call<String> addBusiness(
            @Body RequestBody requestBody
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


    /**
     * 悬赏大厅列表
     *
     * @return
     */
    @GET("apiorder/taskList")
    Call<String> getTaskList(
//            pageNum	是	int	第几页
//            pageSize	是	int	每页多少条
//            businessType	否	string	任务类型 1 信息咨询 2悬赏帮助 3紧急求助 4失物招领
//            productType	否	string	产品类型 1普通 2竞标
//            longitude	否	string	坐标（经度）
//            latitude	否	string	坐标（纬度）
//            paymentType	否	string	付款方式 1人民币 2钻石
//            biddingType	否	string	是否需要竞标 0否 1是
//            bondType	否	string	是否需要保证金 0否 1是
            @Query("pageNum") String pageNum,
            @Query("pageSize") String pageSize,
            @Query("businessType") String businessType,
            @Query("productType") String productType,
            @Query("longitude") String longitude,
            @Query("latitude") String latitude,
            @Query("paymentType") String paymentType,
//            @Query("biddingType") String biddingType,
            @Query("bondType") String bondType
    );

    /**
     * 悬赏大厅 任务详情
     *
     * @return
     */
    @GET("apibusiness/find/{businessId}")
    Call<String> getMissionDetail(
            @Path("businessId") String businessId
    );

    /**
     * 悬赏大厅 -接单
     *
     * @return
     */
    @POST("apiorder/receipt")
    @FormUrlEncoded
    Call<String> receipt(
            @Field("orderId") String orderId,
            @Field("userId") String receiver
    );

    /**
     * 我的 发布的任务
     * 任务异常(已取消),已完成,进行中
     * 3,,,4,,,1
     *
     * @return
     */
    @GET("apiorder/releaseList")
    Call<String> getReleaseList(
//            pageNum	    是	int	第几页
//            pageSize	    是	int	每页多少条
//            createUserId	是	int	用户ID
//            orderState	否	string	订单状态（0 未付款，1 进行中，2 已经提交 3 已取消 4 已完成 5 退款）
            @Query("pageNum") String pageNum,
            @Query("pageSize") String pageSize,
            @Query("createUserId") String createUserId,
            @Query("orderState") String orderState
    );

    /**
     * 我的 发布的任务
     * 未发布
     * 3
     *
     * @return
     */
    @GET("apibusiness/list")
    Call<String> getBusinessList(
//            createUserId	是	int	用户ID
//            pageNum	    是	int	第几页
//            pageSize	    是	int	每页多少条
//            businessState	是	int	3暂存（未发布）
            @Query("createUserId") String createUserId,
            @Query("pageNum") String pageNum,
            @Query("pageSize") String pageSize,
            @Query("businessState") String businessState
    );

    /**
     * 我的-确认订单
     *
     * @return
     */
    @POST("apiorder/confirm")
    @FormUrlEncoded
    Call<String> confirm(
            @Field("orderId") String orderId,
            @Field("receiver") String receiver
    );

    /**
     * 我的 领取的任务
     * 进行中 已完成 任务异常
     * 1,,,4,,,3
     *
     * @return
     */
    @GET("apiorder/receiveList")
    Call<String> getReceiveList(
//            pageNum	    是	int	第几页
//            pageSize	    是	int	每页多少条
//            receiver	    是	int	用户ID
//            orderState	否	string	订单状态（0 未付款，1 进行中，2 已经提交 3已取消 4已完成 5退款）
            @Query("receiver") String receiver,
            @Query("pageNum") String pageNum,
            @Query("pageSize") String pageSize,
            @Query("orderState") String businessState
    );

    /**
     * 我的钱包
     */
    @GET("apiwallet/find/{userId}")
    Call<String> getAcount(
            @Path("userId") String userId
    );
    ///////////   微通知

    /**
     * 查询我创建的群列表
     *
     * @param userId 当前用户id
     * @return
     */
    @GET("catalogLive/list")
    Call<String> getMicroNotificationList(
            @Query("createUserId") int userId
    );

    /**
     * 查询已读消息列表接口
     *
     * @param userId 当前用户id
     * @return
     */
    @GET("message/readList")
    Call<String> getMicroNotifyMessageCheckList(
            @Query("userId") int userId
    );

    /**
     * 查询未读消息列表接口
     *
     * @param userId 当前用户id
     * @return
     */
    @GET("message/unreadList")
    Call<String> getMicroNotifyMessageUnCheckList(
            @Query("userId") int userId
    );

    /**
     * 查询群目录
     *
     * @param createUserId 当前用户id
     * @param parentId     第一级目录parentId传0，查询二、三级目录parentId传对应上一级的catalogId的值
     * @return
     */
    @GET("catalogLive/listHigh")
    Call<String> getMicroNotifyManagerList(
            @Query("createUserId") int createUserId,
            @Query("parentId") int parentId
    );

    /**
     * 查询第二、三级群目录
     *
     * @param createUserId 当前用户id
     * @param parentId     第一级目录parentId传0，查询二、三级目录parentId传对应上一级的catalogId的值
     * @return
     */
    @GET("catalogLive/listOther")
    Call<String> getMicroNotifyManagerListOther(
            @Query("createUserId") int createUserId,
            @Query("parentId") int parentId
    );

    /**
     * 群成员关联列表
     *
     * @param catalogId 当前选择群id
     * @param pageNum   第几页
     * @param pageSize  每页多少条
     * @return
     */
    @GET("catalogLive/listOther")
    Call<String> getGroupMember(
            @Query("catalogId") int catalogId,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );
    //todo 模板

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