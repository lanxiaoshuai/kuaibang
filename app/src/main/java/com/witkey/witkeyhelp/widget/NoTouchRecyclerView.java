package com.witkey.witkeyhelp.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jie on 2020/4/30.
 */

public class NoTouchRecyclerView extends RecyclerView {

    public NoTouchRecyclerView(Context context) {
        super(context);
    }

    public NoTouchRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoTouchRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}
