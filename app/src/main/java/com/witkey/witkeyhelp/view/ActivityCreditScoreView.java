package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.BillFlowBean;
import com.witkey.witkeyhelp.bean.CreditScoreBean;

/**
 * Created by jie on 2019/12/6.
 */

public interface ActivityCreditScoreView extends IView {

    void showBill(CreditScoreBean beanFromJson);
}
