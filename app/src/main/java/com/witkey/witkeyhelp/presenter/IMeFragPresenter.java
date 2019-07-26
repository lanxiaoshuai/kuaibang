package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IMeFragView;

public interface IMeFragPresenter extends IPresenter<IMeFragView>{
    void getUser(int id);

    void getAcount(int userId);
}
