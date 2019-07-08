package com.witkey.witkeyhelp.fragment;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.witkey.witkeyhelp.R;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 悬赏大厅fragment
 */
public class RewardHallFragment extends BaseFragment {
    private NiceSpinner spin_classify;
    private NiceSpinner spin_order;
    private TextView tv_filter;

    private String[] classifyData = {"线上任务", "线下任务", "人命币", "钻石"};
    private String[] orderData = {"任务价格", "截止时间", "按照距离"};



    @Override
    protected int getContentView() {
        return R.layout.fragment_reward_hall;
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected void initEvent() {
        spin_classify.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

            }
        });
        spin_order.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("llx","click");
                Toast.makeText(getActivity(),"click",Toast.LENGTH_LONG).show();
            }
        });
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
        setIncludeTitle("悬赏大厅");
        spin_classify = (NiceSpinner) findViewById(R.id.spin_classify);
        spin_order = (NiceSpinner) findViewById(R.id.spin_order);
        tv_filter = (TextView) findViewById(R.id.tv_filter);

        spin_classify.attachDataSource(Arrays.asList(classifyData));
        spin_order.attachDataSource(Arrays.asList(orderData));
        tv_filter.setText("高级筛选");
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
