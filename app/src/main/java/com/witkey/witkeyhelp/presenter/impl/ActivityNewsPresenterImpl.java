package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.BillFlowBean;
import com.witkey.witkeyhelp.model.ActivityNewsModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.ActivityNewsModelImpl;
import com.witkey.witkeyhelp.presenter.ActivityNewsPresenter;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.view.ActivityNewsView;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityNewsPresenterImpl implements ActivityNewsPresenter {

    private ActivityNewsView view;
    private ActivityNewsModel model;
    @Override
    public void init(ActivityNewsView view) {
        this.view = view;
        model = new ActivityNewsModelImpl();
    }

    @Override
    public void showBillDData(int pageNum,int  pageSize,int userId,int amountType) {
          model.showBillDData(pageNum, pageSize, userId, amountType, new IModel.AsyncCallback() {
              @Override
              public void onSuccess(Object data) {
                  BillFlowBean beanFromJson = JsonUtils.getBeanFromJson(data.toString(), BillFlowBean.class);
              //    List<BillFlowBean.ReturnObjectBean.RowsBean> rows = beanFromJson.getReturnObject().getRows();

                  view.showBill(beanFromJson);
              }

              @Override
              public void onError(Object data) {
                  view.onError(data.toString());
              }
          });
    }
}
