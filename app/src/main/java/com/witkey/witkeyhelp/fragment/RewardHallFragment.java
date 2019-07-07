package com.witkey.witkeyhelp.fragment;

import android.net.Uri;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.witkey.witkeyhelp.R;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 悬赏大厅fragment
 */
public class RewardHallFragment extends BaseFragment {
    private Spinner spin_classify;
    private Spinner spin_order;
    private String[] classifyData={"线上任务","线上任务","线上任务","线上任务"};
    private String[] orderData={"线上任务","线上任务","线上任务","线上任务"};

    @Override
    protected int getContentView() {
        return R.layout.fragment_reward_hall;
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected void initEvent() {

    }


    @Override
    protected String getFragmentName() {
        return null;
    }

    @Override
    protected void initViewExceptPresenter() {

    }

    @Override
    protected void initWidght() {
        spin_classify = (Spinner) findViewById(R.id.spin_classify);
        spin_order = (Spinner) findViewById(R.id.spin_order);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.item_spinner, classifyData);
        spin_classify.setAdapter(spinnerAdapter);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getContext(),
                R.layout.item_spinner, classifyData);
        spin_order.setAdapter(Adapter);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
