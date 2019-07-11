package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.Mission;
import com.witkey.witkeyhelp.bean.MissionFilter;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.IReawardHallFragModel;
import com.witkey.witkeyhelp.model.impl.ReawardHallFragModelImpl;
import com.witkey.witkeyhelp.presenter.IReawardHallFragPresenter;
import com.witkey.witkeyhelp.view.IReawardHallFragView;

import java.util.List;

public class ReawardHallFragPresenterImpl implements IReawardHallFragPresenter {
    private IReawardHallFragView view;
    private IReawardHallFragModel model;

    @Override
    public void init(IReawardHallFragView view) {
        this.view = view;
        model=new ReawardHallFragModelImpl();
    }

    @Override
    public void getMissionList(String chooseClassify, String chooseOrder, MissionFilter filter,String searchKeyWord) {
        model.getMissionList(chooseClassify, chooseOrder, filter,searchKeyWord, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.showMissionList((List<Mission>) data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }
}
