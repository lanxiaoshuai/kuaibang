package com.witkey.witkeyhelp.view.impl;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;

import org.angmarch.views.NiceSpinner;

/**
 * @author lingxu
 * @date 2019/7/12 14:18
 * @description 信息咨询
 */
public class ConsultActivity extends BaseActivity {
    private TextView tv_title_content; //咨询内容
    private EditText et_content;//咨询内容
    private ImageView iv_pic; //显示添加的图片
    private TextView btn_add_pic; //添加图片

    private TextView tv_title_date; //任务截止时间
    private NiceSpinner spin_date; //选择结束时间spinner

    private TextView tv_title_money; //悬赏金额
    private EditText et_money; //输入您的任务金额
    private RadioGroup rg_money_type; //金额类型
    private RadioButton rb_RMB; //人民币
    private RadioButton rb_diamonds; //钻石

    private TextView btn_open_more; //展开更多高级设置

    private TextView tv_title_title; //任务标题
    private EditText et_title; //输入您的任务关键词

    private TextView tv_title_isBargaining; //是否可以议价
    private RadioGroup rg_isBargaining;
    private RadioButton rb_isBargaining_true;
    private RadioButton rb_isBargaining_false;

    private TextView tv_title_contact; //联系方式
    private EditText et_contact; //输入您的联系方式

    private TextView tv_title_mission_num; //任务数量
    private NiceSpinner spin_mission_num;

    private TextView tv_title_isNeedBidding; //是否需要竞标
    private RadioGroup rg_isNeedBidding;
    private RadioButton rb_isNeedBidding_true;
    private RadioButton rb_isNeedBidding_false;

    // TODO: 2019/7/12 点击操作
    private TextView tv_title_isNeedBond; //是否需要保证金,点击操作
    private RadioGroup rg_isNeedBond;
    private RadioButton rb_isNeedBond_true;
    private RadioButton rb_isNeedBond_false;

    private Button btn_save;//暂时保存
    private Button btn_publish;//立即发布

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_consult);
        initViews();
        setIncludeTitle("信息咨询");
    }

    private void initViews() {

    }


}
