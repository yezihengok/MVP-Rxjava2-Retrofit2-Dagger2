package com.demo.dagger2.test3;

import com.demo.mvp.bean.News;

import dagger.Module;
import dagger.Provides;

/**
 *
 * 在Module上加上@ActivitySingleton注解
 *
 * @author yzh-t105
 * @time 2018/12/20 17:27
 */
@Module
public class Module3 {

    @ActivitySingleton
    @Provides
    News provideNews(){
        return new News();
    }
}
