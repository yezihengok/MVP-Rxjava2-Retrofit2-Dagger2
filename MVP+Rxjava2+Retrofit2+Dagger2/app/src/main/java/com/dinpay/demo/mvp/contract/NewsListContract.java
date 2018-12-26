package com.dinpay.demo.mvp.contract;


import com.dinpay.demo.base.mvp.BaseView;
import com.dinpay.demo.bean.NewPic;

/**
 *
 * mvp要写太多接口了 用一个契约类把三个都写在一起
 * @author yzh-t105
 * @time 2018/12/20 10:09
 */
public class NewsListContract {
    //model 接口
    public interface NewsListModelImp {
        void requestList(NewsListCallBack callBack,int page);
    }

    //view 接口
    public interface NewsListViewImp extends BaseView {
        void setData(NewPic newPic, int page);
        void showLoading();
        void hideLoading();
    }

    //数据回调
    public interface NewsListCallBack {
        void ok(NewPic newPic, int page);
    }

}
