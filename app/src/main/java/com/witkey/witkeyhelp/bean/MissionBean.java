package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * @author lingxu
 * @date 2019/7/25 16:28
 * @description 咨询,悬赏大厅列表
 */
public class MissionBean {
    //	否	string	标题
    @SerializedName("title")
    private String title;
    //	是	string	描述
    @SerializedName("describes")
    private String describes;
    //	是	string	图片地址
    @SerializedName("businessImgUrl")
    private String businessImgUrl;
    //	是	BigDecimal	悬赏金额
    @SerializedName("price")
    private String price;
    //	是	string	联系电话
    @SerializedName("contactsPhone")
    private String contactsPhone;
    //	是	int	用户ID
    @SerializedName("createUserId")
    private int createUserId;
    //是	string	任务类型 1 信息咨询 2 悬赏帮助 3 紧急求助 4 失物招领
    @SerializedName("businessType")
    private String businessType;
    //	是	string	产品类型 1普通 2竞标
    @SerializedName("productType")
    private String productType;
    //	否	string	坐标（经度）
    @SerializedName("longitude")
    private String longitude;
    //否	string	坐标（纬度）
    @SerializedName("latitude")
    private String latitude;
    //	是	int	任务数量
    @SerializedName("businessNum")
    private int businessNum;
    //	是	string	付款方式 1人民币 2钻石
    @SerializedName("paymentType")
    private String paymentType;
    //	否	date	结束日期
    @SerializedName("endDate")
    private String endDate;
    //	否	string	是否可以议价 0否 1是
    @SerializedName("bargainingType")
    private String bargainingType;
    //	否	string	是否需要竞标 0否 1是
    @SerializedName("biddingType")
    private String biddingType;
    //	是	string	是否需要保证金 0否 1是
    @SerializedName("bondType")
    private String bondType;

    private HashMap<String, String> photoMap;

    //获取列表
    @SerializedName("businessId")
    private int businessId;
    @SerializedName("orderId")
    private int orderId;
    @SerializedName("createDate")
    private String createDate;
    @SerializedName("remark")
    private String remark;
    @SerializedName("tradeAmt")
    private String tradeAmt; //悬赏金额
    @SerializedName("pageNum")
    private int pageNum;
    @SerializedName("pageSize")
    private int pageSize; //默认为10

//列表
//      "businessId": 168,
//      "orderId": 988,
//      "title": "擦玻璃",
//      "businessType": null,
//      "createDate": "2019-06-05 16:27:57",
//      "longitude": null,
//      "latitude": null,
//      "paymentType": null,
//      "endDate": null,
//      "bargainingType": null,
//      "biddingType": "0",
//      "bondType": "0",
//      "remark": null,
//      "describes": null,
//      "tradeAmt": 30
    @Override
    public String toString() {
        return "ConsultBean{" +
                "title='" + title + '\'' +
                ", describes='" + describes + '\'' +
                ", businessImgUrl='" + businessImgUrl + '\'' +
                ", price='" + price + '\'' +
                ", contactsPhone='" + contactsPhone + '\'' +
                ", createUserId=" + createUserId +
                ", businessType='" + businessType + '\'' +
                ", productType='" + productType + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", businessNum=" + businessNum +
                ", paymentType='" + paymentType + '\'' +
                ", endDate='" + endDate + '\'' +
                ", bargainingType='" + bargainingType + '\'' +
                ", biddingType='" + biddingType + '\'' +
                ", bondType='" + bondType + '\'' +
                ", photoMap=" + photoMap +
                ", businessId=" + businessId +
                ", orderId=" + orderId +
                ", createDate='" + createDate + '\'' +
                ", remark='" + remark + '\'' +
                ", tradeAmt='" + tradeAmt + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
    public MissionBean(String title, String type, String content, String money) {
        this.title = title;
        this.businessType = type;
        this.describes = content;
        this.tradeAmt = money;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(String tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return 10;
    }


    public HashMap<String, String> getPhotoMap() {
        return photoMap;
    }

    public void setPhotoMap(HashMap<String, String> photoMap) {
        this.photoMap = photoMap;
    }

    public MissionBean(String title, String describes, String businessImgUrl, String price, String contactsPhone, int createUserId, String businessType, String productType, String longitude, String latitude, int businessNum, String paymentType, String endDate, String bargainingType, String biddingType, String bondType) {
        this.title = title;
        this.describes = describes;
        this.businessImgUrl = businessImgUrl;
        this.price = price;
        this.contactsPhone = contactsPhone;
        this.createUserId = createUserId;
        this.businessType = businessType;
        this.productType = productType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.businessNum = businessNum;
        this.paymentType = paymentType;
        this.endDate = endDate;
        this.bargainingType = bargainingType;
        this.biddingType = biddingType;
        this.bondType = bondType;
    }

    public MissionBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getBusinessImgUrl() {
        return businessImgUrl;
    }

    public void setBusinessImgUrl(String businessImgUrl) {
        this.businessImgUrl = businessImgUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getBusinessNum() {
        return businessNum;
    }

    public void setBusinessNum(int businessNum) {
        this.businessNum = businessNum;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBargainingType() {
        return bargainingType;
    }

    public void setBargainingType(String bargainingType) {
        this.bargainingType = bargainingType;
    }

    public String getBiddingType() {
        return biddingType;
    }

    public void setBiddingType(String biddingType) {
        this.biddingType = biddingType;
    }

    public String getBondType() {
        return bondType;
    }

    public void setBondType(String bondType) {
        this.bondType = bondType;
    }
}
