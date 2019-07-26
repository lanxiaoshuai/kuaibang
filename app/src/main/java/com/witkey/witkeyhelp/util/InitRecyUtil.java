package com.witkey.witkeyhelp.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018/4/27.
 */

public class InitRecyUtil {
    public static LinearLayoutManager initListRecy(Context context, RecyclerView recyclerView, int recydividerHeightDP) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new
                SpaceItemDecoration(0, UIUtils.dip2px(recydividerHeightDP)));
        return layoutManager;
    }
}
