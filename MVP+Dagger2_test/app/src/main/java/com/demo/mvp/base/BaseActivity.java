package com.demo.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import dagger.android.AndroidInjection;

/**
 * 抽取activity的公共部分
 * @author yzh-t105
 * @time 2018/12/20 09:39
 */
public abstract class BaseActivity<P extends BasePresenter>extends AppCompatActivity implements BaseView {
    //P层主导
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);//Dagger2 使用。mvp demo 忽略此条
        super.onCreate(savedInstanceState);
        try {
            int layoutResId=getLayoutResId(savedInstanceState);
            if(layoutResId!=0){
                setContentView(layoutResId);
                //创建P对象
                mPresenter = createPresenter();
                if(mPresenter!=null){
                    //绑定View
                    mPresenter.attachView(this);//Activity创建时 就把 V 丢到P层里
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        initData(savedInstanceState);


    }

   // public abstract void initInject();

    //每个activity都需要创建Presenter
    public abstract P createPresenter();

    //每个activity都需要创建setLayoutId
    @LayoutRes
    public abstract int getLayoutResId(Bundle savedInstanceState);

    public abstract void initData(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        //把所有的数据销毁释放资源
        if(mPresenter!=null){
            mPresenter.onDestroy();
            mPresenter=null;
        }
        super.onDestroy();
    }

    /**
     *模拟显示加载对话框
     */
    @Override
    public void showLoading() {
        Toast.makeText(this, "加载中", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void hideLoading() {
        Toast.makeText(this, "加载结束", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    public <T extends FragmentActivity> void toActivity(Class<T> aClass){
        Intent intent=new Intent(this,aClass);
        startActivity(intent);
    }


}
