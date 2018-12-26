package com.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.bean.NewPic;
import com.demo.interfacereq.ApiReq;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yzh-t105
 * @time 2018/12/19 10:36
 */
public class Retrofit2Activity extends AppCompatActivity {
    private android.widget.Button button;
    private android.widget.Button button1;
    private android.widget.TextView text;
    private android.widget.ScrollView mScrollView;
    Retrofit retrofit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
         retrofit = new Retrofit.Builder()
                .baseUrl("http://api.jiefu.tv/app2/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                // .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();

    }


    public void getNewList(){
        ApiReq.NewListApi newListApi=retrofit.create(ApiReq.NewListApi.class);
        Map<String, String> params = new HashMap<>();
        params.put("pageNum","0");
        params.put("pageSize","20");
        newListApi.getNewList(params).enqueue(new Callback<NewPic>() {
            @Override
            public void onResponse(Call<NewPic> call, Response<NewPic> response) {
                response.message();
                if(response.body()!=null){
                    Gson gson=new Gson();
                    String s=gson.toJson(response.body());
                    text.setText(s);
                }

            }

            @Override
            public void onFailure(Call<NewPic> call, Throwable t) {
                System.out.println("连接失败:"+t);
            }
        });
    }
}
