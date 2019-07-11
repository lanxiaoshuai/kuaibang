package com.witkey.witkeyhelp.view.impl.fragment;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MessageRecyAdapter;
import com.witkey.witkeyhelp.bean.Message;
import com.witkey.witkeyhelp.presenter.IMessageFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MessageFragPresenterImpl;
import com.witkey.witkeyhelp.view.IMessageFragView;

import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 消息fragment
 */
public class MessageFragment extends BaseListFragment implements IMessageFragView, View.OnClickListener {

    private RelativeLayout rl_message_sys;
    private RelativeLayout rl_message_diamonon;
    private RelativeLayout rl_message_we;

    private IMessageFragPresenter presenter;

    private List<Message> messageList;

    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    private boolean isLoading = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_message;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MessageFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        rl_message_sys.setOnClickListener(this);
        rl_message_diamonon.setOnClickListener(this);
        rl_message_we.setOnClickListener(this);
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
            isLoading = true;
            allGet();
        }
    }

    private void allGet() {
        presenter.getMessageList();
    }

    @Override
    protected void onRefresh() {
        isLoading = false;
        allGet();
    }

    @Override
    protected int setRecyDividerHeight() {
        return 10;
    }

    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        getData();
    }

    private void getData() {
        //刚进来的操作
        if (messageList != null) {
            messageList.clear();
            messageList = null;
        }
        allGet();
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setIncludeTitle("消息中心");
        rl_message_sys = (RelativeLayout) findViewById(R.id.rl_message_sys);
        rl_message_diamonon = (RelativeLayout) findViewById(R.id.rl_message_diamonon);
        rl_message_we = (RelativeLayout) findViewById(R.id.rl_message_we);
    }

    @Override
    public void showMessageList(List<Message> messageList) {
        if(messageList!=null){
            getSuc();
            if (isLoading) {
                this.messageList.addAll(messageList);
                isLoading = false;
            } else {
                this.messageList = messageList;
            }
            showAdapter();
        }
    }

    private void showAdapter() {
        if (adapter == null) {
            adapter = new MessageRecyAdapter(getActivity(), messageList);
            recyclerView.setAdapter(adapter);
        } else {
            ((MessageRecyAdapter) adapter).setData(messageList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_message_sys:

                break;
            case R.id.rl_message_diamonon:

                break;
            case R.id.rl_message_we:

                break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
