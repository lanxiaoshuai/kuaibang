package com.witkey.witkeyhelp.bean;

/**
 * @author lingxu
 * @date 2019/7/9 14:11
 * @description 悬赏大厅列表
 */
public class Mission {
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
    private int businessId;
    private int orderId;
    private String title;
    private String businessType;
    private String createDate;
    private String endDate;
    private String longitude;
    private String latitude;
    private String paymentType;
    private String bargainingType;
    private String biddingType;
    private String bondType;
    private String remark;
    private String describes;
    private String tradeAmt;


    //列表需要
    private int pageNum;
    private int pageSize;

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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


    public Mission(String title, String type, String content, String money) {
        this.title = title;
        this.businessType = type;
        this.describes = content;
        this.tradeAmt = money;
    }

    public Mission() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(String tradeAmt) {
        this.tradeAmt = tradeAmt;
    }
}
