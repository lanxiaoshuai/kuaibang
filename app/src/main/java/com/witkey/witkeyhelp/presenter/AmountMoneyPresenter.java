package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.AmountMoneyView;

/**
 * Created by jie on 2019/12/7.
 */

public interface AmountMoneyPresenter extends IPresenter<AmountMoneyView> {


    void showBondDetails();

    void depositWithdrawalDetails();

    void bankCardInformation();


    void myBankcard(String uid);


    void addBankCard(String uid, String bankId, String realName, String cardNo, String cardBank);


    void getBankCard();

    void cancelBankCard(String id);


    void cashWithdrawal(int userId, double amount, String bankId, int type,String openId);
}
