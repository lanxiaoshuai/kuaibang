package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2020/5/9.
 */

public class BannerBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : [{"id":2,"url":"/banner/5e91de0189c094721f8d90a12cf1bbc.png","type":"1","status":"1","indexes":"0"},{"id":3,"url":"/banner/df7bceeb3db308a049b31da52e899c2.png","type":"1","status":"1","indexes":"1"},{"id":4,"url":"/banner/e44da421300e0474aa8170af30d3bab.png","type":"1","status":"1","indexes":"2"}]
     */

    private String errorCode;
    private String errorMessage;
    private List<ReturnObjectBean> returnObject;

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

    public List<ReturnObjectBean> getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(List<ReturnObjectBean> returnObject) {
        this.returnObject = returnObject;
    }

    public static class ReturnObjectBean {
        /**
         * id : 2
         * url : /banner/5e91de0189c094721f8d90a12cf1bbc.png
         * type : 1
         * status : 1
         * indexes : 0
         */

        private int id;
        private String url;
        private String type;
        private String status;
        private String indexes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIndexes() {
            return indexes;
        }

        public void setIndexes(String indexes) {
            this.indexes = indexes;
        }
    }
}
