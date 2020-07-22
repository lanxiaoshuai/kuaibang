package com.witkey.witkeyhelp.view.impl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.witkey.witkeyhelp.R;

import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import com.witkey.witkeyhelp.view.impl.fragment.TaskEvaluatFragment;
import com.witkey.witkeyhelp.widget.ObservableAlphaScrollView;
import com.witkey.witkeyhelp.widget.XCFlowLayout;

import java.util.ArrayList;


/**
 * Created by jie on 2020/5/13.
 */

public class LikeWChatActivity extends BaseActivity implements ObservableAlphaScrollView.OnAlphaScrollChangeListener {

    private LinearLayout mLlTitle,mLlScHead;
    private int mTitleHeight;
    private int mHeadHeight;
    private int mDistance;
    private ObservableAlphaScrollView mScrollView;
    private ImageView mIvBack;
    private TextView mTvText;
    private int mDistanceY = 30;// 设置一个临界值吧
    private XCFlowLayout mFlowLayout;
    private LabelsView labelsView;
    private CheckBox load_more;

    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private FragmentTransaction transaction;
    private TaskEvaluatFragment  rb1fragment;
    private TaskEvaluatFragment  rb2fragment;
    @Override
    public void onVerticalScrollChanged(int t) {
        if (t<= (mDistance - mDistanceY)){
            mTvText.setAlpha(0f);
            mIvBack.setSelected(false);
            mLlTitle.setBackgroundColor(Color.argb(0, 242, 242, 242));

        }else if (t<=mDistance) {
            mTvText.setAlpha(0f);
            mIvBack.setSelected(false);

        }else if (t <= (mDistance + mDistanceY)){
            mTvText.setAlpha(1f);
            mIvBack.setSelected(true);
            mLlTitle.setBackgroundColor(Color.argb(0, 242, 242, 242));

        }else {
            mTvText.setAlpha(1f);
            mIvBack.setSelected(true);
            mLlTitle.setBackgroundColor(Color.argb(255, 242, 242, 242));

        }

    }

    @Override
    protected void onCreateActivity() {
          setContentView(R.layout.activity_like_wchat);

        mLlTitle = findViewById(R.id.llTitle);
        mLlScHead = findViewById(R.id.llScHead);
        mScrollView = findViewById(R.id.scrollView);

        mIvBack = findViewById(R.id.imageBack);
        mTvText = findViewById(R.id.ivText);
        ViewTreeObserver viewTreeObserver = mLlScHead.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLlScHead.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mTitleHeight = mLlTitle.getMeasuredHeight();
                mHeadHeight = mLlScHead.getMeasuredHeight();
                mDistance = mHeadHeight - mTitleHeight;
                Log.e("result  mTitleHead = ",mTitleHeight+"");
                Log.e("result  mHeadHeight = ",mHeadHeight+"");
                Log.e("result  mDistance = ",mDistance+"");
                mScrollView.setOnAlphaScrollChangeListener(LikeWChatActivity.this);
            }
        });

        labelsView = (LabelsView) findViewById(R.id.labels);
        load_more = findViewById(R.id.load_more);
        ArrayList<String> label = new ArrayList<>();
        label.add("金融管理");
        label.add("金融管理");
        label.add("金融管理");
        label.add("插画师");
        label.add("金融管理");
        label.add("金融管理");
        label.add("金融管理");
        label.add("插画师");
        labelsView.setLabels(label); //直接设置一个字符串数组就可以了。
        load_more.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    labelsView.setMaxLines(0);

                }else {



                    labelsView.setMaxLines(1);

                }
            }
        });
        initWidget();
    }
   private void   initWidget(){

       initWidgetData();
   }

    public void setChoiceItem(int index) {
        transaction = getSupportFragmentManager().beginTransaction();

        //隐藏所有Fragment
        hideFragments(transaction);
        switch (index) {
            case 0:

                transaction.show(rb1fragment);
                break;
            case 1:

                transaction.show(rb2fragment);
                break;


        }
        transaction.commit();
    }

    //隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {
        if (rb1fragment == null) {
            rb1fragment = new TaskEvaluatFragment();
            Bundle bundle = new Bundle();
            bundle.putString("start", "1");


            rb1fragment.setArguments(bundle);

            transaction.add(R.id.fragment, rb1fragment);

        }
        if (rb2fragment == null) {
            rb2fragment = new TaskEvaluatFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("start", "2");


            transaction.add(R.id.fragment, rb2fragment);

        }
        transaction.hide(rb1fragment);
        transaction.hide(rb2fragment);



    }

    private void   initWidgetData(){
        rg = findViewById(R.id.rg);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        setChoiceItem(0);
        rg.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.rb1:

                                setChoiceItem(0);
                                break;
                            case R.id.rb2:

                                setChoiceItem(1);
                                break;

                        }

                    }
                }
        );
    }
}
