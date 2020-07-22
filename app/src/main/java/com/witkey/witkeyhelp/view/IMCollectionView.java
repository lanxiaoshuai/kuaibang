package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.MessageResponse;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;

/**
 * Created by jie on 2019/11/28.
 */

public interface IMCollectionView  extends IView {

    void showCollectionDetail( PagingResponse missionBean);
}
