package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.TaskDetailsBean;

public interface IMissionDetailView extends IView {
    void showMission(MissionBean missionBean);
    void receiptSuc();
    void onError(String  msg);

    void getCollection();


    void getCancelCollection();

    void taskRelieving(String data);

    void issionmAccomplished(String data);

    void showAcount(Acount data);


    /**
     * 上传反馈内容
     */
    void UploadFeedback(String data);
}
