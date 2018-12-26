package com.dinpay.demo.base.dagger2;



import com.dinpay.demo.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author yzh-t105
 * @time 2018/12/24 10:43
 */
@Module(subcomponents = BaseActivityComponent.class)
public abstract class AllActivitysModule {


    @ContributesAndroidInjector(modules = AppModule.class)
    abstract MainActivity contributeMainActivityInjector();



    // 新增一个Activity
//    @ContributesAndroidInjector(modules = AppModule2.class)
//    abstract DaggerAndroid2Activity contributeDaggerAndroid2ActivityInjector();
}

