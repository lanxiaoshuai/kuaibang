package com.witkey.witkeyhelp.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;


import android.content.Intent;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.adapter.viewHolder.FoundPhotoAdapter;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.ReleasePhotoBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.util.SharedPrefHelper;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.TimeUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.ChatActivity;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.widget.CircleImageView;
import com.witkey.witkeyhelp.widget.PopWinShare;
import com.witkey.witkeyhelp.widget.RoundImageView;


import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;


public class MissionRecyAdapter extends BaseRecyAdapter<MissionBean> {
    private int status;

    public MissionRecyAdapter(Context context, List data, int status) {
        super(context, data);
        this.status = status;
    }

    private String[] filterData = {"信息咨询", "悬赏帮忙", "紧急求助", "失物招领"};

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lv_mission, parent, false);
        return new ViewHolder(view);
    }


    //    @SuppressLint("ClickableViewAccessibility")
//    @TargetApi(Build.VERSION_CODES.M)
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MissionBean mission = data.get(position);
        //    ((ViewHolder) holder).tv_mission_type.setText(filterData[Integer.parseInt(mission.getBusinessType()) - 1]);
        if (mission.getPrice().contains(".")) {
            ((ViewHolder) holder).tv_mission_money.setText(mission.getPrice());
        } else {
            ((ViewHolder) holder).tv_mission_money.setText(mission.getPrice() + ".00");
        }
//        if (status == 0) {
//            ((ViewHolder) holder).orders_relayout.setVisibility(View.VISIBLE);
//        } else {
//            ((ViewHolder) holder).orders_relayout.setVisibility(View.GONE);
//        }
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.deafult_icon);
        options.error(R.mipmap.deafult_icon);
        ((ViewHolder) holder).tv_mission_content.setText(mission.getDescribes());
        Glide.with(context).load(URL.getImgPath + mission.getCreateUserHeadUrl()).apply(options).into(((ViewHolder) holder).createUserHeadUrl);

        String paymentType = mission.getPaymentType();


        String businessType = mission.getBusinessType();
        if (businessType.equals("5")) {
            ((ViewHolder) holder).price_image_diamond.setVisibility(View.INVISIBLE);
            ((ViewHolder) holder).tv_image.setVisibility(View.INVISIBLE);
            ((ViewHolder) holder).tv_mission_money.setVisibility(View.INVISIBLE);

        } else if (businessType.equals("2") || businessType.equals("1")) {
            ((ViewHolder) holder).tv_mission_money.setVisibility(View.VISIBLE);
            if (paymentType.equals("1")) {
                ((ViewHolder) holder).tv_mission_money.setTextColor(context.getResources().getColor(R.color.shape_org));
                ((ViewHolder) holder).tv_image.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).price_image_diamond.setVisibility(View.INVISIBLE);
            } else if (paymentType.equals("2")) {
                ((ViewHolder) holder).tv_image.setVisibility(View.INVISIBLE);
                ((ViewHolder) holder).price_image_diamond.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tv_mission_money.setTextColor(context.getResources().getColor(R.color.shape_lan));
            }

        }
        if (null == mission.getCircleName() || "".equals(mission.getCircleName())) {
            ((ViewHolder) holder).cicle.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).cicle.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).cicle.setText(mission.getCircleName());
        }
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        ((ViewHolder) holder).forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity context = (Activity) MissionRecyAdapter.this.context;

                PopWinShare popWinShare = new PopWinShare(1, context, "快来和我一起帮助他人，赚取赏金吧！", "悬赏" + mission.getPrice() + "元  " + mission.getDescribes(), "http://a.app.qq.com/o/simple.jsp?pkgname=com.witkey.witkeyhelp");

                //引入依附的布局
                View parentView = LayoutInflater.from(context).inflate(R.layout.activity_task_details, null);
                //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 0.7f;//调节透明度
                context.getWindow().setAttributes(lp);
                //dismiss时恢复原样
                popWinShare.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                        lp.alpha = 1f;
                        context.getWindow().setAttributes(lp);
                    }
                });
                popWinShare.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
            }
        });

        ((ViewHolder) holder).private_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MissionDetailActivity.class);
                intent.putExtra("EXTRA_BUSINESS_ID", mission.getBusinessId());
                intent.putExtra("TASKDETAILS", 1);
                intent.putExtra("TASK", mission);
                intent.putExtra("TASKORDER", true);
                context.startActivity(intent);
            }
        });

        ((ViewHolder) holder).immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, MissionDetailActivity.class);
//                intent.putExtra("EXTRA_BUSINESS_ID", mission.getBusinessId());
//                intent.putExtra("TASKDETAILS", 1);
//                intent.putExtra("TASK", mission);
//                intent.putExtra("TASKORDER", true);
//                context.startActivity(intent);
                ToastUtils.showTestShort(context, "暂未开发");
            }
        });

        if (null == mission.getBusinessImgUrl() || "".equals(mission.getBusinessImgUrl())) {
            ((MissionRecyAdapter.ViewHolder) holder).photolist.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).photolist.setLayoutManager(new GridLayoutManager(context, 3));
            List<ReleasePhotoBean> photo = new ArrayList<>();
            FoundPhotoAdapter photoAdapter = new FoundPhotoAdapter(context, photo);
            ((MissionRecyAdapter.ViewHolder) holder).photolist.setAdapter(photoAdapter);
            if (mission.getBusinessImgUrl().contains(",")) {
                String[] split = mission.getBusinessImgUrl().split(",");
                for (int i = 0; i < split.length; i++) {
                    photo.add(new ReleasePhotoBean(URL.getImgPath + split[i], true));
                }
            } else {
                photo.add(new ReleasePhotoBean(URL.getImgPath + mission.getBusinessImgUrl(), true));

            }
            try {
                photoAdapter.notifyDataSetChanged();
                ((MissionRecyAdapter.ViewHolder) holder).photolist.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                //    Log.e("tag", e.getMessage());
                e.printStackTrace();
            }

        }
        ((ViewHolder) holder).release_location.setText(mission.getPlaceName());

        ((ViewHolder) holder).publisher_name.setText(mission.getCreateUserName());

        ((ViewHolder) holder).release_time.setText(TimeUtils.DateDistance2now(TimeUtils.date2ms(mission.getCreateDate())));
        /**
         * 屏蔽内部recyclerview 点击事件
         */
        ((ViewHolder) holder).photolist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.itemView.onTouchEvent(event);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // private TextView tv_mission_type;

        private TextView tv_mission_content;
        private TextView tv_mission_money;
        private TextView tv_image;
        private TextView price_image_diamond;
        private RecyclerView photolist;
        private TextView release_time;
        private TextView release_location;
        private TextView publisher_name;
        private CircleImageView createUserHeadUrl;
        private LinearLayout forward;
        //   private RelativeLayout orders_relayout;
        private LinearLayout private_chat;
        private LinearLayout immediately;
        private TextView cicle;

        public ViewHolder(View v) {
            super(v);
            //   tv_mission_type = (TextView) v.findViewById(R.id.tv_mission_type);

            tv_mission_content = (TextView) v.findViewById(R.id.tv_mission_content);
            tv_mission_money = (TextView) v.findViewById(R.id.tv_mission_money);
            tv_image = v.findViewById(R.id.price_image);
            price_image_diamond = v.findViewById(R.id.price_image_diamond);

            photolist = v.findViewById(R.id.photolistrecy);

            release_time = v.findViewById(R.id.release_time);

            release_location = v.findViewById(R.id.release_location);

            publisher_name = v.findViewById(R.id.publisher_name);
            createUserHeadUrl = v.findViewById(R.id.createUserHeadUrl);
            forward = v.findViewById(R.id.forward);
//            orders_relayout = v.findViewById(R.id.orders_relayout);
            private_chat = v.findViewById(R.id.private_chat);
            immediately = v.findViewById(R.id.immediately);
            cicle = v.findViewById(R.id.cicle);

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
