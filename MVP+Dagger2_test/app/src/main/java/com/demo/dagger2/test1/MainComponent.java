package com.demo.dagger2.test1;

import com.demo.dagger2.test2.DaggerTest2Activity;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 * 创建一个MainComponent接口，接口用@Component(modules = MainModule.class)注解，在此方法里inject相应的Activity。
 * @author yzh-t105
 * @time 2018/12/20 14:14
 */
@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {
    void inject(DaggerTest1Activity daggerTest1Activity);
}
