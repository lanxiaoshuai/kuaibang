package com.witkey.witkeyhelp.model.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
            @Field("password") String password,
            @Field("isInvitationCode") String isInvitationCode,
            @Field("smsCode") String smsCode,
            @Field("locationName") String locationName,
            @Field("deviceNumber") String deviceNumber

    );

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return
     */
    @POST("apiuser/sendMsg")
    @FormUrlEncoded
    Call<String> sendMsg(
            @Field("phone") String phone

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
    // apiorder/taskList
    @POST("api/v2/business/list")
    @FormUrlEncoded
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
            @Field("pageNum") String pageNum,
            @Field("pageSize") String pageSize,
            @Field("businessType") String businessType,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("paymentType") String paymentType,
            @Field("bondType") String bondType,
            @Field("mflag") String mflag,
            @Field("circleId") String circleId,
            @Field("createUserId") int createUserId
    );

    /**
     * 悬赏大厅 任务详情
     *
     * @return
     */
    @GET("apibusiness/findBusiness")
    Call<String> getMissionOrder(
            @Query("businessId") String businessId,
            @Query("userId") int userId
    );

    /**
     * 订单 任务详情
     *
     * @return
     */
    @GET("apiorder/findBusiness")
    Call<String> getMissionDetail(
            @Query("orderId") int orderId,
            @Query("userId") int userId
    );

    /**
     * 悬赏大厅 -接单
     *
     * @return
     */
    @POST("apiorder/receipt")
    @FormUrlEncoded
    Call<String> receipt(
            @Field("businessId") String orderId,
            @Field("receiver") String receiver
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
            @Query("orderState") String orderState
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
    //apiorder/receiveList
    @GET("api/v2/order/receiveList")
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
    @GET("catalogUser/list")
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


    //收藏
    @POST("apicollections/add")
    @FormUrlEncoded
//    @GET("apicollections/add")
    Call<String> olleEction(@Field("bsId") String bsId,       //订单ID（orderId）
                            @Field("userId") String userId); ///用户ID


    //收藏列表
    @GET("apiorder/rollectionList")
    Call<String> collectionList(@Query("pageNum") int pageNum,       //第几页
                                @Query("pageSize") int pageSize,   //每页多少条
                                @Query("userId") int userId); ///用户ID


    //收藏
    @POST("apicollections/exit")
    @FormUrlEncoded
//    @GET("apicollections/add")
    Call<String> cancelColleEction(@Field("bsId") String bsId,       //订单ID（orderId）
                                   @Field("userId") String userId); ///用户ID

    //图片上传
    // @Part() List<MultipartBody.Part> parts
    //@Part MultipartBody.Part parts

    @Headers({"base_url:one"})//添加注解，更换baseUrl
    @Multipart
    @POST("community-admin/fileupload/upload")
    Call<String> upLoadImg(@Part() List<MultipartBody.Part> parts); ///用户ID

    //添加银行卡
    @POST("apiUserBank/add")
    @FormUrlEncoded
    Call<String> addBankCard(@Field("uId") String uId,
                             @Field("bankId") String bankId,
                             @Field("realName") String realName,
                             @Field("cardNo") String cardNo,
                             @Field("cardBank") String cardBank); ///用户ID

    // 获取银行卡列表
    @GET("apiUserBank/getUserBankList")
    Call<String> apiUserBankGetUserBankList(@Query("userId") String userId);

    // 删除
    @GET("apiUserBank/unbind")
    Call<String> cancelBankCard(@Query("id") String userId);

    //接单人取消任务
    @POST("apiorder/receiver/cancel")
    @FormUrlEncoded
    Call<String> taskRelievingCollection(@Field("receiver") String receiver,
                                         @Field("orderId") String orderId,
                                         @Field("reason") String reason);

    //悬赏者取消任务
    @POST("apiorder/issuer/cancel")
    @FormUrlEncoded
    Call<String> rewardGiverwithdrawsTask(@Field("userId") String receiver,
                                          @Field("businessId") String businessId,
                                          @Field("reason") String remark,
                                          @Field("type") int type,
                                          @Field("orderId") String orderId
    );

    //接单者提交任务
    @POST("apiorder/subOrder")
    @FormUrlEncoded
    Call<String> confirmsReceiveOrder(@Field("orderId") String orderId);

    //悬赏者确认任务
    @POST("apiorder/confirmOrder")
    @FormUrlEncoded
    //  @FormUrlEncoded
    Call<String> rewardConfirmationTask(@Field("orderId") int orderId,
                                        @Field("type") int type
    );

    //统一下单
    @POST("apiwxpay/wxAppletPay")
    @FormUrlEncoded
    Call<String> wxAppletPay(@Field("body") String body,
                             @Field("price") String price,
                             @Field("ip") String ip,
                             @Field("attachMap") String attachMap
    );

    //查询微信支付结果
    @POST("apiwxpay/result")
    @FormUrlEncoded
    Call<String> apiwxpayresult(@Field("outTradeNo") String outTradeNo);

    //举报  反馈
    @POST("apireport/add")
    @FormUrlEncoded
    Call<String> feedback(@Field("content") String content,
                          @Field("userId") int userId,
                          @Field("imgUrl") String imgUrl,
                          @Field("type") int type,
                          @Field("businessId") Integer businessId,
                          @Field("orderId") int orderId
    );

    //举报  反馈
    @POST("apiorder/isUser/reportOrder")
    @FormUrlEncoded
    Call<String> reportOrder(@Field("content") String content,
                             @Field("userId") int userId,
                             @Field("orderId") int orderId,
                             @Field("imgUrl") String imgUrl,
                             @Field("type") int type


    );


    //提现
    @POST("apiwithDraw/add")
    @FormUrlEncoded
    Call<String> cashWithdrawal(@Field("userId") int userId,
                                @Field("amount") double amount,
                                @Field("ubankId") String ubankId,
                                @Field("type") int type,
                                @Field("openId") String openId);

    //系统消息
    @POST("api/messageBox/list")
    @FormUrlEncoded
    Call<String> SystemsMessage(@Field("pageNum") int pageNum,

                                @Field("pageSize") int paegsize,
                                @Field("userId") int userId


    );

    //是否使用钻石抵扣平台费
    @POST("apiuser/deduction")
    @FormUrlEncoded
    Call<String> getDiamondDeduction(@Field("userId") int userId,
                                     @Field("deduction") int deduction);

    //账单
    @POST("api/AccountFlow/list")
    @FormUrlEncoded
    Call<String> accountFlow(@Field("pageNum") int pageNum,
                             @Field("pageSize") int pageSize,
                             @Field("userId") int userId,
                             @Field("amountType") int amountType);

    //修改个人信息
    @POST("apiuser/edit")
    @FormUrlEncoded
    Call<String> updateUserInfo(@Field("userId") int userId,
                                @Field("realName") String realName,
                                @Field("headUrl") String headUrl,
                                @Field("sex") String sex,
                                @Field("pSignature") String pSignature);

    //修改密码
    @POST("apiuser/forgetPwd")
    @FormUrlEncoded
    Call<String> hangePassword(
            @Field("userName") String userName,
            @Field("password") String password,
            @Field("smsCode") String smsCode);


    //解除任务原因
    @POST("api/reason/reasonList")
    @FormUrlEncoded
    Call<String> reasonList(
            @Field("type") int type);


    //接单人解除任务
    @POST("apiorder/isUser/confirmCancel")
    @FormUrlEncoded
    Call<String> orderCancel(
            @Field("orderId") String orderId

    );

    //信誉分
    @POST("api/reputNum/list")
    @FormUrlEncoded
    Call<String> reputNumlist(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("userId") int usreId

    );

    //发布失误招领
    @POST("api/lostAricle/add")
    @FormUrlEncoded
    Call<String> lostAricleAdd(
            @Field("createUserId") int createUserId,
            @Field("describes") String describes,
            @Field("contactsPhone") String contactsPhone,
            @Field("imgUrl") String imgUrl,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("placeName") String placeName

    );

    //获得失误招领列表
    @POST("api/lostAricle/list")
    @FormUrlEncoded
    Call<String> lostAriclelist(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("createUserId") String createUserId,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude

    );

    //获得失误招领详情
    @POST("api/lostAricle/selectById")
    @FormUrlEncoded
    Call<String> lostAricleSelectById(
            @Field("id") int id
    );

    //确认完成认领
    @POST("api/lostAricle/confirm")
    @FormUrlEncoded
    Call<String> lostAricleConfirm(
            @Field("id") int id


    );

    //举报
    @POST("api/lostAricle/report")
    @FormUrlEncoded
    Call<String> lostAricleReport(
            @Field("lostId") int lostId,
            @Field("content") String content,
            @Field("imgUrl") String imgUrl

    );

    //广告列表
    @POST("api/advertising/list")
    @FormUrlEncoded
    Call<String> advertisinglist(
            @Field("userId") int userId,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("pageNum") String pageNum,
            @Field("pageSize") String pageSize,
            @Field("province") String province,
            @Field("town") String town
    );

    //广告详情
    @POST("api/advertising/selectById")
    @FormUrlEncoded
    Call<String> advertisingSelectById(
            @Field("id") int id,
            @Field("userId") int userId

    );

    //发布广告
    @POST("api/advertising/add")
    @FormUrlEncoded
    Call<String> advertisingAdd(
            @Field("createUserId") int createUserId,
            @Field("amountType") String amountType,
            @Field("content") String content,
            @Field("title") String title,
            @Field("imgUrl") String imgUrl,
            @Field("putNum") String putNum,
            @Field("putArea") String putArea,
            @Field("placeName") String placeName,
            @Field("putScope") String putScope,
            @Field("putBalance") double putBalance,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude,
            @Field("putLocation") String putLocation,
            @Field("type") int type


    );

    //广告情详
    @POST("api/advertising/Mylist")
    @FormUrlEncoded
    Call<String> advertisingMylist(
            @Field("createUserId") int createUserId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize

    );

    //领取赏金
    @POST("api/advertising/getReward")
    @FormUrlEncoded
    Call<String> advertisingGetReward(
            @Field("adverId") int adverId,
            @Field("userId") int userId

    );

    //推荐悬赏
    @POST("apibusiness/recommend")
    @FormUrlEncoded
    Call<String> apibusinessRecommend(
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("longitude") String longitude,
            @Field("latitude") String latitude

    );

    //未读信息
    @POST("api/messageBox/readList")
    @FormUrlEncoded
    Call<String> apiReadList(
            @Field("userId") int userId

    );

    //错误日志
    @POST("api/phoneLog/add")
    @FormUrlEncoded
    Call<String> apiPhoneLog(
            @Field("content") String content,
            @Field("version") String version,
            @Field("phoneType") String phoneType


    );

    //banner
    @GET("api/banner/list")
    Call<String> apiBanener(
            @Query("type") int type
    );

    //个人信息
    @GET("api/v2/user/homePage")
    Call<String> userHomePage(
            @Query("phone") String phone
    );

    //个人中心 发布的
    @POST("api/v2/business/publishList")
    @FormUrlEncoded
    Call<String> businessPublishList(
            @Field("phone") String phone,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize


    );

    //个人中心 领取的
    @POST("api/v2/business/receivelist")
    @FormUrlEncoded
    Call<String> businessReceivelist(
            @Field("userId") int userId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize


    );

    //发布的
    @POST("api/comment/add")
    @FormUrlEncoded
    Call<String> commentAdd(
            @Field("userId") String userId,
            @Field("orderId") String orderId,
            @Field("content") String content,
            @Field("score") String score


    );

    //隐藏显示任务
    @POST("api/v2/business/isHide")
    @FormUrlEncoded
    Call<String> businessIsHide(
            @Field("businessId") int businessId,
            @Field("isHide") int isHide


    );


    //添加显示标签
    @POST("api/v2/user/addTag")
    @FormUrlEncoded
    Call<String> userAddTag(
            @Field("phone") String phone,
            @Field("tagsId") String tagsId


    );


    //浏览记录
    @POST("api/advertising/browseList")
    @FormUrlEncoded
    Call<String> browseList(
            @Field("userId") int userId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize


    );

    //关注的圈子
    @POST("api/circle/myList")
    @FormUrlEncoded
    Call<String> circleMyList(
            @Field("userId") int userId


    );

    //新增关注圈子
    @POST("api/circle/attention")
    @FormUrlEncoded
    Call<String> circleAttention(
            @Field("userId") int userId,
            @Field("cirleIds") List<String> circleId
    );

    //创建圈子
    @POST("api/circle/add")
    @FormUrlEncoded
    Call<String> apiCircleAdd(
            @Field("name") String name,
            @Field("abbreviation") String abbreviation,
            @Field("definition") String definition,
            @Field("createUserId") int createUserId
    );

    //删除圈子
    @POST("api/circle/remove")
    @FormUrlEncoded
    Call<String> circleRemove(

            @Field("ids") List<String> ids
    );

    //推荐圈子
    @POST("api/circle/list")
    @FormUrlEncoded
    Call<String> recommendCircle(

            @Field("isRecommend") int isRecommend
    );

    //搜索圈子
    @POST("api/circle/search")
    @FormUrlEncoded
    Call<String> circleSearch(

            @Field("name") String name
    );

    //关注(未关注)圈子
    @POST("api/circle/isAttention")
    @FormUrlEncoded
    Call<String> circleIsAttention(

            @Field("userId") int userId,
            @Field("circleId") String circleId
    );

    //我的发布
    @POST("api/v2/user/myRelease")
    @FormUrlEncoded
    Call<String> userMyRelease(

            @Field("createUserId") int createuserId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize,
            @Field("businessType") String businessType
    );

    //查询评论
    @POST("api/comment/findCommentByBusiness")
    @FormUrlEncoded
    Call<String> getFindCommentByBusiness(

            @Field("bId") int bId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize
    );

    //添加评论
    @POST("api/comment/add")
    @FormUrlEncoded
    Call<String> getCommentAdd(

            @Field("userId") int userId,
            @Field("content") String content,
            @Field("bId") int bId
    );

    //添加回复
    @POST("api/comment/addReply")
    @FormUrlEncoded
    Call<String> addcommentAddReply(

            @Field("userId") int userId,
            @Field("cId") String cId,
            @Field("content") String content,
            @Field("replyUserId") Integer replyUserId,
            @Field("pId") String pId


    );

    //我的回复
    @POST("api/comment/myReply")
    @FormUrlEncoded
    Call<String> commentMyReply(

            @Field("userId") int userId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize

    );

    //回复我的
    @POST("api/comment/replyMe")
    @FormUrlEncoded
    Call<String> commentReplyMe(

            @Field("userId") int userId,
            @Field("pageNum") int pageNum,
            @Field("pageSize") int pageSize

    );
    //分配赏金
    @POST("api/reward/add")
    @FormUrlEncoded
    Call<String> rewardAdd(

            @Field("commentId") String commentId,
            @Field("userId") int userId,
            @Field("businessId") int businessId,
            @Field("reawrdMoney") double reawrdMoney

    );
    //获取标签

    @GET("api/v2/user/getAllTag")
    Call<String> getAllTag();


    @GET("api/version/checkVersion")
    Call<String> versionUpdate();

    //获取银行卡列表
    @GET("apibank/list")
    Call<String> getBankCard(); ///用户ID


}