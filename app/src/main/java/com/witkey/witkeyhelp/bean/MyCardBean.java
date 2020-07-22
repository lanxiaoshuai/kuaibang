package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2019/12/10.
 */

public class MyCardBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":2,"rows":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"da9fff8f-83bc-4c32-82f1-b0922adc8451","bankId":"asdasq","realName":"刘真","cardNo":"1234","cardBank":"测试","bank":{"id":"asdasq","bankName":"中国农业银行","bankCode":null,"imgUrl":"bank/农业银行.png"},"uid":"221"},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"dbad8cd1-3e5e-412b-8f24-a1fdb178a518","bankId":"asdasq","realName":"刘真","cardNo":"1234","cardBank":"测试","bank":{"id":"asdasq","bankName":"中国农业银行","bankCode":null,"imgUrl":"bank/农业银行.png"},"uid":"221"}],"code":0}
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
         * rows : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"da9fff8f-83bc-4c32-82f1-b0922adc8451","bankId":"asdasq","realName":"刘真","cardNo":"1234","cardBank":"测试","bank":{"id":"asdasq","bankName":"中国农业银行","bankCode":null,"imgUrl":"bank/农业银行.png"},"uid":"221"},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"dbad8cd1-3e5e-412b-8f24-a1fdb178a518","bankId":"asdasq","realName":"刘真","cardNo":"1234","cardBank":"测试","bank":{"id":"asdasq","bankName":"中国农业银行","bankCode":null,"imgUrl":"bank/农业银行.png"},"uid":"221"}]
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
             * createTime : null
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * id : da9fff8f-83bc-4c32-82f1-b0922adc8451
             * bankId : asdasq
             * realName : 刘真
             * cardNo : 1234
             * cardBank : 测试
             * bank : {"id":"asdasq","bankName":"中国农业银行","bankCode":null,"imgUrl":"bank/农业银行.png"}
             * uid : 221
             */

            private Object searchValue;
            private Object createBy;
            private Object createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBean params;
            private String id;
            private String bankId;
            private String realName;
            private String cardNo;
            private String cardBank;
            private BankBean bank;
            private String uid;

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

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
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

            public String getBankId() {
                return bankId;
            }

            public void setBankId(String bankId) {
                this.bankId = bankId;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getCardNo() {
                return cardNo;
            }

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
            }

            public String getCardBank() {
                return cardBank;
            }

            public void setCardBank(String cardBank) {
                this.cardBank = cardBank;
            }

            public BankBean getBank() {
                return bank;
            }

            public void setBank(BankBean bank) {
                this.bank = bank;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public static class ParamsBean {
            }

            public static class BankBean {
                /**
                 * id : asdasq
                 * bankName : 中国农业银行
                 * bankCode : null
                 * imgUrl : bank/农业银行.png
                 */

                private String id;
                private String bankName;
                private Object bankCode;
                private String imgUrl;
                private String miniIcon;

                public String getMiniIcon() {
                    return miniIcon;
                }

                public void setMiniIcon(String miniIcon) {
                    this.miniIcon = miniIcon;
                }

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

                public Object getBankCode() {
                    return bankCode;
                }

                public void setBankCode(Object bankCode) {
                    this.bankCode = bankCode;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }
            }
        }
    }
}
