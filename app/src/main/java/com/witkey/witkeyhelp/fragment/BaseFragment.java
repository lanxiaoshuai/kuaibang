package com.witkey.witkeyhelp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.witkey.witkeyhelp.R;


public abstract class BaseFragment extends Fragment {
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

//    private Set<IPresenter> mAllPresenters = new HashSet<>(1);
//
//    /**
//     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
//     */
//    protected abstract IPresenter[] getPresenters();

    /**
     * 初始化presenters
     */
    protected abstract void onInitPresenters();

    /**
     * 事件监听
     */
    protected abstract void initEvent();

//    private void addPresenters() {
//        IPresenter[] presenters = getPresenters();
//        if (presenters != null) {
//            for (int i = 0; i < presenters.length; i++) {
//                mAllPresenters.add(presenters[i]);
//            }
//        }
//    }

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
//        View view = rootView.findViewById(R.id.tvBack);
//        if (view != null) {
//            view.setVisibility(View.GONE);
//        }
        //实现控件
        initWidght();
        initEvent();
//        addPresenters();
        onInitPresenters();
        initViewExceptPresenter();
        return rootView;
    }

    //上传数据
//    private RequestBean bean;

//    protected RequestBean getRequestBean() {
//        if (bean == null) {
//            String name = "page--ActivityName=" + getActivity().getClass().getName() + ",fragmentName=" + getFragmentName();
//            bean = new RequestBean(name);
//        }
//        return bean;
//    }

    protected abstract String getFragmentName();

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
    protected abstract void initWidght();

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
//            toolbar.setTitle(title);
//            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        }
    }

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
//    @Override
//    public void onError(String error) {
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
//    }

    //封装通用toast方法
//    protected void Toast(String info, int state) {
//        //0 不显示 1 success 2 fail 3 alert
//        ToastUtils.showShort(getActivity(), info, state);
//    }

    public View getTargetView() {
        return targetView;
    }


}
