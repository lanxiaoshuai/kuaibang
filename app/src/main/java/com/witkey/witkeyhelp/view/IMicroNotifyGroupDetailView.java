package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.PagingResponse;

public interface IMicroNotifyGroupDetailView extends IView{
    void showGroupMember(PagingResponse groupMemberListResponse);
}
