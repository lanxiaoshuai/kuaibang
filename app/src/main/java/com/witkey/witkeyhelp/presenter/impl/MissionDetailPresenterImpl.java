package com.witkey.witkeyhelp.presenter.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.TaskDetailsBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.impl.MissionModelImpl;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMissionDetailView;

public class MissionDetailPresenterImpl implements com.witkey.witkeyhelp.presenter.IMissionDetailPresenter {
    private IMissionModel model;
    private IMissionDetailView view;

    @Override
    public void getMissionDetail(String businessId, int userId) {
        model.getMissionDetail(businessId, userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.showMission((MissionBean) data);
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError((String) data);
            }
        });
    }

    @Override
    public void receipt(int orderId, int userId) {
        model.receipt(orderId, userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.receiptSuc();
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void collection(int orderId, int userId) {
        model.ollection(orderId, userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {


                view.getCollection();
            }

            @Override
            public void onError(Object data) {

                view.onError((String) data);
            }
        });
    }

    @Override
    public void cancelCollection(int orderId, int userId) {
        model.cancelCollection(orderId, userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {


                view.getCancelCollection();
            }

            @Override
            public void onError(Object data) {

                view.onError((String) data);
            }
        });
    }

    @Override
    public void taskRelievingCollection(String receiver, String orderId, String businessId, String remark, int start) {
        Log.e("tag",orderId + "bbbb");
        model.taskRelievingCollection(receiver, orderId, businessId, remark, start, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.taskRelieving(data.toString());
            }

            @Override
            public void onError(Object data) {

                DialogUtil.dismissProgress();
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void confirmsReceiveOrder(String orderId, int taskdetails) {
        model.confirmsReceiveOrder(orderId, taskdetails, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.issionmAccomplished(data.toString());
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void getDeductionData(int userId, int deduction) {
        model.getDeductionData(userId, deduction, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
            //    DialogUtil.dismissProgress();
             //   view.showDeductionData(data.toString());
            }

            @Override
            public void onError(Object data) {
              //  DialogUtil.dismissProgress();
                view.onError((String) data);
            }
        });
    }

    @Override
    public void getAcount(int userId) {
        model.getAcount(userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.showAcount((Acount) data);
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError((String) data);
            }
        });
    }

    @Override
    public void UploadFeedback(String content, int userId, String imgUrl, int type, Integer businessId,int orderId) {
        model.UploadFeedback(content, userId, imgUrl, type,businessId,orderId, new IModel.AsyncCallback() {
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
    public void init(IMissionDetailView view) {
        this.view = view;
        this.model = new MissionModelImpl();
    }
}
