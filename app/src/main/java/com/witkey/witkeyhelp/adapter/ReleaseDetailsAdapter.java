package com.witkey.witkeyhelp.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.witkey.witkeyhelp.R;

import com.witkey.witkeyhelp.bean.FullImageInfo;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.view.impl.FullImageActivity;
import com.witkey.witkeyhelp.view.impl.PhotoActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.security.MessageDigest;
import java.util.List;

import static com.facebook.common.internal.ByteStreams.copy;

/**
 * Created by jie on 2020/4/2.
 */

public class ReleaseDetailsAdapter extends BaseRecyAdapter<ReleasePhotoBean> {

    //   private AlbumPoPubWindows popWinShare;

    public ReleaseDetailsAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.releasephoto_item, null);
        return new ViewHolder(view);
    }

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position).getUrl()).into(((ViewHolder) holder).tv_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (lacksPermissions(context, permissionsREAD)) {//读写权限没开启
//                    ActivityCompat.requestPermissions((Activity) context, permissionsREAD, 0);
//                } else {
                //读写权限已开启
                //    if (data.get(position).isaBoolean()) {
                onclilk(data, position);
//                    } else {
//                        switch (position){
//                            case 0:
//                                popWinShare = new AlbumPoPubWindows(context, 3);
//                                break;
//                            case 1:
//                                popWinShare = new AlbumPoPubWindows(context, 2);
//                                break;
//                            case 2:
//                                popWinShare = new AlbumPoPubWindows(context, 1);
//                                break;
//                        }
//                        //引入依附的布局
//                        View parentView = LayoutInflater.from(context).inflate(R.layout.activity_consult, null);
//                        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
//                        final Activity context = (Activity) ReleasePhotoAdapter.this.context;
//
//                        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
//                        lp.alpha = 0.7f;//调节透明度
//                        context.getWindow().setAttributes(lp);
//                        //dismiss时恢复原样
//                        popWinShare.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//                            @Override
//                            public void onDismiss() {
//                                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
//                                lp.alpha = 1f;
//                                context.getWindow().setAttributes(lp);
//                            }
//                        });
//                        popWinShare.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//                    }
//                }


            }
        });
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView tv_image;
        private ImageView expression;

        public ViewHolder(View v) {
            super(v);

            tv_image = v.findViewById(R.id.iv_pic);
            expression = v.findViewById(R.id.expression);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener( OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //      private void onclilk(final View view, String imagePath) {
////        int location[] = new int[2];
////        view.getLocationOnScreen(location);
////        FullImageInfo fullImageInfo = new FullImageInfo();
////        fullImageInfo.setLocationX(location[0]);
////        fullImageInfo.setLocationY(location[1]);
////        fullImageInfo.setWidth(view.getWidth());
////        fullImageInfo.setHeight(view.getHeight());
////        fullImageInfo.setImageUrl(imagePath);
////        Intent intent = new Intent(context, FullImageActivity.class);
////        intent.putExtra("photo", fullImageInfo);
////        context.startActivity(intent);
////        Activity context = (Activity) this.context;
////        context.overridePendingTransition(0, 0);
//
//
//        Intent intent = new Intent(context, PhotoActivity.class);
//        intent.putExtra("photo", imagePath);
//        context.startActivity(intent);
//    }
    private void onclilk(List<ReleasePhotoBean> mlist, int position) {


        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("photo", (Serializable) mlist);
        intent.putExtra("position", position);
        context.startActivity(intent);
        //Activity context = (Activity) this.context;
        //  context.overridePendingTransition(0, 0);
    }
//    /**
//     * 读写权限  自己可以添加需要判断的权限
//     */
//    public static String[] permissionsREAD = {
//            Manifest.permission.CAMERA,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE};
//
//    /**
//     * 判断权限集合
//     * permissions 权限数组
//     * return true-表示没有改权限  false-表示权限已开启
//     */
//    public static boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {
//
//        for (String permission : permissionsREAD) {
//            if (lacksPermission(mContexts, permission)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否缺少权限
//     */
//    private static boolean lacksPermission(Context mContexts, String permission) {
//        return ContextCompat.checkSelfPermission(mContexts, permission) ==
//                PackageManager.PERMISSION_DENIED;
//    }

}
