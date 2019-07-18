package com.witkey.witkeyhelp.view.impl.fragment;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.presenter.IMeFragPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.MeFragPresenterImpl;
import com.witkey.witkeyhelp.view.IMeFragView;

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
    private TextView tv_share;
    private TextView tv_setting;
    private TextView tv_about;
    private TextView tv_quit;

    private IMeFragPresenter presenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    protected IPresenter[] getPresenters() {
        presenter=new MeFragPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initEvent() {
        iv_avatar.setOnClickListener(this);
        tv_balance.setOnClickListener(this);
        tv_diamons.setOnClickListener(this);
        tv_bond.setOnClickListener(this);
        tv_receive_mission.setOnClickListener(this);
        tv_publish_mission.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        tv_about.setOnClickListener(this);
        tv_quit.setOnClickListener(this);
    }

    @Override
    protected void initViewExceptPresenter() {

    }

    @Override
    protected void initWidght() {
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
        tv_share = (TextView) findViewById(R.id.tv_share);
        tv_setting = (TextView) findViewById(R.id.tv_setting);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_quit = (TextView) findViewById(R.id.tv_quit);
    }

    @Override
    public void showUser(User user) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar:
                break;
            case R.id.tv_balance:
                break;
            case R.id.tv_diamons:
                break;
            case R.id.tv_bond:
                break;
            case R.id.tv_receive_mission:
                break;
            case R.id.tv_publish_mission:
                break;
            case R.id.tv_collect:
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_setting:
                break;
            case R.id.tv_about:
                break;
            case R.id.tv_quit:
                break;

        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
