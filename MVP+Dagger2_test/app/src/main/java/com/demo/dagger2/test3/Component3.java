package com.demo.dagger2.test3;

import com.demo.dagger2.test2.DaggerTest2Activity;
import com.demo.dagger2.test2.MainModule2;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 * 在Component加上@ActivitySingleton注解
 * @author yzh-t105
 * @time 2018/12/20 14:14
 */
@ActivitySingleton
@Component(modules = Module3.class)
public interface Component3 {
    void inject(DaggerTest3Activity daggerTest3Activity);
}
