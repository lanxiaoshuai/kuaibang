package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.PagingResponse;

public interface ILostFoundView extends IView{
    void showLostFoundList(PagingResponse lostFoundRequest);
}
