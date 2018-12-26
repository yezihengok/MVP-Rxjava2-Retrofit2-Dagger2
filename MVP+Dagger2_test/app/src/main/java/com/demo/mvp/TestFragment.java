package com.demo.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.demo.mvp.base.BaseFragment;
import com.demo.mvp.bean.News;

import java.util.List;


/**
 * @author yzh-t105
 * @time 2018/12/20 10:59
 */
public class TestFragment extends BaseFragment<NewsListPresenter> implements NewsListContract.NewsListViewImp {

    private TextView texts;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    public void initData(Bundle savedInstanceState,View parentView) {
        texts=parentView.findViewById(R.id.texts);

        //请求数据
        mPresenter.getNewsList();
    }

    //p层处理好了 展示数据
    @Override
    public void showList(List<News> newsList) {
        texts.setText(newsList.get(0).getContent());
    }

    @Override
    public NewsListPresenter createPresenter() {
        return new NewsListPresenter();
    }
}
