package com.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.bean.NewPic;
import com.demo.interfacereq.ApiReq;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit2+ Rxjava2
 *
 * @author yzh-t105
 * @time 2018/12/19 10:36
 */
public class Retrofit2Rxjava2Activity extends AppCompatActivity {
    private Button button;
    private Button button1;
    private TextView text;
    private ScrollView mScrollView;
    Retrofit retrofit;
    public static final String TAG = "RxJava-Retrofit__";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);
        this.mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        this.text = (TextView) findViewById(R.id.text);
        this.button1 = (Button) findViewById(R.id.button1);
        this.button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewList();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewList1();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.jiefu.tv/app2/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 支持RxJava2平台
                .build();

    }


    public void getNewList() {
        ApiReq.NewListApi api = retrofit.create(ApiReq.NewListApi.class);

//-----------<--Retrofit2 传统方式 ->> 采用Call<..>接口 对 发送请求 进行封装

        api.getByType("6",10).enqueue(new Callback<NewPic>() {
            @Override
            public void onResponse(Call<NewPic> call, Response<NewPic> response) {
                response.message();
                if (response.body() != null) {
                    Gson gson = new Gson();
                    String s = gson.toJson(response.body());
                    text.append(s);
                    text.append("\n\n===================这是retrofit2传统方式获取的================\n");
                }else{
                    Log.e(TAG,"ERROR=="+ response.message());
                }
            }
            @Override
            public void onFailure(Call<NewPic> call, Throwable t) {
                System.out.println("连接失败:" + t);
            }
        });

    }

    public void getNewList1() {

        ApiReq.ByTagApi api2 = retrofit.create(ApiReq.ByTagApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("typeId","6");
        params.put("pageNum","10");

// ----------------<--Retrofit2+ RxJava 方式 ->> 采用Observable<...>形式
        Observable<NewPic> observable=api2.getByType("getByType.html",params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewPic>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewPic value) {
                        Gson gson = new Gson();
                        String s = gson.toJson(value);
                        text.append(s);
                        text.append("\n\n=====================这是retrofit2+rxjava2方式获取的================\n");
                        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败"+e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");
                    }
                });
    }

}
