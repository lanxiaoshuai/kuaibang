package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.view.IMeFragView;

public interface IMeFragPresenter extends IPresenter<IMeFragView>{
    void getUser(int id);

    void getAcount(int userId);



    void getDeductionData(int userId,int deduction );

    void updateUserInfo(int userId,String realName , String headUrl,String sex);
}
