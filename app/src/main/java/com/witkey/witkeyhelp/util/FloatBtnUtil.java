package com.witkey.witkeyhelp.util;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by jie on 2020/6/20.
 */

public class FloatBtnUtil {

    private static int height = 0;
    private Activity mcontext;
    private ViewTreeObserver.OnGlobalLayoutListener listener;
    private View root;

    public FloatBtnUtil(Activity mcontext) {
        this.mcontext = mcontext;
        if (height == 0) {
            Display defaultDisplay = mcontext.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            height = point.y;
        }
    }

    public void setFloatView(View root, final View floatview) {
        this.root = root; //视图根节点 floatview // 需要显示在键盘上的View组件
        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                mcontext.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                int heightDifference = height - (r.bottom - r.top);
                boolean isKeyboardShowing = heightDifference > height / 5;
                if (isKeyboardShowing) {

                    floatview.setVisibility(View.VISIBLE);
                    floatview.animate().translationY(-heightDifference).setDuration(0).start();
                } else {

                    floatview.setVisibility(View.GONE);
                    floatview.animate().translationY(0).start();
                }
            }
        };
        root.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    public void clearFloatView() {
        if (listener != null && root != null)
            root.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }
}
