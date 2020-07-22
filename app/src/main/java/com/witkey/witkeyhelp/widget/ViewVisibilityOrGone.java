package com.witkey.witkeyhelp.widget;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jie on 2020/6/28.
 */

public class ViewVisibilityOrGone {

    /**
     * 执行动画对象
     */
    private ValueAnimator animator ;
    /**
     * 操作的目标view
     */
    private View view;

    private int viewHeight;
    /**
     * 移动的方向  支持上下移动 true 向上滑动 false 向下滑动
     */
    private boolean isCurrentMove;
    /**
     * 记录是否已经执行过上滑动
     */
    private boolean isUp;

    /**
     * 记录是否已经执行过下滑动
     */
    private boolean isDown;

    /**
     * 动画执行的时间
     *
     */
    private int animTime = 300 ;



    /**
     * @param view
     */
    public ViewVisibilityOrGone(View view){
        this.view = view ;
        if (view!=null){
            //重新测量
            view.measure(0,0);
            viewHeight = view.getMeasuredHeight();
        }
    }



    /**
     * 设置当前移动的方向
     *
     * @param currentMove  true上滑  false下滑
     *
     */
    public void setCurrentMove(boolean currentMove) {
        isCurrentMove = currentMove;
        if (isCurrentMove){
            moveUp();
        }else {
            moveDown();
        }
    }




    /**
     * 向上滑动
     *
     * 隐藏view
     */
    private void moveUp(){
        if (!isUp){
            if ((animator!=null&&animator.isRunning()))return;
            animator = ValueAnimator.ofFloat(1,0);
            animator.setDuration(animTime);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale= (float) animation.getAnimatedValue();
                    updateViewHeight(scale);
                }
            });
            isUp = true ;
            isDown = false ;
        }
    }



    /**
     * 向下滑动
     *
     * 显示出view
     */
    private void moveDown(){
        if (!isDown){
            if ((animator!=null&&animator.isRunning()))return;
            animator = ValueAnimator.ofFloat(0,1);
            animator.setDuration(animTime);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float scale= (float) animation.getAnimatedValue();
                    updateViewHeight(scale);
                }
            });
            isUp = false ;
            isDown = true ;
        }
    }



    /**
     * 更新view的height
     *
     * @param val
     */
    private void updateViewHeight(float val){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (viewHeight * val);
        view.setLayoutParams(params);
    }

}
