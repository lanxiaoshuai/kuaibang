package com.witkey.witkeyhelp.bean;

public class ConsultBean {
    //	否	string	标题
    private String title;
    //	是	string	描述
    private String describes;
    //	是	string	图片地址
    private String businessImgUrl;
    //	是	BigDecimal	昵称
    private String price;
    //	是	string	联系电话
    private String contactsPhone;
    //	是	int	用户ID
    private int    createUserId;
    //是	string	任务类型 1 信息咨询 2悬赏帮助 3紧急求助 4失物招领
    private String businessType;
    //	是	string	产品类型 1普通 2竞标
    private String productType;
    //	否	string	坐标（经度）
    private String longitude;
    //否	string	坐标（纬度）
    private String latitude;
    //	是	int	任务数量
    private int    businessNum;
    //	是	string	付款方式 1人民币 2钻石
    private String paymentType;
    //	否	date	结束日期
    private String endDate;
    //	否	string	是否可以议价 0否 1是
    private String bargainingType;
    //	否	string	是否需要竞标 0否 1是
    private String biddingType;
    //	是	string	是否需要保证金 0否 1是
    private String bondType;

    public ConsultBean(String title, String describes, String businessImgUrl, String price, String contactsPhone, int createUserId, String businessType, String productType, String longitude, String latitude, int businessNum, String paymentType, String endDate, String bargainingType, String biddingType, String bondType) {
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
                '}';
    }

    public ConsultBean() {
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
