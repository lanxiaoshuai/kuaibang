package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.ReplyBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.util.SharedPrefHelper;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.TimeUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.ChatActivity;
import com.witkey.witkeyhelp.widget.AlbumPoPubWindows;
import com.witkey.witkeyhelp.widget.CircleImageView;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;


/**
 * Created by jie on 2020/7/1.
 */

public class SecondaryAdapter extends BaseRecyAdapter<ReplyBean.ReturnObjectBean.RowsBean.ListBean> {
    private int userId;
    private PopupWindow popupWindow;
    private int createUserId;
    private String businessType;
    private String paymentType;
    private String receiverPhone;

    public SecondaryAdapter(Context context, List data, int createUserId, PopupWindow popupWindow, int userId, String businessType, String paymentType, String receiverPhone) {
        super(context, data);
        this.userId = userId;
        this.popupWindow = popupWindow;
        this.createUserId = createUserId;
        this.businessType = businessType;
        this.paymentType = paymentType;
        this.receiverPhone = receiverPhone;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.commen_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindBiewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (data.get(position).getReplyName() == null || data.get(position).getReplyName().equals("")) {

            ((ViewHolder) holder).comment_item_content.setText(data.get(position).getContent());
        } else {
            final SpannableStringBuilder style = new SpannableStringBuilder();
            style.append("回复@" + data.get(position).getReplyName() + " : " + data.get(position).getContent());
            //设置部分文字点击事件
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    newChat(data.get(position).getReplyUserPhone(), data.get(position).getReplyName());
                    //  startActivity(new Intent(SplashActivity.this, RegistrationActivity.class).putExtra("weburl", URL.versionUrl + "api/share/terms"));
                }
            };
            style.setSpan(clickableSpan, 2, 2 + data.get(position).getReplyName().length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3AA3FF"));
            style.setSpan(foregroundColorSpan, 2, 2 + data.get(position).getReplyName().length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((ViewHolder) holder).comment_item_content.setMovementMethod(LinkMovementMethod.getInstance());
            ((ViewHolder) holder).comment_item_content.setText(style);
        }
        if (data.get(position).getRewardMoney() != null && Double.parseDouble(data.get(position).getRewardMoney().toString()) > 0) {
            ((ViewHolder) holder).bounty_layout.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).amount.setText(data.get(position).getRewardMoney().toString());
        } else {
            ((ViewHolder) holder).bounty_layout.setVisibility(View.GONE);
        }
        if (createUserId == userId) {

            if (createUserId == data.get(position).getUserId()) {
                ((ViewHolder) holder).release_author.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).release_author.setText("发布者");
                ((ViewHolder) holder).release_author.setTextColor(Color.parseColor("#ffffff"));
                ((ViewHolder) holder).release_author.setBackgroundColor(Color.parseColor("#FFC000"));

            } else {
                if (businessType.equals("1")) {
                    ((ViewHolder) holder).release_author.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).release_author.setText("分配赏金");
                    ((ViewHolder) holder).release_author.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    ((ViewHolder) holder).release_author.setTextColor(Color.parseColor("#999999"));
                    ((ViewHolder) holder).release_author.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((ViewHolder) holder).release_author.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int position = ((ViewHolder) holder).getLayoutPosition(); // 1
                                    mOnItemDistributionClickListener.onItemClick(v, position);
                                }
                            });
                        }
                    });
                } else if (businessType.equals("2")) {
                    if (null != receiverPhone && data.get(position).getUserPhone().equals(receiverPhone)) {
                        ((ViewHolder) holder).release_author.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).release_author.setText("接单者");
                        ((ViewHolder) holder).release_author.setTextColor(Color.parseColor("#B3B3B3"));
                        ((ViewHolder) holder).release_author.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    } else {
                        ((ViewHolder) holder).release_author.setVisibility(View.GONE);
                    }
                } else {
                    ((ViewHolder) holder).release_author.setVisibility(View.GONE);
                }

            }
        } else {
            if (createUserId == data.get(position).getUserId()) {
                ((ViewHolder) holder).release_author.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).release_author.setText("发布者");
                ((ViewHolder) holder).release_author.setTextColor(Color.parseColor("#ffffff"));
                ((ViewHolder) holder).release_author.setBackgroundColor(Color.parseColor("#FFC000"));

            } else {
                if (businessType.equals("2")) {
                    if (null != receiverPhone && data.get(position).getUserPhone().equals(receiverPhone)) {
                        ((ViewHolder) holder).release_author.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).release_author.setText("接单者");
                        ((ViewHolder) holder).release_author.setTextColor(Color.parseColor("#B3B3B3"));
                        ((ViewHolder) holder).release_author.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    } else {
                        ((ViewHolder) holder).release_author.setVisibility(View.GONE);
                    }
                } else {
                    ((ViewHolder) holder).release_author.setVisibility(View.GONE);
                }

            }
        }

        if (paymentType.equals("1")) {
            ((ViewHolder) holder).bounty_layout.setBackgroundColor(Color.parseColor("#FCFAE8"));
            ((ViewHolder) holder).bountystatus.setTextColor(Color.parseColor("#FFBD5C"));
            ((ViewHolder) holder).price_icon.setImageResource(R.mipmap.reply_money_icon);
            ((ViewHolder) holder).amount.setTextColor(Color.parseColor("#FFBD5C"));

        } else {
            ((ViewHolder) holder).bounty_layout.setBackgroundColor(Color.parseColor("#E8F4FC"));
            ((ViewHolder) holder).bountystatus.setTextColor(Color.parseColor("#79B4DA"));
            ((ViewHolder) holder).price_icon.setImageResource(R.mipmap.reply_zuanshi_icon);
            ((ViewHolder) holder).amount.setTextColor(Color.parseColor("#79B4DA"));
        }
        ((ViewHolder) holder).comment_item_userName.setText(data.get(position).getUserName());
        ((ViewHolder) holder).comment_item_time.setText(TimeUtils.DateDistance2now(TimeUtils.date2ms(data.get(position).getCreateTime())));
        Glide.with(context).load(URL.getImgPath + data.get(position).getHeadUrl()).into(((ViewHolder) holder).comment_item_logo);
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
        ((ViewHolder) holder).comment_item_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChat(data.get(position).getUserPhone(), data.get(position).getUserName());
            }
        });

    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView comment_item_logo;
        private TextView comment_item_userName;
        private TextView comment_item_time;
        private TextView comment_item_content;
        private TextView release_author;
        private LinearLayout bounty_layout;
        private TextView bountystatus;
        private ImageView price_icon;
        private TextView amount;

        public ViewHolder(View v) {
            super(v);
            comment_item_logo = (CircleImageView) v.findViewById(R.id.comment_item_logo);
            comment_item_userName = (TextView) v.findViewById(R.id.comment_item_userName);
            comment_item_time = (TextView) v.findViewById(R.id.comment_item_time);
            comment_item_content = (TextView) v.findViewById(R.id.comment_item_content);
            release_author = v.findViewById(R.id.release_author);
            bounty_layout = v.findViewById(R.id.bounty_layout);
            bountystatus = v.findViewById(R.id.bountystatus);
            price_icon = v.findViewById(R.id.price_icon);
            amount = v.findViewById(R.id.amount);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemDistributionClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemDistributionClickListener mOnItemDistributionClickListener;

    public void setOnDistributionItemClickListener(OnItemDistributionClickListener mOnItemDistributionClickListener) {
        this.mOnItemDistributionClickListener = mOnItemDistributionClickListener;
    }


    private void newChat(String userPhone, String userName) {
        User user = SpUtils.getObject(context, "LOGIN");
        if (userPhone.equals(user.getUserName())) {
            Log.e("tag", "不能聊天");
            ToastUtils.showTestShort(context, "不能与自己本身聊天");

            return;
        }
        popupWindow.setAnimationStyle(0);
        popupWindow.update();
        Conversation conv = JMessageClient.getSingleConversation(userPhone, SharedPrefHelper.getInstance().getAppKey());
        //如果会话为空，使用EventBus通知会话列表添加新会话
        if (conv == null) {
            Conversation.createSingleConversation(userPhone, SharedPrefHelper.getInstance().getAppKey());
            Log.e("TAG", userPhone);
        }
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("USERNAME", userPhone);

        intent.putExtra(MyAPP.TARGET_ID, userPhone);
        intent.putExtra(MyAPP.TARGET_APP_KEY, SharedPrefHelper.getInstance().getAppKey());
        intent.putExtra(MyAPP.CONV_TITLE, userName);
        context.startActivity(intent);

    }

    @Override
    public void setCcontent(TextView textView) {
        super.setCcontent(textView);
        textView.setText("还没有回复，不要让酷友久等哦");
    }
}
