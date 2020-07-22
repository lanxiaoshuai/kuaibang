package com.witkey.witkeyhelp.widget.pickerimage.view;


import com.witkey.witkeyhelp.R;

public class ToolBarOptions {
    /**
     * toolbar的title资源id
     */
    public int titleId = 0;
    /**
     * toolbar的title
     */
    public String titleString;
    /**
     * toolbar的返回按钮资源id，默认开启的资源
     */
    public int navigateId = R.mipmap.back;
    /**
     * toolbar的返回按钮，默认开启
     */
    public boolean isNeedNavigate = true;
}
