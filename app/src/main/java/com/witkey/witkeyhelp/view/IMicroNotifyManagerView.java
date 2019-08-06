package com.witkey.witkeyhelp.view;

import com.witkey.witkeyhelp.bean.MicroNotifyManagerBean;

import java.util.ArrayList;

public interface IMicroNotifyManagerView extends IView{
    void showMicroNotifyManager(ArrayList<MicroNotifyManagerBean> microNotifyManagerBeans);
}
