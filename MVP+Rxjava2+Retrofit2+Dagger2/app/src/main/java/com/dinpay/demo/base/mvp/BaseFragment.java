package com.dinpay.demo.base.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import dagger.android.support.AndroidSupportInjection;

/**
 * @author yzh-t105
 * @time 2018/12/20 09:53
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    private View parentView;
    protected FragmentActivity activity;
    public P mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //AndroidInjection.inject(this);
        AndroidSupportInjection.inject(this);//diagger2(android.support.v4.app.Fragment 使用AndroidSupportInjection)
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();//创建presenter
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getActivity();
        return parentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initData(savedInstanceState,parentView);
    }



    @LayoutRes
    public abstract int getLayoutResId();
    //初始化views
    public abstract void initData(Bundle state,View parentView);
    public abstract P createPresenter();

    @Override
    public void onDestroy() {
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity=null;
    }

    /**
     *模拟显示加载对话框
     */
    @Override
    public void showLoading() {
        Toast.makeText(activity, "加载中", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void hideLoading() {
        Toast.makeText(activity, "加载结束", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

}
