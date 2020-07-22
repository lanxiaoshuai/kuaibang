package com.witkey.witkeyhelp.view.impl;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.FullImageInfo;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.widget.SmoothImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie on 2019/12/5.
 */

public class FullImageActivity extends Activity {
    private SmoothImageView imageView;

    SmoothImageView fullImage;
    LinearLayout fullLay;
    private int mLeft;
    private int mTop;
    private float mScaleX;
    private float mScaleY;
    private Drawable mBackground;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_full_image);
        fullImage = (SmoothImageView) findViewById(R.id.full_image);
        fullLay = (LinearLayout) findViewById(R.id.full_lay);

        fullImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick();
            }
        });
        FullImageInfo fullImageInfo = (FullImageInfo) getIntent().getSerializableExtra("photo");
        onDataSynEvent(fullImageInfo);
        MyAPP.getInstance().addActivity(this);






    }





    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onDataSynEvent(final FullImageInfo fullImageInfo) {
        final int left = fullImageInfo.getLocationX();
        final int top = fullImageInfo.getLocationY();
        final int width = fullImageInfo.getWidth();
        final int height = fullImageInfo.getHeight();
        mBackground = new ColorDrawable(Color.BLACK);
        fullLay.setBackground(mBackground);
        fullImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fullImage.getViewTreeObserver().removeOnPreDrawListener(this);
                int location[] = new int[2];
                fullImage.getLocationOnScreen(location);
                mLeft = left - location[0];
                mTop = top - location[1];
                mScaleX = width * 1.0f / fullImage.getWidth();
                mScaleY = height * 1.0f / fullImage.getHeight();
                activityEnterAnim();
                return true;
            }
        });
        Glide.with(this).load(fullImageInfo.getImageUrl())



                .apply(
                        new RequestOptions()
//                                        .frame(3000000)
                                .centerCrop()
                            //    .error(R.mipmap.ic_launcher)
                            //    .placeholder(R.mipmap.ic_launcher)
                                .fitCenter()  //让图片全充，
                )

                .into(fullImage);
    }


    private void activityEnterAnim() {
        fullImage.setPivotX(0);
        fullImage.setPivotY(0);
        fullImage.setScaleX(mScaleX);
        fullImage.setScaleY(mScaleY);
        fullImage.setTranslationX(mLeft);
        fullImage.setTranslationY(mTop);
        fullImage.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).
                setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void activityExitAnim(Runnable runnable) {
        fullImage.setPivotX(0);
        fullImage.setPivotY(0);
        fullImage.animate().scaleX(mScaleX).scaleY(mScaleY).translationX(mLeft).translationY(mTop).
                withEndAction(runnable).
                setDuration(500).setInterpolator(new DecelerateInterpolator()).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackground, "alpha", 255, 0);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(500);
        objectAnimator.start();
    }

    @Override
    public void onBackPressed() {
        activityExitAnim(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    public void onImageClick() {
        activityExitAnim(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
    /**
     * Called when the activity is first created.
     */



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        int mLocationX = getIntent().getIntExtra("locationX", 0);
//        int mLocationY = getIntent().getIntExtra("locationY", 0);
//        int mWidth = getIntent().getIntExtra("width", 0);
//        int mHeight = getIntent().getIntExtra("height", 0);
//        String imagePath = getIntent().getStringExtra("imagePath");
//        imageView = new SmoothImageView(this);
//        imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
//        imageView.transformIn();
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
//        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        setContentView(imageView);
//        Glide.with(this).load(imagePath)
//
//                .apply(
//                       new RequestOptions()
//                                      .frame(3000000)
//                          .centerCrop()
//                          .error(R.mipmap.ic_launcher)
//                              .placeholder(R.mipmap.ic_launcher)
//                              .fitCenter()  //让图片全充，
//               )
//                .into(imageView);
//
//
//    }
//   @Override
//   public void onBackPressed() {
//
//             finish();
//              overridePendingTransition(0, 0);
//
//  }

}
