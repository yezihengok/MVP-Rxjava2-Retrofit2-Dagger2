package com.demo.mvp.base;

/**
 *
 * 先封装View 层  想象一下每个View的共性。把每个子类都会用到的方法放在这里
 * @author yzh-t105
 * @time 2018/12/20 09:23
 */
public interface BaseView {
    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showToast(String message);
}
