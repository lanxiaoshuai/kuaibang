package com.witkey.witkeyhelp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.widget.CircleImageView;
import com.witkey.witkeyhelp.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Author: Moos
 * E-mail: moosphon@gmail.com
 * Date:  18/4/20.
 * Desc: 评论与回复列表的适配器
 */

public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<ReplyBean.ReturnObjectBean.RowsBean> commentBeanList;

    private Context context;
    private int pageIndex = 1;
    private int userId;
    private int createUserId;
    private String businessType;
    private String paymentType;
    private String receiverPhone;
    private static final int EMPTY_VIEW = 1;

    public CommentExpandAdapter(Context context, List<ReplyBean.ReturnObjectBean.RowsBean> commentBeanList, int userId, int createUserId, String businessType, String paymentType, String receiverPhone) {
        this.context = context;
        this.commentBeanList = commentBeanList;
        this.userId = userId;
        this.createUserId = createUserId;
        this.businessType = businessType;
        this.paymentType = paymentType;
        this.receiverPhone = receiverPhone;

    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {

        if (commentBeanList.get(i).getList() == null) {
            return 0;
        } else {
            return commentBeanList.get(i).getList().size() > 0 ? 1 : 0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    boolean isLike = false;


    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {


        final GroupHolder groupHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        Glide.with(context).load(URL.getImgPath + commentBeanList.get(groupPosition).getHeadUrl()).into(groupHolder.logo);
        groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getUserName());
        groupHolder.tv_time.setText(TimeUtils.DateDistance2now(TimeUtils.date2ms(commentBeanList.get(groupPosition).getCreateTime())));
        groupHolder.tv_content.setText( commentBeanList.get(groupPosition).getContent());


        if (createUserId == userId) {

            if (createUserId == commentBeanList.get(groupPosition).getUserId()) {
                groupHolder.release_author.setVisibility(View.VISIBLE);
                groupHolder.release_author.setText("发布者");
                groupHolder.release_author.setTextColor(Color.parseColor("#ffffff"));
                groupHolder.release_author.setBackgroundColor(Color.parseColor("#FFC000"));
            } else {
                if (businessType.equals("1")) {
                    groupHolder.release_author.setVisibility(View.VISIBLE);
                    groupHolder.release_author.setText("分配赏金");
                    groupHolder.release_author.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    groupHolder.release_author.setTextColor(Color.parseColor("#999999"));
                    groupHolder.release_author.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mOnItemClickListener.onItemClick(groupPosition); // 2
                        }
                    });
                } else if (businessType.equals("2")) {
                    if (null != receiverPhone && commentBeanList.get(groupPosition).getUserPhone().equals(receiverPhone)) {
                        groupHolder.release_author.setVisibility(View.VISIBLE);
                        groupHolder.release_author.setText("接单者");
                        groupHolder.release_author.setTextColor(Color.parseColor("#B3B3B3"));
                        groupHolder.release_author.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    } else {
                        groupHolder.release_author.setVisibility(View.GONE);
                    }
                } else {
                    groupHolder.release_author.setVisibility(View.GONE);
                }


            }
        } else {
            if (createUserId == commentBeanList.get(groupPosition).getUserId()) {
                groupHolder.release_author.setVisibility(View.VISIBLE);
                groupHolder.release_author.setText("发布者");
                groupHolder.release_author.setTextColor(Color.parseColor("#ffffff"));
                groupHolder.release_author.setBackgroundColor(Color.parseColor("#FFC000"));

            } else {

                if (businessType.equals("2")) {
                    if (null != receiverPhone && commentBeanList.get(groupPosition).getUserPhone().equals(receiverPhone)) {
                        groupHolder.release_author.setVisibility(View.VISIBLE);
                        groupHolder.release_author.setText("接单者");
                        groupHolder.release_author.setTextColor(Color.parseColor("#B3B3B3"));
                        groupHolder.release_author.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    } else {
                        groupHolder.release_author.setVisibility(View.GONE);
                    }
                } else {
                    groupHolder.release_author.setVisibility(View.GONE);
                }

            }


        }

        if (commentBeanList.get(groupPosition).getRewardMoney() != null && Double.parseDouble(commentBeanList.get(groupPosition).getRewardMoney().toString()) > 0) {
            groupHolder.bounty_layout.setVisibility(View.VISIBLE);
            groupHolder.amount.setText(commentBeanList.get(groupPosition).getRewardMoney().toString());
        } else {
            groupHolder.bounty_layout.setVisibility(View.GONE);
        }
        if (paymentType.equals("1")) {
            groupHolder.bounty_layout.setBackgroundColor(Color.parseColor("#FCFAE8"));
            groupHolder.bountystatus.setTextColor(Color.parseColor("#FFBD5C"));
            groupHolder.price_icon.setImageResource(R.mipmap.reply_money_icon);
            groupHolder.amount.setTextColor(Color.parseColor("#FFBD5C"));
        } else {
            groupHolder.bounty_layout.setBackgroundColor(Color.parseColor("#E8F4FC"));
            groupHolder.bountystatus.setTextColor(Color.parseColor("#79B4DA"));
            groupHolder.price_icon.setImageResource(R.mipmap.reply_zuanshi_icon);
            groupHolder.amount.setTextColor(Color.parseColor("#79B4DA"));
        }
        groupHolder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newChat(commentBeanList.get(groupPosition).getUserPhone(), commentBeanList.get(groupPosition).getUserName());
            }
        });


        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {


        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout, viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        String replyUser = commentBeanList.get(groupPosition).getList().get(childPosition).getUserName();
        childHolder.tv_name.setText(replyUser + ":");
        childHolder.tv_content.setText(commentBeanList.get(groupPosition).getList().get(childPosition).getUserName() + " : " +commentBeanList.get(groupPosition).getList().get(childPosition).getContent());
        childHolder.expand_reply.setText("展开" + commentBeanList.get(groupPosition).getList().size() + "条回复");
        if (commentBeanList.get(groupPosition).getList().size() == 1) {
            childHolder.expand_reply.setVisibility(View.GONE);
        } else if (commentBeanList.get(groupPosition).getList().size() > 1) {
            childHolder.expand_reply.setVisibility(View.VISIBLE);
        }


        return convertView;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }



    private class GroupHolder {
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private ImageView iv_like;
        private TextView release_author;
        private LinearLayout bounty_layout;
        private TextView bountystatus;
        private ImageView price_icon;
        private TextView amount;

        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
            release_author = view.findViewById(R.id.release_author);
            bounty_layout = view.findViewById(R.id.bounty_layout);
            bountystatus = view.findViewById(R.id.bountystatus);
            price_icon = view.findViewById(R.id.price_icon);
            amount = view.findViewById(R.id.amount);

        }
    }

    private class ChildHolder {
        private TextView tv_name, tv_content, expand_reply;

        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
            expand_reply = (TextView) view.findViewById(R.id.expand_reply);
        }
    }


    private void newChat(String userPhone, String userName) {
        User user = SpUtils.getObject(context, "LOGIN");
        if (userPhone.equals(user.getUserName())) {
            Log.e("tag", "不能聊天");
            ToastUtils.showTestShort(context, "不能与自己本身聊天");

            return;
        }

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


}
