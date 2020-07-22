package com.witkey.witkeyhelp.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
/**
 * 
 * Title: SplashAdapter.java
 * @author wangshijie
 * 2015-11-25
 * @version 1.0
 */
public class SplashAdapter extends PagerAdapter{
	private ArrayList<ImageView> mImageViews;
	
	public SplashAdapter(ArrayList<ImageView> mImageViews){
		this.mImageViews = mImageViews;
	}
	@Override
	public int getCount() {
		return mImageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// 初始化界面数据,类似getView
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = mImageViews.get(position);
		container.addView(imageView);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}


}
