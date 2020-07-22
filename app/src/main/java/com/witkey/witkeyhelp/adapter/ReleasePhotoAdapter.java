package com.witkey.witkeyhelp.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.view.impl.PhotoActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jie on 2020/4/1.
 */

public class ReleasePhotoAdapter extends BaseRecyAdapter<ReleasePhotoBean> {

    private AlbumPoPubWindows popWinShare;

    public ReleasePhotoAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = View.inflate(context, R.layout.releasephoto_item, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position).getUrl()).thumbnail(0.1f).into(((ViewHolder) holder).tv_image);

        if (data.get(position).isaBoolean()) {
            ((ViewHolder) holder).expression.setVisibility(View.VISIBLE);
        } else {
            ((ViewHolder) holder).expression.setVisibility(View.GONE);
        }
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            ((ViewHolder) holder).expression.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        if(mOnItemPhotoClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition(); // 1
                    mOnItemPhotoClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (lacksPermissions(context, permissionsREAD)) {//读写权限没开启
//                    ActivityCompat.requestPermissions((Activity) context, permissionsREAD, 0);
//                } else {
//                    //读写权限已开启
//                    if (data.get(position).isaBoolean()) {
//                         List<ReleasePhotoBean> mlist=new ArrayList<>();
//                        for (int i = 0; i <data.size() ; i++) {
//                         if(data.get(i).isaBoolean()){
//                             mlist.add(data.get(i));
//                         }
//                        }
//                        onclilk(mlist,position);
//                    } else {
//                        switch (position) {
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
//
//
//            }
//        });
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

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemPhotoClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemPhotoClickListener mOnItemPhotoClickListener;

    public void setOnItemPhotoClickListener(OnItemPhotoClickListener mOnItemPhotoClickListener) {
        this.mOnItemPhotoClickListener = mOnItemPhotoClickListener;
    }

    private void onclilk(List<ReleasePhotoBean>  mlist, int position) {


        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("photo", (Serializable) mlist);
        intent.putExtra("position", position);
        context.startActivity(intent);
        //Activity context = (Activity) this.context;
        //  context.overridePendingTransition(0, 0);
    }

    /**
     * 读写权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREAD = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public static boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {

        for (String permission : permissionsREAD) {
            if (lacksPermission(mContexts, permission)) {

                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

}
