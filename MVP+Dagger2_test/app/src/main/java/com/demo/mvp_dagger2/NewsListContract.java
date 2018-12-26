package com.demo.mvp_dagger2;

import com.demo.mvp.base.BaseView;
import com.demo.mvp.bean.News;

import java.util.List;


/**
 *
 * mvp要写太多接口了 用一个契约类把三个都写在一起
 * @author yzh-t105
 * @time 2018/12/20 10:09
 */
public class NewsListContract {
    //model 接口
    interface NewsListModel {
        void requestList(NewsListCallBack callBack);
    }

    //view 接口
    interface NewsListView extends BaseView {
        void showList(List<News> newsList);
        void showLoading();
        void hideLoading();
    }

    //数据回调
    interface NewsListCallBack {
        void ok(List<News> newsList);
    }

}
