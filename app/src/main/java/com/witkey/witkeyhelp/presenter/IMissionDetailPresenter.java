package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IMissionDetailView;

public interface IMissionDetailPresenter extends IPresenter<IMissionDetailView>{
    void getMissionDetail(String businessId);

    void receipt(int orderId, int userId);
}
