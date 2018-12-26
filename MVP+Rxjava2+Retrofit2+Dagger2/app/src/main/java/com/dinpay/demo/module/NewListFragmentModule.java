package com.dinpay.demo.module;

import com.dinpay.demo.mvp.model.HotListModel;
import com.dinpay.demo.mvp.model.NewsListModel;
import com.dinpay.demo.mvp.presenter.HotListPresenter;
import com.dinpay.demo.mvp.presenter.NewsListPresenter;
import com.dinpay.demo.utils.CommUtil;
import com.dinpay.demo.utils.UilImagePresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yzh-t105
 * @time 2018/12/24 17:23
 */
@Module
public abstract class NewListFragmentModule {

    @Provides
    static UilImagePresenter provideUilImagePresenter() {
        return new UilImagePresenter();
    }



    /*@Provides
    NewsListModel provideNewsListModel() {
        return new NewsListModel();
    }

    @Provides
    HotListModel provideHotListModel(){
        return new HotListModel();
    }*/

    @Provides
    static NewsListPresenter provideNewsListPresenter(NewsListModel newsListModel) {
        return new NewsListPresenter(newsListModel);
    }

    @Provides
    static HotListPresenter provideHotListPresenter(HotListModel hotListModel){
        return new HotListPresenter(hotListModel);
    }
}
