package com.witkey.witkeyhelp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Set;


/**
 * @author lingxu
 * @date 2019/7/24 14:19
 * @description 高级筛选
 */
public class MissionFilterDialog extends Dialog {
    private String TAG = "llx";
    private MissionBean missionBean;

    private TagFlowLayout tfl_mission_filter;
    private TagFlowLayout tfl_mission_type;
    private TagFlowLayout tfl_is_need_bond;
    private TagFlowLayout tfl_is_need_bidding;

    private TagAdapter filterAdapter;
    private TagAdapter typeAdapter;
    private TagAdapter isNeedBondAdapter;
    private TagAdapter isNeedBiddingAdapter;

    private Button btn_commit;

    private String[] filterData = {"信息咨询", "悬赏帮忙", "紧急求助", "失物招领"};
    private String[] typeData = {"普通", "竞标"};
    private String[] isNeedBondData = {"是", "否"};
    private String[] isNeedBiddingData = {"是", "否"};

    private Context context; //tagflowlayout需要context  getcontext不可以

    private ICommitOnclickListener onclickListener;
    private View belowView;

    public interface ICommitOnclickListener {
        void onCommit(MissionBean missionBean);
    }

    public void setOnclickListener(ICommitOnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }


    public MissionFilterDialog(Context context, MissionBean missionBean, View belowView) {
        super(context, R.style.ShareDialog);
        this.context = context;
        this.missionBean = missionBean;
        this.belowView = belowView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //点击空余位置不可关闭
        this.setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.dia_mission_filter);
        //弹窗点击周围空白处弹出层自动消失弹窗消失(false时为点击周围空白处弹出层不自动消失)
        this.setCanceledOnTouchOutside(false);

//        开始:显示到某个控件下方
        //获取当前Activity所在的窗体
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        //获取通知栏高度  重要的在这，获取到通知栏高度
        int notificationBar = Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
//获取控件 textview 的绝对坐标,( y 轴坐标是控件上部到屏幕最顶部（不包括控件本身）)
        //location [0] 为x绝对坐标;location [1] 为y绝对坐标
        int[] location = new int[2];
        belowView.getLocationInWindow(location); //获取在当前窗体内的绝对坐标
        belowView.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        wlp.x = 0;//对 dialog 设置 x 轴坐标
        wlp.y = location[1] + belowView.getHeight() - notificationBar; //对dialog设置y轴坐标
        wlp.gravity = Gravity.TOP;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
//      结束:显示到某个控件下方
        initView();
        showData();
    }

    private void showData() {
        if (missionBean != null) {
            //任务分类
            filterAdapter = new TagAdapter<String>(filterData) {
                @Override
                public View getView(FlowLayout parent, int position, String str) {
                    TextView tv = (TextView) LayoutInflater.from(
                            getContext()).inflate(R.layout.item_tag_mission_filter, tfl_mission_filter, false);
                    tv.setText(str);
                    return tv;
                }
            };
            tfl_mission_filter.setAdapter(filterAdapter);
            String businessType = missionBean.getBusinessType();
            if (businessType != null) {
                int businessTypeNum = Integer.parseInt(businessType);
                businessType = filterData[businessTypeNum - 1];
            }
            setTagSelect(businessType, filterAdapter, filterData);
            //任务类型
            typeAdapter = new TagAdapter<String>(typeData) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(
                            getContext()).inflate(R.layout.item_tag_mission_filter, tfl_mission_type, false);
                    tv.setText(s);
                    return tv;
                }
            };
            tfl_mission_type.setAdapter(typeAdapter);
            setTagSelect(missionBean.getProductType(), typeAdapter, typeData);
            //是否有保证金
            isNeedBondAdapter = new TagAdapter<String>(isNeedBondData) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(
                            getContext()).inflate(R.layout.item_tag_mission_filter, tfl_is_need_bond, false);
                    tv.setText(s);
                    return tv;
                }
            };
            tfl_is_need_bond.setAdapter(isNeedBondAdapter);
            if (this.missionBean.getBondType() != null) {
                setTagSelect(Integer.parseInt(this.missionBean.getBondType()) == 1 ? "是" : "否", isNeedBondAdapter, isNeedBondData);
            }
            //是否需要竞标
            isNeedBiddingAdapter = new TagAdapter<String>(isNeedBiddingData) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(
                            getContext()).inflate(R.layout.item_tag_mission_filter, tfl_is_need_bidding, false);
                    tv.setText(s);
                    return tv;
                }
            };
            tfl_is_need_bidding.setAdapter(isNeedBiddingAdapter);
            if (this.missionBean.getBiddingType() != null) {
                setTagSelect(Integer.parseInt(this.missionBean.getBiddingType()) == 1 ? "是" : "否", isNeedBiddingAdapter, isNeedBiddingData);
            }
            tfl_mission_filter.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (!selectPosSet.isEmpty()) {
                        for (int i : selectPosSet) {
                            if (filterData[i].equals("信息咨询")) {
                                missionBean.setBusinessType(1 + "");
                            } else if (filterData[i].equals("悬赏帮忙")) {
                                missionBean.setBusinessType(2 + "");
                            } else if (filterData[i].equals("紧急求助")) {
                                missionBean.setBusinessType(3 + "");
                            } else if (filterData[i].equals("失物招领")) {
                                missionBean.setBusinessType(4 + "");
                            }
                        }
                    }
                }
            });
            tfl_mission_type.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (!selectPosSet.isEmpty()) {
                        for (int i : selectPosSet) {
                            missionBean.setProductType(typeData[i]);
                        }
                    }
                }
            });
            tfl_is_need_bond.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (!selectPosSet.isEmpty()) {
                        for (int i : selectPosSet) {
                            missionBean.setBondType(isNeedBondData[i].equals("是") ? 1 + "" : 0 + "");
                        }
                    }
                }
            });
            tfl_is_need_bidding.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                @Override
                public void onSelected(Set<Integer> selectPosSet) {
                    if (!selectPosSet.isEmpty()) {
                        for (int i : selectPosSet) {
                            missionBean.setBiddingType(isNeedBiddingData[i].equals("是") ? 1 + "" : 0 + "");
                        }
                    }
                }
            });
        }

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickListener.onCommit(missionBean);
                Log.d(TAG, "onClick: " + missionBean.toString());
                //关闭dialog
                MissionFilterDialog.this.dismiss();
            }
        });
    }

    /**
     * 设置选中的tag
     *
     * @param str
     * @param adapter
     * @param data
     */
    private void setTagSelect(String str, TagAdapter adapter, String[] data) {
        if (str != null) {
            if (!str.isEmpty()) {
                for (int i = 0; i < data.length; i++) {
                    if (str.equals(data[i])) {
                        adapter.setSelectedList(i);
                    }
                }
            }
        }
    }


    private void initView() {
        tfl_mission_filter = findViewById(R.id.tfl_mission_filter);
        tfl_mission_type = findViewById(R.id.tfl_mission_type);
        tfl_is_need_bond = findViewById(R.id.tfl_is_need_bond);
        tfl_is_need_bidding = findViewById(R.id.tfl_is_need_bidding);
        btn_commit = findViewById(R.id.btn_commit);

        //设置单选
        tfl_mission_filter.setMaxSelectCount(1);
        tfl_mission_type.setMaxSelectCount(1);
        tfl_is_need_bond.setMaxSelectCount(1);
        tfl_is_need_bidding.setMaxSelectCount(1);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        missionBean = null;
    }

}
