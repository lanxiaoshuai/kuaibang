package com.witkey.witkeyhelp.bean;

/**
 * Created by jie on 2020/4/7.
 */

public class AdDetailsBean  {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"searchValue":null,"createBy":null,"createTime":"2020-04-10 10:00:20","updateBy":null,"updateTime":null,"remark":null,"params":{"residue":9},"id":32,"createUserId":312,"amountType":"1","content":"谢谢","title":"程序","imgUrl":"120200410100019595092388.jpg,1202004101000191540794176.jpg","putNum":10,"putArea":"全国","placeName":null,"putScope":null,"putBalance":1.15,"status":"1","longitude":"112.595368","latitude":"37.787955","putLocation":"千美酒店(太原南站店)","type":1,"money":1,"user":{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"userId":312,"userName":"18810945325","password":"","salt":null,"realName":"匿名","cardNo":null,"sex":"0","age":null,"phone":null,"email":null,"headUrl":"1202004091430331720460234.jpg","cardPicUrl":null,"cardJustUrl":"","cardBackUrl":"","address":null,"userType":null,"available":"0","lastLoginDate":null,"lastLoginIP":null,"clientId":"","checkState":"1","checkUserId":null,"checkUserName":null,"checkDate":null,"checkOpinion":null,"createDate":"2020-03-30 09:41:01","remarks":null,"delFlag":"0","accessToken":null,"errorTimes":0,"ryToken":null,"communityName":null,"ryUserId":null,"isdeduction":1,"invitationCode":"982029","isInvitationCode":null,"reputationNum":99}}
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
         * createTime : 2020-04-10 10:00:20
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {"residue":9}
         * id : 32
         * createUserId : 312
         * amountType : 1
         * content : 谢谢
         * title : 程序
         * imgUrl : 120200410100019595092388.jpg,1202004101000191540794176.jpg
         * putNum : 10
         * putArea : 全国
         * placeName : null
         * putScope : null
         * putBalance : 1.15
         * status : 1
         * longitude : 112.595368
         * latitude : 37.787955
         * putLocation : 千美酒店(太原南站店)
         * type : 1
         * money : 1.0
         * user : {"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"userId":312,"userName":"18810945325","password":"","salt":null,"realName":"匿名","cardNo":null,"sex":"0","age":null,"phone":null,"email":null,"headUrl":"1202004091430331720460234.jpg","cardPicUrl":null,"cardJustUrl":"","cardBackUrl":"","address":null,"userType":null,"available":"0","lastLoginDate":null,"lastLoginIP":null,"clientId":"","checkState":"1","checkUserId":null,"checkUserName":null,"checkDate":null,"checkOpinion":null,"createDate":"2020-03-30 09:41:01","remarks":null,"delFlag":"0","accessToken":null,"errorTimes":0,"ryToken":null,"communityName":null,"ryUserId":null,"isdeduction":1,"invitationCode":"982029","isInvitationCode":null,"reputationNum":99}
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
        private Object placeName;
        private Object putScope;
        private double putBalance;
        private String status;
        private String longitude;
        private String latitude;
        private String putLocation;
        private int type;
        private double money;
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

        public Object getPlaceName() {
            return placeName;
        }

        public void setPlaceName(Object placeName) {
            this.placeName = placeName;
        }

        public Object getPutScope() {
            return putScope;
        }

        public void setPutScope(Object putScope) {
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

        public String getPutLocation() {
            return putLocation;
        }

        public void setPutLocation(String putLocation) {
            this.putLocation = putLocation;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class ParamsBean {
            /**
             * residue : 9
             */

            private String isRead;
            private int residue;

            public String getIsRead() {
                return isRead;
            }

            public void setIsRead(String isRead) {
                this.isRead = isRead;
            }

            public int getResidue() {
                return residue;
            }

            public void setResidue(int residue) {
                this.residue = residue;
            }
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
             * userId : 312
             * userName : 18810945325
             * password :
             * salt : null
             * realName : 匿名
             * cardNo : null
             * sex : 0
             * age : null
             * phone : null
             * email : null
             * headUrl : 1202004091430331720460234.jpg
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
             * createDate : 2020-03-30 09:41:01
             * remarks : null
             * delFlag : 0
             * accessToken : null
             * errorTimes : 0
             * ryToken : null
             * communityName : null
             * ryUserId : null
             * isdeduction : 1
             * invitationCode : 982029
             * isInvitationCode : null
             * reputationNum : 99
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
            private String sex;
            private Object age;
            private Object phone;
            private Object email;
            private String headUrl;
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
            private Object isInvitationCode;
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

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public Object getPhone() {
                return phone;
            }

            public void setPhone(Object phone) {
                this.phone = phone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
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

            public static class ParamsBeanX {
            }
        }
    }
}
