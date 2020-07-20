package com.witkey.witkeyhelp.view.impl.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.URL;
import com.witkey.witkeyhelp.bean.Acount;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.presenter.IMeFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MeFragPresenterImpl;
import com.witkey.witkeyhelp.util.ExampleUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.view.IMeFragView;
import com.witkey.witkeyhelp.view.impl.AboutActivity;
import com.witkey.witkeyhelp.view.impl.ActivityBalance;
import com.witkey.witkeyhelp.view.impl.ActivityBond;
import com.witkey.witkeyhelp.view.impl.ActivityCreditScore;
import com.witkey.witkeyhelp.view.impl.ActivityLostFound;
import com.witkey.witkeyhelp.view.impl.ActivityMyPost;
import com.witkey.witkeyhelp.view.impl.ActivityNews;
import com.witkey.witkeyhelp.view.impl.CollectionActivity;
import com.witkey.witkeyhelp.view.impl.DiamondDeductionActivity;
import com.witkey.witkeyhelp.view.impl.FriendInvitationActivity;
import com.witkey.witkeyhelp.view.impl.LoginActivity;
import com.witkey.witkeyhelp.view.impl.MyHelpActivity;
import com.witkey.witkeyhelp.view.impl.MyReplyActivity;
import com.witkey.witkeyhelp.view.impl.ProblemFeedbackActivity;
import com.witkey.witkeyhelp.view.impl.ReceiveMissionActivity;
import com.witkey.witkeyhelp.view.impl.ReleaseMissionActivity;
import com.witkey.witkeyhelp.view.impl.UserInfoActivity;

import java.util.Random;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;

/**
 * @author lingxu
 * @date 2019/7/4 14:13
 * @description 我fragment
 */
public class MeFragment extends BaseFragment implements IMeFragView, View.OnClickListener {
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_ID;
    private TextView tv_reputation_num;
    //余额\n0.00
    private TextView tv_balance;
    //钻石\n0.00
    private TextView tv_diamons;
    //保证金\n0.00
    private TextView tv_bond;
    private TextView tv_receive_mission;
    private TextView tv_publish_mission;
    private TextView tv_collect;

    private TextView tv_setting;
    private TextView tv_about;
    private TextView tv_quit;

    private IMeFragPresenter presenter;

    private User user;
    private Acount acount;
    private UICustomDialog2 dialog2;
    private RelativeLayout rl_top;
    private double balance;
    private double diamondsBalance;
    private int type;
    private TextView tv_fenx;
    private RequestOptions options;
    private TextView problem;
    private TextView tv_my_reply;


    @Override
    protected int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new MeFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initEvent() {

        tv_balance.setOnClickListener(this);
        tv_diamons.setOnClickListener(this);
        tv_bond.setOnClickListener(this);
        tv_receive_mission.setOnClickListener(this);
        tv_publish_mission.setOnClickListener(this);
        tv_collect.setOnClickListener(this);

        tv_setting.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        tv_quit.setOnClickListener(this);
        rl_top.setOnClickListener(this);
        tv_fenx.setOnClickListener(this);
        tv_reputation_num.setOnClickListener(this);
        problem.setOnClickListener(this);
        tv_my_reply.setOnClickListener(this);
    }

    @Override
    protected void initViewExceptPresenter() {
        setIncludeTitle("个人中心");
        user = SpUtils.getObject(getActivity(), "LOGIN");

        if (user != null) {
            show(user);
            //  presenter.getAcount(user.getUserId());
        } else {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.putExtra("type", "0");
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    private void show(User user) {
        //显示个人中心信息
        if (user != null && user.getUserId() != 0) {
            // TODO: 2019/7/19 个人中心图片


            Glide.with(this).load(URL.getImgPath + user.getHeadUrl()).into(iv_avatar);
            tv_name.setText(user.getRealName());
            tv_ID.setText(hidePhoneNum(user.getUserName()));

        }
    }

    @Override
    protected void initWidget() {
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_ID = (TextView) findViewById(R.id.tv_ID);
        tv_reputation_num = (TextView) findViewById(R.id.tv_reputation_num);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_diamons = (TextView) findViewById(R.id.tv_diamons);
        tv_bond = (TextView) findViewById(R.id.tv_bond);
        tv_receive_mission = (TextView) findViewById(R.id.tv_receive_mission);
        tv_publish_mission = (TextView) findViewById(R.id.tv_publish_mission);
        tv_collect = (TextView) findViewById(R.id.tv_collect);

        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_quit = (TextView) findViewById(R.id.tv_quit);
        rl_top = (RelativeLayout) findViewById(R.id.rl_top);
        tv_fenx = (TextView) findViewById(R.id.tv_fenx);
        problem = (TextView) findViewById(R.id.problem);
        tv_my_reply = (TextView) findViewById(R.id.tv_my_reply);


        acount = new Acount();
    }


    @Override
    public void showUser(User user) {
        // TODO: 2019/7/19 获取user信息 修改数据后

        this.user = user;
        show(this.user);
    }

    @Override
    public void showAcount(Acount acount) {
        if (acount != null) {
            type = 1;
            this.acount = acount;
            balance = acount.getBalance();
            diamondsBalance = acount.getDiamondsBalance();
            tv_balance.setText("余额\n" + acount.getBalance());
            tv_diamons.setText("钻石\n" + acount.getDiamondsBalance());
            tv_bond.setText("保证金\n" + acount.getDeposit() + "");
            tv_reputation_num.setText("信誉分:" + acount.getReputationNum());

        }
    }

    /**
     * 隐藏部分手机号码
     *
     * @param phone
     * @return
     */
    public static String hidePhoneNum(String phone) {
        String result = "";
        if (phone != null && !"".equals(phone)) {

            result = phone.substring(0, 3) + "****" + phone.substring(7);

        }
        return result;
    }

    @Override
    public void showDeductionData(String data) {

    }

    @Override
    public void updateUserInfo(String data) {

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {

            case R.id.tv_balance:

                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                user = SpUtils.getObject(getActivity(), "LOGIN");
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    MobclickAgent.onEvent(getActivity(), "balance");
                    i = new Intent(getActivity(), ActivityBalance.class);
                    i.putExtra("price", balance + "");
                    startActivity(i);
                }


                break;
            case R.id.tv_diamons:
                user = SpUtils.getObject(getActivity(), "LOGIN");
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {

                    if (PventQuickClick.isFastDoubleClick()) {
                        break;
                    }
                    MobclickAgent.onEvent(getActivity(), "mydiamond");
                    i = new Intent(getActivity(), ActivityNews.class);
                    i.putExtra("amountmoney", "钻石");
                    startActivity(i);
                }


                break;
            case R.id.tv_bond:

                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    MobclickAgent.onEvent(getActivity(), "mybond");
                    user = SpUtils.getObject(getActivity(), "LOGIN");
                    i = new Intent(getActivity(), ActivityBond.class);
                    i.putExtra("price", acount.getDeposit() + "");
                    startActivity(i);
                }


                break;
            case R.id.tv_receive_mission:

                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    MobclickAgent.onEvent(getActivity(), "receivedrTasks");
                    i = new Intent(getActivity(), MyHelpActivity.class);
                    i.putExtra("EXTRA_IS_RELEASE", false);
                    startActivity(i);
                }

                break;
            case R.id.tv_publish_mission:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    MobclickAgent.onEvent(getActivity(), "ublishedpTasks");
                    i = new Intent(getActivity(), ActivityMyPost.class);
                    i.putExtra("EXTRA_IS_RELEASE", true);
                    startActivity(i);
                }

                break;
            case R.id.tv_collect:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {


                    i = new Intent(getActivity(), CollectionActivity.class);

                    startActivity(i);
                }


                break;

            case R.id.tv_setting:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    MobclickAgent.onEvent(getActivity(), "setUp");
                    Intent intent = new Intent(getActivity(), DiamondDeductionActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_about:

                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
//                if (user == null) {
//                    i = new Intent(getActivity(), LoginActivity.class);
//                    i.putExtra("type", "0");
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//                    return;
//                } else {
                MobclickAgent.onEvent(getActivity(), "aboutUs");
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                //  }
                break;
            case R.id.tv_quit:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    dialog2 = new UICustomDialog2(getActivity(), "确定退出", "3");
                    dialog2.setCancelButton(R.string.dialog_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.hide();
                        }
                    });
                    dialog2.setOkButton(R.string.ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setAlias("");
                            MobclickAgent.onEvent(getActivity(), "signout");
                            dialog2.hide();
                            JMessageClient.logout();
                            //MyAPP.getInstance().exit();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.putExtra("type", "0");
                            startActivity(intent);
                            SpUtils.putObject(getActivity(), "LOGIN", null);
                            getActivity().finish();


                        }
                    });
                    dialog2.show();
                }
                break;
            case R.id.rl_top:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    i = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(i);
                }

                break;
            case R.id.tv_fenx:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    i = new Intent(getActivity(), FriendInvitationActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.tv_reputation_num:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.putExtra("type", "0");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                } else {
                    MobclickAgent.onEvent(getActivity(), "reditcScore");
                    i = new Intent(getActivity(), ActivityCreditScore.class);
                    startActivity(i);
                }
                break;
            case R.id.problem:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }

                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);


                } else {
                    i = new Intent(getActivity(), ProblemFeedbackActivity.class);
                    i.putExtra("type", 0);
                    startActivity(i);
                }

                break;
            case R.id.tv_my_reply:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if (user == null) {
                    i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);


                } else {
                    i = new Intent(getActivity(), MyReplyActivity.class);
                    i.putExtra("type", 0);
                    startActivity(i);
                }

                break;

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();

            return;
        } else {  // 在最前端显示 相当于调用了onResume();
            //网络数据刷新
            user = SpUtils.getObject(getActivity(), "LOGIN");

            if (user != null) {
                presenter.getAcount(user.getUserId());
            } else {
                tv_ID.setText("未登录");
                tv_reputation_num.setText("信誉分:");
                tv_balance.setText("余额\n0.00");
                tv_diamons.setText("钻石\n0.00");
                tv_bond.setText("保证金\n0.00");
                iv_avatar.setImageResource(R.drawable.jmui_head_icon);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        user = SpUtils.getObject(getActivity(), "LOGIN");
        if (user != null) {
            show(user);

            presenter.getAcount(user.getUserId());
        } else {
            tv_ID.setText("未登录");
            tv_reputation_num.setText("信誉分:");
            tv_balance.setText("余额\n0.00");
            tv_diamons.setText("钻石\n0.00");
            tv_bond.setText("保证金\n0.00");
            iv_avatar.setImageResource(R.drawable.jmui_head_icon);
        }
    }

    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias(String name) {
        // EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
        String alias = name;


        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:


                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:


                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:


            }
            // ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getActivity(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}
