package com.witkey.witkeyhelp.bean;

import java.util.List;

public class MyReplyBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":1,"rows":[{"createTime":null,"params":{},"id":"6566b5c7-4822-4b4e-bcc3-b61e61021877","userId":341,"content":"测试","userName":"匿名","headUrl":"120200512154715791086860.jpeg","describes":"山西大学校长信箱在家打不开是怎么回事啊？","list":[],"bid":1591}],"code":0}
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
         * rows : [{"createTime":null,"params":{},"id":"6566b5c7-4822-4b4e-bcc3-b61e61021877","userId":341,"content":"测试","userName":"匿名","headUrl":"120200512154715791086860.jpeg","describes":"山西大学校长信箱在家打不开是怎么回事啊？","list":[],"bid":1591}]
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
             * createTime : null
             * params : {}
             * id : 6566b5c7-4822-4b4e-bcc3-b61e61021877
             * userId : 341
             * content : 测试
             * userName : 匿名
             * headUrl : 120200512154715791086860.jpeg
             * describes : 山西大学校长信箱在家打不开是怎么回事啊？
             * list : []
             * bid : 1591
             */

            private String createTime;
            private ParamsBean params;
            private String id;
            private int userId;
            private String content;
            private String userName;
            private String headUrl;
            private String describes;
            private int bId;
            private String orderId;
            private String orderState;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderState() {
                return orderState;
            }

            public void setOrderState(String orderState) {
                this.orderState = orderState;
            }

            private List<?> list;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public ParamsBean getParams() {
                return params;
            }

            public void setParams(ParamsBean params) {
                this.params = params;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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

            public String getDescribes() {
                return describes;
            }

            public void setDescribes(String describes) {
                this.describes = describes;
            }

            public int getBid() {
                return bId;
            }

            public void setBid(int bid) {
                this.bId = bid;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }

            public static class ParamsBean {
            }
        }
    }
}
