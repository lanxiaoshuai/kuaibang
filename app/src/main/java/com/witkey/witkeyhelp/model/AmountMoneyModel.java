package com.witkey.witkeyhelp.model;

/**
 * Created by jie on 2019/12/7.
 */

public interface AmountMoneyModel extends IModel {


    void showBondDetails(AsyncCallback callback);

    void depositWithdrawalDetails(AsyncCallback callback);

    void bankCardInformation(AsyncCallback callback);


    void myBankcard(String uid, AsyncCallback callback);


    void addBankCard(String uid, String bankId, String realName, String cardNo, String cardBank, AsyncCallback callback);


    void getBankCard(AsyncCallback callback);


    void cancelBankCard(String id, AsyncCallback callback);


    void cashWithdrawal(int userId, double amount, String ubankId, int type,String openId, AsyncCallback callback);
}
