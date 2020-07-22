package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2020/5/21.
 */

public class UserTaskBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":1,"rows":[{"businessId":1678,"price":1,"deposit":null,"describes":"【官方】悬赏在应用商店为 酷爱帮 打分任务。\r\n请在你的应用商店对 酷爱帮 进行真实评价及打分。打分后截图并标注出你评论的内容，用屏幕左下角的聊天功能回传截图，提交任务。","businessImgUrl":null,"paymentType":"1","createUserId":384,"createDate":"2020-05-15 15:28:11","businessType":null,"orderState":"4","userName":null,"headUrl":null}],"code":0}
     */

    private String errorCode;
    private String errorMessage;
    private ReturnObjectBean returnObject;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ReturnObjectBean getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(ReturnObjectBean returnObject) {
        this.returnObject = returnObject;
    }

    public static class ReturnObjectBean {
        /**
         * total : 1
         * rows : [{"businessId":1678,"price":1,"deposit":null,"describes":"【官方】悬赏在应用商店为 酷爱帮 打分任务。\r\n请在你的应用商店对 酷爱帮 进行真实评价及打分。打分后截图并标注出你评论的内容，用屏幕左下角的聊天功能回传截图，提交任务。","businessImgUrl":null,"paymentType":"1","createUserId":384,"createDate":"2020-05-15 15:28:11","businessType":null,"orderState":"4","userName":null,"headUrl":null}]
         * code : 0
         */

        private int total;
        private int code;
        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * businessId : 1678
             * price : 1
             * deposit : null
             * describes : 【官方】悬赏在应用商店为 酷爱帮 打分任务。
             * 请在你的应用商店对 酷爱帮 进行真实评价及打分。打分后截图并标注出你评论的内容，用屏幕左下角的聊天功能回传截图，提交任务。
             * businessImgUrl : null
             * paymentType : 1
             * createUserId : 384
             * createDate : 2020-05-15 15:28:11
             * businessType : null
             * orderState : 4
             * userName : null
             * headUrl : null
             */

            private int businessId;
            private int price;
            private Object deposit;
            private String describes;
            private String businessImgUrl;
            private String paymentType;
            private int createUserId;
            private String createDate;
            private Object businessType;
            private String orderState;
            private String userName;
            private String headUrl;
            private int orderId;

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getBusinessId() {
                return businessId;
            }

            public void setBusinessId(int businessId) {
                this.businessId = businessId;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public Object getDeposit() {
                return deposit;
            }

            public void setDeposit(Object deposit) {
                this.deposit = deposit;
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

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public Object getBusinessType() {
                return businessType;
            }

            public void setBusinessType(Object businessType) {
                this.businessType = businessType;
            }

            public String getOrderState() {
                return orderState;
            }

            public void setOrderState(String orderState) {
                this.orderState = orderState;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }
        }
    }
}
