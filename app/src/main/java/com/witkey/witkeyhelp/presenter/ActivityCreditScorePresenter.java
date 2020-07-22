package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.ActivityCreditScoreView;
import com.witkey.witkeyhelp.view.ActivityNewsView;

/**
 * Created by jie on 2019/12/6.
 */

public interface ActivityCreditScorePresenter extends IPresenter<ActivityCreditScoreView> {

    void showBillDData(int pageNum, int pageSize, int userId);
}
