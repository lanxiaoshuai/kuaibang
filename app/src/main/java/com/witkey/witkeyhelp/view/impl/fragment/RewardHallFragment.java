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
import com.witkey.witkeyhelp.bean.MissionBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
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

//    private String[] classifyData = {"线上任务", "线下任务", "人命币", "钻石"};
    private String[] classifyData = {"信息咨询", "悬赏帮忙", "紧急求助"};
    private String[] orderData = {"任务价格", "发布时间", "按照距离"};

    private IReawardHallFragPresenter presenter;
    private boolean isLoading = false;
    //控制数据的变量
    private MissionBean missionBean;
    private String searchKeyWord;

    private MissionBean chooseMissionBean;

    //获取的任务列表数据
    private PagingResponse missionResponse;
    private List<MissionBean> missionList;

    //高级筛选dialog
    private MissionFilterDialog missionFilterDialog;

    private int pageNum = 1;

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
//                chooseClassify = classifyData[position];
                if (classifyData[position].equals("信息咨询")) {
                    missionBean.setBusinessType(1 + "");
                } else if (classifyData[position].equals("悬赏帮忙")) {
                    missionBean.setBusinessType(2 + "");
                }else if (classifyData[position].equals("紧急求助")) {
                    missionBean.setBusinessType(3 + "");
                }
                allGet();
            }
        });
        spin_order.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
//                chooseOrder = orderData[position];
                allGet();
            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                missionFilterDialog = new MissionFilterDialog(getActivity(), missionBean, findViewById(R.id.ll_spinner));
                missionFilterDialog.setOnclickListener(new MissionFilterDialog.ICommitOnclickListener() {
                    @Override
                    public void onCommit(MissionBean missionFilter) {
                        missionBean.setProductType(missionFilter.getProductType());
                        missionBean.setBondType(missionFilter.getBondType());
                        missionBean.setPaymentType(missionFilter.getPaymentType());
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
            if (missionResponse != null) {
                int totalPage = missionResponse.getTotal() / 10;
                if (missionResponse.getTotal() % 10 != 0) {
                    totalPage += 1;
                }
                if (totalPage > pageNum) {
                    pageNum += 1;
                    isLoading = true; // 在获取前修改状态
                    allGet();
                }
            }
        }
    }

    @Override
    protected void onRefresh() {
        pageNum = 1;
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
//        if(missionBean!=null){
//            //todo 设为空
//        }
        pageNum = 1;
        if (missionList != null) {
            missionList.clear();
            missionList = null;
        }
        if (adapter != null) {
            adapter = null;
        }
        allGet();
    }

    /**
     * 获取数据
     */
    private void allGet() {
        missionBean.setPageNum(pageNum);
        presenter.getMissionList(missionBean, searchKeyWord);
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

        missionBean = new MissionBean();
    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void showMissionList(PagingResponse missionResponse) {
        getSuc();
        this.missionResponse = missionResponse;
        if (missionResponse != null) {
            if (isLoading) {
                missionList.addAll(missionResponse.getRows());
                isLoading = false;
            } else {
                missionList = missionResponse.getRows();
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
        void onFragmentInteraction(Uri uri);
    }
}
