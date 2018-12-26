package com.demo.dagger2.test3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.mvp.R;
import com.demo.mvp.bean.News;

import javax.inject.Inject;

/**
 *
 *
 * 要注入一个类，可以通过两种方法：
 *
 * 1 在Bean类的构造方法上添加@Inject注解
 * 2 @Module +@Providers注入工厂的方式
 * @Inject 在需要依赖注入的地方使用，配合注入方法将要注入的对象注入
 * @Module 用次注解的类，提供@Provides注解的以provide开头的方法，具体提供依赖
 *
 *
 *
 * @author yzh-t105
 * @time 2018/12/20 12:25
 */
public class DaggerTest3Activity extends AppCompatActivity {

    @Inject
    News news1;
    @Inject
    News news2;

    private TextView texts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dag);
        this.texts = (TextView) findViewById(R.id.texts);

        //添加依赖关系
        //第一种方式
        DaggerComponent3.create().inject(this);
        //第二种方式
        //DaggerMainComponent2.builder().build().inject(this);

        texts.append("这里可以看到 news1 news2 对象值相等+\n");
        texts.append(news1.toString());
        texts.append("\n"+news2.toString());

    }
}
