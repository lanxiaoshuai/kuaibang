package com.witkey.witkeyhelp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jie on 2019/12/10.
 */

public class AllCardBean {


    /**
     * id : 2342sddf2322edsf
     * bankName : 中国邮政储蓄
     * bankCode : null
     * imgUrl : null
     */
    @SerializedName("id")
    private String id;
    @SerializedName("bankName")
    private String bankName;
    @SerializedName("bankCode")
    private String bankCode;
    @SerializedName("imgUrl")
    private String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        return "AllCardBean{" +
                "id='" + id + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
