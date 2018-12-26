package com.demo.mvp_dagger2.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.mvp.R;
import com.demo.mvp.base.BaseFragment;
import com.demo.mvp.base.BasePresenter;
import com.demo.mvp.bean.News;
import com.demo.mvp_dagger2.NewsListPresenter;

import javax.inject.Inject;


public class Fragment02 extends BaseFragment {

    @Inject
    News news;

    @Inject
    NewsListPresenter newsListPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_dag;
    }

    @Override
    public void initData(Bundle state, View parentView) {

        TextView texts = parentView.findViewById(R.id.texts);
        texts.setText(news.toString());
    }

    @Override
    public NewsListPresenter createPresenter() {
        return newsListPresenter;
    }

}
