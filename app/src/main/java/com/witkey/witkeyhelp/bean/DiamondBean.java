package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2020/4/7.
 */

public class DiamondBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":2,"rows":[{"searchValue":null,"createBy":null,"createTime":"2020-04-07 10:17:07","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":1,"createUserId":3,"amountType":"1","content":"11","title":"1","imgUrl":"1","putNum":1,"putArea":"1","placeName":"1","putScope":"1","putBalance":null,"status":"1"}],"code":0}
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
         * total : 2
         * rows : [{"searchValue":null,"createBy":null,"createTime":"2020-04-07 10:17:07","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":1,"createUserId":3,"amountType":"1","content":"11","title":"1","imgUrl":"1","putNum":1,"putArea":"1","placeName":"1","putScope":"1","putBalance":null,"status":"1"}]
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
             * createTime : 2020-04-07 10:17:07
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * id : 1
             * createUserId : 3
             * amountType : 1
             * content : 11
             * title : 1
             * imgUrl : 1
             * putNum : 1
             * putArea : 1
             * placeName : 1
             * putScope : 1
             * putBalance : null
             * status : 1
             */

            private Object searchValue;
            private Object createBy;
            private String createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBean params;
            private int id;
            private int createUserId;
            private String amountType;
            private String content;
            private String title;
            private String imgUrl;
            private int putNum;
            private String putArea;
            private String placeName;
            private String putScope;
            private double putBalance;
            private String status;

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

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
            }

            public String getAmountType() {
                return amountType;
            }

            public void setAmountType(String amountType) {
                this.amountType = amountType;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getPutNum() {
                return putNum;
            }

            public void setPutNum(int putNum) {
                this.putNum = putNum;
            }

            public String getPutArea() {
                return putArea;
            }

            public void setPutArea(String putArea) {
                this.putArea = putArea;
            }

            public String getPlaceName() {
                return placeName;
            }

            public void setPlaceName(String placeName) {
                this.placeName = placeName;
            }

            public String getPutScope() {
                return putScope;
            }

            public void setPutScope(String putScope) {
                this.putScope = putScope;
            }

            public double getPutBalance() {
                return putBalance;
            }

            public void setPutBalance(double putBalance) {
                this.putBalance = putBalance;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public static class ParamsBean {
            }
        }
    }
}
