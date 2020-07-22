package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class SystemMessageBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":3,"rows":[{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"234","content":"sdfvw","sendTime":"2019-12-14 15:26:21"},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"cwesdcwe","content":"2","sendTime":"2019-12-14 15:26:14"},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"qsad","content":"1","sendTime":"2019-12-14 15:26:09"}],"code":0}
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
         * total : 3
         * rows : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"234","content":"sdfvw","sendTime":"2019-12-14 15:26:21"},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"cwesdcwe","content":"2","sendTime":"2019-12-14 15:26:14"},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":"qsad","content":"1","sendTime":"2019-12-14 15:26:09"}]
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
             * id : 234
             * content : sdfvw
             * sendTime : 2019-12-14 15:26:21
             */

            private Object searchValue;
            private Object createBy;
            private String createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBean params;
            private String id;
            private String content;
            private String sendTime;
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getSendTime() {
                return sendTime;
            }

            public void setSendTime(String sendTime) {
                this.sendTime = sendTime;
            }

            public static class ParamsBean {
            }
        }
    }
}
