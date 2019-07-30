package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.ILostFoundDetailView;

public interface ILostFoundDetailPresenter extends IPresenter<ILostFoundDetailView>{
    void getLostFoundDetail(int id);
}
