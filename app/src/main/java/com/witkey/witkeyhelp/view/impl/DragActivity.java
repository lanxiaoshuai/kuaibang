package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.witkey.witkeyhelp.Interfacecallback.Modifystate;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.ListDataSave;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.drag.DragView;
import com.witkey.witkeyhelp.adapter.MyDragAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;


/**
 * Created by Egos on 2017/11/7.
 */

public class DragActivity extends BaseActivity implements Modifystate {

    private final static String TAG = "DragActivity";

    public final static String EXTRA_UNDRAG_LIST = "extra_undrag_list";
    public final static String EXTRA_SELECT_LIST = "extra_select_list";
    public final static String EXTRA_UNSELECT_LIST = "extra_unselect_list";

    public final static String INDEXFRAGMENT = "INDEX";

    private List<CicleBean.ReturnObjectBean.RowsBean> undragLst;
    private List<CicleBean.ReturnObjectBean.RowsBean> selectLst;
    private List<CicleBean.ReturnObjectBean.RowsBean> unselectLst;


    DragView dragView;

    private MyDragAdapter mAdapter;

    private boolean hetherwEdit;
    private TextView edit_circle;
    private ListDataSave listDataSave;


    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_drag);
        dragView = (DragView) findViewById(R.id.dragView);

        dragView.setCallBack(this);

        edit_circle = findViewById(R.id.edit_circle);


        undragLst = getSavedInstanceState() != null ? (List<CicleBean.ReturnObjectBean.RowsBean>) getSavedInstanceState().getSerializable(
                EXTRA_UNDRAG_LIST) : (List<CicleBean.ReturnObjectBean.RowsBean>) getIntent().getSerializableExtra(EXTRA_UNDRAG_LIST);

        selectLst = getSavedInstanceState() != null ? (List<CicleBean.ReturnObjectBean.RowsBean>) getSavedInstanceState().getSerializable(
                EXTRA_SELECT_LIST) : (List<CicleBean.ReturnObjectBean.RowsBean>) getIntent().getSerializableExtra(EXTRA_SELECT_LIST);



        unselectLst = new ArrayList<>();
        listDataSave = new ListDataSave(this, "CICLE");
        unselectLst = listDataSave.getCicleList("CIRCLELIST");


        for (int i = 0; i < selectLst.size(); i++) {
            String id = selectLst.get(i).getCircleId();
            for (int j = 0; j < unselectLst.size(); j++) {
                if (unselectLst.get(j).getCircleId().equals(id)) {
                    unselectLst.remove(j);

                }
            }
        }


//        mAdapter = new MyDragAdapter(this, undragLst, selectLst, unselectLst);
//        dragView.setAdapter(mAdapter);
//        edit_circle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (hetherwEdit) {
//                    hetherwEdit = false;
//                    edit_circle.setText("编辑");
//                    listDataSave.setCicleList("CIRCLELIST", unselectLst);
//                } else {
//                    hetherwEdit = true;
//                    edit_circle.setText("完成");
//                }
//                dragView.getHetherwEdit(hetherwEdit);
//
//                dragView.editCircle(hetherwEdit);
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_UNDRAG_LIST, (Serializable) undragLst);
        outState.putSerializable(EXTRA_SELECT_LIST, (Serializable) selectLst);
        outState.putSerializable(EXTRA_UNSELECT_LIST, (Serializable) unselectLst);
    }

    @Override
    public void onBackPressed() {


        Intent intent = new Intent();
        intent.putExtra(DragActivity.EXTRA_SELECT_LIST, (Serializable) selectLst);
        intent.putExtra(DragActivity.EXTRA_UNSELECT_LIST, (Serializable) unselectLst);
        intent.putExtra(DragActivity.EXTRA_UNDRAG_LIST, (Serializable) undragLst);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void Modifiable() {

        hetherwEdit = true;
        edit_circle.setText("完成");
        //   dragView.editCircle(true);
    }

    @Override
    public void pageBack(int position) {

        Intent intent = new Intent();
        intent.putExtra(INDEXFRAGMENT, position);
        intent.putExtra(DragActivity.EXTRA_SELECT_LIST, (Serializable) selectLst);
        intent.putExtra(DragActivity.EXTRA_UNSELECT_LIST, (Serializable) unselectLst);
        intent.putExtra(DragActivity.EXTRA_UNDRAG_LIST, (Serializable) undragLst);
        setResult(RESULT_OK, intent);
        finish();

    }



}
