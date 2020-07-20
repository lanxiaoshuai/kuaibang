package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.User;

public interface IMeFragView extends IView{
    void showUser(User user);

    void showAcount(Acount data);


    void showDeductionData(String data);


    void updateUserInfo(String data);
}
