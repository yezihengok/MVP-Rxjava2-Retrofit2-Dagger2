package com.demo.mvp.base;

/**
 *
 * 每个Presenter.都需要下面几个方法。 抽取一下
 * @author yzh-t105
 * @time 2018/12/20 09:27
 */
public interface IPresenter<T extends BaseView>{
    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 销毁
     */
    void onDestroy();

    /**
     * 绑定View
     */
    void attachView(T view);

    T getView();

}
