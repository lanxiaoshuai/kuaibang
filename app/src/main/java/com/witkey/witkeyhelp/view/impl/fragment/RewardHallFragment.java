package com.witkey.witkeyhelp.view.impl.fragment;

import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MissionRecyAdapter;
import com.witkey.witkeyhelp.bean.Mission;
import com.witkey.witkeyhelp.bean.MissionFilter;
import com.witkey.witkeyhelp.dialog.MissionFilterDialog;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.IReawardHallFragPresenter;
import com.witkey.witkeyhelp.presenter.impl.ReawardHallFragPresenterImpl;
import com.witkey.witkeyhelp.view.IReawardHallFragView;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.List;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 悬赏大厅fragment
 */
public class RewardHallFragment extends BaseListFragment implements IReawardHallFragView {
    private NiceSpinner spin_classify;
    private NiceSpinner spin_order;
    private TextView tv_filter;
    private EditText et_search;

    private String[] classifyData = {"线上任务", "线下任务", "人命币", "钻石"};
    private String[] orderData = {"任务价格", "截止时间", "按照距离"};

    private IReawardHallFragPresenter presenter;
    private boolean isLoading = false;
    //控制数据的变量
    private String chooseClassify;
    private String chooseOrder;
    private MissionFilter filter;
    private String searchKeyWord;

    //获取的任务列表数据
    private List<Mission> missionList;

    //高级筛选dialog
    private MissionFilterDialog missionFilterDialog;


    @Override
    protected int getContentView() {
        return R.layout.fragment_reward_hall;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new ReawardHallFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        spin_classify.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                chooseClassify = classifyData[position];
                allGet();
            }
        });
        spin_order.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                chooseOrder = orderData[position];
                allGet();
            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionFilterDialog = new MissionFilterDialog(getActivity(), filter, findViewById(R.id.ll_spinner));
                missionFilterDialog.setOnclickListener(new MissionFilterDialog.ICommitOnclickListener() {
                    @Override
                    public void onCommit(MissionFilter missionFilter) {
                        filter = missionFilter;
                        allGet();
                    }
                });
                missionFilterDialog.show();
            }
        });
        //搜索框操作
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    searchKeyWord = s.toString().trim();
                    allGet();
                }
            }
        });
    }

    @Override
    protected void onLoadMore() {
        if (!isLoading) {
//            if (customer != null) {
//                if (customer.getMeta().getPagination().getTotal_pages() > customer.getMeta().getPagination().getCurrent_page()) {
//                    page = customer.getMeta().getPagination().getCurrent_page() + 1;
            isLoading = true; // 在获取前修改状态
            allGet();
//                }
//            }
        }
    }

    @Override
    protected void onRefresh() {
        // TODO: 2019/7/10 设置页
        isLoading = false;
        allGet();
    }

    @Override
    protected int setRecyDividerHeight() {
        return 0;
    }


    @Override
    protected void initViewExceptPresenter() {
        super.initViewExceptPresenter();
        // TODO: 2019/7/9 刷新操作
        getData();
    }

    /**
     * 刷新数据
     */
    private void getData() {
        //不确定是否全部刷新
//        if (spin_classify != null) {
//            spin_classify.setSelectedIndex(0);
//            chooseClassify = "线上任务";
//        }
//        if (spin_order != null) {
//            spin_order.setSelectedIndex(0);
//            chooseOrder = "任务价格";
//        }
//        if(filter!=null){
//            //todo 设为空
//        }
        if (missionList != null) {
            missionList.clear();
            missionList = null;
        }
        allGet();
    }

    /**
     * 获取数据
     */
    private void allGet() {
        presenter.getMissionList(chooseClassify, chooseOrder, filter, searchKeyWord);
        // TODO: 2019/7/9 显示刷新
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setIncludeTitle("悬赏大厅");
        spin_classify = (NiceSpinner) findViewById(R.id.spin_classify);
        spin_order = (NiceSpinner) findViewById(R.id.spin_order);
        tv_filter = (TextView) findViewById(R.id.tv_filter);
        et_search = (EditText) findViewById(R.id.et_search);

        spin_classify.attachDataSource(Arrays.asList(classifyData));
        spin_order.attachDataSource(Arrays.asList(orderData));
        tv_filter.setText("高级筛选");

        filter = new MissionFilter();
    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void showMissionList(List<Mission> missions) {
        if (missions != null) {
            getSuc();
            if (isLoading) {
                missionList.addAll(missions);
                isLoading = false;
            } else {
                missionList = missions;
            }
            showAdapter();
        }
    }

    private void showAdapter() {
        if (adapter == null) {
            adapter = new MissionRecyAdapter(getActivity(), missionList);
            recyclerView.setAdapter(adapter);
        } else {
            ((MissionRecyAdapter) adapter).setData(missionList);
            adapter.notifyDataSetChanged();
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
