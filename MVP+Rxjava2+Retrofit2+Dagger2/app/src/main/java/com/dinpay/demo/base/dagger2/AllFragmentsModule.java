package com.dinpay.demo.base.dagger2;


import com.dinpay.demo.HotListFragment;
import com.dinpay.demo.NewListFragment;
import com.dinpay.demo.module.NewListFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseFragmentComponent.class)
public abstract class AllFragmentsModule {

    @ContributesAndroidInjector(modules = NewListFragmentModule.class)
    abstract NewListFragment contributeNewListFragmentInjector();


//    @ContributesAndroidInjector(modules = HotListFragmentModule.class)
//    abstract HotListFragment contributeHotListFragmentInjector();
@ContributesAndroidInjector(modules = NewListFragmentModule.class)
abstract HotListFragment contributeHotListFragmentInjector();

}

