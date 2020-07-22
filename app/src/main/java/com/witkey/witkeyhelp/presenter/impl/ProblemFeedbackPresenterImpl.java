package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.ProblemFeedbackModel;
import com.witkey.witkeyhelp.model.impl.MissionModelImpl;
import com.witkey.witkeyhelp.model.impl.ProblemFeedbackModelImpl;
import com.witkey.witkeyhelp.presenter.ProblemFeedbackPresenter;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IReawardHallFragView;
import com.witkey.witkeyhelp.view.ProblemFeedbackView;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class ProblemFeedbackPresenterImpl implements ProblemFeedbackPresenter {


    private ProblemFeedbackView view;
    private ProblemFeedbackModel model;

    @Override
    public void init(ProblemFeedbackView view) {
        this.view = view;
        model = new ProblemFeedbackModelImpl();
    }

    @Override
    public void UploadFeedback(String content, int userId, String imgUrl, int type,Integer  businessId,int orderId) {

        model.UploadFeedback(content, userId, imgUrl, type,businessId, orderId,new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.UploadFeedback((String) data);
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError((String) data);
            }
        });
    }

    @Override
    public void saveImgSuc(List<ReleasePhotoBean> missionBean) {
        model.saveImgSuc(missionBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.saveImgSuc((String) data);
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError((String) data);

            }
        });
    }
}
