package com.demo.mvp_dagger2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.demo.dagger2.test1.DaggerTest1Activity;
import com.demo.dagger2.test2.DaggerTest2Activity;
import com.demo.dagger2.test3.DaggerTest3Activity;
import com.demo.mvp.R;
import com.demo.mvp.base.BaseActivity;
import com.demo.mvp.bean.News;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


/*Dagger2开发Android的问题:

摘自 https://www.jianshu.com/p/2ec39d8b7e98

        //添加依赖关系
        DaggerMainComponent.builder().build().inject(this);

        1.上面的代码要复制粘贴到所有的Activity中，这就会给以后重构代码造成麻烦，
        你的团队越来越多的人复制上面的代码块，会有越来越少的人知道这块代码的真正用途。

        2.更重要的是，它要求请求注射类型（FrombulationActivity ）知道它的注射器，
        既即使它可以通过接口而不是具体的类型完成，但是它打破了依赖注入的核心原则：
        一个类不应该知道任何关于它是如何注入的。

        以上，所以基于Dagger2的，适用于Android开发的Dagger2-Android*/



public class MVP_Dagger2Activity extends BaseActivity<NewsListPresenter> implements
        NewsListContract.NewsListView, View.OnClickListener {

    private TextView texts;

    @Inject
    NewsListPresenter newsListPresenter;
    @Inject
    StringUtils stringUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }



    @Override
    public NewsListPresenter createPresenter() {
         //return new NewsListPresenter();
        return newsListPresenter;
    }

    @Override
    public int getLayoutResId(Bundle savedInstanceState) {
        return R.layout.activity_mains;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


        texts = findViewById(R.id.texts);
        //请求数据
        mPresenter.getNewsList();


        findViewById(R.id.button).setOnClickListener(this);
    }

    //p层处理好了 展示数据
    @Override
    public void showList(List<News> newsList) {
        texts.setText(newsList.get(0).getContent() + stringUtils.hideString("18672945250", 4));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                toActivity(MVP_Dagger2FragmentActivity.class);
                break;
            case R.id.button2:
                break;
            case R.id.button3:

                break;
        }
    }
}
