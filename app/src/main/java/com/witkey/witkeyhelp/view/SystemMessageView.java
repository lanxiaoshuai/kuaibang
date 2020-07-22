package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.SystemMessageBean;

import java.util.List;

/**
 * Created by jie on 2019/12/6.
 */

public interface SystemMessageView extends IView {

    void showSystemMessages(     SystemMessageBean.ReturnObjectBean rows );
}
