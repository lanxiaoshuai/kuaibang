package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.SystemMessageBean;
import com.witkey.witkeyhelp.model.IMissionModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.SystemMessageModel;
import com.witkey.witkeyhelp.model.impl.MissionModelImpl;
import com.witkey.witkeyhelp.model.impl.SystemMessageModelImpl;
import com.witkey.witkeyhelp.presenter.IReawardHallFragPresenter;
import com.witkey.witkeyhelp.presenter.SystemMessagePresenter;
import com.witkey.witkeyhelp.view.IReawardHallFragView;
import com.witkey.witkeyhelp.view.SystemMessageView;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class SystemMessagePresenterImpl implements SystemMessagePresenter {


    private SystemMessageView view;
    private SystemMessageModel model;

    @Override
    public void init(SystemMessageView view) {
        this.view = view;
        model = new SystemMessageModelImpl();
    }


    @Override
    public void showSystemMessages(int pageNum, int paegsize,int userId) {
      model.showSystemMessages(pageNum, paegsize, userId,new IModel.AsyncCallback() {
          @Override
          public void onSuccess(Object data) {
              SystemMessageBean data1 = (SystemMessageBean) data;
              SystemMessageBean.ReturnObjectBean returnObject = data1.getReturnObject();


              view.showSystemMessages(returnObject);
          }

          @Override
          public void onError(Object data) {
              view.onError(data.toString());
          }
      });
    }
}
