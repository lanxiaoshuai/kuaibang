package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.LostFoundResponse;

public interface ILostFoundView extends IView{
    void showLostFoundList(LostFoundResponse lostFoundRequest);
}
