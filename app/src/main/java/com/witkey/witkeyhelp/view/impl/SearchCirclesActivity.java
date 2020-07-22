package com.witkey.witkeyhelp.view.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.util.FloatBtnUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.ListDataSave;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.view.impl.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.NO_ID;

/**
 * Created by jie on 2020/6/13.
 */

public class SearchCirclesActivity extends BaseActivity {

    private EditText circle_search;
    private ImageView search_edit_delete;
    private LabelsView ecommended_circles;
    private List<CicleBean.ReturnObjectBean.RowsBean> labList;
    private static final String ARG_TITLE = "section_number";
    private List<CicleBean.ReturnObjectBean.RowsBean> history;
    private List<String> searchHistory;
    private ListDataSave browseDataSave;
    private ListDataSave listDataSave;
    private TextView history_search;
    private LabelsView cloud_tag;
    private TextView create_circle;
    private List<CicleBean.ReturnObjectBean.RowsBean> searchlist;
    private RelativeLayout on_cicle;
    private TextView to_create;
    private TextView no_name;
    private ImageView return_icon;
    private LinearLayout lin_root;
    private TextView circle_recommendation;

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.searchcircles_activity);
        circle_search = findViewById(R.id.circle_search);
        ecommended_circles = findViewById(R.id.ecommended_circles);

        history_search = findViewById(R.id.history_search);
        circle_recommendation = findViewById(R.id.circle_recommendation);

        TextView search_for = findViewById(R.id.search_for);


        cloud_tag = findViewById(R.id.cloud_tag);
        create_circle = findViewById(R.id.create_circle);

        on_cicle = findViewById(R.id.on_cicle);
        to_create = findViewById(R.id.to_create);
        no_name = findViewById(R.id.no_name);
        return_icon = findViewById(R.id.return_icon);

        labList = new ArrayList<>();

        search_edit_delete = findViewById(R.id.search_edit_delete);
        to_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchCirclesActivity.this, CreateCircleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        return_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHistory.clear();
                listDataSave.setSearchList("SEARCHHISTORY", searchHistory);
                finish();
            }
        });
        create_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchCirclesActivity.this, CreateCircleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        circle_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    search_edit_delete.setVisibility(View.VISIBLE);
                } else {
                    search_edit_delete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        search_edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                circle_search.setText("");
            }
        });
        ecommended_circles.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                Intent intent = new Intent(SearchCirclesActivity.this, CircleActivity.class);
                intent.putExtra("circleId", labList.get(position).getCircleId());
                intent.putExtra(ARG_TITLE, labList.get(position));

                startActivity(intent);
                if (history.size() == 0) {
                    history.add(labList.get(position));
                } else {
                    String cid = "";
                    for (int i = 0; i < history.size(); i++) {
                        String circleId = history.get(i).getCircleId();
                        if (circleId.equals(labList.get(position).getCircleId())) {
                            cid = circleId;
                            CicleBean.ReturnObjectBean.RowsBean rowsBean = history.get(i);
                            history.remove(rowsBean);

                            history.add(0, rowsBean);

                            break;
                        }
                    }
                    if ("".equals(cid) || cid == null) {
                        history.add(0, labList.get(position));
                    }
                }
                browseDataSave.setCicleList(user.getUserName(), history);

                if (searchHistory.size() == 0) {
                    searchHistory.add(labList.get(position).getCircleName());
                } else {
                    String name = "";
                    for (int i = 0; i < searchHistory.size(); i++) {

                        if (searchHistory.get(i).equals(labList.get(position).getCircleName())) {
                            String circleName = searchHistory.get(i);
                            name = circleName;
                            searchHistory.remove(circleName);
                            //   Collections.reverse(searchHistory);
                            searchHistory.add(0, circleName);
                            break;
                        }
                    }
                    if ("".equals(name) || name == null) {

                        searchHistory.add(0, labList.get(position).getCircleName());
                    }
                }
                cloud_tag.setVisibility(View.VISIBLE);
                history_search.setVisibility(View.VISIBLE);

                listDataSave.setSearchList("SEARCHHISTORY", searchHistory);
                finish();
            }
        });
        ecommendedrCircles();


        listDataSave = new ListDataSave(this, "CICLE");
        browseDataSave = new ListDataSave(this, "BROWSE");
        history = browseDataSave.getCicleList(user.getUserName());


        searchHistory = listDataSave.getSearchList("SEARCHHISTORY");
        if (history == null) {
            history = new ArrayList<>();

        }
        if (searchHistory == null) {
            searchHistory = new ArrayList<>();

        }
        if (searchHistory.size() == 0) {
            cloud_tag.setVisibility(View.GONE);
            history_search.setVisibility(View.GONE);
        }
        if (searchHistory.size() > 9) {
            for (int i = 9; i < searchHistory.size(); i++) {
                searchHistory.remove(i);
            }
        }
        cloud_tag.setLabels(searchHistory);
        cloud_tag.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                circle_search.setText(searchHistory.get(position));
                String name = searchHistory.get(position);
                searchCircles();
                closeKeyboard();
                searchHistory.remove(name);
                searchHistory.add(0, name);
                listDataSave.setSearchList("SEARCHHISTORY", searchHistory);

            }
        });
        search_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circle_search.getText().toString().equals("")) {
                    return;
                }
                if (searchHistory.size() == 0) {
                    searchHistory.add(circle_search.getText().toString());
                } else {
                    String name = "";
                    for (int i = 0; i < searchHistory.size(); i++) {

                        if (searchHistory.get(i).equals(circle_search.getText().toString())) {
                            String circleName = searchHistory.get(i);
                            name = circleName;
                            searchHistory.remove(circleName);
                            //  Collections.reverse(searchHistory);
                            searchHistory.add(0, circleName);
                            break;
                        }
                    }
                    if ("".equals(name) || name == null) {
                        searchHistory.add(0, circle_search.getText().toString());
                    }
                }
                searchCircles();
                closeKeyboard();

                listDataSave.setSearchList("SEARCHHISTORY", searchHistory);
                history_search.setVisibility(View.VISIBLE);
                cloud_tag.setVisibility(View.VISIBLE);

            }
        });
        circle_search.post(new Runnable() {
            @Override
            public void run() {
                circle_search.requestFocus();
            }
        });

        circle_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (circle_search.getText().toString().equals("")) {
                    return true;
                }
                if (searchHistory.size() == 0) {
                    searchHistory.add(circle_search.getText().toString());
                } else {
                    String name = "";
                    for (int i = 0; i < searchHistory.size(); i++) {
                        if (searchHistory.get(i).equals(circle_search.getText().toString())) {
                            String circleName = searchHistory.get(i);
                            name = circleName;
                            searchHistory.remove(circleName);
                            //  Collections.reverse(searchHistory);
                            searchHistory.add(0, circleName);
                            break;
                        }
                    }
                    if ("".equals(name) || name == null) {

                        searchHistory.add(0, circle_search.getText().toString());
                    }
                }

                history_search.setVisibility(View.VISIBLE);
                cloud_tag.setVisibility(View.VISIBLE);
                searchCircles();
                closeKeyboard();
                listDataSave.setSearchList("SEARCHHISTORY", searchHistory);
                return true;
            }
        });
        //initFloatBtn();

    }

    private void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void ecommendedrCircles() {
        MyAPP.getInstance().getApi().recommendCircle(1).enqueue(new Callback(IModel.callback, "推荐圈子失败") {
            @Override
            public void getSuc(String body) {
                CicleBean beanFromJson = JsonUtils.getBeanFromJson(body, CicleBean.class);
                labList.clear();
                labList.addAll(beanFromJson.getReturnObject().getRows());
                ecommended_circles.setLabels(labList, new LabelsView.LabelTextProvider<CicleBean.ReturnObjectBean.RowsBean>() {
                    @Override
                    public CharSequence getLabelText(TextView label, int position, CicleBean.ReturnObjectBean.RowsBean data) {
                        return data.getCircleName();
                    }
                });
            }


        });
    }

    private void searchCircles() {
        MyAPP.getInstance().getApi().circleSearch(circle_search.getText().toString()).enqueue(new Callback(IModel.callback, "推荐圈子失败") {
            @Override
            public void getSuc(String body) {
                searchlist = new ArrayList<>();
                CicleBean beanFromJson = JsonUtils.getBeanFromJson(body, CicleBean.class);
                searchlist.addAll(beanFromJson.getReturnObject().getRows());
                if (beanFromJson.getReturnObject().getRows().size() == 0) {
                    history_search.setVisibility(View.GONE);
                    cloud_tag.setVisibility(View.GONE);
                    circle_recommendation.setVisibility(View.GONE);
                    ecommended_circles.setVisibility(View.GONE);
                    on_cicle.setVisibility(View.VISIBLE);
                    to_create.setText("去创建“" + circle_search.getText().toString() + "”圈");
                    no_name.setText("暂无“" + circle_search.getText().toString() + "”相关内容");
                } else {
                    history_search.setVisibility(View.VISIBLE);
                    cloud_tag.setVisibility(View.VISIBLE);
                    circle_recommendation.setVisibility(View.VISIBLE);
                    ecommended_circles.setVisibility(View.VISIBLE);
                    on_cicle.setVisibility(View.GONE);
                    Intent intent = new Intent(SearchCirclesActivity.this, ActivityListCicle.class);
                    intent.putExtra("CIRCLELIST", (Serializable) searchlist);
                    intent.putExtra("content", circle_search.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }


        });
    }

    private void initFloatBtn() {
        FloatBtnUtil floatBtnUtil = new FloatBtnUtil(this);
        // LinearLayout lin_bottom = (LinearLayout) this.findViewById(R.id.create_circle);
        LinearLayout lin_root = (LinearLayout) this.findViewById(R.id.lin_root);
        floatBtnUtil.setFloatView(lin_root, create_circle);
    }

    @Override
    protected boolean isGetUser() {
        return true;
    }

    private static final String NAVIGATION = "navigationBarBackground";

    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    public static boolean isNavigationBarExist(@NonNull Activity activity) {
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId() != NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }


}
