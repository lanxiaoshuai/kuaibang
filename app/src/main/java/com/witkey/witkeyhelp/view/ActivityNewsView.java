package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.BillFlowBean;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public interface ActivityNewsView  extends IView {

    void showBill( BillFlowBean beanFromJson );
}
