package com.witkey.witkeyhelp.presenter.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.AllCardBean;
import com.witkey.witkeyhelp.bean.MyCardBean;
import com.witkey.witkeyhelp.model.AmountMoneyModel;
import com.witkey.witkeyhelp.model.IMicroNotificationModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.AmountMoneyModelImpl;
import com.witkey.witkeyhelp.model.impl.MicroNotificationModelImpl;
import com.witkey.witkeyhelp.presenter.AmountMoneyPresenter;
import com.witkey.witkeyhelp.presenter.IMessageFragPresenter;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.AmountMoneyView;
import com.witkey.witkeyhelp.view.IAddMicroNotificationView;

import java.util.List;

/**
 * Created by jie on 2019/12/7.
 */

public class AmountMoneyPresenterImpl implements AmountMoneyPresenter {

    private AmountMoneyView view;
    private AmountMoneyModel model;

    @Override
    public void init(AmountMoneyView view) {
        this.view = view;
        this.model = new AmountMoneyModelImpl();
    }

    @Override
    public void showBondDetails() {

    }

    @Override
    public void depositWithdrawalDetails() {

    }

    @Override
    public void bankCardInformation() {

    }

    @Override
    public void myBankcard(String uid) {
        model.myBankcard(uid, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.myBankcard((List<MyCardBean.ReturnObjectBean.RowsBean>) data);
            }

            @Override
            public void onError(Object data) {
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void addBankCard(String uid, String bankId, String realName, String cardNo, String cardBank) {
        model.addBankCard(uid, bankId, realName, cardNo, cardBank, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                Log.e("tag", data.toString());
                view.addBankCard();
                DialogUtil.dismissProgress();
            }

            @Override
            public void onError(Object data) {
                Log.e("tag", data.toString());
                DialogUtil.dismissProgress();
            }
        });
    }

    @Override
    public void getBankCard() {
        model.getBankCard(new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {

                view.getBankCard((List<AllCardBean>) data);
            }

            @Override
            public void onError(Object data) {
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void cancelBankCard(String id) {
        model.cancelBankCard(id, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.cancelBankCard();
            }

            @Override
            public void onError(Object data) {
                view.onError(data.toString());
            }
        });
    }

    @Override
    public void cashWithdrawal(int userId, double amount, String ubankId, int type,String openId) {
        model.cashWithdrawal(userId, amount, ubankId, type,openId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                DialogUtil.dismissProgress();
              view.cashWithdrawal(data.toString());
            }

            @Override
            public void onError(Object data) {
                DialogUtil.dismissProgress();

                view.onError(data.toString());
            }
        });
    }

}
