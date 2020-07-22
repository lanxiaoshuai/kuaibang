package com.witkey.witkeyhelp.view.impl.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;


import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.Interfacecallback.Modifystate;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.adapter.MyDragAdapter;
import com.witkey.witkeyhelp.adapter.ViewCiclerPagerAdapter;
import com.witkey.witkeyhelp.bean.CicleBean;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.dialog.UICustomDialog2;
import com.witkey.witkeyhelp.event.AttentionEvent;
import com.witkey.witkeyhelp.event.LoginEvent;
import com.witkey.witkeyhelp.model.IModel;
import com.witkey.witkeyhelp.model.util.Callback;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.services.DownloadServise;
import com.witkey.witkeyhelp.util.GPSIsOpenUtil;
import com.witkey.witkeyhelp.util.JsonUtils;
import com.witkey.witkeyhelp.util.ListDataSave;
import com.witkey.witkeyhelp.util.PventQuickClick;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.util.viewUtil.DialogUtil;
import com.witkey.witkeyhelp.view.impl.ActivityDiamond;
import com.witkey.witkeyhelp.view.impl.ActivityReleaseDiamonds;
import com.witkey.witkeyhelp.view.impl.ConsultActivity;
import com.witkey.witkeyhelp.view.impl.CreateCircleActivity;
import com.witkey.witkeyhelp.view.impl.DragActivity;
import com.witkey.witkeyhelp.view.impl.LoginActivity;
import com.witkey.witkeyhelp.view.impl.MissionDetailActivity;
import com.witkey.witkeyhelp.view.impl.SearchCirclesActivity;
import com.witkey.witkeyhelp.widget.PublishDialog;
import com.witkey.witkeyhelp.widget.ViewVisibilityOrGone;
import com.witkey.witkeyhelp.widget.drag.DragView;

import android.support.design.widget.TabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Egos on 2017/11/7.
 */

public class CircleFragment extends BaseFragment implements Modifystate {

    private final static String TAG = "CircleFragment";

    private final static int REQUEST_CHANGE = 1;

    private ViewPager container;
    private TabLayout tablayout;

    private List<CicleBean.ReturnObjectBean.RowsBean> undragLst;
    public List<CicleBean.ReturnObjectBean.RowsBean> selectLst;
    private List<CicleBean.ReturnObjectBean.RowsBean> unselectLst;

    private ViewCiclerPagerAdapter mViewAdapter;


    private List<Fragment> fragments;
    private PublishDialog publishDialog;
    private PopupWindow selectCircle;
    private TextView search_jump;
    private User user;

    private ProgressBar pb;

    private JSONObject jsonObject;
    private LinearLayout update_layout;
    private String downloadUrl;
    private Dialog relievepopupWindow;
    private ViewVisibilityOrGone viewVisibilityOrGone;
    private PopupWindow releaseTaskPopob;
    private ImageView release_btn;
    public Modifystate modify;


    public void setCallBack(Modifystate modifystate) {
        modify = modifystate;
    }

    @Override
    protected int getContentView() {
        return R.layout.circle_main;
    }

    @Override
    protected IPresenter[] getPresenters() {
        return new IPresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }


    @Override
    protected void initEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        update();
        popubWiodowTask();

        container = (ViewPager) findViewById(R.id.container);
        tablayout = (TabLayout) findViewById(R.id.pagerSlidingTabStrip);
        search_jump = (TextView) findViewById(R.id.search_jump);
        ImageView refresh_iamgevi = (ImageView) findViewById(R.id.refresh_iamgevi);

        ImageView fab = (ImageView) findViewById(R.id.fab);

        initData();
        CicleBean.ReturnObjectBean.RowsBean rowsBean = new CicleBean.ReturnObjectBean.RowsBean();
        rowsBean.setCircleId("0");
        rowsBean.setAbbreviation("全部");
        rowsBean.setCircleName("全部");
        CicleBean.ReturnObjectBean.RowsBean recommend = new CicleBean.ReturnObjectBean.RowsBean();

        recommend.setCircleId("1");
        recommend.setAbbreviation("活动");
        recommend.setCircleName("活动");
        undragLst.add(rowsBean);
        undragLst.add(recommend);
        selectLst.addAll(0, undragLst);
        for (int i = 0; i < selectLst.size(); i++) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ARG_TITLE, selectLst.get(i).getCircleId());
            fragment.setArguments(bundle);
            fragments.add(fragment);

        }

        mViewAdapter = new ViewCiclerPagerAdapter(getChildFragmentManager(), fragments, selectLst);
        container.setAdapter(mViewAdapter);
        tablayout.setupWithViewPager(container);

        user = SpUtils.getObject(getActivity(), "LOGIN");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                getReadydialog();


            }
        });
        refresh_iamgevi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewAdapter.getCurrentFragment().refreshTop();

            }
        });
        search_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                if (user == null) {
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    return;
                }
                Intent intent = new Intent(getActivity(), SearchCirclesActivity.class);
                startActivity(intent);
            }
        });
        attenTion();
        //实例化对象

    }


    @Override
    protected void initViewExceptPresenter() {

    }

    @Override
    protected void initWidget() {

    }

    private void attenTion() {
        User user = SpUtils.getObject(getActivity(), "LOGIN");
        if (user == null) {

            return;
        }
        MyAPP.getInstance().getApi().circleMyList(user.getUserId()).enqueue(new Callback(IModel.callback, "失物招领发布失败") {
            @Override
            public void getSuc(String body) {

                //   ToastUtils.showTestShort(getContext(), "获取关注的圈子成功");

                CicleBean beanFromJson = JsonUtils.getBeanFromJson(body, CicleBean.class);

                CicleBean.ReturnObjectBean returnObject = beanFromJson.getReturnObject();

                List<CicleBean.ReturnObjectBean.RowsBean> rows = returnObject.getRows();

                for (int i = 0; i < rows.size(); i++) {
                    PlaceholderFragment fragment = new PlaceholderFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_TITLE, rows.get(i).getCircleId());
                    fragment.setArguments(bundle);
                    fragments.add(fragment);
                }
                selectLst.addAll(rows);
                MyAPP.selectLst = selectLst;
                mViewAdapter.notifyDataSetChanged();

            }


        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DragActivity.EXTRA_UNDRAG_LIST, (Serializable) undragLst);
        outState.putSerializable(DragActivity.EXTRA_SELECT_LIST, (Serializable) selectLst);
        outState.putSerializable(DragActivity.EXTRA_UNSELECT_LIST, (Serializable) unselectLst);
    }


    private void initData() {
        undragLst = new ArrayList<>();
        selectLst = new ArrayList<>();
        unselectLst = new ArrayList<>();
        fragments = new ArrayList<>();


        createdailaog();
    }

    private static final String ARG_TITLE = "section_number";


    private void createdailaog() {
        release_btn = (ImageView) findViewById(R.id.release_btn);
        release_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                releaseTaskPopob.showAsDropDown(release_btn, 0, 0, Gravity.CENTER);
            }
        });


    }

    private void buryingPoint(String eventId) {
        MobclickAgent.onEvent(getActivity(), eventId);

    }

    private void startActivity(String s) {
        Intent i = new Intent(getActivity(), ConsultActivity.class);
        i.putExtra("EXTRA_PAGE_TYPE", s);
        startActivityForResult(i, 100);
    }


    DragView dragView;

    private MyDragAdapter mAdapter;
    private TextView edit_circle;
    private ListDataSave listDataSave;
    private boolean hetherwEdit;

    private void getReadydialog() {
        if (user == null) {
            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            return;
        }
        unselectLst = new ArrayList<>();
        listDataSave = new ListDataSave(getContext(), "BROWSE");
        unselectLst = listDataSave.getCicleList(user.getUserName());
        if (unselectLst.size() > 9) {
            for (int i = 9; i < unselectLst.size(); i++) {
                unselectLst.remove(i);
            }
        }
        for (int i = 0; i < selectLst.size(); i++) {
            String id = selectLst.get(i).getCircleId();
            for (int j = 0; j < unselectLst.size(); j++) {
                if (unselectLst.get(j).getCircleId().equals(id)) {
                    unselectLst.remove(j);

                }
            }
        }
        View view = getLayoutInflater().inflate(R.layout.activity_drag, null, false);//引入弹窗布局
        //   if (selectCircle == null) {
        selectCircle = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        //  }
        //设置进出动画
        selectCircle.setAnimationStyle(R.style.anim_pop_bottombar);

        //引入依附的布局
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        search_jump = (TextView) findViewById(R.id.search_jump);

        dragView = (DragView) view.findViewById(R.id.dragView);

        dragView.setCallBack(this);

        edit_circle = view.findViewById(R.id.edit_circle);
        view.findViewById(R.id.colse_cicle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCircle.dismiss();
            }
        });

        mAdapter = new MyDragAdapter(getContext(), undragLst, selectLst, unselectLst, fragments);
        dragView.setAdapter(mAdapter);
        edit_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hetherwEdit) {
                    hetherwEdit = false;
                    edit_circle.setText("编辑");
                    edit_circle.setTextColor(getResources().getColor(R.color.font_color));
                    listDataSave.setCicleList(user.getUserName(), unselectLst);
                } else {
                    hetherwEdit = true;
                    edit_circle.setText("完成");
                    edit_circle.setTextColor(getResources().getColor(R.color.price_bli_colorliu));
                }
                dragView.getHetherwEdit(hetherwEdit);

                dragView.editCircle(hetherwEdit);
            }
        });

        selectCircle.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                MyAPP.selectLst = selectLst;
                mViewAdapter.notifyDataSetChanged();

                List<String> miciler = new ArrayList<>();
                for (int i = 2; i < selectLst.size(); i++) {
                    miciler.add(selectLst.get(i).getCircleId());
                }
                MyAPP.getInstance().getApi().circleAttention(user.getUserId(), miciler).enqueue(new Callback(IModel.callback, "替换圈子失败") {
                    @Override
                    public void getSuc(String body) {

                    }
                });
            }
        });

        selectCircle.showAsDropDown(search_jump);
    }


    @Override
    public void Modifiable() {
        hetherwEdit = true;
        edit_circle.setText("完成");
        edit_circle.setTextColor(getResources().getColor(R.color.price_bli_colorliu));
    }

    @Override
    public void pageBack(int index) {

    }

    private void update() {
        MyAPP.getInstance().getApi().versionUpdate().enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    jsonObject = new JSONObject(response.body());
                    String constraint = jsonObject.getString("updateStatus");
                    String versionCode = jsonObject.getString("versionCode");
                    //下载地址
                    downloadUrl = jsonObject.getString("downloadUrl");

                    if (Integer.parseInt(versionCode) > getVersionCode(getContext())) {
                        task_submission_fail(getContext(), jsonObject.getString("versionName"), jsonObject.getString("apkSize"), jsonObject.getString("modifyContent"), jsonObject.getString("uploadTime"), jsonObject.getString("downloadUrl"), constraint);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }


        });
    }

    private void downLoadCs(String downloadUrl) {  //下载调用

        Intent intent = new Intent(getActivity(), DownloadServise.class);
        intent.putExtra(DownloadServise.BUNDLE_KEY_DOWNLOAD_URL, downloadUrl);
        isBindService = getActivity().bindService(intent, conn, getActivity().BIND_AUTO_CREATE); //绑定服务即开始下载 调用onBind()
    }


    private boolean isBindService;
    private ServiceConnection conn = new ServiceConnection() { //通过ServiceConnection间接可以拿到某项服务对象

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadServise.DownloadBinder binder = (DownloadServise.DownloadBinder) service;
            final DownloadServise downloadServise = binder.getService();

            //接口回调，下载进度
            downloadServise.setOnProgressListener(new DownloadServise.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {
                    if ((int) (fraction * 100) >= 100) {

                        pb.setProgress(100);
                    } else {

                        pb.setProgress((int) (fraction * 100));
                    }


                    //判断是否真的下载完成进行安装了，以及是否注册绑定过服务
                    if (fraction == downloadServise.UNBIND_SERVICE && isBindService) {

                        getActivity().unbindService(conn);
                        isBindService = false;
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public static boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {

        for (String permission : permissionsREAD) {
            if (lacksPermission(mContexts, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 照相权限  自己可以添加需要判断的权限
     */
    public static String[] permissionsREADWrite = {

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

    private void task_submission_fail(Context context, String versioncontent, String siezecontent, String updatelogu, String timeupdate, final String downloadUrl, String constraint) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popub_upgrade, null, false);//引入弹窗布局

        ImageView expression = vPopupWindow.findViewById(R.id.expression);

        TextView version_name = vPopupWindow.findViewById(R.id.version_name);
        TextView packagesize = vPopupWindow.findViewById(R.id.packagesize);

        TextView update_content = vPopupWindow.findViewById(R.id.update_content);

        TextView not_new = vPopupWindow.findViewById(R.id.not_new);
        pb = vPopupWindow.findViewById(R.id.pb);

        final TextView update_text = vPopupWindow.findViewById(R.id.update_text);

        TextView updatetime = vPopupWindow.findViewById(R.id.updatetime);
        View middle_line = vPopupWindow.findViewById(R.id.middle_line);
        update_layout = vPopupWindow.findViewById(R.id.update_layout);
        updatetime.setText("更新时间 :   " + timeupdate);
        version_name.setText("版本 :   " + versioncontent);
        packagesize.setText("包大小 :   " + siezecontent + "MB");
        update_content.setText(updatelogu);

        relievepopupWindow = new Dialog(getActivity(), R.style.CustomDialog);

        if (constraint.equals("1")) {
            middle_line.setVisibility(View.GONE);
            not_new.setVisibility(View.GONE);
            relievepopupWindow.setCancelable(false); // false 禁止返回键 和下边的touch必须同时使用
            relievepopupWindow.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失

            relievepopupWindow.setCanceledOnTouchOutside(false); // true点击dialog外消失，false不消失


            update_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // new DownloadUtils(getActivity(), downloadUrl, "kuaibang.apk");


                    if (lacksPermissions(getContext(), permissionsREADWrite)) {//读写权限
                        requestPermissions(permissionsREADWrite, 1);
                    } else {
                        downLoadCs(downloadUrl);
                        update_layout.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                    }


                }
            });


            not_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showTestShort(getActivity(), "此次更新为强制更新");
                }
            });
            expression.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showTestShort(getActivity(), "此次更新为强制更新");
                }
            });
        } else {

            update_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (lacksPermissions(getContext(), permissionsREADWrite)) {//读写权限
                        requestPermissions(permissionsREADWrite, 1);
                    } else {
                        downLoadCs(downloadUrl);
                        update_layout.setVisibility(View.GONE);
                        pb.setVisibility(View.VISIBLE);
                    }
                }
            });

            not_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relievepopupWindow.dismiss();
                }
            });
            expression.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relievepopupWindow.dismiss();
                }
            });
        }


        // 添加动态内容
        relievepopupWindow.setContentView(vPopupWindow, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        relievepopupWindow.show();


    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {


                    break;
                }
            }
            downLoadCs(downloadUrl);
            update_layout.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
        }
        if (requestCode == 0) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == -1) {

                    // 权限被拒绝，弹出dialog 提示去开启权限
                    //         showPermissions();
                    break;
                }
            }

        }

    }

    // 通过注解方式响应事件
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1, sticky = false)
    public void onEvent(CicleBean.ReturnObjectBean.RowsBean event) {
        // 响应事件
        boolean attention = event.isSelected();
        boolean addTo = false;
        if (attention) {
            for (int i = 0; i < selectLst.size(); i++) {
                if (event.getCircleId().equals(selectLst.get(i).getCircleId())) {
                    addTo = true;
                    break;
                }
            }
            if (!addTo) {
                selectLst.add(event);
                PlaceholderFragment fragment = new PlaceholderFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARG_TITLE, event.getCircleId());
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }

        } else {
            for (int i = 0; i < selectLst.size(); i++) {
                if (event.getCircleId().equals(selectLst.get(i).getCircleId())) {
                    selectLst.remove(i);
                    fragments.remove(i);
                    break;
                }
            }
        }
        MyAPP.selectLst = selectLst;
        mViewAdapter.notifyDataSetChanged();
        container.setCurrentItem(0);

    }


    private void popubWiodowTask() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.popubpublish_task, null, false);//引入弹窗布局

        TextView informationconsulting = vPopupWindow.findViewById(R.id.informationconsulting);

        TextView reward_help = vPopupWindow.findViewById(R.id.reward_help);

        TextView dynamic = vPopupWindow.findViewById(R.id.dynamic);

        informationconsulting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }
                releaseTaskPopob.dismiss();
                buryingPoint("information");
                startActivity("1");


            }
        });
        reward_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PventQuickClick.isFastDoubleClick()) {
                    return;
                }

                releaseTaskPopob.dismiss();
                buryingPoint("rewardhelp");
                startActivity("2");


            }
        });

        dynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseTaskPopob.dismiss();
                startActivity("5");

            }
        });
        releaseTaskPopob = new PopupWindow(vPopupWindow, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        releaseTaskPopob.setBackgroundDrawable(new BitmapDrawable());

        releaseTaskPopob.setOutsideTouchable(true);

        releaseTaskPopob.setFocusable(true);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            modify.Modifiable();
        }
    }
}
