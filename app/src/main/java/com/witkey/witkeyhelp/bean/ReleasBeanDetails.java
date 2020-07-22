package com.witkey.witkeyhelp.bean;

/**
 * Created by asus on 2020/3/21.
 */

public class ReleasBeanDetails {

    /**
     * errorCode : 200
     * errorMessage : 成功
     * returnObject : {"searchValue":null,"createBy":null,"createTime":"2020-03-19 17:02:55","updateBy":null,"updateTime":null,"remark":null,"params":{},"id":2,"createUserId":301,"describes":"测试内容","status":null,"imgUrl":null,"contactsPhone":"18835543042","longitude":"110.151212","latitude":"33.26451","placeName":"地理位置","user":{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"userId":301,"userName":"17608061937","password":"dUmxJDKncvVLc562NTqL2w==","salt":null,"realName":"匿名","cardNo":null,"sex":null,"age":null,"phone":null,"email":null,"headUrl":null,"cardPicUrl":null,"cardJustUrl":"","cardBackUrl":"","address":null,"userType":null,"available":"0","lastLoginDate":null,"lastLoginIP":null,"clientId":"","checkState":"1","checkUserId":null,"checkUserName":null,"checkDate":null,"checkOpinion":null,"createDate":"2020-03-04 14:54:23","remarks":null,"delFlag":"0","accessToken":null,"errorTimes":0,"ryToken":null,"communityName":null,"ryUserId":null,"isdeduction":1,"invitationCode":"8BX7VE","isInvitationCode":"7hqsca","reputationNum":94}}
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
         * searchValue : null
         * createBy : null
         * createTime : 2020-03-19 17:02:55
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * id : 2
         * createUserId : 301
         * describes : 测试内容
         * status : null
         * imgUrl : null
         * contactsPhone : 18835543042
         * longitude : 110.151212
         * latitude : 33.26451
         * placeName : 地理位置
         * user : {"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"userId":301,"userName":"17608061937","password":"dUmxJDKncvVLc562NTqL2w==","salt":null,"realName":"匿名","cardNo":null,"sex":null,"age":null,"phone":null,"email":null,"headUrl":null,"cardPicUrl":null,"cardJustUrl":"","cardBackUrl":"","address":null,"userType":null,"available":"0","lastLoginDate":null,"lastLoginIP":null,"clientId":"","checkState":"1","checkUserId":null,"checkUserName":null,"checkDate":null,"checkOpinion":null,"createDate":"2020-03-04 14:54:23","remarks":null,"delFlag":"0","accessToken":null,"errorTimes":0,"ryToken":null,"communityName":null,"ryUserId":null,"isdeduction":1,"invitationCode":"8BX7VE","isInvitationCode":"7hqsca","reputationNum":94}
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
        private String describes;
        private String  status;
        private String imgUrl;
        private String contactsPhone;
        private String longitude;
        private String latitude;
        private String placeName;
        private UserBean user;

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

        public String getDescribes() {
            return describes;
        }

        public void setDescribes(String describes) {
            this.describes = describes;
        }

        public String  getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getContactsPhone() {
            return contactsPhone;
        }

        public void setContactsPhone(String contactsPhone) {
            this.contactsPhone = contactsPhone;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class ParamsBean {
        }

        public static class UserBean {
            /**
             * searchValue : null
             * createBy : null
             * createTime : null
             * updateBy : null
             * updateTime : null
             * remark : null
             * params : {}
             * userId : 301
             * userName : 17608061937
             * password : dUmxJDKncvVLc562NTqL2w==
             * salt : null
             * realName : 匿名
             * cardNo : null
             * sex : null
             * age : null
             * phone : null
             * email : null
             * headUrl : null
             * cardPicUrl : null
             * cardJustUrl :
             * cardBackUrl :
             * address : null
             * userType : null
             * available : 0
             * lastLoginDate : null
             * lastLoginIP : null
             * clientId :
             * checkState : 1
             * checkUserId : null
             * checkUserName : null
             * checkDate : null
             * checkOpinion : null
             * createDate : 2020-03-04 14:54:23
             * remarks : null
             * delFlag : 0
             * accessToken : null
             * errorTimes : 0
             * ryToken : null
             * communityName : null
             * ryUserId : null
             * isdeduction : 1
             * invitationCode : 8BX7VE
             * isInvitationCode : 7hqsca
             * reputationNum : 94
             */

            private Object searchValue;
            private Object createBy;
            private Object createTime;
            private Object updateBy;
            private Object updateTime;
            private Object remark;
            private ParamsBeanX params;
            private int userId;
            private String userName;
            private String password;
            private Object salt;
            private String realName;
            private Object cardNo;
            private Object sex;
            private Object age;
            private String phone;
            private Object email;
            private Object headUrl;
            private Object cardPicUrl;
            private String cardJustUrl;
            private String cardBackUrl;
            private Object address;
            private Object userType;
            private String available;
            private Object lastLoginDate;
            private Object lastLoginIP;
            private String clientId;
            private String checkState;
            private Object checkUserId;
            private Object checkUserName;
            private Object checkDate;
            private Object checkOpinion;
            private String createDate;
            private Object remarks;
            private String delFlag;
            private Object accessToken;
            private int errorTimes;
            private Object ryToken;
            private Object communityName;
            private Object ryUserId;
            private int isdeduction;
            private String invitationCode;
            private String isInvitationCode;
            private int reputationNum;

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

            public ParamsBeanX getParams() {
                return params;
            }

            public void setParams(ParamsBeanX params) {
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

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Object getSalt() {
                return salt;
            }

            public void setSalt(Object salt) {
                this.salt = salt;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public Object getCardNo() {
                return cardNo;
            }

            public void setCardNo(Object cardNo) {
                this.cardNo = cardNo;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public Object getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(Object headUrl) {
                this.headUrl = headUrl;
            }

            public Object getCardPicUrl() {
                return cardPicUrl;
            }

            public void setCardPicUrl(Object cardPicUrl) {
                this.cardPicUrl = cardPicUrl;
            }

            public String getCardJustUrl() {
                return cardJustUrl;
            }

            public void setCardJustUrl(String cardJustUrl) {
                this.cardJustUrl = cardJustUrl;
            }

            public String getCardBackUrl() {
                return cardBackUrl;
            }

            public void setCardBackUrl(String cardBackUrl) {
                this.cardBackUrl = cardBackUrl;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getUserType() {
                return userType;
            }

            public void setUserType(Object userType) {
                this.userType = userType;
            }

            public String getAvailable() {
                return available;
            }

            public void setAvailable(String available) {
                this.available = available;
            }

            public Object getLastLoginDate() {
                return lastLoginDate;
            }

            public void setLastLoginDate(Object lastLoginDate) {
                this.lastLoginDate = lastLoginDate;
            }

            public Object getLastLoginIP() {
                return lastLoginIP;
            }

            public void setLastLoginIP(Object lastLoginIP) {
                this.lastLoginIP = lastLoginIP;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getCheckState() {
                return checkState;
            }

            public void setCheckState(String checkState) {
                this.checkState = checkState;
            }

            public Object getCheckUserId() {
                return checkUserId;
            }

            public void setCheckUserId(Object checkUserId) {
                this.checkUserId = checkUserId;
            }

            public Object getCheckUserName() {
                return checkUserName;
            }

            public void setCheckUserName(Object checkUserName) {
                this.checkUserName = checkUserName;
            }

            public Object getCheckDate() {
                return checkDate;
            }

            public void setCheckDate(Object checkDate) {
                this.checkDate = checkDate;
            }

            public Object getCheckOpinion() {
                return checkOpinion;
            }

            public void setCheckOpinion(Object checkOpinion) {
                this.checkOpinion = checkOpinion;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public Object getRemarks() {
                return remarks;
            }

            public void setRemarks(Object remarks) {
                this.remarks = remarks;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public Object getAccessToken() {
                return accessToken;
            }

            public void setAccessToken(Object accessToken) {
                this.accessToken = accessToken;
            }

            public int getErrorTimes() {
                return errorTimes;
            }

            public void setErrorTimes(int errorTimes) {
                this.errorTimes = errorTimes;
            }

            public Object getRyToken() {
                return ryToken;
            }

            public void setRyToken(Object ryToken) {
                this.ryToken = ryToken;
            }

            public Object getCommunityName() {
                return communityName;
            }

            public void setCommunityName(Object communityName) {
                this.communityName = communityName;
            }

            public Object getRyUserId() {
                return ryUserId;
            }

            public void setRyUserId(Object ryUserId) {
                this.ryUserId = ryUserId;
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

            public String getIsInvitationCode() {
                return isInvitationCode;
            }

            public void setIsInvitationCode(String isInvitationCode) {
                this.isInvitationCode = isInvitationCode;
            }

            public int getReputationNum() {
                return reputationNum;
            }

            public void setReputationNum(int reputationNum) {
                this.reputationNum = reputationNum;
            }

            public static class ParamsBeanX {
            }
        }
    }
}
