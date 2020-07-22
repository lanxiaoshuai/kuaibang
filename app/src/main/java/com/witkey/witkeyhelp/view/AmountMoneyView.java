package com.witkey.witkeyhelp.view;


import com.witkey.witkeyhelp.bean.AllCardBean;
import com.witkey.witkeyhelp.bean.MyCardBean;

import java.util.List;

/**
 * Created by jie on 2019/12/7.
 */

public interface AmountMoneyView extends IView {


    void showBondDetails();

    void depositWithdrawalDetails();

    void bankCardInformation();


    void myBankcard(List<MyCardBean.ReturnObjectBean.RowsBean> mlist);


    void addBankCard();




    void  getBankCard( List<AllCardBean> mDatas);

    void cancelBankCard();


    void cashWithdrawal(String data);


}
