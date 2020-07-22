package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2020/6/13.
 */

public class MyCicleBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":1,"rows":[{"createTime":"2020-06-13 14:05:11","params":{},"id":"ea853566-1045-4ae7-a81d-225db9065f2d","userId":343,"circleId":"2","circleName":"测试","abbreviation":"测试"}],"code":0}
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
         * rows : [{"createTime":"2020-06-13 14:05:11","params":{},"id":"ea853566-1045-4ae7-a81d-225db9065f2d","userId":343,"circleId":"2","circleName":"测试","abbreviation":"测试"}]
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
             * createTime : 2020-06-13 14:05:11
             * params : {}
             * id : ea853566-1045-4ae7-a81d-225db9065f2d
             * userId : 343
             * circleId : 2
             * circleName : 测试
             * abbreviation : 测试
             */

            private String createTime;
            private ParamsBean params;
            private String id;
            private int userId;
            private String circleId;
            private String circleName;
            private String abbreviation;

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

            public String getCircleId() {
                return circleId;
            }

            public void setCircleId(String circleId) {
                this.circleId = circleId;
            }

            public String getCircleName() {
                return circleName;
            }

            public void setCircleName(String circleName) {
                this.circleName = circleName;
            }

            public String getAbbreviation() {
                return abbreviation;
            }

            public void setAbbreviation(String abbreviation) {
                this.abbreviation = abbreviation;
            }

            public static class ParamsBean {
            }
        }
    }
}
