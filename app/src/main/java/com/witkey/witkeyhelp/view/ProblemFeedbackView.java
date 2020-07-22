package com.witkey.witkeyhelp.view;

import android.view.View;

import com.witkey.witkeyhelp.bean.PagingResponse;

/**
 * Created by jie on 2019/12/6.
 */

public interface ProblemFeedbackView  extends IView{

    /**
     * 上传反馈内容
     */
    void UploadFeedback(String data);

    /**
     * 上传图片
     */

    void saveImgSuc(String imgurl);
}
