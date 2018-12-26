package com.demo.mvp_dagger2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.dagger2.test1.DaggerTest1Activity;
import com.demo.mvp.R;
import com.demo.mvp.base.BaseActivity;
import com.demo.mvp.bean.News;
import com.demo.mvp_dagger2.fragment.Fragment01;
import com.demo.mvp_dagger2.fragment.Fragment02;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
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
public class MVP_Dagger2FragmentActivity extends BaseActivity<NewsListPresenter> implements
 View.OnClickListener,HasSupportFragmentInjector {

    private TextView texts;

//    @Inject
//    NewsListPresenter newsListPresenter;
    @Inject
    StringUtils stringUtils;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    ViewPager viewPager;
    ArrayList<Fragment> list;



    //如果使用普通的Fragment(即android.app.Fragment)，应该实现HasFragmentInjector接口
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {//v4 Fragment
        return fragmentInjector;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }


    @Override
    public NewsListPresenter createPresenter() {
         //return newsListPresenter;
        return null;
    }

    @Override
    public int getLayoutResId(Bundle savedInstanceState) {
        return R.layout.activity_dagger_fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {


//        texts = findViewById(R.id.texts);
//        //请求数据
//        mPresenter.getNewsList();


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        // 初始化ViewPager对象中所需要的数据
        list = new ArrayList<Fragment>();
        list.add(new Fragment01());
        list.add(new Fragment02());


        viewPager.setAdapter(new MyAdapterState(getSupportFragmentManager()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                toActivity(DaggerTest1Activity.class);
                break;
            case R.id.button2:
                break;
            case R.id.button3:

                break;
        }
    }


    class MyAdapterState extends FragmentStatePagerAdapter {
        public MyAdapterState(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            return super.instantiateItem(arg0, arg1);
        }

    }


}
