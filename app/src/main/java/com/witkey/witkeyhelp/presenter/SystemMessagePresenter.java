package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.ProblemFeedbackView;
import com.witkey.witkeyhelp.view.SystemMessageView;

/**
 * Created by jie on 2019/12/6.
 */

public interface SystemMessagePresenter extends IPresenter<SystemMessageView> {


    void showSystemMessages(int pageNum, int paegsize,int userId);
}
