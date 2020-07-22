package com.witkey.witkeyhelp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2019/12/16.
 */

public class BillFlowBean implements Serializable {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":2,"rows":[{"searchValue":null,"createBy":null,"createTime":"2019-12-17 12:39:57","updateBy":null,"updateTime":null,"remark":null,"params":{"createTime":"2019-12-17 12:39:57","bankName":"中国农业银行","endTime":null},"id":"1201912171239571817270325","userId":255,"title":"余额提现","amount":1,"amountType":1,"flowType":null,"orderId":null,"description":"余额提现","outTradeNo":null,"type":"sub","wid":"688672bf-f799-4057-a69e-11440baf1905"}],"code":0}
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

    public static class ReturnObjectBean implements Serializable {
        /**
         * total : 2
         * rows : [{"searchValue":null,"createBy":null,"createTime":"2019-12-17 12:39:57","updateBy":null,"updateTime":null,"remark":null,"params":{"createTime":"2019-12-17 12:39:57","bankName":"中国农业银行","endTime":null},"id":"1201912171239571817270325","userId":255,"title":"余额提现","amount":1,"amountType":1,"flowType":null,"orderId":null,"description":"余额提现","outTradeNo":null,"type":"sub","wid":"688672bf-f799-4057-a69e-11440baf1905"}]
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

        public static class RowsBean implements Serializable {
            /**
             * searchValue : null
             * createBy : null
             * createTime : 2019-12-17 12:39:57
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {"createTime":"2019-12-17 12:39:57","bankName":"中国农业银行","endTime":null}
             * id : 1201912171239571817270325
             * userId : 255
             * title : 余额提现
             * amount : 1
             * amountType : 1
             * flowType : null
             * orderId : null
             * description : 余额提现
             * outTradeNo : null
             * type : sub
             * wid : 688672bf-f799-4057-a69e-11440baf1905
             */

            private Object searchValue;
            private Object createBy;
            private String createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBean params;
            private String id;
            private int userId;
            private String title;
            private double amount;
            private int amountType;
            private Object flowType;
            private Object orderId;
            private String description;
            private Object outTradeNo;
            private String type;
            private String wid;
            private String credited;

            public String getCredited() {
                return credited;
            }

            public void setCredited(String credited) {
                this.credited = credited;
            }

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getAmountType() {
                return amountType;
            }

            public void setAmountType(int amountType) {
                this.amountType = amountType;
            }

            public Object getFlowType() {
                return flowType;
            }

            public void setFlowType(Object flowType) {
                this.flowType = flowType;
            }

            public Object getOrderId() {
                return orderId;
            }

            public void setOrderId(Object orderId) {
                this.orderId = orderId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Object getOutTradeNo() {
                return outTradeNo;
            }

            public void setOutTradeNo(Object outTradeNo) {
                this.outTradeNo = outTradeNo;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getWid() {
                return wid;
            }

            public void setWid(String wid) {
                this.wid = wid;
            }

            public static class ParamsBean implements Serializable {
                /**
                 * createTime : 2019-12-17 12:39:57
                 * bankName : 中国农业银行
                 * endTime : null
                 */

                private String createTime;
                private String bankName;
                private String endTime;

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getBankName() {
                    return bankName;
                }

                public void setBankName(String bankName) {
                    this.bankName = bankName;
                }

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }
            }
        }
    }
}
