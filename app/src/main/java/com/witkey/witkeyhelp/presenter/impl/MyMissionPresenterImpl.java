package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.MissionResponse;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MissionModelImpl;
import com.witkey.witkeyhelp.view.IMyMissionView;

public class MyMissionPresenterImpl implements com.witkey.witkeyhelp.presenter.IMyMissionPresenter {
    private IMissionModel model;
    private IMyMissionView view;

    @Override
    public void getReleaseMissionList(MissionBean missionBean) {
        model.getReleaseMissionList(missionBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMissionList((MissionResponse) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void getReceiveMissionList(MissionBean missionBean) {
        model.getReceiveMissionList(missionBean, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMissionList((MissionResponse) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void init(IMyMissionView view) {
        this.view = view;
        this.model = new MissionModelImpl();
    }
}
