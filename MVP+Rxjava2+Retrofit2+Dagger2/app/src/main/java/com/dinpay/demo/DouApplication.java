package com.dinpay.demo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.dinpay.demo.base.dagger2.DaggerAppComponent;
import com.dinpay.demo.utils.CommUtil;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yzh-t105 on 2016/9/20.
 */
public class DouApplication extends Application implements HasActivityInjector {

    private Context mContext;

    private ArrayList<Activity> activities = null; // 存储浏览过的Activity
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Inject
    @Named("default")
    public Retrofit retrofit;

    @Inject
    @Named("DDSQ")
    public Retrofit retrofit2;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.create().inject(this);
        application = this;
       // initOk();
    }

    private void initOk() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                //其他配置
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(CommUtil.BASE_URL) //设置网络请求的Url地址
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 支持RxJava2平台
                .build();


    }

    private static DouApplication application;
    public static DouApplication getInstance()
    {
        if(application==null){
            application=new DouApplication();
        }
        return application;
    }

    public Context getContext() {
        if(mContext==null){
            mContext=getApplicationContext();
        }
        return mContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //CommUtil.getInstance().stopService();
    }

    /*
	 * 将浏览过的activity储存起来
	 */
    public void addActivity(Activity activity)
    {
        if (activities == null)
            activities = new ArrayList<>();
        activities.add(activity);
    }

    /**
     * 结束所有acitvity
     */
    public void removeAllActivity(){
        if (activities != null)
        {
            for (Activity activity : activities)
            {
                if (!activity.isFinishing())
                {
                    //CommUtils.log("Exit:" + activity.toString() + ":isFinishing:" + activity.isFinishing());
                    activity.finish();
                   // CommUtils.log(Constant.TAG, "Exit:" + activity.toString());
                }
            }
            activities.clear();
            activities = null;
        }
    }

}
