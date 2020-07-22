package com.witkey.witkeyhelp.model.impl;

import com.witkey.witkeyhelp.bean.AllCardBean;
import com.witkey.witkeyhelp.bean.MyCardBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.AmountMoneyModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by jie on 2019/12/7.
 */

public class AmountMoneyModelImpl implements AmountMoneyModel {


    @Override
    public void showBondDetails(AsyncCallback callback) {

    }

    @Override
    public void depositWithdrawalDetails(AsyncCallback callback) {

    }

    @Override
    public void bankCardInformation(AsyncCallback callback) {

    }

    @Override
    public void myBankcard(String uid, final AsyncCallback callback) {
        api.apiUserBankGetUserBankList(uid).enqueue(new Callback(callback, "获取自己银行卡列表失败") {
            @Override
            public void getSuc(String body) {
                //  Log.e("TAG",body);
                MyCardBean beanFromJson = JsonUtils.getBeanFromJson(body, MyCardBean.class);
                List<MyCardBean.ReturnObjectBean.RowsBean> rows = beanFromJson.getReturnObject().getRows();

                callback.onSuccess(rows);
            }
        });
    }

    @Override
    public void addBankCard(String uid, String bankId, String realName, String cardNo, String cardBank, final AsyncCallback callback) {
        api.addBankCard(uid, bankId, realName, cardNo, cardBank).enqueue(new Callback(callback, "添加银行卡失败") {
            @Override
            public void getSuc(String body) {


                callback.onSuccess(body);
            }
        });
    }

    @Override
    public void getBankCard(final AsyncCallback callback) {

        api.getBankCard().enqueue(new Callback(callback, "获取银行卡列表失败") {
            @Override
            public void getSuc(String body) {


                PagingResponse missionResponse = JSONUtil.fromJsonObjectList(gson, JSONUtil.getValueToString(body, "returnObject"), AllCardBean.class);
                callback.onSuccess(missionResponse.getRows());
            }
        });
    }

    @Override
    public void cancelBankCard(String id, final AsyncCallback callback) {
        api.cancelBankCard(id).enqueue(new Callback(callback, "删除银行卡失败") {
            @Override
            public void getSuc(String body) {
                callback.onSuccess(body);
            }
        });
    }

    @Override
    public void cashWithdrawal(int userId, double amount, String ubankId, int type,String openId, final AsyncCallback callback) {
        api.cashWithdrawal(userId, amount, ubankId, type,openId).enqueue(new Callback(callback, "提现失败") {
            @Override
            public void getSuc(String body) {
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String errorMessage = jsonObject.getString("errorMessage");
                    callback.onSuccess(errorMessage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
