package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.view.IConsultView;

public interface IConsultPresenter extends IPresenter<IConsultView>{
    void saveConsult(MissionBean missionBean);
    void publishConsult(String businessId);
}
