package com.witkey.witkeyhelp.presenter.impl;

import com.witkey.witkeyhelp.bean.BillFlowBean;
import com.witkey.witkeyhelp.bean.CreditScoreBean;
import com.witkey.witkeyhelp.model.ActivityCreditScoreModel;
import com.witkey.witkeyhelp.model.ActivityCreditScoreModelImpl;
import com.witkey.witkeyhelp.model.ActivityNewsModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.ActivityNewsModelImpl;
import com.witkey.witkeyhelp.presenter.ActivityCreditScorePresenter;
import com.witkey.witkeyhelp.presenter.ActivityNewsPresenter;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.view.ActivityCreditScoreView;
import com.witkey.witkeyhelp.view.ActivityNewsView;

/**
 * Created by jie on 2019/12/6.
 */

public class ActivityCrediScorePresenterImpl implements ActivityCreditScorePresenter {

    private ActivityCreditScoreView view;
    private ActivityCreditScoreModel model;
    @Override
    public void init(ActivityCreditScoreView view) {
        this.view = view;
        model = new ActivityCreditScoreModelImpl();
    }

    @Override
    public void showBillDData(int pageNum,int  pageSize,int userId) {
          model.showBillDData(pageNum, pageSize, userId, new IModel.AsyncCallback() {
              @Override
              public void onSuccess(Object data) {
                  CreditScoreBean beanFromJson = JsonUtils.getBeanFromJson(data.toString(), CreditScoreBean.class);


                  view.showBill(beanFromJson);
              }

              @Override
              public void onError(Object data) {
                  view.onError(data.toString());
              }
          });
    }
}
