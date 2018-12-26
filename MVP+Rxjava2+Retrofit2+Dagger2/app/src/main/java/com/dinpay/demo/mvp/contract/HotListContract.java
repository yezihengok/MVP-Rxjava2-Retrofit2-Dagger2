package com.dinpay.demo.mvp.contract;


import com.dinpay.demo.base.mvp.BaseView;
import com.dinpay.demo.bean.NewPic;

/**
 *
 * mvp要写太多接口了 用一个契约类把三个都写在一起
 * @author yzh-t105
 * @time 2018/12/20 10:09
 */
public abstract class HotListContract {


    //model 接口
    public interface HotListModelImp {
        void requestList(HotListCallBack callBack,int page);
    }

    //view 接口
    public interface HotListViewImp extends BaseView {
        void setData(NewPic newPic, int page);
        void showLoading();
        void hideLoading();
    }

    //数据回调
    public interface HotListCallBack {
        void ok(NewPic newPic, int page);
    }

}
