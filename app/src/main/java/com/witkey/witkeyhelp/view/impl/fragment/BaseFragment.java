package com.witkey.witkeyhelp.view.impl.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.witkey.witkeyhelp.MyAPP;
import com.witkey.witkeyhelp.R;
import com.witkey.witkeyhelp.bean.User;
import com.witkey.witkeyhelp.presenter.IPresenter;
import com.witkey.witkeyhelp.util.DialogCreator;
import com.witkey.witkeyhelp.util.FileHelper;
import com.witkey.witkeyhelp.util.SharePreferenceManager;
import com.witkey.witkeyhelp.util.SpUtils;
import com.witkey.witkeyhelp.util.ToastUtils;
import com.witkey.witkeyhelp.view.IView;
import com.witkey.witkeyhelp.view.impl.LoginActivity;

import com.witkey.witkeyhelp.view.impl.base.BaseMessageToActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;


public abstract class BaseFragment extends Fragment implements IView {
    /**
     * 根View
     **/
    protected View rootView;
    //    private ReplaceViewHelper replaceViewHelper;
    private View targetView;
    protected String TAG = "llx";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * 获取布局资源
     **/
    protected abstract int getContentView();

    private Set<IPresenter> mAllPresenters = new HashSet<>(1);

    /**
     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
     */
    protected abstract IPresenter[] getPresenters();

    /**
     * 初始化presenters
     */
    protected abstract void onInitPresenters();

    /**
     * 事件监听
     */
    protected abstract void initEvent();

    private void addPresenters() {
        IPresenter[] presenters = getPresenters();
        if (presenters != null) {
            for (int i = 0; i < presenters.length; i++) {
                mAllPresenters.add(presenters[i]);
            }
        }
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = setRootView();
        //避免重复加载

        if (rootView == null) {
            rootView = inflater.inflate(getContentView(), container, false);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        View view = rootView.findViewById(R.id.tvBack);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
        //实现控件
        initWidget();
        initEvent();
        addPresenters();
        onInitPresenters();
        initViewExceptPresenter();
        return rootView;
    }

    protected float mDensity;
    protected int mDensityDpi;
    protected int mWidth;
    protected int mHeight;
    protected float mRatio;
    protected int mAvatarSize;
    private Context mContext;
    private boolean mVisiable = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        JMessageClient.registerEventReceiver(this);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);
    }
    private boolean mFirstInit = true;
    @Override
    public void onResume() {
        Log.d(TAG, "onResume----" + this.toString());
        super.onResume();
        //若首次初始化,默认可见并开启友盟统计
        if (mFirstInit) {
            mVisiable = true;
            mFirstInit = false;
            MobclickAgent.onPageStart(getClass().getSimpleName());
            return;
        }

        //若当前界面可见,调用友盟开启跳转统计
        if (mVisiable) {
            MobclickAgent.onPageStart(getClass().getSimpleName());
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause----" + this.toString());
        super.onPause();
        //若当前界面可见,调用友盟结束跳转统计
        if (mVisiable) {
            MobclickAgent.onPageEnd("MainScreen");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mVisiable = true;
            MobclickAgent.onPageStart(getClass().getSimpleName());
        } else {
            mVisiable = false;
            MobclickAgent.onPageEnd(getClass().getSimpleName());
        }
    }
    //上传数据
//    private RequestBean bean;
//
//    protected RequestBean getRequestBean() {
//        if (bean == null) {
//            String name = "page--ActivityName=" + getActivity().getClass().getName() + ",fragmentName=" + getFragmentName();
//            bean = new RequestBean(name);
//        }
//        return bean;
//    }
//    protected abstract String getFragmentName();

    /**
     * 初始化presenter结束进行的操作
     */
    protected abstract void initViewExceptPresenter();

    //为想设置不重复加载的界面准备,重写返回null将重复接在此fragment界面
    protected View setRootView() {
        return rootView;
    }

//    public void setShowAdd(final IAddCallback addCallback) {
//        RelativeLayout tvAdd = (RelativeLayout) findViewById(R.id.tvAdd);
//        if (tvAdd != null) {
//            tvAdd.setVisibility(View.VISIBLE);
//            tvAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "onClick: ");
//                    addCallback.onAdd();
//                }
//            });
//        }
//    }

    /**
     * 实例化view
     */
    protected abstract void initWidget();

    /**
     * 根据id查找View
     **/
    protected View findViewById(int id) {
        return rootView.findViewById(id);
    }

    /**
     * 获取FragmentManager
     **/
    protected FragmentManager getSFM() {
        return getActivity().getSupportFragmentManager();
    }

    Toolbar toolbar;

    /**
     * 设置标题
     */
    public void setIncludeTitle(String title) {
        TextView tv = (TextView) findViewById(R.id.include_title);
        if (tv != null) {
            tv.setText(title);
        }
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            toolbar.setName(title);
//            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        }
    }

    private Dialog dialog;

//    @Override
//    public void onDestroy() {
//
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//
//        super.onDestroy();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JMessageClient.unRegisterEventReceiver(this);
        EventBus.getDefault().unregister(this);
    }


//    public void onEventMainThread(LoginStateChangeEvent event) {
//        Log.e("tag", "136236");
//        final LoginStateChangeEvent.Reason reason = event.getReason();
//        Log.e("tag", reason.toString());
//        UserInfo myInfo = event.getMyInfo();
//        if (myInfo != null) {
//            String path;
//            File avatar = myInfo.getAvatarFile();
//            if (avatar != null && avatar.exists()) {
//                path = avatar.getAbsolutePath();
//            } else {
//                path = FileHelper.getUserAvatarPath(myInfo.getUserName());
//            }
//            SharePreferenceManager.setCachedUsername(myInfo.getUserName());
//            SharePreferenceManager.setCachedAvatarPath(path);
//            JMessageClient.logout();
//        }
//        switch (reason) {
//            case user_logout:
//                View.OnClickListener listener = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (v.getId()) {
//                            case R.id.jmui_cancel_btn:
//                                dialog.dismiss();
//                                JMessageClient.logout();
//                                MyAPP.getInstance().exit();
//                                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                                intent.putExtra("type", "0");
//                                startActivity(intent);
//                                SpUtils.putObject(getActivity(), "LOGIN", null);
//                                getActivity().finish();
//                                break;
//
//
//                            case R.id.jmui_commit_btn:
//                                User user = SpUtils.getObject(getActivity(), "LOGIN");
//                                JMessageClient.login(user.getUserName(), "123456", new BasicCallback() {
//                                    @Override
//                                    public void gotResult(int responseCode, String responseMessage) {
//                                        if (responseCode == 0) {
//                                            ToastUtils.showTestShort(getContext(), "登录成功");
//                                            dialog.dismiss();
//                                        } else {
//                                            ToastUtils.showTestShort(getContext(), "登录失败，请重新登录");
//                                            JMessageClient.logout();
//                                            MyAPP.getInstance().exit();
//                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
//                                            intent.putExtra("type", "0");
//                                            startActivity(intent);
//                                            SpUtils.putObject(getActivity(), "LOGIN", null);
//
//                                            getActivity().finish();
//                                        }
//                                    }
//                                });
//                                dialog.dismiss();
//                                break;
//                        }
//                    }
//                };
//                dialog = DialogCreator.createLogoutStatusDialog(getActivity(), "您的账号在其他设备上登陆", listener);
//                dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.setCancelable(false);
//                dialog.show();
////                break;
//            case user_password_change:
//                Intent intent = new Intent(mContext, LoginActivity.class);
//                startActivity(intent);
//                break;
//        }
//    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ReplaceView: 覆盖上errorView
     */
//    protected void showErrorView() {
//        if (replaceViewHelper != null) {
//            //删除refreshView
//            removeOtherView();
//            //显示errorView
//            replaceViewHelper.toReplaceView(targetView, R.layout.layout_error);
//            isMainView = false;
//            //刷新操作
//            replaceViewHelper.getView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //判断是否联网,联网才进行刷新
//                    if (MyAPP.getInstance().isNetContect()) {
//                        //错误时
//                        startRefresh(targetView, callback);
//                    } else {
//                        Toast("请检查网络哦~", 3);
//                    }
//                }
//            });
//        }
//    }

    /**
     * 当前是否为原本的view
     */
//    private boolean isMainView = true;

//    private BaseActivity.IIsDefaultCallback callback;

    /**
     * ReplaceView: 开启刷新
     *
     * @param targetView 要覆盖的view
     */
//    protected void startRefresh(View targetView, BaseActivity.IIsDefaultCallback callback) {
//        if (targetView != null) {
//            this.callback = callback;
//            this.targetView = targetView;
//            if (targetView != null) {
//                //刷新操作
//                if (replaceViewHelper == null) {
//                    replaceViewHelper = new ReplaceViewHelper(getActivity());
//                }
//                if (!isMainView) {
//                    removeOtherView();
//                }
//                replaceViewHelper.toReplaceView(targetView, R.layout.layout_refresh);
//                callback.refreshWork();
//                isMainView = false;
//            }
//        }
//    }

    /**
     * 成功后删除
     * ReplaceView: 删除覆盖的View
     */
//    protected void removeOtherView() {
//        if (replaceViewHelper != null) {
//            replaceViewHelper.removeView();
//            isMainView = true;
//        }
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onError(String error) {
//        DialogUtil.dismissProgress();
//        if (!"请确认网络已连接~~".equals(error)) {
//            Log.d(TAG, "onError: " + error);
//            Error.showError(error, getActivity());
//        }
//        // ReplaceView: 进行覆盖errorView
//        if (callback != null) {
//            if (callback.isDefaultError()) {
//                showErrorView();
//            }
//        }
    }

    //封装通用toast方法
//    protected void Toast(String info, int state) {
//        //0 不显示 1 success 2 fail 3 alert
//        ToastUtils.showShort(getActivity(), info, state);
//    }

    public View getTargetView() {
        return targetView;
    }


}
