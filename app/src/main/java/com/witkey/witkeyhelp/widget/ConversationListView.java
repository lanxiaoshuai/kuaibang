package com.witkey.witkeyhelp.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MessageHeaderAdapter;
import com.witkey.witkeyhelp.util.SpaceItemDecoration;
import com.witkey.witkeyhelp.util.ThreadUtil;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.impl.MyReplyActivity;
import com.witkey.witkeyhelp.view.impl.SystemMessageActivity;
import com.witkey.witkeyhelp.view.impl.fragment.ConversationListFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${chenyn} on 2017/3/13.
 */

public class ConversationListView {
    private View mConvListFragment;
    private ListView mConvListView = null;
    private TextView mTitle;
    private ImageButton mCreateGroup;
    //  private LinearLayout mSearchHead;
    private LinearLayout mHeader;
    private RelativeLayout mLoadingHeader;
    private ImageView mLoadingIv;
    private LinearLayout mLoadingTv;
    private Context mContext;
    private TextView mNull_conversation;

    private LinearLayout mHeadMessage;
    //  private LinearLayout mSearch;
    //private TextView mAllUnReadMsg;
    private ConversationListFragment mFragment;
    private ListView noice_addhead;

    public ConversationListView(View view, Context context, ConversationListFragment fragment) {
        this.mConvListFragment = view;
        this.mContext = context;
        this.mFragment = fragment;
    }

    private void buryingPoint(String eventId) {
        MobclickAgent.onEvent(mContext, eventId);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initModule() {
        mConvListView = (ListView) mConvListFragment.findViewById(R.id.conv_list_view);
        mCreateGroup = (ImageButton) mConvListFragment.findViewById(R.id.create_group_btn);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = (LinearLayout) inflater.inflate(R.layout.conv_list_head_view, mConvListView, false);

        mHeadMessage = (LinearLayout) inflater.inflate(R.layout.notice_add_head, mConvListView, false);

        RelativeLayout notification_relay = mHeadMessage.findViewById(R.id.notification_relay);
        RelativeLayout relayout_rebly = mHeadMessage.findViewById(R.id.relayout_rebly);
        RelativeLayout official_layout = mHeadMessage.findViewById(R.id.official_layout);

        notification_relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ToastUtils.showTestShort(mContext, "暂未开发");
            }
        });
        relayout_rebly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MyReplyActivity.class);
                i.putExtra("reply", true);
                mContext.startActivity(i);
            }
        });
        official_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, SystemMessageActivity.class));
                buryingPoint("systemMessages");
            }
        });
        // mSearchHead = (LinearLayout) inflater.inflate(R.layout.conversation_head_view, mConvListView, false);
        mLoadingHeader = (RelativeLayout) inflater.inflate(R.layout.jmui_drop_down_list_header, mConvListView, false);
        mLoadingIv = (ImageView) mLoadingHeader.findViewById(R.id.jmui_loading_img);
        mLoadingTv = (LinearLayout) mLoadingHeader.findViewById(R.id.loading_view);
        //  mSearch = (LinearLayout) mSearchHead.findViewById(R.id.search_title);
        mNull_conversation = (TextView) mConvListFragment.findViewById(R.id.null_conversation);
        // mAllUnReadMsg = (TextView) mFragment.getActivity().findViewById(R.id.all_unread_number);
        mConvListView.addHeaderView(mLoadingHeader);
        //  mConvListView.addHeaderView(mSearchHead);
        mConvListView.addHeaderView(mHeader);
        mConvListView.addHeaderView(mHeadMessage, null, false);

    }


    public void setConvListAdapter(ListAdapter adapter) {
        mConvListView.setAdapter(adapter);
    }


    public void setListener(View.OnClickListener onClickListener) {
        //  mSearch.setOnClickListener(onClickListener);
        mCreateGroup.setOnClickListener(onClickListener);
    }

    public void setItemListeners(AdapterView.OnItemClickListener onClickListener) {
        mConvListView.setOnItemClickListener(onClickListener);
    }

    public void setLongClickListener(AdapterView.OnItemLongClickListener listener) {
        mConvListView.setOnItemLongClickListener(listener);
    }


    public void showHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.VISIBLE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.VISIBLE);
    }

    public void dismissHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.GONE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.GONE);
    }

    public void showLoadingHeader() {
        mLoadingIv.setVisibility(View.VISIBLE);
        mLoadingTv.setVisibility(View.VISIBLE);
        AnimationDrawable drawable = (AnimationDrawable) mLoadingIv.getDrawable();
        drawable.start();
    }

    public void dismissLoadingHeader() {
        mLoadingIv.setVisibility(View.GONE);
        mLoadingTv.setVisibility(View.GONE);
    }

    public void setNullConversation(final boolean isHaveConv) {
        Activity mContext = (Activity) this.mContext;
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isHaveConv) {

                    mNull_conversation.setVisibility(View.GONE);
                } else {
                    mNull_conversation.setVisibility(View.VISIBLE);
                }
            }
        });

    }

//
//    public void setUnReadMsg(final int count) {
//        ThreadUtil.runInUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mAllUnReadMsg != null) {
//                    if (count > 0) {
//                        mAllUnReadMsg.setVisibility(View.VISIBLE);
//                        if (count < 100) {
//                            mAllUnReadMsg.setText(count + "");
//                        } else {
//                            mAllUnReadMsg.setText("99+");
//                        }
//                    } else {
//                        mAllUnReadMsg.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
//    }


}
