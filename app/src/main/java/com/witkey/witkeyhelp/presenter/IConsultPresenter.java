package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.ConsultBean;
import com.witkey.witkeyhelp.view.IConsultView;

public interface IConsultPresenter extends IPresenter<IConsultView>{
    void saveConsult(ConsultBean consultBean);
    void publishConsult(String businessId);
}
