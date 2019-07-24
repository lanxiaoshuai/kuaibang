package com.witkey.witkeyhelp.model;

import com.witkey.witkeyhelp.bean.ConsultBean;

public interface IConsultModel extends IModel{
    void saveConsult(ConsultBean consultBean, AsyncCallback callback);

    void publishConsult(String businessId, AsyncCallback callback);
}
