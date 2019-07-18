package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.view.IMeFragView;

public interface IMeFragPresenter extends IPresenter<IMeFragView>{
    void getUser(User user);
}
