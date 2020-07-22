package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.witkey.witkeyhelp.adapter.ListCicleAdapter;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.util.ListDataSave;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import java.util.List;

/**
 * Created by jie on 2020/6/20.
 */

public class ActivityListCicle extends BaseActivity {
    private List<CicleBean.ReturnObjectBean.RowsBean> searchlist;
    private static final String ARG_TITLE = "section_number";
    private List<CicleBean.ReturnObjectBean.RowsBean> history;

    private ListDataSave listDataSave;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_cicler_list);
        setIncludeTitle(getIntent().getStringExtra("content"));
        listDataSave = new ListDataSave(this, "BROWSE");

        history = listDataSave.getCicleList(user.getUserName());
        searchlist = (List<CicleBean.ReturnObjectBean.RowsBean>) getIntent().getSerializableExtra("CIRCLELIST");
        if (null == searchlist || searchlist.size() == 0) {
            return;
        }
        ListView cicle_list = findViewById(R.id.cicle_list);
        findViewById(R.id.create_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityListCicle.this, CreateCircleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ListCicleAdapter listCicleAdapter = new ListCicleAdapter(this, searchlist);
        cicle_list.setAdapter(listCicleAdapter);
        cicle_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityListCicle.this, CircleActivity.class);
                intent.putExtra("circleId", searchlist.get(position).getCircleId());
                intent.putExtra(ARG_TITLE, searchlist.get(position));
                startActivity(intent);

                if (history.size() == 0) {
                    history.add(searchlist.get(position));
                } else {
                    String cid = "";
                    for (int i = 0; i < history.size(); i++) {
                        String circleId = history.get(i).getCircleId();
                        if (circleId.equals(searchlist.get(position).getCircleId())) {
                            cid = circleId;
                            CicleBean.ReturnObjectBean.RowsBean rowsBean = history.get(i);
                            history.remove(rowsBean);
                            //    Collections.reverse(history);
                            history.add(0, rowsBean);

                            break;
                        }
                    }
                    if ("".equals(cid) || cid == null) {
                        history.add(0, searchlist.get(position));
                    }
                }
                listDataSave.setCicleList(user.getUserName(), history);
                finish();
            }
        });
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }
}
