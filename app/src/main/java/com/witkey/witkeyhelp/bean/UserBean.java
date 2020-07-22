package com.witkey.witkeyhelp.bean;

import java.util.List;

/**
 * Created by jie on 2020/5/20.
 */

public class UserBean {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"params":{"grade":5,"tag":[]},"userId":343,"userName":"18810945325","realName":"ccc","sex":"0","headUrl":"1202005121546531500086551.png","createDate":"2020-04-28 11:06:23","isdeduction":1,"invitationCode":"894351","isInvitationCode":null,"reputationNum":100,"openId":"oSBtswA4u-ciiU-psKry0LkygEhA","locationName":null,"pSignature":"还没有个性签名"}
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
         * params : {"grade":5,"tag":[]}
         * userId : 343
         * userName : 18810945325
         * realName : ccc
         * sex : 0
         * headUrl : 1202005121546531500086551.png
         * createDate : 2020-04-28 11:06:23
         * isdeduction : 1
         * invitationCode : 894351
         * isInvitationCode : null
         * reputationNum : 100
         * openId : oSBtswA4u-ciiU-psKry0LkygEhA
         * locationName : null
         * pSignature : 还没有个性签名
         */

        private ParamsBean params;
        private int userId;
        private String userName;
        private String realName;
        private String sex;
        private String headUrl;
        private String createDate;
        private int isdeduction;
        private String invitationCode;
        private Object isInvitationCode;
        private int reputationNum;
        private String openId;
        private Object locationName;
        private String pSignature;

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getIsdeduction() {
            return isdeduction;
        }

        public void setIsdeduction(int isdeduction) {
            this.isdeduction = isdeduction;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
        }

        public Object getIsInvitationCode() {
            return isInvitationCode;
        }

        public void setIsInvitationCode(Object isInvitationCode) {
            this.isInvitationCode = isInvitationCode;
        }

        public int getReputationNum() {
            return reputationNum;
        }

        public void setReputationNum(int reputationNum) {
            this.reputationNum = reputationNum;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public Object getLocationName() {
            return locationName;
        }

        public void setLocationName(Object locationName) {
            this.locationName = locationName;
        }

        public String getPSignature() {
            return pSignature;
        }

        public void setPSignature(String pSignature) {
            this.pSignature = pSignature;
        }

        public static class ParamsBean {
            /**
             * grade : 5.0
             * tag : []
             */

            private String grade;
            private List<String> tag;

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public List<String> getTag() {
                return tag;
            }

            public void setTag(List<String> tag) {
                this.tag = tag;
            }
        }
    }
}
