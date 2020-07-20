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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.MissionBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * @author lingxu
 * @date 2019/7/24 14:19
 * @description 高级筛选
 */
public class MissionFilterDialog extends Dialog {
    private String TAG = "llx";
    private MissionBean missionBean;

    private TagFlowLayout tfl_mission_type;
    private TagFlowLayout tfl_is_need_bond;
    private TagFlowLayout tfl_price_type;

    private TagAdapter filterAdapter;


    private TagAdapter isNeedBondAdapter;
    private TagAdapter priceTypeAdapter;

    private LinearLayout btn_commit;
    private LinearLayout withdraw;
    private String[] filterData = {"信息咨询", "悬赏帮忙", "紧急求助", "失物招领"};
    //   private String[] typeData = {"普通", "竞标"};
    private String[] isNeedBondData = {"是", "否"};
    private String[] priceTypeData = {"人民币", "钻石"};
    private String[] regionTypeData = {"附近的悬赏", "不限制地域"};
    private Context context; //tagflowlayout需要context  getcontext不可以

    public ICommitOnclickListener onclickListener;
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
        this.setCanceledOnTouchOutside(true);
        this.setContentView(R.layout.dia_mission_filter);
        //弹窗点击周围空白处弹出层自动消失弹窗消失(false时为点击周围空白处弹出层不自动消失)
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

    }

    @Override
    public void show() {
        super.show();
        showData();
    }

    private void showData() {
        if (missionBean != null) {


            filterAdapter = new TagAdapter<String>(regionTypeData) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(
                            getContext()).inflate(R.layout.item_tag_mission_filter, tfl_mission_type, false);
                    tv.setText(s);
                    return tv;
                }
            };

            tfl_mission_type.setAdapter(filterAdapter);
            if (this.missionBean.getUnareaType() != null) {
                setTagSelect(Integer.parseInt(missionBean.getUnareaType()) == 1 ? "附近的悬赏" : "不限制地域", filterAdapter, regionTypeData);
            }


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
            priceTypeAdapter = new TagAdapter<String>(priceTypeData) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) LayoutInflater.from(
                            getContext()).inflate(R.layout.item_tag_mission_filter, tfl_price_type, false);
                    tv.setText(s);
                    return tv;
                }
            };
            tfl_price_type.setAdapter(priceTypeAdapter);


            tfl_mission_type.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int i, FlowLayout flowLayout) {

                    if (i == 0) {
                        if ((null == missionBean.getUnareaType() || missionBean.getUnareaType().equals("0")) && i == 0) {
                            missionBean.setUnareaType("1");
                        } else if ((missionBean.getUnareaType().equals("1") || missionBean.getUnareaType().equals("0")) && i == 0) {
                            missionBean.setUnareaType(null);
                        }
                    }
                    if (i == 1) {
                        if ((null == missionBean.getUnareaType() || missionBean.getUnareaType().equals("1")) && i == 1) {
                            missionBean.setUnareaType("0");
                        } else if ((missionBean.getUnareaType().equals("0") || missionBean.getUnareaType().equals("1")) && i == 1) {
                            missionBean.setUnareaType(null);
                        }

                    }


                    return true;
                }
            });

            tfl_is_need_bond.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()

            {
                @Override
                public boolean onTagClick(View view, int i, FlowLayout flowLayout) {
                    if (i == 0) {
                        if ((null == missionBean.getBondType() || missionBean.getBondType().equals("0")) && i == 0) {
                            missionBean.setBondType("1");
                        } else if ((missionBean.getBondType().equals("1") || missionBean.getBondType().equals("0")) && i == 0) {
                            missionBean.setBondType(null);
                        }
                    }
                    if (i == 1) {
                        if ((null == missionBean.getBondType() || missionBean.getBondType().equals("1")) && i == 1) {
                            missionBean.setBondType("0");
                        } else if ((missionBean.getBondType().equals("0") || missionBean.getBondType().equals("1")) && i == 1) {
                            missionBean.setBondType(null);
                        }

                    }
                    return true;
                }
            });
            if (this.missionBean.getPaymentType() != null)

            {
                setTagSelect(Integer.parseInt(this.missionBean.getPaymentType()) == 1 ? "人民币" : "钻石", priceTypeAdapter, priceTypeData);
            } else

            {

            }
            tfl_price_type.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()

            {
                @Override
                public boolean onTagClick(View view, int i, FlowLayout flowLayout) {

                    switch (i) {
                        case 0:
                            if (i == 0) {
                                if ((null == missionBean.getPaymentType() || "2".equals(missionBean.getPaymentType())) && i == 0) {
                                    missionBean.setPaymentType("1");

                                } else if ((missionBean.getPaymentType().equals("1") || "2".equals(missionBean.getPaymentType())) && i == 0) {
                                    missionBean.setPaymentType(null);

                                }
                            }
                            break;
                        case 1:
                            if (i == 1) {
                                if ((null == missionBean.getPaymentType() || "1".equals(missionBean.getPaymentType())) && i == 1) {
                                    missionBean.setPaymentType("2");

                                } else if ((missionBean.getPaymentType().equals("2") || "1".equals(missionBean.getPaymentType())) && i == 1) {
                                    missionBean.setPaymentType(null);

                                }

                            }
                            break;
                    }


                    return true;
                }
            });
        }

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onclickListener.onCommit(missionBean);
                if (missionBean.getPaymentType() == "1") {
                    MobclickAgent.onEvent(context, "chineseYuan");
                } else {
                    MobclickAgent.onEvent(context, "diamond");
                }
                if (missionBean.getUnareaType() == "1") {
                    MobclickAgent.onEvent(context, "bountynearby");
                } else {
                    MobclickAgent.onEvent(context, "nlimiteduTerritory");
                }
                if (missionBean.getBondType() == "1") {
                    MobclickAgent.onEvent(context, "rewardHallbond");
                } else {
                    MobclickAgent.onEvent(context, "NoRewardHallbond");
                }

                //关闭dialog
                MissionFilterDialog.this.dismiss();

            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(context, "noRewardHallreset");
                missionBean.setBondType(null);
                missionBean.setPaymentType(null);
                missionBean.setUnareaType(null);
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
//        tfl_mission_filter = findViewById(R.id.tfl_mission_filter);
        tfl_mission_type = findViewById(R.id.tfl_mission_type);
        tfl_is_need_bond = findViewById(R.id.tfl_is_need_bond);
        tfl_price_type = findViewById(R.id.tfl_price_type);
        btn_commit = findViewById(R.id.btn_commit);
        withdraw = findViewById(R.id.withdraw);
        //设置单选
//        tfl_mission_filter.setMaxSelectCount(1);
        tfl_mission_type.setMaxSelectCount(1);
        tfl_is_need_bond.setMaxSelectCount(1);
        tfl_price_type.setMaxSelectCount(1);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //missionBean = null;
    }

}
