package com.witkey.witkeyhelp;


public interface URL {
    //显示图片
    String IP = "http://marketbackend.wxhemei.com";
    String versionUrl = IP + "/api/v1/";
    String getImgPath = "http://market.wxhmfcyy.com/uploads";

    //上传Error
    String appLog = versionUrl + "appLog";

    //排行榜-渠道数列表. [GET /newMediaChannelRank]
    String newMediaEffectiveFansRank = versionUrl + "newMediaEffectiveFansRank";

}
