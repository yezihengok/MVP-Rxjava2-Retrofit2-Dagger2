package com.dinpay.demo.mvp.presenter;


import com.dinpay.demo.base.mvp.BasePresenter;
import com.dinpay.demo.bean.NewPic;
import com.dinpay.demo.mvp.contract.NewsListContract;
import com.dinpay.demo.mvp.model.NewsListModel;

import javax.inject.Inject;


/**
 * p层 处理具体业务逻辑 并持有M与V 的对象   解耦View，让activity看起简洁 不处理具体业务
 * @author yzh-t105
 * @time 2018/12/20 10:23
 */
public class NewsListPresenter extends BasePresenter<NewsListContract.NewsListViewImp> {
    //M

    //传统方式
//    private NewsListModel newsListModel;
//    public NewsListPresenter() {
//        newsListModel=new NewsListModel();
//    }

    //digger2方式
    NewsListModel newsListModel;
    @Inject
    public NewsListPresenter(NewsListModel newsListModel) {
        this.newsListModel=newsListModel;
    }
    /**
     * 模拟请求数据
     */
    public void getNewsList(final int page) {
        //显示加载
        mView.showLoading();
        //调用m的方法加载数据
        newsListModel.requestList(new NewsListContract.NewsListCallBack() {
            @Override
            public void ok(NewPic newPic, int page) {
                mView.setData(newPic,page);
            }

        },page);
        mView.hideLoading();
    }

}
