package com.demo.mvp_dagger2.base_dagger;

import com.demo.mvp_dagger2.MyApplication;
import com.demo.mvp_dagger2.NewsListPresenter;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * @author yzh-t105
 * @time 2018/12/24 10:31
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,AppModule.class, AllActivitysModule.class,AllFragmentsModule.class})
public interface AppComponent {
    void inject(MyApplication application);
    void inject(NewsListPresenter NewsListPresenter);
}
