package com.witkey.witkeyhelp.presenter;

import com.witkey.witkeyhelp.view.IMCollectionView;

/**
 * Created by jie on 2019/11/28.
 */

public interface IMCollectionPresenter extends IPresenter<IMCollectionView> {

    void showCollection(int pageNum,int pageSize,int userId);
}
