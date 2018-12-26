package com.dinpay.demo.mvp.presenter;


import com.dinpay.demo.base.mvp.BasePresenter;
import com.dinpay.demo.bean.NewPic;
import com.dinpay.demo.mvp.contract.HotListContract;
import com.dinpay.demo.mvp.model.HotListModel;

import javax.inject.Inject;


/**
 * p层 处理具体业务逻辑 并持有M与V 的对象   解耦View，让activity看起简洁 不处理具体业务
 * @author yzh-t105
 * @time 2018/12/20 10:23
 */
public class HotListPresenter extends BasePresenter<HotListContract.HotListViewImp> {
    //M
//    private HotListModel hotListModel;
//    public HotListPresenter() {
//        hotListModel=new HotListModel();
//    }

    private HotListModel hotListModel;
    @Inject
    public HotListPresenter(HotListModel hotListModel) {
        this.hotListModel=hotListModel;
    }


    /**
     * 模拟请求数据
     */
    public void gethotList(int page) {
        //显示加载
        mView.showLoading();
        //调用m的方法加载数据
        hotListModel.requestList(new HotListContract.HotListCallBack() {
            @Override
            public void ok(NewPic newPic, int page) {
                mView.setData(newPic,page);
            }
        }, page);
        mView.hideLoading();
    }

}
