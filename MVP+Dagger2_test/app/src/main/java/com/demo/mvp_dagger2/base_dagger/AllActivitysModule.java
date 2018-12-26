package com.demo.mvp_dagger2.base_dagger;

import com.demo.mvp.MainActivity;
import com.demo.mvp_dagger2.DaggerFragmentActProModule;
import com.demo.mvp_dagger2.MVP_Dagger2Activity;
import com.demo.mvp_dagger2.MVP_Dagger2FragmentActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author yzh-t105
 * @time 2018/12/24 10:43
 */
@Module(subcomponents = BaseActivityComponent.class)
public abstract class AllActivitysModule {



    @ContributesAndroidInjector(modules = AppModule.class)
    abstract MVP_Dagger2Activity contributeMVP_Dagger2ActivityInjector();
    @ContributesAndroidInjector(modules = AppModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector(modules = DaggerFragmentActProModule.class)
    abstract MVP_Dagger2FragmentActivity contributeMVP_Dagger2FragmentActivityInjector();

    // 新增一个Activity
//    @ContributesAndroidInjector(modules = AppModule2.class)
//    abstract DaggerAndroid2Activity contributeDaggerAndroid2ActivityInjector();
}

