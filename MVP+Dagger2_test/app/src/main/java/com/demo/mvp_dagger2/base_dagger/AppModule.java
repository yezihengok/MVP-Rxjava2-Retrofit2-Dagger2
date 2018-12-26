package com.demo.mvp_dagger2.base_dagger;

import com.demo.mvp_dagger2.NewsListModel;
import com.demo.mvp_dagger2.NewsListPresenter;
import com.demo.mvp_dagger2.StringUtils;

import dagger.Module;
import dagger.Provides;

/**
 * @author yzh-t105
 * @time 2018/12/20 18:04
 */
@Module
public class AppModule {
    public AppModule() {
    }

   // @Singleton
    @Provides
    static StringUtils provideStringUtils(){
        return new StringUtils();
    }
    @Provides
    NewsListModel provideNewsListModel(){
        return new NewsListModel();
    }

    @Provides
    NewsListPresenter provideNewsListPresenter(NewsListModel newsListModel){
        return new NewsListPresenter(newsListModel);
    }

}
