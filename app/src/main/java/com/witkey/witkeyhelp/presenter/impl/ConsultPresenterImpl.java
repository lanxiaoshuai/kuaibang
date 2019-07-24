package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.ConsultBean;
import com.witkey.witkeyhelp.model.IConsultModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.ConsultModelImpl;
import com.witkey.witkeyhelp.view.IConsultView;

public class ConsultPresenterImpl implements com.witkey.witkeyhelp.presenter.IConsultPresenter {
    private IConsultView view;
    private IConsultModel model;
    @Override
    public void saveConsult(ConsultBean consultBean) {
        model.saveConsult(consultBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.saveSuc((String)data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void publishConsult(String businessId) {
        model.publishConsult(businessId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.publishSuc();
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IConsultView view) {
        this.view=view;
        this.model=new ConsultModelImpl();
    }
}
