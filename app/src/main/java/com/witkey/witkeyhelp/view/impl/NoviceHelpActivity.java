package com.witkey.witkeyhelp.view.impl;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.NoviceHelpAdapter;
import com.witkey.witkeyhelp.bean.NoviceHelpBean;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2020/3/16.
 */

public class NoviceHelpActivity extends BaseActivity {
    private ListView noviceHelpList;
    private List<NoviceHelpBean> mlist;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_novice_help);
        setIncludeTitle("新手帮助");

        noviceHelpList = findViewById(R.id.noviceHelpList);
        mlist = new ArrayList<>();
        mlist.add(new NoviceHelpBean("【发布任务步骤】- 新手必看", "", "", ""));
        mlist.add(new NoviceHelpBean("【接任务步骤】- 新手必看", "", "", ""));
        mlist.add(new NoviceHelpBean("发布/接单任务规则", "发布/接单任务规则", "发布任务规则\n信誉分不足90不能发悬赏", "接单任务规则\n（1）帮忙者信誉分不足95后不能接单 \n（2）每人最多同时接5单 \n（3）一人接单后其他人不可再接此单"));
        mlist.add(new NoviceHelpBean("如何撤回悬赏？", "如何撤回悬赏？", "请前往个人中心-发布的任务-已发布详情页-点击 \n撤回（撤回后赏金将退回您的余额账户）", ""));
        mlist.add(new NoviceHelpBean("帮忙者地理位置是什么？", "帮忙者地理位置是什么？", "悬赏者在发布悬赏时可以在更多高级设置中选择帮\n忙者地理位置 \n若您的悬赏适合附近的人完成，请选择附近的人，\n您的悬赏将优先被附近的人看到 \n若您的悬赏对帮忙者所在地域无要求，则选择不限\n地域", ""));

        mlist.add(new NoviceHelpBean("进行中任务如何解除？", "进行中任务如何解除？", "悬赏者解除\n1）请前往个人中心 -发布的任务-进行中详情页- \n任务解除按钮-选择解除理由-发起任务解除\n（2）帮忙者同意后方可成功解除，解除成功后赏金 \n退还到余额\n（3）悬赏者只能因为帮忙者的原因发起任务解除， \n解除成功后扣除帮忙者1分信誉分，其他原因请点击 \n举报", "帮忙者解除\n（1）请前往个人中心 -领取的任务-进行中详情页- \n任务解除按钮-选择解除理由-解除成功\n（2）任务解除后重新返回悬赏大厅\n（3）只能选择自己的原因进行解除，若因为悬赏者 \n的原因请点击举报\n（4）解除成功后将扣除自己1分信誉分，信誉分低\n于95不能接单"));
        mlist.add(new NoviceHelpBean("保证金任务规则/流程", "保证金任务规则/流程", "保证金任务规则\n保证金任务进行中不可解除，有问题可以联系客服 \n解决 \n（个人中心-意见反馈）", "保证金任务流程\n 悬赏者发布： \n发布-更多设置-是否需要帮忙者缴纳保证金-选择 \n是-输入需要帮忙者缴纳的金额-发布\n帮忙者接单： \n领取保证金任务前提：需要帮帮忙者保证金账户中\n的保证金数额不少于悬赏者要求的保证金数额，若\n保证金不足则平台会提示先充值保证金"));
        mlist.add(new NoviceHelpBean("什么是钻石？", "什么是钻石？", "钻石是酷爱帮内流通的货币。可以用来发布信息咨 \n询、悬赏帮忙、抵扣平台费\n 抵扣平台费时10钻石=1元", "如何获得钻石\n（1）新用户注册送10，邀请送10 \n（2）完成钻石悬赏获得 \n（3）钻石通知获得"));
        mlist.add(new NoviceHelpBean("提现规则/流程", "提现规则/流程", "提现规则\n（1）余额0元起提 \n（2）费率1%,最低1元 \n（3）24小时到账 \n（4）余额提现到银行卡", "提现流程\n 请前往个人中心-余额-提现-绑定银行卡-提现到银 \n行卡-24小时到账"));
        mlist.add(new NoviceHelpBean("信誉分规则", "信誉分规则", "信誉分规则\n（1）每人初始分为100分 \n（2）普通悬赏有责解除-1分 \n（3）累计完成100元悬赏，增加一分信誉分 \n（4）累计发布并被完成100元悬赏，增加一分信誉分 \n（5）信誉分＜95，禁止接悬赏；信誉分＜90，\n禁止发悬赏 ", ""));
        mlist.add(new NoviceHelpBean("钻石如何抵扣平台费？", "钻石如何抵扣平台费？", "帮忙者完成悬赏任务时，系统会自动扣除赏金的\n15%作为平台费 \n若钻石钱包不为0，则先扣除钻石作为平台费 \n钻石抵扣平台费时：10钻石=1元", ""));

        NoviceHelpAdapter noviceHelpAdapter = new NoviceHelpAdapter(this, mlist);
        noviceHelpList.setAdapter(noviceHelpAdapter);
        noviceHelpList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (PventQuickClick.isLastFastDoubleClick()) {
                    return;
                }
                if (position < 2) {
                    Intent intent = new Intent(NoviceHelpActivity.this, NoviceImageDetailsActivity.class);

                    switch (position) {
                        case 0:
                            intent.putExtra("image", R.mipmap.noviceone_image);
                            break;
                        case 1:
                            intent.putExtra("image", R.mipmap.novicetwo_image);
                            break;
                    }
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(NoviceHelpActivity.this, NoviceHelpDetailsActivity.class);
                    intent.putExtra("title", mlist.get(position).getTitle());
                    intent.putExtra("rule_one", mlist.get(position).getGuizhe());
                    intent.putExtra("rule_two", mlist.get(position).getGuizheTwo());
                    startActivity(intent);
                }


            }
        });
    }


}
