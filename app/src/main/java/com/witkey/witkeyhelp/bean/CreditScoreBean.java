package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by asus on 2020/2/29.
 */

public class CreditScoreBean  {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":22,"rows":[{"searchValue":null,"createBy":null,"createTime":"2020-02-26 15:25:57","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":2,"num":-1,"type":"sub","businessId":null,"userId":293,"reason":"有责解除悬赏任务"}],"code":0}
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
         * total : 22
         * rows : [{"searchValue":null,"createBy":null,"createTime":"2020-02-26 15:25:57","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":2,"num":-1,"type":"sub","businessId":null,"userId":293,"reason":"有责解除悬赏任务"}]
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
             * searchValue : null
             * createBy : null
             * createTime : 2020-02-26 15:25:57
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * id : 2
             * num : -1
             * type : sub
             * businessId : null
             * userId : 293
             * reason : 有责解除悬赏任务
             */

            private Object searchValue;
            private Object createBy;
            private String createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBean params;
            private int id;
            private int num;
            private String type;
            private Object businessId;
            private int userId;
            private String reason;

            public Object getSearchValue() {
                return searchValue;
            }

            public void setSearchValue(Object searchValue) {
                this.searchValue = searchValue;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public Object getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(Object updateTime) {
                this.updateTime = updateTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public ParamsBean getParams() {
                return params;
            }

            public void setParams(ParamsBean params) {
                this.params = params;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Object getBusinessId() {
                return businessId;
            }

            public void setBusinessId(Object businessId) {
                this.businessId = businessId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public static class ParamsBean {
            }
        }
    }
}
