package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.view.IReawardHallFragView;
import com.witkey.witkeyhelp.view.ProblemFeedbackView;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public interface ProblemFeedbackPresenter extends IPresenter<ProblemFeedbackView> {

    /**
     * 上传反馈内容
     */
    void UploadFeedback(String content, int userId ,String imgUrl,int type,Integer  businessId,int orderId);

    /**
     * 上传图片
     */

    void saveImgSuc(List<ReleasePhotoBean> missionBean);

}
