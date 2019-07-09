package com.witkey.witkeyhelp.view;

/**
 * @author lingxu
 * @date 2019/7/9 14:17
 * @description IView
 */
public interface IView {
    /**
     * 获取数据错误时的信息
     *
     * @param error 错误信息
     */
    void onError(String error);
}
