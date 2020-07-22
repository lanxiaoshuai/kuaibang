package com.witkey.witkeyhelp.presenter.impl;

import android.util.Log;

import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.model.IMCollectionModel;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.impl.ConsultModelImpl;
import com.witkey.witkeyhelp.model.impl.IMCollectionModelImpl;
import com.witkey.witkeyhelp.presenter.IMCollectionPresenter;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.IMCollectionView;

/**
 * Created by jie on 2019/11/28.
 */

public class IMCollectionPresenterImpl implements IMCollectionPresenter {

    IMCollectionModel model;
    IMCollectionView view;
    @Override
    public void init(IMCollectionView view) {
        this.view=view;
        model = new IMCollectionModelImpl();
    }

    @Override
    public void showCollection(int pageNum,int pageSize,int userId) {
        model.showCollectionDetail(pageNum, pageSize, userId, new IModel.AsyncCallback() {
            @Override
            public void onSuccess(Object data) {

                view.showCollectionDetail((PagingResponse)data);
            }

            @Override
            public void onError(Object data) {
                view.onError((String) data);


            }
        });
    }
}
