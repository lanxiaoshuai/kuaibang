package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.ActivityNewsView;
import com.witkey.witkeyhelp.view.SystemMessageView;

/**
 * Created by jie on 2019/12/6.
 */

public interface ActivityNewsPresenter extends IPresenter<ActivityNewsView> {

    void showBillDData(int pageNum,int  pageSize,int userId,int amountType);
}
