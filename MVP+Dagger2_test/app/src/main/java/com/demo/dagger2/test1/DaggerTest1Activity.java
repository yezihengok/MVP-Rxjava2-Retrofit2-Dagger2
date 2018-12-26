package com.demo.dagger2.test1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.demo.mvp.R;

import javax.inject.Inject;

/**
 * @author yzh-t105
 * @time 2018/12/20 12:25
 */
public class DaggerTest1Activity extends AppCompatActivity {

    @Inject
    Users users;// 注入Users实例
    private android.widget.TextView texts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dag);
        this.texts = (TextView) findViewById(R.id.texts);

        //添加依赖关系
        DaggerMainComponent.builder().build().inject(this);

        texts.setText(users.getStr());

    }
}
