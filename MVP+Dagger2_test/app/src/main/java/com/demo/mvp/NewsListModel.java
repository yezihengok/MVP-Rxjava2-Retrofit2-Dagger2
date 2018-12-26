package com.demo.mvp;

import com.demo.mvp.bean.News;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * M层 (Model具体实现类)
 * @author yzh-t105
 * @time 2018/12/20 10:14
 */
public class NewsListModel implements NewsListContract.NewsListModelImp{

    @Override
    public void requestList(NewsListContract.NewsListCallBack callBack) {
        //todo 。。请求接口数据

        News news=new News("标题","这是一条新闻","2018-12-20 10:57:14");
        News newss=new News("标题s","这是一条新闻s","2018-12-20");
        List<News> list=new ArrayList<>();
        list.add(news);
        list.add(newss);
        callBack.ok(list);
    }
}
