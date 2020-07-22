package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2020/7/3.
 */

public class MyPostBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":28,"rows":[{"businessId":2082,"price":1,"deposit":0,"describes":"hhhh","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-07-10 10:57:50","businessType":"2","orderState":null,"userName":null,"headUrl":null},{"businessId":2077,"price":1,"deposit":0,"describes":"v哼唧即可出锅","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-29 10:47:20","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2076,"price":1,"deposit":0,"describes":"亲亲亲亲亲亲啊","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-29 10:46:51","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2075,"price":1,"deposit":0,"describes":"不会喝酒就","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-29 10:35:03","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2073,"price":1,"deposit":0,"describes":"QEWQWEQWEQWEQWEQWE","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:42:28","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2072,"price":1,"deposit":0,"describes":"dasdasdasdasdasda","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:39:35","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2071,"price":1,"deposit":0,"describes":"qqqqqqqqqqqqqqqqqqqqqqqqqqq","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:37:35","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2070,"price":1,"deposit":0,"describes":"eeeeeeeeeeeeee","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:36:34","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2069,"price":1,"deposit":0,"describes":"ffffffffffffffff","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:36:07","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2067,"price":1,"deposit":0,"describes":"eeeeeeeeeee","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:35:09","businessType":"1","orderState":null,"userName":null,"headUrl":null}],"code":0}
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
         * total : 28
         * rows : [{"businessId":2082,"price":1,"deposit":0,"describes":"hhhh","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-07-10 10:57:50","businessType":"2","orderState":null,"userName":null,"headUrl":null},{"businessId":2077,"price":1,"deposit":0,"describes":"v哼唧即可出锅","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-29 10:47:20","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2076,"price":1,"deposit":0,"describes":"亲亲亲亲亲亲啊","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-29 10:46:51","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2075,"price":1,"deposit":0,"describes":"不会喝酒就","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-29 10:35:03","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2073,"price":1,"deposit":0,"describes":"QEWQWEQWEQWEQWEQWE","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:42:28","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2072,"price":1,"deposit":0,"describes":"dasdasdasdasdasda","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:39:35","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2071,"price":1,"deposit":0,"describes":"qqqqqqqqqqqqqqqqqqqqqqqqqqq","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:37:35","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2070,"price":1,"deposit":0,"describes":"eeeeeeeeeeeeee","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:36:34","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2069,"price":1,"deposit":0,"describes":"ffffffffffffffff","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:36:07","businessType":"1","orderState":null,"userName":null,"headUrl":null},{"businessId":2067,"price":1,"deposit":0,"describes":"eeeeeeeeeee","businessImgUrl":null,"paymentType":"2","createUserId":null,"createDate":"2020-06-28 17:35:09","businessType":"1","orderState":null,"userName":null,"headUrl":null}]
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
             * businessId : 2082
             * price : 1.0
             * deposit : 0
             * describes : hhhh
             * businessImgUrl : null
             * paymentType : 2
             * createUserId : null
             * createDate : 2020-07-10 10:57:50
             * businessType : 2
             * orderState : null
             * userName : null
             * headUrl : null
             */

            private int businessId;
            private double price;
            private int deposit;
            private String describes;
            private String businessImgUrl;
            private String paymentType;
            private Object createUserId;
            private String createDate;
            private String businessType;
            private String orderState;
            private Object userName;
            private Object headUrl;
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

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getDeposit() {
                return deposit;
            }

            public void setDeposit(int deposit) {
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

            public Object getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(Object createUserId) {
                this.createUserId = createUserId;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getBusinessType() {
                return businessType;
            }

            public void setBusinessType(String businessType) {
                this.businessType = businessType;
            }

            public String getOrderState() {
                return orderState;
            }

            public void setOrderState(String orderState) {
                this.orderState = orderState;
            }

            public Object getUserName() {
                return userName;
            }

            public void setUserName(Object userName) {
                this.userName = userName;
            }

            public Object getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(Object headUrl) {
                this.headUrl = headUrl;
            }
        }
    }
}
