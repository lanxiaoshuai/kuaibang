package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.MissionRequest;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.impl.MissionModelImpl;
import com.witkey.witkeyhelp.presenter.IReawardHallFragPresenter;
import com.witkey.witkeyhelp.view.IReawardHallFragView;

public class ReawardHallFragPresenterImpl implements IReawardHallFragPresenter {
    private IReawardHallFragView view;
    private IMissionModel model;

    @Override
    public void init(IReawardHallFragView view) {
        this.view = view;
        model=new MissionModelImpl();
    }

    @Override
    public void getMissionList( MissionBean missionBean,String searchKeyWord) {
        model.getMissionList(missionBean,searchKeyWord, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMissionList((MissionRequest) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }
}
