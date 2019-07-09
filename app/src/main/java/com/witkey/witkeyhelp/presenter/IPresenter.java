package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IView;

public interface IPresenter<V extends IView> {
    void init(V view);
}
