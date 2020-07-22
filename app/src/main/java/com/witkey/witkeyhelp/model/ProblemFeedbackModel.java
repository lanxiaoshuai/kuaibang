package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.ReleasePhotoBean;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public interface ProblemFeedbackModel extends IModel {




    /**
     * 上传反馈内容
     */
    void UploadFeedback(String content, int userId ,String imgUrl,int type,Integer  businessId , int orderId,  AsyncCallback callback);

    /**
     * 上传图片
     */

    void saveImgSuc(List<ReleasePhotoBean> missionBean, AsyncCallback callback);
}
