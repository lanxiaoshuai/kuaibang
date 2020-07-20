package com.witkey.witkeyhelp.presenter;

import android.util.Log;

import com.witkey.witkeyhelp.bean.LoginRequest;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.model.ILoginModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.LoginModelImpl;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.ILoginView;

public class LoginPresenterImpl implements ILoginPresenter {
    private ILoginModel model;
    private ILoginView view;


    @Override
    public void Login(final LoginRequest loginRequest) {
        model.Login(loginRequest, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {

                view.passSuccess((User) data);

            }

            @Override
            public void onError(Object data) {


                view.onError((String) data);
            }
        });
    }

    @Override
    public void GetCode(LoginRequest loginRequest) {
        model.GetCode(loginRequest, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {
                view.getCodeSuccess(data.toString());
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);
            }
        });
    }

    @Override
    public void register(LoginRequest loginRequest) {
        model.register(loginRequest, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {

                view.passRegisterSuccess();

            }

            @Override
            public void onError(Object data) {

                view.onError(data.toString());
            }
        });
    }

    @Override
    public void init(ILoginView view) {
        model = new LoginModelImpl();
        this.view = view;
    }

}
