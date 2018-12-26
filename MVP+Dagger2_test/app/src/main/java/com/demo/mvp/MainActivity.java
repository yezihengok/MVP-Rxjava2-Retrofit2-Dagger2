package com.demo.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.demo.dagger2.test1.DaggerTest1Activity;
import com.demo.dagger2.test2.DaggerTest2Activity;
import com.demo.dagger2.test3.DaggerTest3Activity;
import com.demo.mvp.base.BaseActivity;
import com.demo.mvp.bean.News;
import com.demo.mvp_dagger2.MVP_Dagger2Activity;

import java.util.List;




/**
 *
 * 核心思想
 * MVP把Activity中的UI逻辑抽象成View接口，
 * 把业务逻辑抽象成Presenter接口，
 * Model类还是原来的Model。
 * 1. 分离了视图逻辑和业务逻辑，降低了耦合
 * 2. Activity只处理生命周期的任务，代码变得更加简洁
 * 3. 视图逻辑和业务逻辑分别抽象到了View和Presenter的接口中去，提高代码的可阅读性
 * 4. Presenter被抽象成接口，可以有多种具体的实现，所以方便进行单元测试
 * 5. 业务逻辑抽到Presenter中去，避免后台线程引用着Activity导致Activity的资源无法被系统回收从而引起内存泄露和OOM
 */



/**
 *   V层 只需展示数据、没有具体的逻辑操作 完成解耦
 */
public class MainActivity extends BaseActivity<NewsListPresenter> implements
        NewsListContract.NewsListViewImp,View.OnClickListener {

    private TextView texts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }



    @Override
    public NewsListPresenter createPresenter() {
        return new NewsListPresenter();
    }

    @Override
    public int getLayoutResId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        texts=findViewById(R.id.texts);

        //请求数据
        mPresenter.getNewsList();


        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
    }

    //p层处理好了 展示数据
    @Override
    public void showList(List<News> newsList) {
        texts.setText(newsList.get(0).getContent());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                toActivity(DaggerTest1Activity.class);
                break;
            case R.id.button2:
                toActivity(DaggerTest2Activity.class);
                break;
            case R.id.button3:
                toActivity(DaggerTest3Activity.class);
                break;
            case R.id.button4:
                toActivity(MVP_Dagger2Activity.class);
                break;
        }
    }
}
