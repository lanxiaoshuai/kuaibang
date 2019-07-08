package com.witkey.witkeyhelp.fragment;

import android.content.Context;
import android.net.Uri;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.witkey.witkeyhelp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 首页fragment
 */
public class HomeFragment extends BaseFragment {
    private OnFragmentInteractionListener mListener;
    private RelativeLayout rl_consult;
    private RelativeLayout rl_help;
    private GridView gv_function;
    private ImageView iv_bottom;
    private ArrayList<Map<String, Object>> funtionData;
    private String[] functionStrs = {"紧急求助", "失物招领", "微通知", "钻石通知"};
    private int[] functionImg = {R.drawable.ic_test, R.drawable.ic_test, R.drawable.ic_test, R.drawable.ic_test};
    private SimpleAdapter adapter_function;

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected String getFragmentName() {
        return "首页";
    }

    @Override
    protected void initViewExceptPresenter() {

    }

    protected void initWidght() {
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
