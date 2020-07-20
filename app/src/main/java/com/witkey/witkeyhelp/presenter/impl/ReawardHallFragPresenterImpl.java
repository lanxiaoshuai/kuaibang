package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.MissionModelImpl;
import com.witkey.witkeyhelp.presenter.IReawardHallFragPresenter;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
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
    public void getMissionList( MissionBean missionBean,String circleId) {
        model.getMissionList(missionBean,circleId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
                view.showMissionList((PagingResponse) data);
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();
                view.onError((String) data);
            }
        });
    }
}
