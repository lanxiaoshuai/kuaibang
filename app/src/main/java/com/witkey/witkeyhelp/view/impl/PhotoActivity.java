package com.witkey.witkeyhelp.view.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;


import com.luck.picture.lib.photoview.PhotoView;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by jie on 2020/4/10.
 */

public class PhotoActivity extends BaseActivity {

    private ArrayList<ReleasePhotoBean> size;
    private ViewPager viewPager;
    private List<View> views;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_photoimagview);


        viewPager = findViewById(R.id.viewpager);
        size = (ArrayList<ReleasePhotoBean>) getIntent().getSerializableExtra("photo");
        int position = getIntent().getIntExtra("position", 0);
        views = new ArrayList<>();

        for (int i = 0; i < size.size(); i++) {
            View inflate = getLayoutInflater().inflate(R.layout.photoimag_item, null);

            PhotoView iv_photo = inflate.findViewById(R.id.iv_photo);
            Glide.with(this).load(size.get(i).getUrl()).into(iv_photo);
            iv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            });
            views.add(inflate);
        }

        class GuideAdapter extends PagerAdapter {

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(views.get(position));
                //super.destroyItem(container, position, object);
            }
        }

        viewPager.setAdapter(new GuideAdapter());
        viewPager.setCurrentItem(position);
    }


}
