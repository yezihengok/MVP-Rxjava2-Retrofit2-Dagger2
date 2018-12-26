package com.dinpay.demo.base.mvp;

/**
 *
 * BasePresenter：实现一些公共的方法
 * @author yzh-t105
 * @time 2018/12/20 09:31
 */
public abstract class BasePresenter<V extends BaseView> implements IPresenter<V> {
    //View 显示回显的接口
    protected V mView;

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
    }

    /**
     * 与View建立链接
     */
    @Override
    public void attachView(V view) {
        this.mView=view;
    }

    /**
     * 销毁对象，防止泄漏
     */
    @Override
    public void onDestroy() {
        this.mView = null;
    }

    @Override
    public V getView() {
        return mView;
    }

}
