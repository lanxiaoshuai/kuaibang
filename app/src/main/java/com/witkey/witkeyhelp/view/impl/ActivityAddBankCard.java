package com.witkey.witkeyhelp.view.impl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.BankAdapter;
import com.witkey.witkeyhelp.adapter.BankListAdapter;
import com.witkey.witkeyhelp.bean.AllCardBean;

import com.witkey.witkeyhelp.bean.MyCardBean;
import com.witkey.witkeyhelp.bean.PagingResponse;
import com.witkey.witkeyhelp.bean.SystemMessageBean;
import com.witkey.witkeyhelp.model.api.Api;
import com.witkey.witkeyhelp.presenter.AmountMoneyPresenter;
import com.witkey.witkeyhelp.presenter.IMissionDetailPresenter;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.presenter.impl.AmountMoneyPresenterImpl;
import com.witkey.witkeyhelp.util.JSONUtil;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.AmountMoneyView;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;
import com.witkey.witkeyhelp.view.impl.base.InitPresenterBaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jie on 2019/12/7.
 */

public class ActivityAddBankCard extends InitPresenterBaseActivity implements AmountMoneyView, View.OnClickListener {

    private EditText cardname;
    private EditText cardnumber;
    private EditText subbranch;
    private RelativeLayout affiliated_bank;
    private ImageView gorightback;
    private TextView bank_name;
    private Button determine;
    private ImageView colse_add;
    private LinearLayout add_back_list;

    @Override
    protected void initWidget() {

        initview();
        addTextWear(cardname);
        addTextWear(cardnumber);
        addTextWear(subbranch);
    }

    private void addTextWear(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (cardnumber.getText().toString().length() > 0 && subbranch.getText().toString().length() > 0 && cardname.getText().toString().length() > 0 && bank_name.getText().toString().length() > 0) {

                    determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                } else {
                    determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.add_bank_card;
    }

    @Override
    protected void initEvent() {

    }

    private void initview() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!isChinese(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        cardname = findViewById(R.id.cardname);

        cardname.post(new Runnable() {
            @Override
            public void run() {
                cardname.requestFocus();
            }
        });
        cardname.setFilters(new InputFilter[]{filter});
        cardnumber = findViewById(R.id.cardnumber);

        subbranch = findViewById(R.id.subbranch);
        subbranch.setFilters(new InputFilter[]{filter});
        affiliated_bank = findViewById(R.id.affiliated_bank);

        gorightback = findViewById(R.id.gorightback);

        bank_name = findViewById(R.id.bank_name);

        determine = findViewById(R.id.determine);

        affiliated_bank.setOnClickListener(this);

        determine.setOnClickListener(this);
        setIncludeTitle("添加银行卡");

        showAnimation();

    }


    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.affiliated_bank:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                addBackground();
                View parentView = LayoutInflater.from(this).inflate(R.layout.recharge_balance, null);
                popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);

                break;
            case R.id.determine:
                if (PventQuickClick.isFastDoubleClick()) {
                    break;
                }
                if ("".equals(cardname.getText().toString())) {
                    ToastUtils.showTestShort(this, "请输入持卡人姓名");
                    return;
                }
                if ("".equals(cardnumber.getText().toString())) {
                    ToastUtils.showTestShort(this, "请输入银行卡号");
                    return;
                } else if (cardnumber.getText().toString().length() < 16) {
                    ToastUtils.showTestShort(this, "请输入完整的银行卡号");
                    return;
                }
                if ("".equals(bank_name.getText().toString())) {
                    ToastUtils.showTestShort(this, "请输入所属银行");
                    return;
                }
                if ("".equals(subbranch.getText().toString())) {
                    ToastUtils.showTestShort(this, "请输入支行");
                    return;
                }


//                i.putExtra("cardname", cardname.getText().toString());
//                i.putExtra("cardnumber", cardnumber.getText().toString());
//                i.putExtra("bank_name", bank_name.getText().toString());
//                i.putExtra("subbranch", subbranch.getText().toString());

                DialogUtil.showProgress(mActivity);
                presenter.addBankCard(user.getUserId() + "", bankId, cardname.getText().toString(), cardnumber.getText().toString(), subbranch.getText().toString());


                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //    goCardBack();
        presenter.getBankCard();
    }

    private PopupWindow popupWindow;

    private void addBackground() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;//调节透明度
        getWindow().setAttributes(lp);
        //dismiss时恢复原样

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);

                if (addcardname == null || "".equals(addcardname)) {

                        determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));

                } else {
                    bank_name.setText(addcardname);
                    gorightback.setVisibility(View.GONE);

                }
               // if(bank_name.getText().toString().length()>0){
                    if (cardnumber.getText().toString().length() > 0 && subbranch.getText().toString().length() > 0 && cardname.getText().toString().length() > 0 && bank_name.getText().toString().length() > 0) {
                        determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_true));
                    } else {
                        determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
                    }
//                }else {
//                    determine.setBackground(getResources().getDrawable(R.drawable.shape_gray_home_tixian));
//                }
            }
        });
    }

    private void showAnimation() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.dialog_bankcard, null, false);//引入弹窗布局
        if (popupWindow == null) {
            popupWindow = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        }

        //设置背景透明


        //设置进出动画
        popupWindow.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局

        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移


        dialogshowInfo(vPopupWindow);
    }

    private ListView mListView;
    private BankAdapter mAdapter;
    private List<AllCardBean> mDatas;
    private String addcardname;

    private String bankId;

    private void dialogshowInfo(View vPopupWindow) {

        mListView = (ListView) vPopupWindow.findViewById(R.id.bank_list);
        colse_add = (ImageView) vPopupWindow.findViewById(R.id.colse_add);
        add_back_list = (LinearLayout) vPopupWindow.findViewById(R.id.add_back_list);
        add_back_list.setVisibility(View.GONE);
        mDatas = new ArrayList<AllCardBean>();
        mAdapter = new BankAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bank_name.setText(mDatas.get(position).getBankName());
                bankId = mDatas.get(position).getId();
                gorightback.setVisibility(View.GONE);
                popupWindow.dismiss();
            }
        });
        colse_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void showBondDetails() {

    }

    @Override
    public void depositWithdrawalDetails() {

    }

    @Override
    public void bankCardInformation() {

    }

    @Override
    public void myBankcard(List<MyCardBean.ReturnObjectBean.RowsBean> data) {

    }

    @Override
    public void addBankCard() {

        ToastUtils.showTestShort(this, "绑定成功");
        Intent i = new Intent();
        setResult(1, i);
        finish();
    }

    @Override
    public void getBankCard(List<AllCardBean> mDatas) {

        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelBankCard() {

    }

    @Override
    public void onError(String error) {

        ToastUtils.showTestShort(this, error);
    }

    @Override
    public void cashWithdrawal(String data) {

    }

    private AmountMoneyPresenter presenter;

    @Override
    protected IPresenter[] getPresenters() {
        presenter = new AmountMoneyPresenterImpl();
        return new IPresenter[]{presenter};
    }

    @Override
    protected void onInitPresenters() {
        presenter.init(this);
    }

    @Override
    protected void initViewExceptPresenter() {
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }
}
