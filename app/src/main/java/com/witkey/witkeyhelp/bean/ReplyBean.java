package com.witkey.witkeyhelp.bean;

import java.io.Serializable;
import java.util.List;

public class ReplyBean implements Serializable {


    /**
     * errorCode : 200
     * errorMessage : 请求成功
     * returnObject : {"total":2,"rows":[{"createTime":null,"params":{},"id":"6566b5c7-4822-4b4e-bcc3-b61e61021877","userId":341,"content":"测试","userName":"匿名","list":[{"createTime":"2020-07-03 17:33:42","id":"22b19d2f-20f8-40eb-9ec4-43ce80f2897e","userId":343,"content":"回复评论","userName":"ccc","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 14:30:01","id":"2f2a1a27-2bdf-4d01-8d85-79ca81798b6f","userId":395,"content":"123","userName":"匿名","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 11:56:32","id":"af6ddd83-459c-4c15-a9a0-8ac0ef5f90e0","userId":349,"content":"回复评论1","userName":"匿名","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 14:31:41","id":"39553246-f8a5-420a-8e42-9e3a8950455c","userId":349,"content":"回复评论1111","userName":"匿名","cid":"22b19d2f-20f8-40eb-9ec4-43ce80f2897e"}],"bid":1},{"createTime":"2020-07-03 18:02:27","params":{},"id":"65e1a7a5-9d4a-4d1b-a55c-e1b559077bfc","userId":342,"content":"测试评论","userName":"匿名","list":[],"bid":1}],"code":0}
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
         * rows : [{"createTime":null,"params":{},"id":"6566b5c7-4822-4b4e-bcc3-b61e61021877","userId":341,"content":"测试","userName":"匿名","list":[{"createTime":"2020-07-03 17:33:42","id":"22b19d2f-20f8-40eb-9ec4-43ce80f2897e","userId":343,"content":"回复评论","userName":"ccc","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 14:30:01","id":"2f2a1a27-2bdf-4d01-8d85-79ca81798b6f","userId":395,"content":"123","userName":"匿名","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 11:56:32","id":"af6ddd83-459c-4c15-a9a0-8ac0ef5f90e0","userId":349,"content":"回复评论1","userName":"匿名","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 14:31:41","id":"39553246-f8a5-420a-8e42-9e3a8950455c","userId":349,"content":"回复评论1111","userName":"匿名","cid":"22b19d2f-20f8-40eb-9ec4-43ce80f2897e"}],"bid":1},{"createTime":"2020-07-03 18:02:27","params":{},"id":"65e1a7a5-9d4a-4d1b-a55c-e1b559077bfc","userId":342,"content":"测试评论","userName":"匿名","list":[],"bid":1}]
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
             * createTime : null
             * params : {}
             * id : 6566b5c7-4822-4b4e-bcc3-b61e61021877
             * userId : 341
             * content : 测试
             * userName : 匿名
             * list : [{"createTime":"2020-07-03 17:33:42","id":"22b19d2f-20f8-40eb-9ec4-43ce80f2897e","userId":343,"content":"回复评论","userName":"ccc","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 14:30:01","id":"2f2a1a27-2bdf-4d01-8d85-79ca81798b6f","userId":395,"content":"123","userName":"匿名","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 11:56:32","id":"af6ddd83-459c-4c15-a9a0-8ac0ef5f90e0","userId":349,"content":"回复评论1","userName":"匿名","cid":"6566b5c7-4822-4b4e-bcc3-b61e61021877"},{"createTime":"2020-07-04 14:31:41","id":"39553246-f8a5-420a-8e42-9e3a8950455c","userId":349,"content":"回复评论1111","userName":"匿名","cid":"22b19d2f-20f8-40eb-9ec4-43ce80f2897e"}]
             * bid : 1
             */

            private String createTime;
            private ParamsBean params;
            private String id;
            private int userId;
            private String content;
            private String userName;
            private String headUrl;
            private String userPhone;
            private Object isShow;
            private Object rewardMoney;

            public Object getIsShow() {
                return isShow;
            }

            public void setIsShow(Object isShow) {
                this.isShow = isShow;
            }

            public Object getRewardMoney() {
                return rewardMoney;
            }

            public void setRewardMoney(Object rewardMoney) {
                this.rewardMoney = rewardMoney;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public String getHeadUrl() {
                return headUrl;
            }

            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            private int bid;
            private List<ListBean> list;

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

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getBid() {
                return bid;
            }

            public void setBid(int bid) {
                this.bid = bid;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ParamsBean implements Serializable {
            }

            public static class ListBean implements Serializable {
                /**
                 * createTime : 2020-07-03 17:33:42
                 * id : 22b19d2f-20f8-40eb-9ec4-43ce80f2897e
                 * userId : 343
                 * content : 回复评论
                 * userName : ccc
                 * cid : 6566b5c7-4822-4b4e-bcc3-b61e61021877
                 */

                private String createTime;
                private String id;
                private int userId;
                private String content;
                private String userName;
                private String cid;
                private String headUrl;
                private String replyUserId;
                private String replyName;
                private String replyHeadUrl;
                private boolean more;
                private String userPhone;
                private String replyUserPhone;
                private Object isShow;
                private Object rewardMoney;

                public Object getIsShow() {
                    return isShow;
                }

                public void setIsShow(Object isShow) {
                    this.isShow = isShow;
                }

                public Object getRewardMoney() {
                    return rewardMoney;
                }

                public void setRewardMoney(Object rewardMoney) {
                    this.rewardMoney = rewardMoney;
                }

                public String getReplyUserPhone() {
                    return replyUserPhone;
                }

                public void setReplyUserPhone(String replyUserPhone) {
                    this.replyUserPhone = replyUserPhone;
                }

                public String getUserPhone() {
                    return userPhone;
                }

                public void setUserPhone(String userPhone) {
                    this.userPhone = userPhone;
                }

                public boolean isMore() {
                    return more;
                }

                public void setMore(boolean more) {
                    this.more = more;
                }

                public String getReplyName() {
                    return replyName;
                }

                public void setReplyName(String replyName) {
                    this.replyName = replyName;
                }

                public String getReplyHeadUrl() {
                    return replyHeadUrl;
                }

                public void setReplyHeadUrl(String replyHeadUrl) {
                    this.replyHeadUrl = replyHeadUrl;
                }

                public String getReplyUserId() {
                    return replyUserId;
                }


                public void setReplyUserId(String replyUserId) {
                    this.replyUserId = replyUserId;
                }

                public String getHeadUrl() {
                    return headUrl;
                }

                public void setHeadUrl(String headUrl) {
                    this.headUrl = headUrl;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
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

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getCid() {
                    return cid;
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }
            }
        }
    }
}
