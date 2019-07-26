package com.witkey.witkeyhelp.view.impl.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.util.IntentUtil;
import com.witkey.witkeyhelp.view.IHomeFragView;
import com.witkey.witkeyhelp.view.impl.ConsultActivity;
import com.witkey.witkeyhelp.view.impl.LostFoundActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 首页fragment
 */
public class HomeFragment extends BaseFragment implements IHomeFragView, View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private RelativeLayout rl_consult;
    private RelativeLayout rl_help;
    private GridView gv_function;
    private ImageView iv_bottom;
    private ArrayList<Map<String, Object>> funtionData;
    private String[] functionStrs = {"紧急求助", "失物招领", "微通知", "钻石通知"};
    private int[] functionImg = {R.mipmap.ic_home_emergencyhelp, R.mipmap.ic_home_lostfound, R.mipmap.ic_home_notice, R.mipmap.ic_home_diamondnotice};
    private SimpleAdapter adapter_function;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected IPresenter[] getPresenters() {
        return new IPresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected void initEvent() {
        rl_consult.setOnClickListener(this);
        rl_help.setOnClickListener(this);
        iv_bottom.setOnClickListener(this);
        gv_function.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //紧急求助
                    startActivity("3");
                } else if (position == 1) {
                    //失物招领
                    IntentUtil.startActivity(getActivity(), LostFoundActivity.class);
                } else if (position == 2) {
                    //微通知
                } else if (position == 3) {
                    //钻石通知
                }
            }
        });
    }

    @Override
    protected void initViewExceptPresenter() {

    }

    protected void initWidget() {
        setIncludeTitle("威客帮");
        rl_consult = (RelativeLayout) findViewById(R.id.rl_consult);
        rl_help = (RelativeLayout) findViewById(R.id.rl_help);
        gv_function = (GridView) findViewById(R.id.gv_function);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        initData();
    }


    private void initData() {
        funtionData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < functionStrs.length; i++) {
            setData(functionImg[i], functionStrs[i], funtionData);
        }
        adapter_function = new SimpleAdapter(getActivity(), funtionData, R.layout.item_gv_home, new String[]{"icon", "str"}, new int[]{R.id.iv, R.id.tv});
        gv_function.setAdapter(adapter_function);
    }

    private void setData(int icon, String str, List data) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("icon", icon);
        hashMap.put("str", str);
        data.add(hashMap);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
    public void onClick(View v) {
        switch (v.getId()) {
            //信息咨询
            case R.id.rl_consult:
                startActivity("1");
                break;
            //悬赏帮忙
            case R.id.rl_help:
                startActivity("2");
                break;
            //下面广告位
            case R.id.iv_bottom:
                break;
        }

    }

    private void startActivity(String s) {
        Intent i = new Intent(getActivity(), ConsultActivity.class);
        i.putExtra("EXTRA_PAGE_TYPE", s);
        startActivity(i);
    }

    @Override
    public void onError(String error) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
