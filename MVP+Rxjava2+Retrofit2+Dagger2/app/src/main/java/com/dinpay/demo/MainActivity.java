package com.dinpay.demo;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;

import com.dinpay.demo.base.dagger2.ActivityScope;
import com.dinpay.demo.base.mvp.BaseActivity;
import com.dinpay.demo.base.mvp.BasePresenter;
import com.dinpay.demo.utils.FragmentPagerAdapter;
import com.dinpay.demo.utils.ImagePresenter;
import com.dinpay.demo.utils.SnackbarUtil;
import com.dinpay.demo.utils.UilImagePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static android.view.View.OnClickListener;


/**
 * Created by yzh on 2016/09/25.
 */
public class MainActivity extends BaseActivity
        implements ViewPager.OnPageChangeListener, OnClickListener
, HasSupportFragmentInjector {

    //Fragment 使用android-digger

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;
    //如果使用普通的Fragment(即android.app.Fragment)，应该实现HasFragmentInjector接口
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;

    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private FragmentPagerAdapter mViewPagerAdapter;

    private int page = 0;
    NewListFragment listFragment;
    HotListFragment hotListFragment;
    private String TAG = this.getClass().getSimpleName();


    private Context context;

    @Inject
    SnackbarUtil snackbarUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        application.addActivity(this);
        context = this;
        // 初始化各种控件
        initViews();


        ///test
        //延迟1s后执行事件
        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                snackbarUtil.show(mViewPager,"test~~~~~~~~~~~~~",-1);
            }
        });

    }

    //我这里的首页Activity只是展示view 没M层（接口请求等业务逻辑）
    // 暂时不需要用mvp模式  mvp模式体现在Fragment里
    @Override
    public BasePresenter createPresenter() {
        return null;
    }
    @Override
    public int getLayoutResId(Bundle savedInstanceState) {
        return 0;
    }
    @Override
    public void initData(Bundle savedInstanceState) {
    }


    private void initViews() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.id_coordinatorlayout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.id_appbarlayout);
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.id_floatingactionbutton);

        mTitles = getResources().getStringArray(R.array.tab_titles);

        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();

        Bundle mBundle = new Bundle();
        mBundle.putInt("flag", 0);
        listFragment = new NewListFragment();
        listFragment.setArguments(mBundle);
        mFragments.add(listFragment);
        hotListFragment=new HotListFragment();
        mFragments.add(hotListFragment);


        configViews();

    }




    private void configViews() {

        // 设置显示Toolbar
        setSupportActionBar(mToolbar);



        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        //mViewPager.setOffscreenPageLimit(mTitles.length);
        // 给ViewPager添加页面动态监听器（为了让Toolbar中的Title可以变化相应的Tab的标题）
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(1);

        //设置TabLayout的模式
        mTabLayout.setTabMode(TabLayout.MODE_FIXED); //Tab平分当前页面
        // mTabLayout.setTabMode(MODE_SCROLLABLE); 可超当前页面多个可滑动
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);

        // 设置FloatingActionButton的点击事件
        mFloatingActionButton.setOnClickListener(this);



    }


    @Override
    public void onPageSelected(int position) {
        mToolbar.setTitle(mTitles[position]);
        page = position;
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {

    }
}

