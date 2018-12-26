package com.dinpay.demo.base.dagger2;

import com.dinpay.demo.bean.NewPic;
import com.dinpay.demo.mvp.model.HotListModel;
import com.dinpay.demo.mvp.model.NewsListModel;
import com.dinpay.demo.utils.CommUtil;
import com.dinpay.demo.utils.SnackbarUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * 主Module
 * @author yzh-t105
 * @time 2018/12/20 18:04
 */
@Module
public class AppModule {
    public AppModule() { }


    @Singleton
    @Provides
    @Named("default")
    Retrofit provideRetrofit(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                //其他配置
                .build();

        return new Retrofit.Builder()
                .baseUrl(CommUtil.BASE_URL) //设置网络请求的Url地址
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 支持RxJava2平台
                .build();
    }

    @Singleton
    @Provides
    @Named("DDSQ")
    Retrofit provideRetrofit2(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                //其他配置
                .build();

        return new Retrofit.Builder()
                .baseUrl(CommUtil.DDSQ) //设置网络请求的Url地址
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 支持RxJava2平台
                .build();
    }


    @ActivityScope
    @Provides
    NewPic provideNewPic(){
        return new NewPic();
    }

    @Provides
     SnackbarUtil provideSnackbarUtil(){
        return new SnackbarUtil();
    }

    @Provides
    NewsListModel provideNewsListModel() {
        return new NewsListModel();
    }

    @Provides
    HotListModel provideHotListModel(NewPic newPic){
        return new HotListModel(newPic);
    }

}
