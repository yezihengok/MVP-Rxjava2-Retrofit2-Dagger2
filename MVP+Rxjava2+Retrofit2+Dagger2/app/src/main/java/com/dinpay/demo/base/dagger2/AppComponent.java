package com.dinpay.demo.base.dagger2;

import com.dinpay.demo.DouApplication;
import com.dinpay.demo.mvp.presenter.HotListPresenter;
import com.dinpay.demo.mvp.presenter.NewsListPresenter;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 *
 * 在Component加上@ActivityScope
 * @author yzh-t105
 * @time 2018/12/24 10:31
 */
@ActivityScope
@Singleton
@Component(modules = {AndroidInjectionModule.class,AppModule.class
        , AllActivitysModule.class,AllFragmentsModule.class})
public interface AppComponent {
    void inject(DouApplication application);

}
